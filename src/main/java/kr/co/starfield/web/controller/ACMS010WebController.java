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
 *  메인 배너 컨트롤
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
public class ACMS010WebController {

	private Logger ll = LoggerFactory.getLogger(ACMS010WebController.class);
	
	@Autowired
    AACT001Service aact001service;
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/main/bannerGroups"
			, method = RequestMethod.GET)
	public ModelAndView getMainBannerGroupList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("main/ACMSW010");
		
		aact001service.regAdminAction(req, session, "상단 히어로 배너 그룹 조회 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/main/bannerGroups/{bn_group_seq}"
			, method = RequestMethod.GET)
	public ModelAndView getMainBannerGroup(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="bn_group_seq") String bn_group_seq) throws BaseException {
		
		ModelAndView mv = new ModelAndView();

		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("bn_group_seq", bn_group_seq);
		mv.setViewName("main/ACMSW010D");
		
		aact001service.regAdminAction(req, session, "상단 히어로 배너 그룹 등록/수정 페이지 이동", null);
		
		return mv;
	}
}
