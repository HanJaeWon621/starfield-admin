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
import kr.co.starfield.mapper.ACMS003Mapper;
import kr.co.starfield.model.BlogExcel;
import kr.co.starfield.model.BlogWeb;
import kr.co.starfield.model.Language;
import kr.co.starfield.model.LanguageFilter;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.NewsWeb;
import kr.co.starfield.model.NoticeWeb;
import kr.co.starfield.model.ThemeExcel;
import kr.co.starfield.model.ThemeWeb;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACMS001Service;
import kr.co.starfield.service.ACMS003Service;
import kr.co.starfield.service.ACMS005Service;

/**
 * BLOG
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
public class ACMS001WebController {
	
	@Autowired
	private ACMS001Service acms001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	private Logger ll = LoggerFactory.getLogger(ACMS001WebController.class);
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/blog"
			, method = RequestMethod.GET)
	public ModelAndView getBlogList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("starfieldStory/ACMSW001");
		
		aact001service.regAdminAction(req, session, "블로그 조회 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/blog/{blog_seq}"
			, method = RequestMethod.GET)
	public ModelAndView getBlogDetail(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="blog_seq") String blog_seq) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("blog_seq", blog_seq);
		mv.setViewName("starfieldStory/ACMSW001D");
		
		aact001service.regAdminAction(req, session, "블로그 등록/수정 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/blog/download"
			, method = RequestMethod.GET
			, produces = "application/vnd.ms-excel")
	public ModelAndView getBlogsExcelDownload(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "Blog_list_01_" + strToday + ".xls\"");
		
		
		ll.info("getBlogsExcelDownload bcn_cd : {}", bcn_cd);

		List<BlogExcel> blogList = acms001Service.getBlogExcelList(bcn_cd);
		
		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = {"지점코드", "제목", "이미지", "링크", "글자색상(APP)", "노출여부", "등록일자"};
		String[] colNameList = {"bcn_cd", "blog_titl", "img_uri", "blog_url", "txt_colr_cd", "sts", "reg_dttm"};
		
		setting.put("sheetName", "블로그 목록");
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", blogList);
		
		
		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);
		
		aact001service.regAdminAction(req, session, "블로그 excel 다운로드", null);
		
		return mav;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/theme"
			, method = RequestMethod.GET)
	public ModelAndView getThemeList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("starfieldStory/ACMSW003");
		
		aact001service.regAdminAction(req, session, "추천테마 조회 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/theme/{thme_seq}"
			, method = RequestMethod.GET)
	public ModelAndView getThemeDetail(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="thme_seq") String thme_seq) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("thme_seq", thme_seq);
		mv.setViewName("starfieldStory/ACMSW003D");
		
		aact001service.regAdminAction(req, session, "추천테마 등록/수정 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/theme/download"
			, method = RequestMethod.GET
			, produces = "application/vnd.ms-excel")
	public ModelAndView getThemeExcelDownload(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "Theme_list_01_" + strToday + ".xls\"");
		
		
		ll.info("getBlogsExcelDownload bcn_cd : {}", bcn_cd);

		List<ThemeExcel> themeList = acms001Service.getThemeExcelList(bcn_cd);
		
		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = {"지점코드", "제목", "이미지", "링크", "글자색상(APP)", "노출여부", "등록일자"};
		String[] colNameList = {"bcn_cd", "thme_titl", "img_uri", "thme_link_url", "txt_colr_cd", "sts", "reg_dttm"};
		
		setting.put("sheetName", "추천테마 목록");
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", themeList);
		
		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);
		
		aact001service.regAdminAction(req, session, "추천테마 excel 다운로드", null);
		
		return mav;
	}
}
