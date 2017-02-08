package kr.co.starfield.service;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonMappingException;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCodeOld;
import kr.co.starfield.common.Utils;
import kr.co.starfield.mapper.HomeMapper;
import kr.co.starfield.model.BasicLoginParam;
import kr.co.starfield.model.Dept;
import kr.co.starfield.model.Emp;
import kr.co.starfield.model.EmpDept;
import kr.co.starfield.model.EmpListResult;
import kr.co.starfield.model.LoginResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.DeptVo;
import kr.co.starfield.model.vo.EmpVo;


/**
 *  REST 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

@Service
public class HomeService {

	@Autowired
	private HomeMapper homeMapper;
	
	private static final Logger ll = LoggerFactory.getLogger(HomeService.class);
	
	/*sample*/
	public String getRDSTime() {
		String result  = homeMapper.getRDSTime();
		return result; 
	}
	
	/*sample*/
	public EmpVo getUser(EmpVo empVo) {
		EmpVo vo  = homeMapper.getUser(empVo);
		ll.info("ename => " + empVo.ename);
		ll.info("result => " + vo.ename);
		
		return vo; 
	}
	
	/*sample*/
	public ArrayList<EmpVo> getAllUser() {
		ArrayList<EmpVo> result  = homeMapper.getAllUser();
		ll.info("result => " + result);
		
		return result; 
	}

	/*	
	 * 1. 	서비스를 다른 서비스에서 호출하는 경우에도 명확하게 예외를 알수있도록 서비스에서 예외처리를 한다.
	 * 2. 	별도의 Checked Exception 필요한 경우 추가하여 사용한다.(ex : 아래 DataAccessException 및 JsonMappingException)
	 * 3. 	에러 로그는 로그관리 차원에서 e.printStackTrace 보다 log4j형태로 남긴다. (ex : log.error("Exception", e))
	 * 4.	예외발생 시, 트랜잭션 및 롤백적용 (ex : @Transactional(rollbackFor = Exception.class))
	 */
	
	/**
	 * 사원목록 가져오기
	 * @param offset
	 * @param limit
	 * @param q
	 * @return
	 */
	public EmpListResult getEmps(String _offset, String _limit, String q) throws BaseException, Exception {
		
		EmpListResult result = new EmpListResult();
		
		try {
			
			int offset = Integer.parseInt(_offset);
			int limit = Integer.parseInt(_limit);
			
			EmpVo vo = new EmpVo();
			vo.offset = offset;
			vo.limit = limit;
			
			Utils.Str.qParser(vo, q);
			
			int totCnt = homeMapper.getTotCntEmp();
			
			// set paging - basic page limit
			Paging paging = new Paging(offset, limit, totCnt);
			
			result.totCnt = totCnt;
			result.paging = paging;
			
			
			// 일반 for문을 통한 변환 작업
			
			
			
			EmpVo[] voList = homeMapper.getEmps(vo);
			result.empList = new Emp[voList.length];
			
			for(int i=0; i < voList.length; i++) {
				result.empList[i] = voList[i].toModel();
			}
			
			
						
			
			// 람다 forEach문을 사용한 변환 작업 => 불필요한 ArrayList를 선언해야 한다.
			
			/*
			
			ArrayList<Emp> empList = new ArrayList<Emp>();
			
			Arrays.stream(mapper.getEmps(vo)).forEach((EmpVo empVo) -> {
				empList.add(empVo.toModel());
			});
			
			result.empList = (Emp[]) empList.toArray();
			
			*/

			
			// 람다 최종
			// 스트림에서 map함수를 통해 "Emp[] 스트림"을 가져온후 그것을 다시 Emp[] 배열형태로 반환
			//result.empList = Arrays.stream(homeMapper.getEmps(vo)).map(EmpVo::toModel).toArray(Emp[]::new);

		} catch (DataAccessException e) {
			ll.error("DataAccessException", e);
			BaseException be = new BaseException(ErrorCodeOld.DB_FAIL, e.getMessage(), ErrorCodeOld.HTTP_DB_ERROR);
			throw be;

		} catch (Exception e) {
			if(e instanceof NullPointerException) {
				ll.error("NullPointerException", e);
				BaseException be = new BaseException(ErrorCodeOld.NULL_POINT_FAIL, e.getMessage(), ErrorCodeOld.HTTP_INTERNAL_SERVER_ERROR);
				throw be;

			} else if(e instanceof JsonMappingException) {
				ll.error("JsonMappingException", e);
				BaseException be = new BaseException(ErrorCodeOld.JSONMAPPING_FAIL, e.getMessage(), ErrorCodeOld.HTTP_JSONMAPPING_ERROR);
				throw be;

			} else {
				ll.error("Exception", e);
				throw e;
			}
		}

		return result;
	}

	
	/**
	 * 부서 목록
	 * @return
	 */
	public Dept[] getDepts() throws BaseException, Exception {
		
		Dept[] deptList = null;
		
		try {
		
			DeptVo[] deptVoList = homeMapper.getDepts();
			deptList = new Dept[deptVoList.length];
			
			for(int i=0; i < deptVoList.length; i++) {
				deptList[i] = deptVoList[i].toModel();
			}
			
			// 람다형식으로 변경
			//deptList = Arrays.stream(homeMapper.getDepts()).map(DeptVo::toModel).toArray(Dept[]::new);	
		
		} catch (DataAccessException e) {
			ll.error("DataAccessException", e);
			BaseException be = new BaseException(ErrorCodeOld.DB_FAIL, e.getMessage(), ErrorCodeOld.HTTP_DB_ERROR);
			throw be;

		} catch (Exception e) {
			if(e instanceof NullPointerException) {
				ll.error("NullPointerException", e);
				BaseException be = new BaseException(ErrorCodeOld.NULL_POINT_FAIL, e.getMessage(), ErrorCodeOld.HTTP_INTERNAL_SERVER_ERROR);
				throw be;

			} else if(e instanceof JsonMappingException) {
				ll.error("JsonMappingException", e);
				BaseException be = new BaseException(ErrorCodeOld.JSONMAPPING_FAIL, e.getMessage(), ErrorCodeOld.HTTP_JSONMAPPING_ERROR);
				throw be;

			} else {
				ll.error("Exception", e);
				throw e;
			}
		}
		
		return deptList;
	}

	
	/**
	 * 사원 등록
	 * @param vo
	 * @return 
	 */
	public SimpleResult regEmp(EmpVo vo) throws BaseException, Exception {
		SimpleResult result = new SimpleResult();
		
		try {
			
			homeMapper.regEmp(vo);

		} catch (DataAccessException e) {
			ll.error("DataAccessException", e);
			BaseException be = new BaseException(ErrorCodeOld.DB_FAIL, e.getMessage(), ErrorCodeOld.HTTP_DB_ERROR);
			throw be;

		} catch (Exception e) {
			if(e instanceof NullPointerException) {
				ll.error("NullPointerException", e);
				BaseException be = new BaseException(ErrorCodeOld.NULL_POINT_FAIL, e.getMessage(), ErrorCodeOld.HTTP_INTERNAL_SERVER_ERROR);
				throw be;

			} else if(e instanceof JsonMappingException) {
				ll.error("JsonMappingException", e);
				BaseException be = new BaseException(ErrorCodeOld.JSONMAPPING_FAIL, e.getMessage(), ErrorCodeOld.HTTP_JSONMAPPING_ERROR);
				throw be;

			} else {
				ll.error("Exception", e);
				throw e;
			}
		}
		
		return result;
	}

	
	/**
	 * 사원 삭제
	 * @param empno
	 * @return
	 */
	public SimpleResult deleteEmp(String empno) throws BaseException, Exception {
		SimpleResult result = new SimpleResult();
		
		try {
			
			homeMapper.deleteEmp(empno);
			
		} catch (DataAccessException e) {
			ll.error("DataAccessException", e);
			BaseException be = new BaseException(ErrorCodeOld.DB_FAIL, e.getMessage(), ErrorCodeOld.HTTP_DB_ERROR);
			throw be;

		} catch (Exception e) {
			if(e instanceof NullPointerException) {
				ll.error("NullPointerException", e);
				BaseException be = new BaseException(ErrorCodeOld.NULL_POINT_FAIL, e.getMessage(), ErrorCodeOld.HTTP_INTERNAL_SERVER_ERROR);
				throw be;

			} else if(e instanceof JsonMappingException) {
				ll.error("JsonMappingException", e);
				BaseException be = new BaseException(ErrorCodeOld.JSONMAPPING_FAIL, e.getMessage(), ErrorCodeOld.HTTP_JSONMAPPING_ERROR);
				throw be;

			} else {
				ll.error("Exception", e);
				throw e;
			}
		}
		
		return result;
	}
	
	
	/**
	 * 사원 조회
	 * @param empno
	 * @return
	 */
	public EmpDept getEmp(String empno) throws BaseException, Exception {
		EmpDept empDept = null;
		
		try {
			
			empDept = homeMapper.getEmp(empno);
			
		} catch (DataAccessException e) {
			ll.error("DataAccessException", e);
			BaseException be = new BaseException(ErrorCodeOld.DB_FAIL, e.getMessage(), ErrorCodeOld.HTTP_DB_ERROR);
			throw be;
		
		} catch (Exception e) {
			if(e instanceof NullPointerException) {
				ll.error("NullPointerException", e);
				BaseException be = new BaseException(ErrorCodeOld.NULL_POINT_FAIL, e.getMessage(), ErrorCodeOld.HTTP_INTERNAL_SERVER_ERROR);
				throw be;

			} else if(e instanceof JsonMappingException) {
				ll.error("JsonMappingException", e);
				BaseException be = new BaseException(ErrorCodeOld.JSONMAPPING_FAIL, e.getMessage(), ErrorCodeOld.HTTP_JSONMAPPING_ERROR);
				throw be;

			} else {
				ll.error("Exception", e);
				throw e;
			}
		}
		
		return empDept;
	}

	
	/**
	 * 사원 수정
	 * @param vo
	 * @return 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyEmp(EmpDept vo) throws BaseException, Exception {
		SimpleResult result  = new SimpleResult();

		try {
			
			//Transactional test
/*			EmpVo empvo = new EmpVo();
			empvo.ename = "HANJO";
			empvo.job= "NINJA";
			empvo.mgr= 7698;
			empvo.sal = 10000;
			empvo.comm = 9999;
			empvo.deptno = 30;
			mapper.regEmp(empvo);
			ll.info("HANJO 사원 등록");*/
			
			//Transactional test
