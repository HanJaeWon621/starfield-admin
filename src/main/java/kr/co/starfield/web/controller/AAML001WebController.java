/*
\ * AuthorizationController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.model.AAML001;
import kr.co.starfield.model.AAML002;
import kr.co.starfield.model.AAML003;
import kr.co.starfield.model.InstallStats;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.MovePathStats;
import kr.co.starfield.service.AAML001Service;

/**
 * 앰사용통계
 *
 * Copyright Copyright (c) 2016 Company Paprika
 *
 * @author dhlee
 * @version 1.0,
 * @see
 * @date 2016.04.11
 */

@Controller
@RequestMapping("/")
public class AAML001WebController {
	@Autowired
	AAML001Service aaml001Service;
	private Logger ll = LoggerFactory.getLogger(AAML001WebController.class);

	// 전체 방문자 통계
	@RequestMapping(value = "/{bcn_cd}/stats/visitors")
	public ModelAndView visitorsStats() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("stats/AAMLW001");

		return mv;
	}

	// 테는트별 방문자 통계 - > 전체방문자 통계
	@RequestMapping(value = "/{bcn_cd}/stats/tenant/visitors")
	public ModelAndView tenantVisitorsStats() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("stats/AAMLW002");

		return mv;
	}

	// 테넌트별 방문자 동계 - > 회원기준 동계
	@RequestMapping(value = "/{bcn_cd}/stats/tenant/memb")
	public ModelAndView tenantMembStats() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("stats/AAMLW003");

		return mv;
	}

	// 앱캠페인 효과분석 - > 전체 데이터
	@RequestMapping(value = "/{bcn_cd}/stats/campaign/all")
	public ModelAndView campaignStats() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("stats/AAMLW004");

		return mv;
	}

	// 앱캠페인 효과분석 - > 회원기준 통계
	@RequestMapping(value = "/{bcn_cd}/stats/campaign/memb")
	public ModelAndView campaignMbmbStats() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("stats/AAMLW005");

		return mv;
	}

	// 전체 방문자 통계
	@RequestMapping(value = "/{bcn_cd}/getAllVisitorStats/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getAllVisitorStatsExcelDownload(HttpServletRequest req, HttpServletResponse res,
			HttpSession session, @RequestParam(value = "sh_dt_type") String sh_dt_type,
			@RequestParam(value = "sh_strt_dt") String sh_strt_dt, @RequestParam(value = "sh_end_dt") String sh_end_dt,
			@RequestParam(value = "sortColumn") String sortColumn,
			@RequestParam(value = "sortColumnAscDesc") String sortColumnAscDesc) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "all_stats_list_" + strToday + ".xls\"");

		AAML001 vo = new AAML001();

		vo.limit = -1;
		vo.sh_strt_dt = sh_strt_dt;
		vo.sh_end_dt = sh_end_dt;
		vo.sortColumn = sortColumn;
		vo.sortColumnAscDesc = sortColumnAscDesc;

		ListResult<AAML001> cplist = null;
		String sheetName = "";
		if (sh_dt_type.equals("day")) {
			cplist = aaml001Service.getAllVisitorStatsDay(vo);
			sheetName = "전체방문자통계(일별)";
		} else if (sh_dt_type.equals("week")) {
			cplist = aaml001Service.getAllVisitorStatsWeek(vo);
			sheetName = "전체방문자통계(주별)";
		} else if (sh_dt_type.equals("month")) {
			cplist = aaml001Service.getAllVisitorStatsMonth(vo);
			sheetName = "전체방문자통계(월별)";
		} else if (sh_dt_type.equals("year")) {
			cplist = aaml001Service.getAllVisitorStatsYear(vo);
			sheetName = "전체방문자통계(년별)";
		}

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "기간", "전체방문자(전년도)", "전체방문자(현재년도)", "회원방문자(전년도)", "전체방문자(현재년도)", "신규방문자(전년도)",
				"신규방문자(전년도)", "재방문자(전년도)", "재방문자(현재년도)" };
		String[] colNameList = { "visit_dt", "pre_all_visit_cnt", "all_visit_cnt", "pre_all_visit_mbr_cnt",
				"all_visit_mbr_cnt", "pre_new_visit_cnt", "new_visit_cnt", "pre_re_visit_cnt", "re_visit_cnt" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", cplist.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}

	// 테넌트별 방문자 통계 > 전체 방문자 통계
	@RequestMapping(value = "/{bcn_cd}/getTntAllVisitorStats/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getTntAllVisitorStatsExcelDownload(HttpServletRequest req, HttpServletResponse res,
			HttpSession session, @RequestParam(value = "sh_tnt_nm") String sh_tnt_nm,
			@RequestParam(value = "sh_strt_dt") String sh_strt_dt, @RequestParam(value = "sh_end_dt") String sh_end_dt,
			@RequestParam(value = "sortColumn") String sortColumn,
			@RequestParam(value = "sortColumnAscDesc") String sortColumnAscDesc) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "tnt_all_stats_list_" + strToday + ".xls\"");

		AAML002 vo = new AAML002();

		vo.limit = -1;
		vo.sh_strt_dt = sh_strt_dt;
		vo.sh_end_dt = sh_end_dt;
		vo.sh_tnt_nm = sh_tnt_nm;
		vo.sortColumn = sortColumn;
		vo.sortColumnAscDesc = sortColumnAscDesc;

		ListResult<AAML002> cplist = null;
		String sheetName = "";

		cplist = aaml001Service.getTntAllVisitorStats(vo);
		sheetName = "테넌트별 방문자통계>전체방문자통계";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "순위", "테넌트 명", "카테고리", "전체 방문자수", "회원 방문자 수", "금일 방문자", "재방문자" };
		String[] colNameList = { "no", "tnt_nm", "cate_nm", "all_visit_cnt", "all_visit_mbr_cnt", "new_visit_cnt",
				"re_visit_cnt" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", cplist.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}

	// 테넌트별 방문자 통계 > 회원기준 통계
	@RequestMapping(value = "/{bcn_cd}/getTntMbrVisitorStats/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getTntMbrVisitorStatsExcelDownload(HttpServletRequest req, HttpServletResponse res,
			HttpSession session, @RequestParam(value = "sh_tnt_nm") String sh_tnt_nm,
			@RequestParam(value = "sh_strt_dt") String sh_strt_dt, @RequestParam(value = "sh_end_dt") String sh_end_dt,
			@RequestParam(value = "sortColumn") String sortColumn,
			@RequestParam(value = "sortColumnAscDesc") String sortColumnAscDesc) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "tnt_mbr_stats_list_" + strToday + ".xls\"");

		AAML003 vo = new AAML003();

		vo.limit = -1;
		vo.sh_strt_dt = sh_strt_dt;
		vo.sh_end_dt = sh_end_dt;
		vo.sh_tnt_nm = sh_tnt_nm;
		vo.sortColumn = sortColumn;
		vo.sortColumnAscDesc = sortColumnAscDesc;

		ListResult<AAML003> cplist = null;
		String sheetName = "";

		cplist = aaml001Service.getTntMbrVisitorStats(vo);
		sheetName = "테넌트별 방문자통계>전체방문자통계";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "순위", "테넌트 명", "회원 방문자수", "금일 방문회원 수", "재방문 회원수", "방문회원(여비율)", "방문회원(남비율)", "방문회원(여10비율)",
				"방문회원(여20비율)", "방문회원(여30비율)", "방문회원(여40비율)", "방문회원(여50비율)", "방문회원(여60~비율)", "방문회원(남10비율)",
				"방문회원(남20비율)", "방문회원(남30비율)", "방문회원(남40비율)", "방문회원(남50비율)", "방문회원(남60~비율)" };
		String[] colNameList = { "no", "tnt_nm", "all_visit_mbr_cnt", "today_visit_mbr_cnt", "re_visit_mbr_cnt",
				"sex_rt_f", "sex_rt_m", "sex_rt_10_f", "sex_rt_20_f", "sex_rt_30_f", "sex_rt_40_f", "sex_rt_50_f",
				"sex_rt_60_f", "sex_rt_10_m", "sex_rt_20_m", "sex_rt_30_m", "sex_rt_40_m", "sex_rt_50_m",
				"sex_rt_60_m" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", cplist.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}

	// 이동 경로 분석
	@RequestMapping(value = "/{bcn_cd}/stats/move")
	public ModelAndView move() {
		//TODO 1. 테넌트명, 층정보
		ModelAndView mv = new ModelAndView();
		mv.setViewName("stats/AAMLW008");

		return mv;
	}

	// app 설치 통계 전체 누적
	@RequestMapping(value = "/{bcn_cd}/appStats/install")
	public ModelAndView install() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("stats/AAMLW006");

		return mv;
	}

	// app 설치 동계 기간별 설치 통계
	@RequestMapping(value = "/{bcn_cd}/appTermStats/install")
	public ModelAndView installTerm() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("stats/AAMLW007");

		return mv;
	}

	// app 설치 통계 전체 누적 엑셀
	@RequestMapping(value = "/{bcn_cd}/excel/getInstallTermStats", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getInstallTermStats(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "offset") String offset, @RequestParam(value = "limit") String limit,
			@RequestParam(value = "sh_strt_dt") String sh_strt_dt, @RequestParam(value = "sh_end_dt") String sh_end_dt,
			@RequestParam(value = "sh_dt_type") String sh_dt_type) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "tnt_mbr_stats_list_" + strToday + ".xls\"");

		InstallStats installStats = new InstallStats();
		installStats.offset = Integer.parseInt(offset);
		installStats.limit = Integer.parseInt(limit);
		installStats.sh_strt_dt = sh_strt_dt;
		installStats.sh_end_dt = sh_end_dt;
		installStats.sh_dt_type = sh_dt_type;
		installStats.limit = -1;
		ListResult<InstallStats> installStatsList = null;
		String sheetName = "";

		installStatsList = aaml001Service.getInstallTermStats(installStats);

		sheetName = "앱사용 통계 > APP 설치 통계 > 기간별 설치 통계";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "기간", "ios 설치 수", "Android 설치 수", "전체" };
		String[] colNameList = { "reg_dttm", "i_cnt", "a_cnt", "sum_cnt" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", installStatsList.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}

	// app 설치 통계 전체 누적 엑셀
	@RequestMapping(value = "/{bcn_cd}/excel/getInstallStats", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getInstallStats(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "offset") String offset, @RequestParam(value = "limit") String limit,
			@RequestParam(value = "sh_strt_dt") String sh_strt_dt, @RequestParam(value = "sh_end_dt") String sh_end_dt,
			@RequestParam(value = "sh_dt_type") String sh_dt_type) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "tnt_mbr_stats_list_" + strToday + ".xls\"");

		InstallStats installStats = new InstallStats();
		installStats.offset = Integer.parseInt(offset);
		installStats.limit = Integer.parseInt(limit);
		installStats.sh_strt_dt = sh_strt_dt;
		installStats.sh_end_dt = sh_end_dt;
		installStats.sh_dt_type = sh_dt_type;
		installStats.limit = -1;
		ListResult<InstallStats> installStatsList = null;
		String sheetName = "";

		installStatsList = aaml001Service.getInstallStats(installStats);

		sheetName = "앱사용 통계 > APP 설치 통계 > 전체 누적 설치 통계";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "기간", "ios 설치 수", "Android 설치 수", "전체" };
		String[] colNameList = { "reg_dttm", "i_cnt", "a_cnt", "sum_cnt" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", installStatsList.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}

	// 동선 통계
	@RequestMapping(value = "/{bcn_cd}/excel/getMovePathStats", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getMovePathStats(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "offset") String offset, @RequestParam(value = "limit") String limit,
			@RequestParam(value = "sh_dt") String sh_dt, 
			@RequestParam(value = "sh_dt_type") String sh_dt_type) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "move_stats_list_" + strToday + ".xls\"");

		MovePathStats installStats = new MovePathStats();
		installStats.offset = Integer.parseInt(offset);
		installStats.limit = -1;
		installStats.sh_dt = sh_dt;
		installStats.limit = -1;
		ListResult<MovePathStats> installStatsList = null;
		String sheetName = "";

		installStatsList = aaml001Service.getMovePathStats(installStats);

		sheetName = "이동경로 분석";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		/*
		public String sts_dt; 
		public String move_dt; 
		public String move_tm; 
		public String tnt_nm;
		public String fl_nm;
		public String all_visit_cnt; 
		public String all_unk_visit_cnt; 
		public String all_tnt_rt; 
		public String input_top_tnt_nm; 
		public String exit_top_tnt_nm;
		*/
		
		String[] arrTitle = { "순위", "테넌트명", "층 정보", "총방문자 수", "유일방문자", "방문자비율", "유입경로 TOP", "이탈경로 TOP" };
		String[] colNameList = { "no", "tnt_nm", "fl_nm", "all_visit_cnt", "all_unk_visit_cnt", "all_tnt_rt", "input_top_tnt_nm", "exit_top_tnt_nm" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", installStatsList.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}
}
