package kr.co.starfield.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.model.APPLOG;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SAML002;
import kr.co.starfield.model.SAML007;
import kr.co.starfield.service.APLI004Service;

/**
 * 개인 위치정보 삭제 접수 관리
 *
 * Copyright Copyright (c) 2016 Company
 *
 * @author ldh
 * @version 1.0,
 * @see
 * @date 2016.10.07
 */

@Controller
@RequestMapping("/")
public class APLI004WebController {

	@Autowired
	APLI004Service apli004Service;

	@RequestMapping(value = "/{bcn_cd}/location/reqViews", method = RequestMethod.GET)
	public ModelAndView coupon(@PathVariable(value = "bcn_cd") String bcn_cd) throws BaseException {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("ITO/APLIW004");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/location/reqView", method = RequestMethod.GET)
	public ModelAndView reqView(@PathVariable(value = "bcn_cd") String bcn_cd) throws BaseException {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("ITO/APLIW004D");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/ITO/locationUses", method = RequestMethod.GET)
	public ModelAndView uses(@PathVariable(value = "bcn_cd") String bcn_cd) throws BaseException {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("ITO/APLIW003");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/ITO/appLog", method = RequestMethod.GET)
	public ModelAndView appLog(@PathVariable(value = "bcn_cd") String bcn_cd) throws BaseException {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("ITO/APLIW005");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	// 앱로그
	@RequestMapping(value = "/{bcn_cd}/getAppLogList/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getAppLogListExcel(HttpServletRequest req, HttpServletResponse res

			, @RequestParam(value = "offset") String offset, @RequestParam(value = "limit") String limit,
			@RequestParam(value = "sh_strt_dt") String sh_strt_dt, @RequestParam(value = "sh_end_dt") String sh_end_dt,
			@RequestParam(value = "sh_text_type") String sh_text_type,
			@RequestParam(value = "sh_type1") String sh_type1, @RequestParam(value = "sh_type2") String sh_type2,
			@RequestParam(value = "sh_text") String sh_text, @RequestParam(value = "sortColumn") String sortColumn,
			@RequestParam(value = "sortColumnAscDesc") String sortColumnAscDesc) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "app_log_list_" + strToday + ".xls\"");

		APPLOG applog = new APPLOG();
		applog.limit = -1;
		applog.sh_end_dt = sh_end_dt;
		applog.sh_strt_dt = sh_strt_dt;
		applog.sh_text = sh_text;
		applog.sh_text_type = sh_text_type;
		applog.sh_type1 = sh_type1;
		applog.sh_type2 = sh_type2;
		applog.sortColumn = sortColumn;
		applog.sortColumnAscDesc = sortColumnAscDesc;

		ListResult<APPLOG> List = apli004Service.getAppLogList(applog);

		String sheetName = "";

		sheetName = "APP로그목록";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "번호", "로그유형", "회원구분", "접속계정", "발생일시", "사용로그명" };
		String[] colNameList = { "no", "grp_log_type_nm", "mbr_div_nm", "uuid", "f_reg_dttm", "log_type_nm" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", List.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}

	// 위치정보 요청목록
	@RequestMapping(value = "/{bcn_cd}/getLocationReqViewList/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getLocationReqViewListExcel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "sh_strt_dt") String sh_strt_dt, @RequestParam(value = "sh_end_dt") String sh_end_dt,
			@RequestParam(value = "sh_text_type") String sh_text_type, @RequestParam(value = "sh_text") String sh_text,
			@RequestParam(value = "sortColumn") String sortColumn,
			@RequestParam(value = "sortColumnAscDesc") String sortColumnAscDesc) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "LocationReq_list_" + strToday + ".xls\"");

		SAML007 saml007 = new SAML007();
		saml007.limit = -1;
		saml007.sh_end_dt = sh_end_dt;
		saml007.sh_strt_dt = sh_strt_dt;
		saml007.sh_text = sh_text;
		saml007.sh_text_type = sh_text_type;
		saml007.sortColumn = sortColumn;
		saml007.sortColumnAscDesc = sortColumnAscDesc;

		ListResult<SAML007> List = apli004Service.getLocationReqViewList(saml007);

		String sheetName = "";

		sheetName = "위치정보열람요청";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "번호", "취급자", "요청자", "목적", "처리현황", "이용일시" };
		String[] colNameList = { "no", "dealadm_nm", "req_adm_nm", "read_obj", "dn_yn", "use_dttm" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", List.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}

	// 위치정보
	@RequestMapping(value = "/{bcn_cd}/locationReqViewExcel/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getLocationReqViewExcel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "mem_seq") String mem_seq) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "Location_list_" + strToday + ".xls\"");

		ListResult<SAML002> List = apli004Service.getLocationReqViewExcel(mem_seq);

		String sheetName = "";

		sheetName = "위치정보열람";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "x좌표", "y좌표", "체류시간" };
		String[] colNameList = { "x_ctn_cord", "y_ctn_cord", "stay_time" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", List.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}

}
