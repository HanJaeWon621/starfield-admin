package kr.co.starfield.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import kr.co.starfield.model.JoinStats;
import kr.co.starfield.model.JoinStatsFilter;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ASAT001Service;

/**
 * DASHBOARD
 *
 * Copyright Copyright (c) 2016 Company
 *
 * @author EEZY
 * @version 1.0,
 * @see
 * @date 2016.10.07
 */

@Controller
@RequestMapping("/")
public class ASAT001WebController {

	@Autowired
	private ASAT001Service asat001Service;

	@Autowired
	AACT001Service aact001service;

	private Logger ll = LoggerFactory.getLogger(ASAT001WebController.class);

	@AuthRequired(authArr = { "system", "starfield", "store_master", "store_sub", "handle_personal_info" })
	@RequestMapping(value = "/{bcn_cd}/dashboard", method = RequestMethod.GET)
	public ModelAndView getDashboard(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@PathVariable(value = "bcn_cd") String bcn_cd) throws BaseException {

		ModelAndView mv = new ModelAndView();

		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("cur_date",new SimpleDateFormat("YYYYMMDD").format(new Date()));
		mv.setViewName("dashboard/ASATW001");

		aact001service.regAdminAction(req, session, "DASHBOARD SUMMARY 페이지 이동", null);

		return mv;
	}

	@AuthRequired(authArr = { "system", "starfield", "store_master", "store_sub", "handle_personal_info" })
	@RequestMapping(value = "/{bcn_cd}/dashboard/join", method = RequestMethod.GET)
	public ModelAndView getJoinCount(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@PathVariable(value = "bcn_cd") String bcn_cd) throws BaseException {

		ModelAndView mv = new ModelAndView();

		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("cur_date",new SimpleDateFormat("YYYYMMDD").format(new Date()));
		mv.setViewName("dashboard/ASATW002");

		aact001service.regAdminAction(req, session, "DASHBOARD 회원 가입자 현황 페이지 이동", null);

		return mv;
	}

	@AuthRequired(authArr = { "system", "starfield", "store_master", "store_sub", "handle_personal_info" })
	@RequestMapping(value = "/{bcn_cd}/dashboard/join/download", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getJoinCountDownload(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@PathVariable(value = "bcn_cd") String bcn_cd, @RequestParam(value = "date_type") String date_type,
			@RequestParam(value = "start_date") String start_date, @RequestParam(value = "end_date") String end_date)
			throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "member_join_01_" + strToday + ".xls\"");

		JoinStatsFilter filter = new JoinStatsFilter();

		filter.bcn_cd = bcn_cd;
		filter.date_type = date_type;
		filter.start_date = start_date;
		filter.end_date = end_date;

		ll.info("getJoinCountDownload filter : {}", filter);

