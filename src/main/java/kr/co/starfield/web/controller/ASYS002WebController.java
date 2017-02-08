package kr.co.starfield.web.controller;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.common.BaseException;
import kr.co.starfield.model.Language;
import kr.co.starfield.model.LanguageFilter;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.QNA;
import kr.co.starfield.model.QNAFilter;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ASYS002Service;

/**
 * 다국어 관리
 *
 * Copyright Copyright (c) 2016
 * Company 
 *
 * @author EEZY
 * @version	1.0,
 * @see
 * @date 2016.09.30
 */

@Controller
@RequestMapping("/")  
public class ASYS002WebController {

	private Logger ll = LoggerFactory.getLogger(ASYS002WebController.class);
	
	@Autowired
	ASYS002Service asys002Service;
	
	@Autowired
    AACT001Service aact001service;
	
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcn_cd}/language"
			, method = RequestMethod.GET)
	public ModelAndView getOperation(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("language/ASYSW002");
		
		aact001service.regAdminAction(req, session, "다국어 PAGE 관리 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcn_cd}/language/detail"
			, method = RequestMethod.GET)
	public ModelAndView getOperation(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="pg_id") String pg_id) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("pg_id", pg_id);
		mv.setViewName("language/ASYSW002D");
		
		aact001service.regAdminAction(req, session, "다국어 PAGE 관리 등록/수정 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcn_cd}/language/download"
			, method = RequestMethod.GET
			, produces = "application/vnd.ms-excel")
	public ModelAndView getQNAExcelDownload(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "Language_list_01_" + strToday + ".xls\"");
		
		LanguageFilter languageFilter = new LanguageFilter();
		
		languageFilter.bcn_cd = bcn_cd;
		
		ll.info("getQNAExcelDownload LanguageFilter : {}", languageFilter);

		ListResult<Language> languageList = asys002Service.getLocaleList(languageFilter);
		
		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = {"지점코드", "페이지 ID", "ID 설명", "항목명", "국문", "영문", "중문", "일문"};
		String[] colNameList = {"bcn_cd", "pg_id", "txt_desc", "txt_symb", "txt_ko", "txt_en", "txt_cn", "txt_jp"};
		
		setting.put("sheetName", "다국어 목록");
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", languageList.list);
		
		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);
		
		aact001service.regAdminAction(req, session, "다국어 PAGE 관리 excel 다운로드", null);
		
		return mav;
	}
	
}
