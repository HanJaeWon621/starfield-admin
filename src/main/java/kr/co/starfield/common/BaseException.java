/*
 * BaseException.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.common;

/**
 * Exception 커스터마이징을 위한 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class BaseException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private int errCode = 0;
	private String errMsg = null;
	private int httpStatusCode = 0;
	private int severityCode = 0;
	private String moduleNm = null;
	private String methodNm = null;
	private String serverNm = null;
	private String stackTraceString = null;
	private String awssnsErrMsg;
	
	public BaseException(int errCode, String errMsg, int httpStatusCode, int severityCode, String moduleNm,
			String methodNm, String serverNm, String stackTraceString, String awssnsErrMsg) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.httpStatusCode = httpStatusCode;
		this.severityCode = severityCode;
		this.moduleNm = moduleNm;
		this.methodNm = methodNm;
		this.serverNm = serverNm;
		this.stackTraceString = stackTraceString;
		this.awssnsErrMsg = awssnsErrMsg;
	}

	public BaseException(int errCode, String errMsg, int httpStatusCode, int severityCode){
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.httpStatusCode = httpStatusCode;
		this.severityCode = severityCode;
	}
	
	public BaseException(int errCode, String errMsg, int httpStatusCode){
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.httpStatusCode = httpStatusCode;
	}
	
	public BaseException(IErrorCode code){
		super();
		this.errCode = code.getCode();
		this.errMsg = code.getMessage();
		this.httpStatusCode = code.getHttpStatusCode().getCode();
		this.severityCode = code.getSeverityCode();
		
		final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
		final String s = e.getClassName();
		this.moduleNm = e.getClassName();
		this.methodNm = e.getMethodName();
		
	}
	
	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	
	public void setSeverityCode(int severityCode) {
		this.severityCode = severityCode;
	}
	
	public int getSeverityCode() {
		return severityCode;
	}

	public String getModuleNm() {
		return moduleNm;
	}

	public void setModuleNm(String moduleNm) {
		this.moduleNm = moduleNm;
	}

	public String getMethodNm() {
		return methodNm;
	}

	public void setMethodNm(String methodNm) {
		this.methodNm = methodNm;
	}

	public String getServerNm() {
		return serverNm;
	}

	public void setServerNm(String serverNm) {
		this.serverNm = serverNm;
	}

	public String getStackTraceString() {
		return stackTraceString;
	}

	public void setStackTraceString(String stackTraceString) {
		this.stackTraceString = stackTraceString;
	}

	public String getAwssnsErrMsg() {
		return awssnsErrMsg;
	}

	public void setAwssnsErrMsg(String awssnsErrMsg) {
		this.awssnsErrMsg = awssnsErrMsg;
	}
	
	
	
}
