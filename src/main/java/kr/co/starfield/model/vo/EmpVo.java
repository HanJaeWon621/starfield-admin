/*
 * EmpVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.model.Emp;

/**
 *  EMP 테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class EmpVo extends BaseVo {
	public int empno; 
	public String ename;
	public String job;
	public int mgr;
	public Date hiredate;
	public int sal;
	public int comm;
	public int deptno;
	
	public Emp toModel() {
		Emp emp = new Emp();
		
		emp.empno = this.empno;
		emp.ename = this.ename;
		emp.job = this.job;
		emp.mgr = this.mgr;
		emp.hiredate = this.hiredate;
		emp.sal = this.sal;
		emp.comm = this.comm;
		emp.deptno = this.deptno;
				
		return emp;
	}
}
