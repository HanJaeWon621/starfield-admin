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
public class APLI001WebController {
	
	@Autowired
	private APLI001Service apli001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	private Logger ll = LoggerFactory.getLogger(APLI001WebController.class);
	
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/ITO/locInfoManage"
			, method = RequestMethod.GET)
	public ModelAndView getLocInfoManageList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("ITO/APLIW001");
		
		aact001service.regAdminAction(req, session, "개인 위치정보 관리 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/ITO/locInfoManage/{plid_mng_seq}"
			, method = RequestMethod.GET)
	public ModelAndView getLocInfoManage(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="plid_mng_seq") String plid_mng_seq) throws BaseException {
		

		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("plid_mng_seq", plid_mng_seq);
		mv.setViewName("ITO/APLIW001D");
		
		aact001service.regAdminAction(req, session, "개인 위치정보 관리 등록/수정 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/ITO/locInfoManage/download"
			, method = RequestMethod.GET
			, produces = "application/vnd.ms-excel")
	public ModelAndView getLocInfoManageExcelDownload(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "locInfoManage_list_01_" + strToday + ".xls\"");
		
		
		ll.info("getLocInfoManageExcelDownload bcn_cd : {}", bcn_cd);

		boolean masking = true;
		
		if(Utils.Adm.authTypeCheck(session, "handle_personal_info")) {
			masking = false;
		}
		
		List<LocInfoManageExcel> themeList = apli001Service.getLocInfoManageExcelList(bcn_cd, masking);
		
		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = {"지점코드", "고객 이름", "고객 전화번호", "삭제 요청일", "조치여부", "조치결과", "조치일", "조치자ID"};
		String[] colNameList = {"bcn_cd", "name", "phone_num", "req_del_dttm", "status", "act_yn", "act_dttm", "act_usr"};
		
		setting.put("sheetName", "추천테마 목록");
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", themeList);
		
		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);
		
		aact001service.regAdminAction(req, session, "개인 위치정보 관리 excel 다운로드", null);
		
		return mav;
	}
}
