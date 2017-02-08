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

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.CommonCode;
import kr.co.starfield.model.ALBS001;
import kr.co.starfield.model.ALBS002;
import kr.co.starfield.model.ALBS003;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.vo.ALBS001Vo;
import kr.co.starfield.model.vo.ALBS002Vo;
import kr.co.starfield.model.vo.ALBS003Vo;
import kr.co.starfield.service.ALBS001Service;

/**
 * app push 관련 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016 Company Paprika
 *
 * @author xxx
 * @version 1.0,
 * @see
 * @date 2016.04.11
 */

@Controller
@RequestMapping("/")
public class ALBS001WebController {
	@Autowired
	ALBS001Service albs001Service;

	private Logger ll = LoggerFactory.getLogger(ALBS001WebController.class);

	@RequestMapping(value = "/{bcn_cd}/welcome/push")
	public ModelAndView wcPushMsg(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();

		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("appPush/ALBSW011");
		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/welcome/reqPush")
	public ModelAndView reqWelcomePush(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@PathVariable(value = "bcn_cd") String bcn_cd)
					throws BaseException, JsonGenerationException, JsonMappingException, IOException {
		ModelAndView mv = new ModelAndView();

		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("appPush/ALBSW012");

		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/welcome/push/{wel_msg_push_seq}")
	public ModelAndView modifyWcPushMsg(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("appPush/ALBSW012");

		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/scenario/push")
	public ModelAndView reqScenarioPushList(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("appPush/ALBSW021");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/outbound/push")
	public ModelAndView reqOutboundPushList(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("appPush/ALBSW021");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	@RequestMapping(value = "/{bcnCd}/scenarioPushs/{type}", method = RequestMethod.GET)
	public ModelAndView regScenarioPush(@PathVariable(value = "bcn_cd") String bcn_cd,
			@PathVariable(value = "type") String type) {
		ModelAndView mv = new ModelAndView();

		mv.addObject("tgtMbr", CommonCode.Scenario.TgtMbr.getList());
		mv.addObject("tgtSex", CommonCode.Scenario.TgtSex.getList());
		mv.addObject("tgtAge", CommonCode.Scenario.TgtAge.getList());
		mv.addObject("stayZone", CommonCode.Scenario.StayZone.getList());
		mv.addObject("visitTime", CommonCode.Scenario.VisitTime.getList());
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("type", type);
		mv.setViewName("appPush/ALBSW021");

		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/scenario/push/{type}/{scn_otb_cp_push_seq}")
	public ModelAndView modifyScenarioPush(@PathVariable(value = "bcn_cd") String bcn_cd,
			@PathVariable(value = "type") String type) {

		ModelAndView mv = new ModelAndView();

		mv.addObject("tgtMbr", CommonCode.Scenario.TgtMbr.getList());
		mv.addObject("tgtSex", CommonCode.Scenario.TgtSex.getList());
		mv.addObject("tgtAge", CommonCode.Scenario.TgtAge.getList());
		mv.addObject("stayZone", CommonCode.Scenario.StayZone.getList());
		mv.addObject("visitTime", CommonCode.Scenario.VisitTime.getList());
		mv.addObject("type", type);
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("appPush/ALBSW022");

		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/outbound/push/{type}/{scn_otb_cp_push_seq}")
	public ModelAndView modifyOutboundPush(@PathVariable(value = "bcn_cd") String bcn_cd,
			@PathVariable(value = "type") String type) {

		ModelAndView mv = new ModelAndView();

		mv.addObject("tgtMbr", CommonCode.Scenario.TgtMbr.getList());
		mv.addObject("tgtSex", CommonCode.Scenario.TgtSex.getList());
		mv.addObject("tgtAge", CommonCode.Scenario.TgtAge.getList());
		mv.addObject("stayZone", CommonCode.Scenario.StayZone.getList());
		mv.addObject("visitTime", CommonCode.Scenario.VisitTime.getList());
		mv.addObject("type", type);
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("appPush/ALBSW022");

		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/scenario/{type}")
	public ModelAndView reqScenarioPush(@PathVariable(value = "bcn_cd") String bcn_cd,
			@PathVariable(value = "type") String type) {

		ModelAndView mv = new ModelAndView();

		mv.addObject("tgtMbr", CommonCode.Scenario.TgtMbr.getList());
		mv.addObject("tgtSex", CommonCode.Scenario.TgtSex.getList());
		mv.addObject("tgtAge", CommonCode.Scenario.TgtAge.getList());
		mv.addObject("stayZone", CommonCode.Scenario.StayZone.getList());
		mv.addObject("visitTime", CommonCode.Scenario.VisitTime.getList());
		mv.addObject("type", type);
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("appPush/ALBSW022");

		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/outbound/{type}")
	public ModelAndView reqOutboundPush(@PathVariable(value = "bcn_cd") String bcn_cd,
			@PathVariable(value = "type") String type) {

		ModelAndView mv = new ModelAndView();

		mv.addObject("tgtMbr", CommonCode.Scenario.TgtMbr.getList());
		mv.addObject("tgtSex", CommonCode.Scenario.TgtSex.getList());
		mv.addObject("tgtAge", CommonCode.Scenario.TgtAge.getList());
		mv.addObject("stayZone", CommonCode.Scenario.StayZone.getList());
		mv.addObject("visitTime", CommonCode.Scenario.VisitTime.getList());
		mv.addObject("type", type);
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("appPush/ALBSW022");

		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/reqBanner")
	public ModelAndView regBanner(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();

		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("banner/ALBSW002");
		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/modifyBanner/{bn_seq}")
	public ModelAndView modifyBanner(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();

		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("banner/ALBSW002");
		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/banners")
	public ModelAndView banners(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();

		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("banner/ALBSW001");
		return mv;
	}

	// 웰컴메시지목록
	@RequestMapping(value = "/{bcn_cd}/getWcPushMsgs/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getWcPushMsgsExcel(HttpServletRequest req, HttpServletResponse res,

			@RequestParam(value = "search_strt_dt") String search_strt_dt,
			@RequestParam(value = "search_end_dt") String search_end_dt,
			@RequestParam(value = "push_search_strt_dt") String push_search_strt_dt,
			@RequestParam(value = "push_search_end_dt") String push_search_end_dt,
			@RequestParam(value = "search_dt_type") String search_dt_type,
			// @RequestParam(value = "sh_text_type") String sh_text_type,
			// @RequestParam(value = "sh_text") String sh_text,
			@RequestParam(value = "sortColumn") String sortColumn,
			@RequestParam(value = "sortColumnAscDesc") String sortColumnAscDesc) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "welcome_msg_list_" + strToday + ".xls\"");

		ALBS003Vo vo = new ALBS003Vo();

		vo.limit = -1;
		vo.search_dt_type = search_dt_type;
		vo.search_strt_dt = search_strt_dt;
		vo.search_end_dt = search_end_dt;
		vo.push_search_strt_dt = push_search_strt_dt;
		vo.push_search_end_dt = push_search_end_dt;
		
		vo.sortColumn = sortColumn;
		vo.sortColumnAscDesc = sortColumnAscDesc;

		ListResult<ALBS003> wcPushMsgList = albs001Service.getWcPushMsgs(vo);

		String sheetName = "";

		sheetName = "웰컴목록";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "NO", "등록일시", "타이틀", "푸시카운팅","푸시카운팅(누적)", "노출시작일", "노출종료일", "쿠폰적용여부", "노출현황" };
		String[] colNameList = { "no", "reg_dttm", "push_titl", "push_cnt", "tot_push_cnt", "exp_strt_dt", "exp_end_dt", "apply_yn",
				"exp_yn" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", wcPushMsgList.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}

	// 웰컴메시지목록
	@RequestMapping(value = "/{bcn_cd}/getScenarios/{type}/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getScenariosExcel(HttpServletRequest req, HttpServletResponse res

	, @PathVariable(value = "type") String type, @RequestParam(value = "offset") String offset,
			@RequestParam(value = "limit") String limit, @RequestParam(value = "sh_strt_dt") String sh_strt_dt,
			@RequestParam(value = "sh_end_dt") String sh_end_dt,
			@RequestParam(value = "sh_memb_type") String sh_memb_type,
			@RequestParam(value = "sh_text_type") String sh_text_type, @RequestParam(value = "sh_text") String sh_text,
			@RequestParam(value = "sortColumn") String sortColumn,
			@RequestParam(value = "sortColumnAscDesc") String sortColumnAscDesc) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "scn_msg_list_" + strToday + ".xls\"");

		ALBS001Vo vo = new ALBS001Vo();

		vo.limit = -1;

		vo.sh_strt_dt = sh_strt_dt;
		vo.sh_end_dt = sh_end_dt;
		vo.sh_memb_type = sh_memb_type;
		vo.sh_text_type = sh_text_type;
		vo.sh_text = sh_text;
		vo.sortColumn = sortColumn;
		vo.sortColumnAscDesc = sortColumnAscDesc;
		String sheetName = "";
		if (type.equals("scenario")) {
			vo.scn_otb_div_cd = "1";
			sheetName = "시나리오푸시목록";
		} else {
			sheetName = "아웃바운드푸시목록";
			vo.scn_otb_div_cd = "2";
		}

		ListResult<ALBS001> albs001 = albs001Service.getScenarios(vo);

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "NO", "시나리오타이틀", "푸시대상", "푸시쿠폰", "푸시상황", "노출현황", "푸시카운팅", "푸시카운팅(누적)", "다운로드수", "등록일" };
		String[] colNameList = { "no", "scn_cp_push_titl", "app_tgt_mbr_div_nm", "cp_titl", "push_time_div_nm",
				"exp_yn", "push_cnt","tot_push_cnt", "dn_cnt", "reg_dttm" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", albs001.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}

	// 웰컴메시지목록
	@RequestMapping(value = "/{bcn_cd}/getBanners/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getBannersExcel(HttpServletRequest req, HttpServletResponse res,

			@RequestParam(value = "search_strt_dt") String search_strt_dt,
			@RequestParam(value = "search_end_dt") String search_end_dt,
			@RequestParam(value = "search_dt_type") String search_dt_type,
			@RequestParam(value = "sortColumn") String sortColumn,
			@RequestParam(value="sh_post_type") String sh_post_type,
			@RequestParam(value="sh_text_type") String sh_text_type,
			@RequestParam(value="sh_text") String sh_text,
			@RequestParam(value = "sortColumnAscDesc") String sortColumnAscDesc) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "banner_list_" + strToday + ".xls\"");

		ALBS002Vo vo = new ALBS002Vo();

		vo.limit = -1;
		vo.search_dt_type = search_dt_type;
		vo.search_strt_dt = search_strt_dt;
		vo.search_end_dt = search_end_dt;
		vo.sh_post_type = sh_post_type;
		vo.sh_text_type = sh_text_type;
		vo.sh_text = sh_text;
		vo.sortColumn = sortColumn;
		vo.sortColumnAscDesc = sortColumnAscDesc;

		ListResult<ALBS002> wcPushMsgList = albs001Service.getBanners(vo);

		String sheetName = "";

		sheetName = "배너목록";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = { "NO", "배너타이틀", "노출물유형", "연결페이지타이틀", "등록일시", "노출시작일", "노출종료일", "정렬순서", "노출현황", "클릭카운팅" };
		String[] colNameList = { "no", "bn_titl", "bn_post_type", "link_pg_titl", "f_reg_dttm", "bn_post_strt_dt","bn_post_end_dt","ord_seq","bn_exp_yn","click_cnt" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", wcPushMsgList.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}
}
