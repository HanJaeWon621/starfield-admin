package kr.co.starfield.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.common.BaseException;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACMS010Service;

/**
 *  메인 왓츠뉴 컨트롤
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
public class ACMS011WebController {

	private Logger ll = LoggerFactory.getLogger(ACMS011WebController.class);

	@Autowired
    AACT001Service aact001service;
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/main/whatsNewGroups"
			, method = RequestMethod.GET)
	public ModelAndView getWhatsNewGroupList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException {
		
		ModelAndView mv = new ModelAndView();

		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("main/ACMSW012");
		
		aact001service.regAdminAction(req, session, "STARFIELD NOW 그룹 조회 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/main/whatsNewGroups/{what_group_seq}"
			, method = RequestMethod.GET)
	public ModelAndView getWhatsNewGroup(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="what_group_seq") String what_group_seq) throws BaseException {
		
		ModelAndView mv = new ModelAndView();

		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("what_group_seq", what_group_seq);
		mv.setViewName("main/ACMSW012D");
		
		aact001service.regAdminAction(req, session, "STARFIELD NOW 그룹 등록/수정 페이지 이동", null);
		
		return mv;
	}
}
