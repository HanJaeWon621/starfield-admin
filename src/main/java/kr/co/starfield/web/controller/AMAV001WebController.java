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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.common.BaseException;
import kr.co.starfield.model.AppVer;
import kr.co.starfield.model.AppVerFilter;
import kr.co.starfield.model.ThemeExcel;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.AMAV001Service;

/**
 * App 버전 관리
 *
 * Copyright Copyright (c) 2016
 * Company 
 *
 * @author EEZY
 * @version	1.0,
 * @see
 * @date 2016.10.11
 */

@Controller
@RequestMapping("/")  
public class AMAV001WebController {

	private Logger ll = LoggerFactory.getLogger(AMAV001WebController.class);
	
	@Autowired
	AMAV001Service amav001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcn_cd}/operation/appVer"
			, method = RequestMethod.GET)
	public ModelAndView getAppverList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("operation/AMAVW001");
		
		
		aact001service.regAdminAction(req, session, "APP 버전 조회 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcn_cd}/operation/appVer/download"
			, method = RequestMethod.GET
			, produces = "application/vnd.ms-excel")
	public ModelAndView getAppVerExcelDownload(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="sort_name", required=false, defaultValue = "rnum") String sort_name
			, @RequestParam(value="sort_order", required=false, defaultValue = "desc") String sort_order
	) throws Exception {
		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "app_ver_list_01_" + strToday + ".xls\"");
		
		
		ll.info("getAppVerExcelDownload bcn_cd : {}", bcn_cd);

		
		AppVerFilter filter = new AppVerFilter();
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		filter.sort_name = sort_name;
		filter.sort_order = sort_order;
		
		List<AppVer> appVerList = amav001Service.getAppVerList(filter).list;
		
		for(AppVer appVer : appVerList) {
			if(appVer.pltf_type == 1) {
				appVer.pltf_name = "iOS";
			} else {
				appVer.pltf_name = "Android";
			}
		}
		
		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = {"지점코드", "OS 구분", "버전", "등록일", "적용일"};
		String[] colNameList = {"bcn_cd", "pltf_name", "version", "reg_dttm", "apply_dttm"};
		
		setting.put("sheetName", "APP 버전 목록");
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", appVerList);
		
		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);
		
		aact001service.regAdminAction(req, session, "APP 버전 excel 다운로드", null);
		
		return mav;
	}
}
