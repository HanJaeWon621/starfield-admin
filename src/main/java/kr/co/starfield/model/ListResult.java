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
 *  ListResult model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author 김윤희
 * @version	1.0,
 * @see
 * @date 2016.04.14
 */

public class ListResult<T> {
	public Paging paging;
	public List<T> list = new ArrayList<>();
	public String srch_cnt;
	public int tot_cnt;
}
