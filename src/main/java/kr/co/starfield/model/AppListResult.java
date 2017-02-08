/*
 * EmpListResult.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  AppListResult model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author dhlee
 * @version	1.0,
 * @see
 * @date 2016.04.14
 */

public class AppListResult<T> {
	public int code;
	public String desc;
	public String extra;
	public List<T> list = new ArrayList<>();
}
