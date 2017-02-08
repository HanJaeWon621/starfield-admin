/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.BasicLoginParam;
import kr.co.starfield.model.Emp;
import kr.co.starfield.model.EmpDept;
import kr.co.starfield.model.vo.DeptVo;
import kr.co.starfield.model.vo.EmpVo;

/**
 *  HomeMapper interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public interface HomeMapper {

	//현재시간(sample)
	public String getRDSTime();

	//특정멤버(sample)
	EmpVo getUser(EmpVo baseVo);
	
	//전체 멤버(sample)
	ArrayList<EmpVo> getAllUser();
	

	// 사원 총 명수
	public int getTotCntEmp();
	
	// 사원 목록
	public EmpVo[] getEmps(EmpVo vo);

	// 부서 목록
	public DeptVo[] getDepts();

	// 사원 조회
	public EmpDept getEmp(String empno);

	// 사원 등록
	public void regEmp(EmpVo vo);

	// 사원 수정
	public void modifyEmp(EmpDept vo);
	
	// 사원 삭제
	public void deleteEmp(String empno);

	// 로그인
	public Emp loginCheck(BasicLoginParam Param);

}
