package kr.co.starfield.web.controller;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import kr.co.starfield.common.Utils;
import kr.co.starfield.mapper.ACMS003Mapper;
import kr.co.starfield.mapper.APLI001Mapper;
import kr.co.starfield.model.BlogWeb;
import kr.co.starfield.model.Language;
import kr.co.starfield.model.LanguageFilter;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.LocInfoManageExcel;
import kr.co.starfield.model.NewsWeb;
import kr.co.starfield.model.NoticeWeb;
import kr.co.starfield.model.ThemeExcel;
import kr.co.starfield.model.ThemeWeb;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACMS001Service;
import kr.co.starfield.service.ACMS003Service;
import kr.co.starfield.service.ACMS005Service;
import kr.co.starfield.service.APLI001Service;

/**
 * 개인 위치정보 삭제 접수 관리
 *
 * Copyright Copyright (c) 2016
 * Company 
 *
 * @author EEZY
 * @version	1.0,
 * @see
 * @date 2016.10.07
 */

@Controller
@RequestMapping("/")  
public class APLI002WebController {
	
	@Autowired
	private APLI001Service apli001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	private Logger ll = LoggerFactory.getLogger(APLI002WebController.class);
	
	
	@RequestMapping(value = "/{bcn_cd}/ITO/locInfoManagePrc"
			, method = RequestMethod.GET)
	public ModelAndView getLocInfoManageList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("ITO/APLIW002");
		
		aact001service.regAdminAction(req, session, "개인 위치정보 관리 페이지 이동", null);
		
		return mv;
	}
	
}
