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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.Event;
import kr.co.starfield.model.EventFilter;
import kr.co.starfield.model.FAQExcel;
import kr.co.starfield.model.FAQWeb;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.NoticeWeb;
import kr.co.starfield.model.QNA;
import kr.co.starfield.model.QNAFilter;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACMS006Service;
import kr.co.starfield.service.ACMS008Service;
import kr.co.starfield.service.ACMS010Service;

/**
 * FAQ
 *
 * Copyright Copyright (c) 2016
 * Company 
 *
 * @author EEZY
 * @version	1.0,
 * @see
 * @date 2016.10.05
 */

@Controller
@RequestMapping("/")  
public class ACMS006WebController {

	@Autowired
	ACMS006Service acms006Service;
	
	@Autowired
    AACT001Service aact001service;
	
	private Logger ll = LoggerFactory.getLogger(ACMS006WebController.class);
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/service/faq"
			, method = RequestMethod.GET)
	public ModelAndView getQNACategoryList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("service/ACMSW006");
		
		aact001service.regAdminAction(req, session, "FAQ 그룹 조회 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/service/faq/{cate_seq}"
			, method = RequestMethod.GET)
	public ModelAndView getQNAList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="cate_seq") String cate_seq) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("cate_seq", cate_seq);
		mv.setViewName("service/ACMSW006D");
		
		aact001service.regAdminAction(req, session, "FAQ 조회 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/service/faq/download"
			, method = RequestMethod.GET
			, produces = "application/vnd.ms-excel")
	public ModelAndView getFAQExcelDownload(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "FAQ_list_01_" + strToday + ".xls\"");
		
		ll.info("getFAQExcelDownload bcn_cd : {}", bcn_cd);

		List<FAQExcel> faqList = acms006Service.getFAQExcelList(bcn_cd);
		
		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = {"지점코드", "카테고리명", "국문 제목", "영문 제목", "중문 제목", "일문 제목", "국문 내용", "영문 내용", "중문 내용", "일문 내용", "노출여부", "등록 일자"};
		String[] colNameList = {"bcn_cd", "cate_nm_ko", "faq_titl_ko", "faq_titl_en", "faq_titl_cn", "faq_titl_jp", "faq_cont_ko", "faq_cont_en", "faq_cont_cn", "faq_cont_jp", "sts", "reg_dttm"};
		
		
		setting.put("sheetName", "FAQ 목록");
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", faqList);
		
		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);
		
		aact001service.regAdminAction(req, session, "FAQ excel 다운로드", null);
		
		return mav;
	}
}
