/*
 * AuthorizationController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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
import kr.co.starfield.common.CommonCode;
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.Event;
import kr.co.starfield.model.EventFilter;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Push;
import kr.co.starfield.model.PushFilter;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.AEVT001Service;
import kr.co.starfield.service.APSH001Service;

/**
 *  앱 푸시 컨트롤러
 *
 * Copyright Copyright (c) 2016
 * Company swyp
 *
 * @author yhkim
 * @version	1.0,
 * @see
 * @date 2016.10.10
 */

@Controller
@RequestMapping("/")  
public class APSH001WebController {

	private Logger ll = LoggerFactory.getLogger(APSH001WebController.class);
	
	@Autowired
	APSH001Service apsh001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/operation/pushes"
			, method = RequestMethod.GET)
	public ModelAndView getPushes(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			) throws BaseException, JsonGenerationException, JsonMappingException, IOException {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("searchOption", CommonCode.Push.SearchOption.getList());
		mv.addObject("sendType", CommonCode.Push.SendType.getList());
		mv.addObject("sendYn", CommonCode.Push.SendYn.getList());
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("appPush/APSHW001");

		aact001service.regAdminAction(req, session, "푸시 조회 페이지 이동", null);
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/operation/pushes/reg"
			, method = RequestMethod.GET)
	public ModelAndView regPush(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="push_mng_seq", required=false) String pushMngSeq
			) throws BaseException, JsonGenerationException, JsonMappingException, IOException {
		ModelAndView mv = new ModelAndView();

		mv.addObject("sendType", CommonCode.Push.SendType.getList());
		mv.addObject("sendYn", CommonCode.Push.SendYn.getList());
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("pushMngSeq", pushMngSeq);
		mv.setViewName("appPush/APSHW002");
		
		aact001service.regAdminAction(req, session, "푸시 등록/수정 페이지 이동", null);
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/operation/pushes/excel"
			, method = RequestMethod.GET
			, produces = "application/vnd.ms-excel")
	public ModelAndView getPushExcelDownload(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", defaultValue = "0") int offset
 			, @RequestParam(value="limit", defaultValue = "-1") int limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "app_push_list_" + strToday + ".xls\"");
		
		PushFilter filter = new PushFilter();
		
		Utils.Str.qParser(filter, q);
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		
		ll.info("getPushExcelDownload pushFilter : {}", filter);

		ListResult<Push> list = apsh001Service.getPushList(filter);
		
		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = {"NO","발송구분", "발송일시", "발송상태", "PUSH 메시지", "등록일"};
		String[] colNameList = {"no", "send_type", "send_dttm", "send_yn", "push_msg", "reg_dttm"};
		
		setting.put("sheetName", "푸시목록");
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", list.list);
		
		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);
		
		aact001service.regAdminAction(req, session,  "푸시 엑셀 다운로드", null);
		return mav;
	}
}
