/*
 * HomeRestController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.model.Dept;
import kr.co.starfield.model.Emp;
import kr.co.starfield.model.EmpDept;
import kr.co.starfield.model.EmpListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.HomeService;

/**
 *  REST 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author xxx
 * @version 1.0,
 * @see
 * @date 2016.04.21
 */

/*@Controller*/
@RestController
@RequestMapping("/rest")
public class HomeRestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(HomeRestController.class);
	
	@Autowired
	HomeService homeService;

	/**
	 * 사원 목록 가져오기
	 * @return
	 */
	@RequestMapping(value = "/emps"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
/*	@ResponseBody*/
	public EmpListResult getEmps(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
			, @RequestParam(value="q", required=false) String q 
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info(String.format("offset is %s", offset));
		ll.info(String.format("limit is %s", limit));
		ll.info(String.format("q is %s", q));
		
		EmpListResult result = homeService.getEmps(offset, limit, q);
	
		return result;
	}
	
	
	/**
	 * 부서 목록 가져오기
	 * @return
	 */
	@AuthRequired
	@RequestMapping(value = "/depts"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public Dept[] getDepts(HttpServletRequest req, HttpServletResponse res, HttpSession session	) throws Exception {
		
		this.initHandler(req, res);
		
		Dept[] deptList = homeService.getDepts();

		return deptList;
	}
	
	
	/**
	 * 사원 등록
	 * @return
	 */
	@RequestMapping(value = "/emps"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult regEmp(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody Emp emp ) throws Exception {
		
		this.initHandler(req, res);
		SimpleResult result = homeService.regEmp(emp.toVo());
		
		return result;
	}
	
	
	/**
	 * 사원 삭제
	 * @return
	 */
	@RequestMapping(value = "/emps/{empno}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult deleteEmp(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="empno") String empno
	) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info(String.format("delete target empno is %s", empno));
		
		SimpleResult result = homeService.deleteEmp(empno);
		
		return result;
	}
	
	
	/**
	 * 사원 조회
	 * @return
	 */
	@RequestMapping(value = "/emps/{empno}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public EmpDept getEmp(
			HttpServletRequest req, HttpServletResponse res, HttpSession session, 
			@PathVariable(value="empno") String empno
	) throws Exception {
		
		ll.info(String.format("empno id is %s", empno));
		
		EmpDept empDept = homeService.getEmp(empno);
		
		return empDept;
	}

	
	/**
	 * 사원 수정
	 * @return
	 */
	@RequestMapping(value = "/emps"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult modifyEmp(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody EmpDept empDept ) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info(String.format("empno id is %s", empDept.empno));
		
		SimpleResult result = homeService.modifyEmp(empDept);
				
		return result;
	}
	
}
