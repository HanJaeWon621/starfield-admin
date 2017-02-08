package kr.co.starfield.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kr.co.starfield.service.ATNT001Service;

@Component
public class ErrorLogger {
	
	private Logger ll = LoggerFactory.getLogger(ErrorLogger.class);

	private final static String PROTOCOL = "http://";
	private final static String AGENT = "JavaLoggingClient";
	private final static String URI = "/logging/errors";
	private final static String MIME_TYPE = "application/x-www-form-urlencoded";
	
	@Value("${server.isLocal}")
	private boolean isLocal;
	
	@Value("${server.name}")
	private String serverName;
	
	@Value("${logger.server.ip}")
	private String loggingServerIp;
	
	@Value("${logger.server.port}")
	private int loggingServerPort;

	public void log(int severity, int errorCode, 
			String moduleName, String methodName, 
			String errorMessage, Throwable err, String serverName) {
		
		if(isLocal || severity == 0) {
			return;
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("severity", Integer.toString(severity));
		map.put("err_cd", Integer.toString(errorCode));
		map.put("datetime", Utils.Dt.toStringDateTime(new Date()));
		map.put("server_nm", serverName == null ? this.serverName : serverName);
		map.put("module_nm", moduleName);
		map.put("method_nm", methodName);
		map.put("err_msg", errorMessage);
		map.put("stack_trace", this.getStackTrace(err));
		
		try {
			send(map);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void log(int severity, int errorCode, 
			String moduleName, String methodName, 
			String errorMessage, Throwable err) {
		
		log(severity, errorCode, 
				moduleName, methodName, 
				errorMessage, err, this.serverName);
		
	}
	
	public void log(BaseException ex) {
		log(ex.getSeverityCode(), ex.getErrCode(), ex.getModuleNm(), ex.getMethodNm()
				, ex.getAwssnsErrMsg() == null ? ex.getErrMsg() : ex.getAwssnsErrMsg(), ex, ex.getServerNm());
	}
	
	private String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
	
	private String makeParam(Map<String, String> params) throws UnsupportedEncodingException {
		
		String key = null, value = null;
		StringBuffer sb = new StringBuffer();
		for(Map.Entry<String, String> entry : params.entrySet()) {
			key = entry.getKey();
			value = entry.getValue() == null ? "" : entry.getValue();
			
			sb.append(key);
			sb.append("=");
			sb.append(URLEncoder.encode(value, "UTF-8"));
			sb.append("&");
		}
		
		return sb.toString();
		
	}
	
	private void send(Map<String, String> params) throws Exception {
		
		String url = ErrorLogger.PROTOCOL + this.loggingServerIp + ":" 
				+ this.loggingServerPort + ErrorLogger.URI; 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", ErrorLogger.AGENT);
		con.setRequestProperty("Content-Type", ErrorLogger.MIME_TYPE);
		

		String urlParameters = this.makeParam(params);
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		ll.info("Sending 'POST' request to URL : " + url);
		ll.info("Post parameters : " + urlParameters);
		ll.info("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		ll.info(response.toString());
		
	}
	
	
}
