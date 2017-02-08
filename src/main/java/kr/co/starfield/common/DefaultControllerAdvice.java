/*
 * DefaultControllerAdvice.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.starfield.model.ExceptionResult;

/**
 *  common Exception 
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

@ControllerAdvice
public class DefaultControllerAdvice {
	@Autowired
	ErrorLogger errorLogger;
	
	private static final Logger ll = LoggerFactory.getLogger(DefaultControllerAdvice.class);

	@ExceptionHandler(BaseException.class)
	@ResponseBody
	public ExceptionResult handleBaseException(HttpServletRequest req, HttpServletResponse res, HttpSession session, BaseException ex) {
		ExceptionResult result = new ExceptionResult();
		
		result.code = ex.getErrCode();
		result.errCode = ex.getErrCode();
		result.errMsg = ex.getErrMsg();
		res.setStatus(ex.getHttpStatusCode());
		ll.debug("==== result ", result);
		ll.error("==== ex ", ex);

		errorLogger.log(ex);
				
		return result; 
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ExceptionResult handleException(HttpServletRequest req, HttpServletResponse res, HttpSession session, Exception ex) {
		ExceptionResult result = new ExceptionResult();

		result.code = ErrorCode.Common.UNKNOWN.getCode();
		result.errMsg = ex.getMessage();
		result.errCode = ErrorCode.Common.UNKNOWN.getCode();
		res.setStatus(ErrorCode.Common.UNKNOWN.getHttpStatusCode().getCode());
		ll.debug("==== result ", result);
		ll.error("==== ex ", ex);
		
		errorLogger.log(ErrorCode.Severity.LEVEL_4, ErrorCode.Common.UNKNOWN.getCode(), "UNKNOWN", "UNKNOWN", ex.getMessage(), ex);
		
		return result;
	}
	
}
