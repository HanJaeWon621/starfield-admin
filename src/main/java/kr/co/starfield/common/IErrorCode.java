package kr.co.starfield.common;

import kr.co.starfield.common.ErrorCode.HttpStatusCode;

public interface IErrorCode {
	int getCode();
	String getMessage();
	HttpStatusCode getHttpStatusCode();
	int getSeverityCode();
}
