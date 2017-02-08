/*
 * BaseModel.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import kr.co.starfield.model.vo.BaseVo;

/**
 *  BaseModel model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public abstract class BaseModel {
	public String user;
	public int tot_cnt;
	abstract public BaseVo toVo();
}