		ArrayList<JoinStats> joinStatsList = asat001Service.getJoinCount(filter);

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "일자", "회원가입자 수", "누적 회원가입자 수" };
		String[] colNameList = { "join_date", "count", "acc_count" };

		setting.put("sheetName", "회원 가입자 현황");
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", joinStatsList);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		aact001service.regAdminAction(req, session, "DASHBOARD excel 다운로드", null);

		return mav;
	}

	@AuthRequired(authArr = { "system", "starfield", "store_master", "store_sub", "handle_personal_info" })
	@RequestMapping(value = "/{bcn_cd}/dashboard/appdown", method = RequestMethod.GET)
	public ModelAndView getAppdownCount(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@PathVariable(value = "bcn_cd") String bcn_cd) throws BaseException {

		ModelAndView mv = new ModelAndView();

		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("cur_date",new SimpleDateFormat("YYYYMMDD").format(new Date()));
		mv.setViewName("dashboard/ASATW003");

		return mv;
	}

	// 앱 다운로드 엑셀
	@RequestMapping(value = "/{bcn_cd}/dashboard/appdown/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getAppDownCount(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@PathVariable(value = "bcn_cd") String bcn_cd, @RequestParam(value = "date_type") String date_type,
			@RequestParam(value = "start_date") String start_date, @RequestParam(value = "end_date") String end_date)
			throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "app_down_list_" + strToday + ".xls\"");

		JoinStatsFilter filter = new JoinStatsFilter();
		filter.bcn_cd = bcn_cd;
		filter.date_type = date_type;
		filter.start_date = start_date;
		filter.end_date = end_date;

		ArrayList<JoinStats> joinStatsList = asat001Service.getAppDownCount(filter);

		String sheetName = "";

		sheetName = "앱다운로드";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "날짜", "ios", "android", "total" };
		String[] colNameList = { "join_date", "ios_count", "and_count", "tot_count" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", joinStatsList);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}

	@AuthRequired(authArr = { "system", "starfield", "store_master", "store_sub", "handle_personal_info" })
	@RequestMapping(value = "/{bcn_cd}/dashboard/appuser", method = RequestMethod.GET)
	public ModelAndView getAppuserCount(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@PathVariable(value = "bcn_cd") String bcn_cd) throws BaseException {

		ModelAndView mv = new ModelAndView();

		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("cur_date",new SimpleDateFormat("YYYYMMDD").format(new Date()));
		mv.setViewName("dashboard/ASATW004");

		return mv;
	}

	// 앱 user excel
	@RequestMapping(value = "/{bcn_cd}/dashboard/appuser/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getAppuserCount(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@PathVariable(value = "bcn_cd") String bcn_cd, @RequestParam(value = "date_type") String date_type,
			@RequestParam(value = "start_date") String start_date, @RequestParam(value = "end_date") String end_date)
			throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "app_user_list_" + strToday + ".xls\"");

		JoinStatsFilter filter = new JoinStatsFilter();
		filter.bcn_cd = bcn_cd;
		filter.date_type = date_type;
		filter.start_date = start_date;
		filter.end_date = end_date;


		ArrayList<JoinStats> joinStatsList = asat001Service.getAppUserCount(filter);
		String sheetName = "";

		sheetName = "앱사용자현황";
		
		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "날짜", "지도사용자", "위치정보동의", "마케팅수신동의", "총 사용자" };
		String[] colNameList = { "join_date", "map_user_cnt", "loc_aggr_cnt", "mkt_aggr_cnt", "acc_count" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", joinStatsList);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}

	@AuthRequired(authArr = { "system", "starfield", "store_master", "store_sub", "handle_personal_info" })
	@RequestMapping(value = "/{bcn_cd}/dashboard/visitor/{floor}/{sh_dt}", method = RequestMethod.GET)
	public ModelAndView getVisitor(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@PathVariable(value = "bcn_cd") String bcn_cd,
			@PathVariable(value = "floor") String floor,
			@PathVariable(value = "sh_dt") String sh_dt
			) throws BaseException {

		ModelAndView mv = new ModelAndView();
		if(floor==null){
			floor="L1";
		}
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("floor", floor);
		mv.addObject("sh_dt", sh_dt);
		mv.addObject("cur_date",new SimpleDateFormat("YYYYMMDD").format(new Date()));
		mv.setViewName("dashboard/ASATW005");

		return mv;
	}

	@AuthRequired(authArr = { "system", "starfield", "store_master", "store_sub", "handle_personal_info" })
	@RequestMapping(value = "/{bcn_cd}/dashboard/big/visitor", method = RequestMethod.GET)
	public ModelAndView getBigVisitor(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@PathVariable(value = "bcn_cd") String bcn_cd, @RequestParam(value = "sh_dt") String sh_dt,
			@RequestParam(value = "sh_floor") String sh_floor) throws BaseException {

		ModelAndView mv = new ModelAndView();

		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("sh_dt", sh_dt);
		mv.addObject("sh_floor", sh_floor);
		mv.setViewName("dashboard/ASATW006");

		return mv;
	}
}
