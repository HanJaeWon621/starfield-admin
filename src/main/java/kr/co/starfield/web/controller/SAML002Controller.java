/*
 * AuthorizationController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * app push 관련 컨트롤러 클래스
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
@RequestMapping("/admin")  
public class SAML002Controller {

	private Logger ll = LoggerFactory.getLogger(SAML002Controller.class);
	
	
	@RequestMapping(value = "/locationLogs")
	public ModelAndView iotManagement() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("log/SAMLW002");
		
		return mv;
	}
	
	
	@RequestMapping(value = "/actionLogs")
	public ModelAndView actionLogs() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("log/SAMLW003");
		
		return mv;
	}
	
	
	@RequestMapping(value = "/locationReadingLogs")
	public ModelAndView locationReadingLogs() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("log/SAMLW004");
		
		return mv;
	}
	
	
	@RequestMapping(value = "/log1")
	public ModelAndView log1() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("log/SAMLW010");
		
		return mv;
	}
	
	
	@RequestMapping(value = "/log2")
	public ModelAndView log2() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("log/SAMLW011");
		
		return mv;
	}
}
