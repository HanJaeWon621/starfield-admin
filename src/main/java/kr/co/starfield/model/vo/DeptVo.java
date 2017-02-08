/*
 * DeptVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import kr.co.starfield.model.Dept;

/**
 *  DEPT 테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class DeptVo extends BaseVo {

	public int deptno;
	public String dname;
	public String loc;
	
	public Dept toModel() {
		Dept dept = new Dept();
		
		dept.deptno = this.deptno;
		dept.dname  = this.dname ;
		dept.loc    = this.loc   ;
		
		return dept;
	}
}
