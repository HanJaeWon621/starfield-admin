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
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.AEVT001Service;

/**
 *  이벤트 컨트롤러
 *
 * Copyright Copyright (c) 2016
 * Company swyp
 *
 * @author yhkim
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

@Controller
@RequestMapping("/")  
public class AEVT001WebController {

	private Logger ll = LoggerFactory.getLogger(AEVT001WebController.class);
	
	@Autowired
	AEVT001Service aevt001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/eventCoupon/events"
			, method = RequestMethod.GET)
	public ModelAndView getEvents(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="evt_seq", required=false) String evtSeq
			) throws BaseException, JsonGenerationException, JsonMappingException, IOException {
		ModelAndView mv = new ModelAndView();

		mv.addObject("eventSearchOption", CommonCode.Event.SearchOption.getList());
		mv.addObject("eventType", CommonCode.Event.EventType.getList());
		mv.addObject("eventDvi", CommonCode.Event.EventDvi.getList());
		mv.addObject("bcn_cd", bcn_cd);
		
		if(evtSeq == null) {
			mv.setViewName("event/AEVTW001");
		} else {
			mv.addObject("evtSeq", evtSeq);
			mv.setViewName("event/AEVTW002");
		}
		
		aact001service.regAdminAction(req, session, "이벤트 조회 페이지 이동", null);
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/eventCoupon/events/reg"
			, method = RequestMethod.GET)
	public ModelAndView regEvent(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="evt_seq", required=false) String evtSeq
			) throws BaseException, JsonGenerationException, JsonMappingException, IOException {
		ModelAndView mv = new ModelAndView();

		mv.addObject("eventSearchOption", CommonCode.Event.SearchOption.getList());
		mv.addObject("eventType", CommonCode.Event.EventType.getList());
		mv.addObject("eventDvi", CommonCode.Event.EventDvi.getList());
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("evtSeq", evtSeq);
		mv.setViewName("event/AEVTW003");
		
		aact001service.regAdminAction(req, session, "이벤트 등록/수정 페이지 이동", null);
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/eventCoupon/events/excel"
			, method = RequestMethod.GET
			, produces = "application/vnd.ms-excel")
	public ModelAndView getEventExcelDownload(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", defaultValue = "0") int offset
 			, @RequestParam(value="limit", defaultValue = "-1") int limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "event_list_" + strToday + ".xls\"");
		
		EventFilter eventFilter = new EventFilter();
		
		Utils.Str.qParser(eventFilter, q);
		eventFilter.bcn_cd = bcn_cd;
		eventFilter.offset = offset;
		eventFilter.limit = limit;
		
		ll.info("getEventList EventFilter : {}", eventFilter);

		ListResult<Event> eventList = aevt001Service.getEventList(eventFilter);
		
		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = {"NO","이벤트 주체", "타이틀", "게시시작일자", "게시시작일자", "이벤트시작일자", "이벤트종료일자", "이벤트유형", "노출여부", "진행상태", "당첨자발표", "등록일", "승인일"};
		String[] colNameList = {"no", "evt_dvi", "evt_titl", "evt_post_strt_dt", "evt_post_end_dt", "evt_strt_dt", "evt_end_dt", "evt_type", "evt_open_yn", "evt_stat","evt_pick_plan_dt", "reg_dttm", "evt_app_dt"};
		
		setting.put("sheetName", "이벤트목록");
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", eventList.list);
		
		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);
		
		aact001service.regAdminAction(req, session,  "이벤트 엑셀 다운로드", null);
		return mav;
	}
}
