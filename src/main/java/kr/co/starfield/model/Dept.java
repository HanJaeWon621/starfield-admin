/*
 * Dept.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import kr.co.starfield.model.vo.DeptVo;

/**
 *  Dept model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class Dept extends BaseModel {
	public int deptno;
	public String dname;
	public String loc;
	
	@Override
	public DeptVo toVo() {
		DeptVo vo = new DeptVo();
		
		vo.deptno = this.deptno;
		vo.dname  = this.dname ;
		vo.loc    = this.loc   ;
		
		return vo;
	}  
	
}
