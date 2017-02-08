package kr.co.starfield.httpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CampaignHttpClient {

	@Value("${lbs.server.scheme}")
    String lbsScheme;
    @Value("${lbs.server.host}")
    String lbsHost;
    @Value("${lbs.server.port}")
    String lbsPort;

	private static final Logger log = LoggerFactory.getLogger(AwsSnsRelayHttpClient.class);

	public String post(String path, String param, String access_token) throws URISyntaxException, BaseException {
		
		URIBuilder builder = new URIBuilder()
				.setScheme(lbsScheme)
				//.setHost(lbsHost+":"+lbsPort) //.setPort()
				.setHost(lbsHost) //.setPort()
				.setPath(path);
		
		URI uri = builder.build(); 
		CloseableHttpClient http = HttpClients.createDefault();
		StringBuffer responseData = new StringBuffer();
		
		String jsonData;
		int httpStatus;
		
		try {
			log.debug("uri : " + uri);
			HttpPost httpPost = new HttpPost(uri);
			//httpGet.setHeader("access_token", lbsBranchAccessToken);
			StringEntity params =new StringEntity(param);
			httpPost.setHeader("Content-Type", "application/json");
			if(!StringUtils.isEmpty(access_token)){
				httpPost.setHeader("request_type", "2");
				httpPost.setHeader("access_token", access_token);
			}
			httpPost.setEntity(params);
			CloseableHttpResponse response = http.execute(httpPost);
			httpStatus = response.getStatusLine().getStatusCode();
			
			if(httpStatus != HttpStatus.SC_OK) {
				BaseException be = new BaseException(ErrorCode.Scenario.TOKEN_NOT_FOUND_DATA);
				throw be;
			}
			
			try {
				HttpEntity res = response.getEntity();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(res.getContent(), Charset.forName("UTF-8")));
				
				String buffer = null;
				while((buffer=br.readLine()) != null ) {
					responseData.append(buffer);
				}
		
				log.debug("responseData : " + responseData);
				
				jsonData = responseData.toString();
				
			}finally{
				response.close();
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			BaseException be = new BaseException(ErrorCode.Scenario.TOKEN_NOT_FOUND_DATA);
			throw be;
		}finally{
			try {
				if(http != null) {
					http.close();
				}
			} catch (IOException e) {
				BaseException be = new BaseException(ErrorCode.Scenario.TOKEN_NOT_FOUND_DATA);
				throw be;
			}
		}

		return jsonData;
	}

	
	public String put(String path, String param, String access_token) throws URISyntaxException, BaseException {
		
		URIBuilder builder = new URIBuilder()
				.setScheme(lbsScheme)
				.setHost(lbsHost+":"+lbsPort) //.setPort()
				.setPath(path);
		
		URI uri = builder.build(); 
		CloseableHttpClient http = HttpClients.createDefault();
		StringBuffer responseData = new StringBuffer();
		
		String jsonData;
		int httpStatus;
		
		try {
			log.debug("uri : " + uri);
			HttpPut httpPut = new HttpPut(uri);
			//httpGet.setHeader("access_token", lbsBranchAccessToken);
			StringEntity params =new StringEntity(param);
			httpPut.setHeader("content-type", "application/json");
			if(!StringUtils.isEmpty(access_token)){
				httpPut.setHeader("request_type", "2");
				httpPut.setHeader("access_token", access_token);
			}
			httpPut.setEntity(params);
			CloseableHttpResponse response = http.execute(httpPut);
			httpStatus = response.getStatusLine().getStatusCode();
			
			if(httpStatus != HttpStatus.SC_OK) {
				BaseException be = new BaseException(ErrorCode.Scenario.TOKEN_NOT_FOUND_DATA);
				throw be;
			}
			
			try {
				HttpEntity res = response.getEntity();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(res.getContent(), Charset.forName("UTF-8")));
				
				String buffer = null;
				while((buffer=br.readLine()) != null ) {
					responseData.append(buffer);
				}
				
				log.debug("responseData : " + responseData);
				
				jsonData = responseData.toString();
				
			}finally{
				response.close();
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			BaseException be = new BaseException(ErrorCode.Scenario.TOKEN_NOT_FOUND_DATA);
			throw be;
		}finally{
			try {
				if(http != null) {
					http.close();
				}
			} catch (IOException e) {
				BaseException be = new BaseException(ErrorCode.Scenario.TOKEN_NOT_FOUND_DATA);
				throw be;
			}
		}
		
		return jsonData;
	}
	
	
	public String delete(String path, String param, String access_token) throws URISyntaxException, BaseException {
		
		URIBuilder builder = new URIBuilder()
				.setScheme(lbsScheme)
				.setHost(lbsHost+":"+lbsPort) //.setPort()
				.setPath(path)
				.setParameter("campaign_id", param);
		URI uri = builder.build(); 
		CloseableHttpClient http = HttpClients.createDefault();
		StringBuffer responseData = new StringBuffer();
		
		String jsonData;
		int httpStatus;
		
		try {
			log.debug("uri : " + uri);
			HttpDelete httpDelete = new HttpDelete(uri);
			StringEntity params =new StringEntity(param);
			httpDelete.setHeader("content-type", "application/json");
			if(!StringUtils.isEmpty(access_token)){
				httpDelete.setHeader("request_type", "2");
				httpDelete.setHeader("access_token", access_token);
			}
			//httpDelete.set
			CloseableHttpResponse response = http.execute(httpDelete);
			httpStatus = response.getStatusLine().getStatusCode();
			
			if(httpStatus != HttpStatus.SC_OK) {
				BaseException be = new BaseException(ErrorCode.Scenario.TOKEN_NOT_FOUND_DATA);
				throw be;
			}
			
			try {
				HttpEntity res = response.getEntity();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(res.getContent(), Charset.forName("UTF-8")));
				
				String buffer = null;
				while((buffer=br.readLine()) != null ) {
					responseData.append(buffer);
				}
				
				log.debug("responseData : " + responseData);
				
				jsonData = responseData.toString();
				
			}finally{
				response.close();
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			BaseException be = new BaseException(ErrorCode.Scenario.TOKEN_NOT_FOUND_DATA);
			throw be;
		}finally{
			try {
				if(http != null) {
					http.close();
				}
			} catch (IOException e) {
				BaseException be = new BaseException(ErrorCode.Scenario.TOKEN_NOT_FOUND_DATA);
				throw be;
			}
		}
		
		return jsonData;
	}


	public Map<String, Object> jsonToMap(String json) throws URISyntaxException, BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mp = new ObjectMapper();
		try {
			map = mp.readValue(json, new TypeReference<HashMap<String, Object>>() {});
		} catch (JsonParseException e) {
			BaseException be = new BaseException(ErrorCode.Scenario.SCENARIO_JSON_PARSE_EXCEPTION);
			throw be;
		} catch (JsonMappingException e) {
			BaseException be = new BaseException(ErrorCode.Scenario.SCENARIO_JSON_PARSE_EXCEPTION);
			throw be;
		} catch (IOException e) {
			BaseException be = new BaseException(ErrorCode.Scenario.SCENARIO_JSON_PARSE_EXCEPTION);
			throw be;
		}
		
		return map;
	}
	
}
