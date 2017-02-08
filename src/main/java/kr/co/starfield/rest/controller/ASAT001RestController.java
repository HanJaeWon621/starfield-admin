package kr.co.starfield.rest.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.model.DashboardStats;
import kr.co.starfield.model.HeatMapFilter;
import kr.co.starfield.model.JoinStats;
import kr.co.starfield.model.JoinStatsFilter;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ASAT001Service;

/**
 *  DASHBOARD
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author eezy
 * @version 1.0,
 * @see
 * @date 2016.06.20
 */
@RestController
@RequestMapping("/admin/rest")
public class ASAT001RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(ASAT001RestController.class);
	
	@Autowired
	ASAT001Service asat001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * 대쉬보드 통계 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/dashboard/stats"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public DashboardStats getDashboardStats(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		
		
		this.initHandler(req, res);
		
		ll.info("getDashboardStats  bcn_cd : {}", new Object[] {bcn_cd});
		
		DashboardStats dashboardStats = asat001Service.getDashboardStats(bcn_cd);
		
		aact001service.regAdminAction(req, session, "DASHBOARD SUMMARY 조회", null);

		return dashboardStats;
	}
	
	
	/**
	 * 가입자 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/dashboard/join"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ArrayList<JoinStats> getJoinCount(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="date_type") String date_type
			, @RequestParam(value="start_date") String start_date
			, @RequestParam(value="end_date") String end_date
	) throws Exception {
		
		
		this.initHandler(req, res);
		
		ll.info("getJoinCount  bcn_cd : {}, start_date : {}, end_date : {}", new Object[] {bcn_cd, start_date, end_date});
		
		JoinStatsFilter filter = new JoinStatsFilter();
		filter.bcn_cd = bcn_cd;
		filter.date_type = date_type;
		filter.start_date = start_date;
		filter.end_date = end_date;

		ArrayList<JoinStats> joinStatsList = asat001Service.getJoinCount(filter);
		
		aact001service.regAdminAction(req, session, "DASHBOARD 회원 가입자 현황 조회", null);
		
		return joinStatsList;
	}
	
	/**
	 * down 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/dashboard/down"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ArrayList<JoinStats> getDownCount(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="date_type") String date_type
			, @RequestParam(value="start_date") String start_date
			, @RequestParam(value="end_date") String end_date
	) throws Exception {
		
		
		this.initHandler(req, res);
		
		ll.info("getJoinCount  bcn_cd : {}, start_date : {}, end_date : {}", new Object[] {bcn_cd, start_date, end_date});
		
		JoinStatsFilter filter = new JoinStatsFilter();
		filter.bcn_cd = bcn_cd;
		filter.date_type = date_type;
		filter.start_date = start_date;
		filter.end_date = end_date;
		
		//ArrayList<JoinStats>
		//ListResult<JoinStats>
		ArrayList<JoinStats> joinStatsList = asat001Service.getAppDownCount(filter);
		
		aact001service.regAdminAction(req, session, "DASHBOARD AppDown 현황 조회", null);
		
		return joinStatsList;
	}
	
	/**
	 * 앱 사용자 현황 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/dashboard/appuser"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ArrayList<JoinStats> getAppUserCount(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="date_type") String date_type
			, @RequestParam(value="start_date") String start_date
			, @RequestParam(value="end_date") String end_date
	) throws Exception {
		
		
		this.initHandler(req, res);
		
		ll.info("getAppUserCount  bcn_cd : {}, start_date : {}, end_date : {}", new Object[] {bcn_cd, start_date, end_date});
		
		JoinStatsFilter filter = new JoinStatsFilter();
		filter.bcn_cd = bcn_cd;
		filter.date_type = date_type;
		filter.start_date = start_date;
		filter.end_date = end_date;

		ArrayList<JoinStats> joinStatsList = asat001Service.getAppUserCount(filter);
		
		aact001service.regAdminAction(req, session, "DASHBOARD AppUser 현황 조회", null);
		
		return joinStatsList;
	}

	
	/**
	 * down 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/getHeatmapList"
	, method = RequestMethod.GET
	, produces = "application/json; charset=utf-8"
	, headers = "content-type=application/json"
			)
	public HeatMapFilter getHeatmapList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="sh_floor") String sh_floor
			, @RequestParam(value="sh_dt") String sh_dt
			) throws Exception {
		
		this.initHandler(req, res);
		
		HeatMapFilter heatmap = new HeatMapFilter();
		heatmap.sh_floor = sh_floor;
		heatmap.sh_dt = sh_dt;
		
		HeatMapFilter result = asat001Service.getHeatmapList(heatmap);
		
		return result;
	}
}
