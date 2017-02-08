package kr.co.starfield.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.ErrorLogger;
import kr.co.starfield.httpClient.AwsSnsRelayHttpClient;
import kr.co.starfield.model.TargetArnInfo;
import kr.co.starfield.model.vo.SMBR005Vo;

@Component
public class AwsSnsRelayHelper {

	@Value("${awssns.rest.uri.prefix}")
    String REST_URI_PREFIX;
    @Value("${awssns.create.endpoint.uri}")
    String CREATE_ENDPOINT_URI;
    @Value("${awssns.create.endpoint.method}")
    String CREATE_ENDPOINT_METHOD;
    @Value("${awssns.update.endpoint.uri}")
    String UPDATE_ENDPOINT_URI;
    @Value("${awssns.update.endpoint.method}")
    String UPDATE_ENDPOINT_METHOD;
    @Value("${awssns.delete.endpoint.uri}")
    String DELETE_ENDPOINT_URI;
    @Value("${awssns.delete.endpoint.method}")
    String DELETE_ENDPOINT_METHOD;
    @Value("${awssns.topic.subscribe.uri}")
    String SUBSCRIBE_URI;
    @Value("${awssns.topic.subscribe.method}")
    String SUBSCRIBE_METHOD;
    @Value("${awssns.topic.unsubscribe.uri}")
    String UNSUBSCRIBE_URI;
    @Value("${awssns.topic.unsubscribe.uri}")
    String UNSUBSCRIBE_METHOD;
    @Value("${awssns.publish.uri}")
    String PUBLISH_URI;
    @Value("${awssns.publish.method}")
    String PUBLISH_METHOD;
    
	private Logger log = LoggerFactory.getLogger(AwsSnsRelayHelper.class);
	
	@Autowired
	AwsSnsRelayHttpClient awsSnsRelayHttpClient;

	@Autowired
	ErrorLogger errorLogger;
	
	public TargetArnInfo createEndpoint(SMBR005Vo vo) throws BaseException {
		Map<String,Object> params = new HashMap<>();
		params.put("dvic_id", vo.dvic_id);
		params.put("push_token", vo.push_token);
		params.put("alram_yn", "Y".equals(vo.alram_yn));
		params.put("pltf_type", vo.pltf_type);
		params.put("method", "POST");
		
		return createEndpoint(params);
	}
	
	public TargetArnInfo createEndpoint(Map<String, Object> params) throws BaseException {
		String result = req(REST_URI_PREFIX+CREATE_ENDPOINT_URI, CREATE_ENDPOINT_METHOD, params);
		
		Map<String, Object> resultMap = jsonStringToMap(result);

		TargetArnInfo targetArnInfo = resultCheck(resultMap);
		
		return targetArnInfo;

	}
	
	public TargetArnInfo createEndpoint(SMBR005Vo newVo, SMBR005Vo oldVo) throws BaseException {
		SMBR005Vo vo = new SMBR005Vo();
		vo.dvic_id = oldVo.dvic_id;
		vo.push_token = newVo.push_token != null ? newVo.push_token : oldVo.push_token;
		vo.alram_yn = newVo.alram_yn != null ?  newVo.alram_yn : oldVo.alram_yn;
		vo.pltf_type = oldVo.pltf_type;
		
		return createEndpoint(vo);

	}
	
	public TargetArnInfo updateEndpoint(SMBR005Vo newVo, SMBR005Vo oldVo, boolean isPushTokenChange, boolean isAlramYnChange) throws BaseException {
		Map<String,Object> params = new HashMap<>();
		params.put("dvic_id", oldVo.dvic_id);
		params.put("push_token", newVo.push_token != null ? newVo.push_token : oldVo.push_token);
		params.put("alram_yn", "Y".equals(newVo.alram_yn != null ?  newVo.alram_yn : oldVo.alram_yn));
		params.put("glob_arn", oldVo.glob_arn);
		params.put("pltf_type", oldVo.pltf_type);
		params.put("isPushTokenChange", isPushTokenChange);
		params.put("isAlramYnChange", isAlramYnChange);
		params.put("method", "PUT");
		
		return updateEndpoint(params, oldVo.end_arn);

	}
	
