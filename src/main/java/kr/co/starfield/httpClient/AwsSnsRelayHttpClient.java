package kr.co.starfield.httpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
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
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;

@Component
public class AwsSnsRelayHttpClient {

    @Value("${awssns.relay.host}")
    String host;
    @Value("${awssns.relay.port}")
    int port;
    @Value("${awssns.relay.context}")
    String context;

	private static final Logger log = LoggerFactory.getLogger(AwsSnsRelayHttpClient.class);

	public String post(String path, Map<String, Object> paramMap) throws BaseException {
		String result = "";
		String jsonParams = mapToJsonString(paramMap);
		
		log.debug("request parmas : "+jsonParams);
		
		StringEntity params = new StringEntity(jsonParams, "UTF-8");
		
		HttpPost httpPost = null;
		
		try {
			httpPost = new HttpPost(makeUri(path));
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.setEntity(params);
			result = req(httpPost);
		} catch (URISyntaxException e) {
			log.error(e.getMessage());
			throw new BaseException(ErrorCode.AwsSnsRelayHttpClient.AWS_SNS_RELAY_URI_SYNTAX_EXCEPTION);
		}

		return result;
	}

	public String put(String path, Map<String, Object> paramMap) throws BaseException {
		String result = "";
		String jsonParams = mapToJsonString(paramMap);
		
		log.debug("request parmas : "+jsonParams);
		
		StringEntity params = new StringEntity(jsonParams, "UTF-8");
		
		HttpPut httpPut = null;
		
		try {
			httpPut = new HttpPut(makeUri(path));
			httpPut.addHeader("Content-Type", "application/json");
			httpPut.setEntity(params);
			result = req(httpPut);
		} catch (URISyntaxException e) {
			log.error(e.getMessage());
			throw new BaseException(ErrorCode.AwsSnsRelayHttpClient.AWS_SNS_RELAY_URI_SYNTAX_EXCEPTION);
		}
		
		return result;
	}

	public String delete(String path) throws BaseException {
		String result = "";
		
		HttpDelete httpDelete= null;
		try {
			httpDelete = new HttpDelete(makeUri(path));
			httpDelete.addHeader("Content-Type", "application/json");
			
			result = req(httpDelete);
		} catch (URISyntaxException e) {
			log.error(e.getMessage());
			throw new BaseException(ErrorCode.AwsSnsRelayHttpClient.AWS_SNS_RELAY_URI_SYNTAX_EXCEPTION);
		}
		
		return result;
		
	}
	
	private String req(HttpRequestBase httpRequest) throws BaseException {

		CloseableHttpClient http = HttpClients.createDefault();
		StringBuffer responseData = new StringBuffer();
		
		int httpStatus;
	
		CloseableHttpResponse response = null;
		
		try {
			response = http.execute(httpRequest);
			httpStatus = response.getStatusLine().getStatusCode();
	
			if(httpStatus != HttpStatus.SC_OK) {
				log.error("NOT HttpStatus.SC_OK : ["+httpStatus+"]");
				throw new BaseException(ErrorCode.AwsSnsRelayHttpClient.AWS_SNS_RELAY_HTTP_CLIENT_UNKNOWN_EXCEPTION);
			}
	
			HttpEntity res = response.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(res.getContent(), Charset.forName("UTF-8")));
	
			String buffer = null;
			while((buffer=br.readLine()) != null ) {
				responseData.append(buffer);
			}
	
			log.debug("responseData : " + responseData);

		} catch (ClientProtocolException e) {
			log.error(e.getMessage());
			throw new BaseException(ErrorCode.AwsSnsRelayHttpClient.AWS_SNS_RELAY_CLIENT_PROTOCOL_EXCEPTION);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new BaseException(ErrorCode.AwsSnsRelayHttpClient.AWS_SNS_RELAY_HTTP_CLIENT_IO_EXCEPTION);
		}finally{
			try {
				if(response != null) {
					response.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			try {
				if(http != null) {
					http.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		
		return responseData.toString();
	}
	
	private URI makeUri(String path) throws URISyntaxException {
		URIBuilder builder = new URIBuilder()
	            .setScheme("http")
	            .setHost(host)
	            .setPort(port)
	            .setPath(context + path);
	    URI uri = builder.build();
	    
	    log.debug("request uri : " + uri);
		return uri;
	}
	
	private String mapToJsonString(Map<String, Object> paramMap) throws BaseException {
		String jsonParams = "";
		try {
			jsonParams = new ObjectMapper().writeValueAsString(paramMap);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new BaseException(ErrorCode.AwsSnsRelayHttpClient.AWS_SNS_RELAY_JSON_PARSE_EXCEPTION);
		}
		return jsonParams;
	}
}
