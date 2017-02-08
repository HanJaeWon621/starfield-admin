/*
 * ExceptionResult.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

/**
 *  Exception model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class ExceptionResult {
	public int code;
	public int errCode;
	public String errMsg;
	
	@Override
	public String toString() {
		return "ExceptionResult [code=" + code + ", errCode=" + errCode + ", errMsg=" + errMsg + "]";
	}
}
