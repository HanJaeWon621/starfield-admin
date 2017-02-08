/*
 * AuthorizationController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
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
import kr.co.starfield.service.ACTG001Service;

/**
 *  카테고리 웹 컨트롤러
 *
 * Copyright Copyright (c) 2016
 * Company swyp
 *
 * @author yhkim
 * @version	1.0,
 * @see
 * @date 2016.10.07
 */

@Controller
@RequestMapping("/")
public class ACTG001WebController {

	private Logger ll = LoggerFactory.getLogger(ACTG001WebController.class);
	
	@Autowired
	ACTG001Service actg001Service;
	
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcn_cd}/category"
			, method = RequestMethod.GET)
	public ModelAndView getTenants(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			) {
		ModelAndView mv = new ModelAndView();

		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("category/ACTGW001");
		
		return mv;
	}
}
