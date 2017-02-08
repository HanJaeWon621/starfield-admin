/*
 * Emp.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import java.util.Date;

import kr.co.starfield.model.vo.EmpVo;

/**
 *  Emp model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class Emp extends BaseModel {
	public int empno    ; 
	public String ename ;
	public String job   ;
	public int mgr      ;
	public Date hiredate;
	public int sal      ;
	public int comm     ;
	public int deptno   ;
	
	@Override
	public EmpVo toVo() {
		EmpVo vo = new EmpVo();
		
		vo.empno    = this.empno    ;
		vo.ename    = this.ename    ;
		vo.job      = this.job      ;
		vo.mgr      = this.mgr      ;
		vo.hiredate = this.hiredate ;
		vo.sal      = this.sal      ;
		vo.comm     = this.comm     ;
		vo.deptno   = this.deptno   ;
				
		return vo;
	}
}