	public TargetArnInfo updateEndpoint(Map<String, Object> params, String end_arn) throws BaseException {
		String result = "";
		
		try {
			result = req(REST_URI_PREFIX+UPDATE_ENDPOINT_URI+"/"+URLEncoder.encode(end_arn, "UTF-8"), UPDATE_ENDPOINT_METHOD, params);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
			throw new BaseException(ErrorCode.AwsSnsRelayHttpClient.AWS_SNS_RELAY_URI_ENCODING_EXCEPTION);
		}
		
		Map<String, Object> resultMap = jsonStringToMap(result);
		
		TargetArnInfo targetArnInfo = resultCheck(resultMap);
		
		return targetArnInfo;
	}
	
	private TargetArnInfo resultCheck(Map<String, Object> resultMap) throws BaseException {
		TargetArnInfo targetArnInfo = new TargetArnInfo();
		
		if(resultMap.get("errCode") == null) {
			targetArnInfo.end_arn = (String) resultMap.get("end_arn");
			targetArnInfo.glob_arn = (String) resultMap.get("glob_arn");
		} else {
			BaseException be = new BaseException((int) resultMap.get("errCode")
					, (String) resultMap.get("errMsg")
					, (int) ErrorCode.HttpStatusCode.HTTP_OK.getCode()
					, (int) resultMap.get("severityCode")
					, (String) resultMap.get("moduleNm")
					, (String) resultMap.get("methodNm")
					, (String) resultMap.get("serverName")
					, (String) resultMap.get("stackTrace")
					, (String) resultMap.get("awssnsErrMsg"));
			
			@SuppressWarnings("unchecked")
			Map<String, Object> deviceInfoMap = (Map<String, Object>) resultMap.get("deviceInfo");
			
			if(deviceInfoMap == null) {
				throw be;
			}
			
			int retry_cnt = (int) deviceInfoMap.get("retry_cnt");
			String method = (String) deviceInfoMap.get("method");
			
			
			boolean isConnectionError = false;
			
			if(resultMap.get("awssnsErrMsg") != null) {
				isConnectionError = ((String) resultMap.get("awssnsErrMsg")).contains("Connection reset") || ((String) resultMap.get("awssnsErrMsg")).contains("Unable to execute HTTP request");
			}
			
			if(retry_cnt < 4 && isConnectionError) {
				errorLogger.log(be);
				
				String end_arn = (String) deviceInfoMap.get("end_arn");
				deviceInfoMap.put("retry_cnt", retry_cnt+1);
				if(method.equals("PUT")) {
					return updateEndpoint(deviceInfoMap, end_arn);
				} else if(method.equals("POST")) {
					return createEndpoint(deviceInfoMap);
				}
			} else {
				throw be;
			}

		}
		
		return targetArnInfo;
	}
	
	private Map<String, Object> jsonStringToMap(String json) throws BaseException {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> resultMap = new HashMap<>();
		
		try {
			if(json.contains("errCode")) {
				resultMap = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});
			} else {
				resultMap = mapper.readValue(json, new TypeReference<Map<String, String>>(){});	
			}
		} catch (JsonParseException e) {
			log.error(e.getMessage());
			throw new BaseException(ErrorCode.AwsSnsRelayHttpClient.AWS_SNS_RELAY_JSON_PARSE_EXCEPTION);
		} catch (JsonMappingException e) {
			log.error(e.getMessage());
			throw new BaseException(ErrorCode.AwsSnsRelayHttpClient.AWS_SNS_RELAY_JSON_PARSE_EXCEPTION);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new BaseException(ErrorCode.AwsSnsRelayHttpClient.AWS_SNS_RELAY_IO_EXCEPTION);
		}
		
		return resultMap;
	}
	
	private String req(String uri, String method, Map<String, Object> params) throws BaseException {

		String result = "";
		String upperCaseMethod = method.toUpperCase();
		
		if("POST".equals(upperCaseMethod)) {
			result = awsSnsRelayHttpClient.post(uri, params);
		}else if("PUT".equals(upperCaseMethod)) {
			result = awsSnsRelayHttpClient.put(uri, params);
		}else if("DELETE".equals(upperCaseMethod)) {
			result = awsSnsRelayHttpClient.delete(uri);
		}
		
		return result;
	}

}
