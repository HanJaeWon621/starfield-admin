/*
 * HomeController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.model.vo.EmpVo;
import kr.co.starfield.service.HomeService;

/**
 *  WEB(페이지이동) 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

@Controller
public class HomeController {

	@Autowired
	HomeService homeService;
	
	private static final Logger ll = LoggerFactory.getLogger(HomeController.class);


	/*home*/
	/*@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView root(EmpVo empVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		String rdsTime = homeService.getRDSTime();
		
		mv.addObject("result", rdsTime);
		mv.setViewName("home");
		
		return mv;
	}*/
	
	/*사원 목록*/
	@RequestMapping(value = "/emps", method = RequestMethod.GET)
	public String empListPage() {
		
		return "emp/emps";
	}
	
	/*사원등록 페이지*/
	@RequestMapping(value = "/emps/post", method = RequestMethod.GET)
	public String empRegPage() {
		
		return "emp/empPost";
	}
	
	/*사원수정 페이지*/
	@RequestMapping(value = "/emps/{empno}", method = RequestMethod.GET)
	public String empDetail(@PathVariable(value="empno") String empno) {
		
		return "emp/empPut";
	}
	
	/*Holiday*/
	@RequestMapping(value = "/holiday/holiday", method = RequestMethod.GET)
	public ModelAndView holiday(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("bcn_cd", "01");
		mv.setViewName("/holiday/holiday");
		
		return mv;
	}
	
}
