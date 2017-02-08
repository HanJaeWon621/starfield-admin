/*
 * AuthorizationController.java	1.00 2016/04/04
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
import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.ACPN005;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.service.ACPN001Service;

/**
 * 로그인 및 사용자인증 관련 컨트롤러 클래스
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
public class ACPN001WebController {

	private Logger ll = LoggerFactory.getLogger(ACPN001WebController.class);

	@Autowired
	ACPN001Service acpn001Service;

	// 쿠폰 사용 목록(가승인 상태)
	@RequestMapping(value = "/{bcn_cd}/useWaitCoupons")
	public ModelAndView getUseWaitCoupons(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("coupon/ACPNW025");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	// 영업정보 대사건 조회
	@RequestMapping(value = "/{bcn_cd}/useCompareCoupons")
	public ModelAndView getUseCompareCoupons(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("coupon/ACPNW026");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/coupon", method = RequestMethod.GET)
	public ModelAndView coupon(@PathVariable(value = "bcn_cd") String bcn_cd) throws BaseException {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("coupon/ACPNW011");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/coupon/{cp_type}")
	public ModelAndView regCoupon(@PathVariable(value = "cp_type") String cp_type,
			@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		String view = "";
		if (cp_type.equals("nmCoupon")) {
			view = "coupon/ACPNW012";
		} else if (cp_type.equals("mbCoupon")) {
			view = "coupon/ACPNW016";
		} else if (cp_type.equals("chCoupon")) {
			view = "coupon/ACPNW014";
		}

		mv.setViewName(view);
		mv.addObject("bcn_cd", bcn_cd);

		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/modifyCoupon/{cp_type}/{cp_seq}")
	public ModelAndView modifyCoupon(@PathVariable(value = "cp_type") String cp_type,
			@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		String view = "";
		if (cp_type.equals("nm")) {
			view = "coupon/ACPNW012";
		} else if (cp_type.equals("mb")) {
			view = "coupon/ACPNW016";
		} else if (cp_type.equals("ch")) {
			view = "coupon/ACPNW014";
		}

		mv.setViewName(view);
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	// 쿠폰 다운 목록
	@RequestMapping(value = "/{bcn_cd}/downCoupons")
	public ModelAndView getDownCoupons(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("coupon/ACPNW021");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	// 쿠폰 다운 상세
	@RequestMapping(value = "/{bcn_cd}/getDownCoupon/{cp_seq}/{cust_id}")
	public ModelAndView getModifyDownCoupons(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("coupon/ACPNW023");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	// 쿠폰 사용 목록
	@RequestMapping(value = "/{bcn_cd}/useCoupons")
	public ModelAndView getUseCoupons(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("coupon/ACPNW022");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	// 쿠폰 사용 상세
	@RequestMapping(value = "/{bcn_cd}/getUseCoupon/{cp_seq}/{cust_id}")
	public ModelAndView getUseCoupon(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("coupon/ACPNW024");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	/*
	 * 영업테넌트 매핑
	 */
	@RequestMapping(value = "/{bcn_cd}/tenant/mapping")
	public ModelAndView tenantMapping(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("coupon/ACPNW031");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/lbsZone/mapping")
	public ModelAndView lbsZoneMapping(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("coupon/ACPNW032");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	@RequestMapping(value = "/{bcn_cd}/faci/mapping")
	public ModelAndView faciMapping(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("coupon/ACPNW033");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	// 쿠폰목록
	@RequestMapping(value = "/{bcn_cd}/getCoupons/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getCouponsExcel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "offset") String offset, @RequestParam(value = "limit") String limit,
			@RequestParam(value = "sh_dt_type") String sh_dt_type,
			@RequestParam(value = "sh_strt_dt") String sh_strt_dt, @RequestParam(value = "sh_end_dt") String sh_end_dt,
			@RequestParam(value = "sh_div_type") String sh_div_type,
			@RequestParam(value = "sh_kind_type") String sh_kind_type,
			@RequestParam(value = "sh_text_type") String sh_text_type, @RequestParam(value = "sh_text") String sh_text,
			@RequestParam(value = "sortColumn") String sortColumn,
			@RequestParam(value = "sortColumnAscDesc") String sortColumnAscDesc) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "cp_list_" + strToday + ".xls\"");

		ACPN001Vo vo = new ACPN001Vo();

		vo.limit = -1;
		vo.sh_dt_type = sh_dt_type;
		vo.sh_strt_dt = sh_strt_dt;
		vo.sh_end_dt = sh_end_dt;
		vo.sh_div_type = sh_div_type;
		vo.sh_kind_type = sh_kind_type;
		vo.sh_text_type = sh_text_type;
		vo.sh_text = sh_text;
		vo.sortColumn = sortColumn;
		vo.sortColumnAscDesc = sortColumnAscDesc;

		ListResult<ACPN001> cplist = acpn001Service.getCoupons(vo);

		String sheetName = "";

		sheetName = "쿠폰목록";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "NO", "쿠폰발급코드", "쿠폰구분", "할인방식", "사용처", "쿠폰종류", "발급타입", "쿠폰타이틀", "전체발행쿠폰수", "푸시카운팅",
				"다운쿠폰수", "회수쿠폰수", "쿠폰회수율", "쿠폰잔여수량", "발급일", "쿠폰유효기간시작", "쿠폰유효기간종료", "노출현황", "등록일" };
		String[] colNameList = { "no", "cp_iss_cd", "cp_div_cd_nm", "cp_sale_div_cd_nm", "tnt_nm_ko", "cp_kind_cd_nm",
				"cp_iss_type_cd_nm", "cp_titl", "cp_iss_cnt", "push_cnt", "dn_cnt", "use_cnt", "use_rt", "remain_cnt",
				"cp_iss_strt_dt", "cp_act_strt_dt", "cp_act_end_dt", "cp_exp_yn", "reg_dttm" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", cplist.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}

	// 다운쿠폰목록
	@RequestMapping(value = "/{bcn_cd}/getDownCoupons/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getDownCouponsExcel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "offset") String offset, @RequestParam(value = "limit") String limit,
			@RequestParam(value = "sh_strt_dt") String sh_strt_dt, @RequestParam(value = "sh_end_dt") String sh_end_dt,
			@RequestParam(value = "sh_div_type") String sh_div_type,
			@RequestParam(value = "sh_text_type") String sh_text_type, @RequestParam(value = "sh_text") String sh_text,
			@RequestParam(value = "sortColumn") String sortColumn,
			@RequestParam(value = "sortColumnAscDesc") String sortColumnAscDesc) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "cp_down_list_" + strToday + ".xls\"");

		ACPN005 acpn005 = new ACPN005();

		acpn005.limit = -1;
		acpn005.sh_strt_dt = sh_strt_dt;
		acpn005.sh_end_dt = sh_end_dt;
		acpn005.sh_div_type = sh_div_type;
		acpn005.sh_text_type = sh_text_type;
		acpn005.sh_text = sh_text;
		acpn005.sortColumn = sortColumn;
		acpn005.sortColumnAscDesc = sortColumnAscDesc;

		ListResult<ACPN005> cplist = acpn001Service.getDownCoupons(acpn005);

		String sheetName = "";

		sheetName = "쿠폰다운목록";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "NO", "쿠폰발급코드", "쿠폰구분", "쿠폰번호", "사용자ID", "쿠폰타이틀", "다운로드일", "사용처" };
		String[] colNameList = { "no", "cp_iss_cd", "cp_div_cd_nm", "cp_kind_cd_nm", "cp_iss_bcd_id", "cust_id",
				"cp_titl", "cp_dn_dt", "use_tnt_nm" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", cplist.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}

	// 다운쿠폰목록
	@RequestMapping(value = "/{bcn_cd}/getUseCoupons/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getUseCouponsExcel(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "offset") String offset, @RequestParam(value = "limit") String limit,
			@RequestParam(value = "sh_strt_dt") String sh_strt_dt, @RequestParam(value = "sh_end_dt") String sh_end_dt,
			@RequestParam(value = "sh_div_type") String sh_div_type,
			@RequestParam(value = "sh_text_type") String sh_text_type, @RequestParam(value = "sh_text") String sh_text,
			@RequestParam(value = "sortColumn") String sortColumn,
			@RequestParam(value = "sortColumnAscDesc") String sortColumnAscDesc) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "cp_use_list_" + strToday + ".xls\"");

		ACPN005 acpn005 = new ACPN005();

		acpn005.limit = -1;
		acpn005.sh_strt_dt = sh_strt_dt;
		acpn005.sh_end_dt = sh_end_dt;
		acpn005.sh_div_type = sh_div_type;
		acpn005.sh_text_type = sh_text_type;
		acpn005.sh_text = sh_text;
		acpn005.sortColumn = sortColumn;
		acpn005.sortColumnAscDesc = sortColumnAscDesc;

		ListResult<ACPN005> cplist = acpn001Service.getUseCoupons(acpn005);

		String sheetName = "";

		sheetName = "쿠폰사용목록";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "NO", "쿠폰발급코드", "쿠폰구분", "쿠폰종류", "쿠폰번호", "사용자ID", "쿠폰타이틀", "처리일시", "사용처리결과", "처리자",
				"사용처" };
		String[] colNameList = { "no", "cp_iss_cd", "cp_div_cd_nm", "cp_kind_cd_nm", "cp_iss_bcd_id", "cust_id",
				"cp_titl", "cp_prc_dt", "cp_use_sts_cd_nm", "reg_usr", "use_tnt_nm" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", cplist.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}
}
