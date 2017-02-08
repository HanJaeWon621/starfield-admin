package kr.co.starfield.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.common.BaseException;
import kr.co.starfield.service.AACT001Service;

/**
 * 스타필드 운영정보 관리
 *
 * Copyright Copyright (c) 2016
 * Company 
 *
 * @author EEZY
 * @version	1.0,
 * @see
 * @date 2016.09.06
 */

@Controller
@RequestMapping("/")  
public class AOPR001WebController {

	private Logger ll = LoggerFactory.getLogger(AOPR001WebController.class);
	
	@Autowired
    AACT001Service aact001service;
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/stores/operation"
			, method = RequestMethod.GET)
	public ModelAndView getOperation(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("stores/AOPRW001");
		
		aact001service.regAdminAction(req, session, "스타필드 운영정보 관리 페이지 이동", null);
		
		return mv;
	}
}
