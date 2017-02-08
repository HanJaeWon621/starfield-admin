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
import kr.co.starfield.service.ACMS002Service;

/**
 * 인스타그램
 *
 * Copyright Copyright (c) 2016
 * Company 
 *
 * @author EEZY
 * @version	1.0,
 * @see
 * @date 2016.10.14
 */

@Controller
@RequestMapping("/")  
public class ACMS002WebController {
	
	@Autowired
    AACT001Service aact001service;
	
	@Autowired
	ACMS002Service acms002Service;
	
	private Logger ll = LoggerFactory.getLogger(ACMS002WebController.class);
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/instagram"
			, method = RequestMethod.GET)
	public ModelAndView getBlogList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("token", acms002Service.getToken());
		mv.addObject("client_id", acms002Service.getClientId());
		
		mv.setViewName("starfieldStory/ACMSW002");
		
		aact001service.regAdminAction(req, session, "인스타그램 관리 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/instagram/{insta_tag_seq}"
			, method = RequestMethod.GET)
	public ModelAndView getBlogList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="insta_tag_seq") String insta_tag_seq) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("insta_tag_seq", insta_tag_seq);
		mv.addObject("token", acms002Service.getToken());
		
		mv.setViewName("starfieldStory/ACMSW002D");
		
		aact001service.regAdminAction(req, session, "인스타그램 관리 이미지 등록/수정 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/instagram/createToken"
			, method = RequestMethod.GET)
	public ModelAndView createInstagramToken(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	

		mv.addObject("bcn_cd", bcn_cd);
		
		mv.setViewName("starfieldStory/ACMSW002D2");
		
		aact001service.regAdminAction(req, session, "인스타그램 토큰 재발급", null);
		
		return mv;
	}
}
