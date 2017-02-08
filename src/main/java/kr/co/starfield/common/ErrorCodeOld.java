/*
 * ErrorCode.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
 */

package kr.co.starfield.common;

/**
 *  ErrorCode
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class ErrorCodeOld {

	//인증(Authentication)
	public static int	AUTH_SUCCESS = 10000;
	public static int	AUTH_FAIL  = 10001;
	public static int	AUTH_SESSION_EXPIRATION  = 10002;

	//SERVER
	public static int	SERVER_SUCCESS  = 20000;
	public static int	SERVER_FAIL  = 20001;

	//쿠폰(coupon)
	public static int	COUPON_SUCCESS  = 30000;
	public static int	COUPON_FAIL = 30001;

	//DB
	public static int	DB_SUCCESS  = 40000;
	public static int	DB_FAIL  = 40001;
	
	//DATA
//	public static int	NOT_FOUND_DATA = 50000;
	
	//etc.
	public static int	JSONMAPPING_FAIL= 90000;
	public static int	NULL_POINT_FAIL= 90001;
	public static int	UNKNOWN  = 99999;
	
	//httpStatus
	public static int	HTTP_OK= 200;
	public static int	HTTP_DB_ERROR = 450;
	public static int	HTTP_JSONMAPPING_ERROR = 460;
	public static int	HTTP_INTERNAL_SERVER_ERROR = 500;
	public static int	HTTP_UNKNOWN= 520;

}