/*			empvo.ename = "KIM";
			empvo.job= "KIM_gane";
			empvo.mgr= 7698;
			empvo.sal = 10000;
			empvo.comm = 9999;
			empvo.deptno = 30;
			mapper.regEmp(empvo);
			ll.info("KIM사원 등록");*/
			
			homeMapper.modifyEmp(vo);
			ll.info("사원 수정");
			
			//Transactional test
/*			empvo.ename = "LEE";
			empvo.job= "LEE_gane";
			empvo.mgr= 7698;
			empvo.sal = 10000;
			empvo.comm = 9999;
			empvo.deptno = 30;
			mapper.regEmp(empvo);
			ll.info("LEE사원 등록");*/

			//exception test
/*			int a = 0;
			a = a / 0;*/
			
		} catch (DataAccessException e) {	//SQLException
			ll.error("DataAccessException", e);
			BaseException be = new BaseException(ErrorCodeOld.DB_FAIL, e.getMessage(), ErrorCodeOld.HTTP_DB_ERROR);
			throw be;
		
		} catch (Exception e) {
			if(e instanceof NullPointerException) {
				ll.error("NullPointerException", e);
				BaseException be = new BaseException(ErrorCodeOld.NULL_POINT_FAIL, e.getMessage(), ErrorCodeOld.HTTP_INTERNAL_SERVER_ERROR);
				throw be;

			} else if(e instanceof JsonMappingException) {
				ll.error("JsonMappingException", e);
				BaseException be = new BaseException(ErrorCodeOld.JSONMAPPING_FAIL, e.getMessage(), ErrorCodeOld.HTTP_JSONMAPPING_ERROR);
				throw be;

			} else {
				ll.error("Exception", e);
				throw e;
			}
		}
		
		return result;
	}
	
	
	/**
	 * 사원 로그인
	 * @param BasicLoginParam
	 * @return 
	 */
	public LoginResult loginCheck(BasicLoginParam param, HttpSession session) throws BaseException, Exception {
		LoginResult result = new LoginResult();
		
		try {
		
			Emp emp= homeMapper.loginCheck(param);
			
			if(emp.ename == null) {
				result.code = -1;
				result.desc = "유효하지 않는 계정입니다.";
				
			} else if(String.valueOf(emp.empno) != null) {
				result.code = 0;
				result.desc = "loginSuccess!!";
				
				//login Success
				session.setAttribute("login_id", emp.ename);
				session.setAttribute("job", emp.job);
				
			} else {
				result.code = -2; //password fail
				result.desc = "유효하지 않는 계정입니다.";
			}
		
		} catch (DataAccessException e) {	//SQLException
			ll.error("DataAccessException", e);
			BaseException be = new BaseException(ErrorCodeOld.DB_FAIL, e.getMessage(), ErrorCodeOld.HTTP_DB_ERROR);
			throw be;
		
		} catch (Exception e) {
			if(e instanceof NullPointerException) {
				ll.error("NullPointerException", e);
				BaseException be = new BaseException(ErrorCodeOld.NULL_POINT_FAIL, e.getMessage(), ErrorCodeOld.HTTP_INTERNAL_SERVER_ERROR);
				throw be;

			} else if(e instanceof JsonMappingException) {
				ll.error("JsonMappingException", e);
				BaseException be = new BaseException(ErrorCodeOld.JSONMAPPING_FAIL, e.getMessage(), ErrorCodeOld.HTTP_JSONMAPPING_ERROR);
				throw be;

			} else {
				ll.error("Exception", e);
				throw e;
			}
		}
		
		return result;
	}
	
}
