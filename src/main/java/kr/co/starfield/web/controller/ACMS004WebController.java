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
import kr.co.starfield.model.Language;
import kr.co.starfield.model.LanguageFilter;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.NewsWeb;
import kr.co.starfield.model.NoticeWeb;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACMS003Service;
import kr.co.starfield.service.ACMS005Service;

/**
 * NEWS
 *
 * Copyright Copyright (c) 2016
 * Company 
 *
 * @author EEZY
 * @version	1.0,
 * @see
 * @date 2016.10.06
 */

@Controller
@RequestMapping("/")  
public class ACMS004WebController {
	
	@Autowired
	private ACMS003Service acms003Service;
	
	@Autowired
    AACT001Service aact001service;
	
	private Logger ll = LoggerFactory.getLogger(ACMS004WebController.class);
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/news"
			, method = RequestMethod.GET)
	public ModelAndView getNewsList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("starfieldStory/ACMSW004");
		
		aact001service.regAdminAction(req, session, "뉴스 조회 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/news/{news_seq}"
			, method = RequestMethod.GET)
	public ModelAndView getNewsDetail(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="news_seq") String news_seq) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("news_seq", news_seq);
		mv.setViewName("starfieldStory/ACMSW004D");
		
		aact001service.regAdminAction(req, session, "뉴스 등록/수정 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/news/download"
			, method = RequestMethod.GET
			, produces = "application/vnd.ms-excel")
	public ModelAndView getNewsExcelDownload(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "Notice_list_01_" + strToday + ".xls\"");
		
		
		ll.info("getNoticeExcelDownload bcn_cd : {}", bcn_cd);

		List<NewsWeb> newsList = acms003Service.redisNewsList(bcn_cd);
		
		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = {"지점코드", "구분", "제목", "내용", "등록일자"};
		String[] colNameList = {"bcn_cd", "cate_nm_ko", "news_titl", "news_cont", "reg_dttm"};
		
		setting.put("sheetName", "공지사항 목록");
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", newsList);
		
		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);
		
		aact001service.regAdminAction(req, session, "뉴스 excel 다운로드", null);
		
		return mav;
	}
	
}
