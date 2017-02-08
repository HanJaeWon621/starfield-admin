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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.CommonCode;
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.FacilityForExcel;
import kr.co.starfield.model.FacilityGroup;
import kr.co.starfield.model.FacilityGroupFilter;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleTenantForList;
import kr.co.starfield.model.TenantFilter;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.AFCT001Service;
import kr.co.starfield.service.ATNT001Service;

/**
 * 편의시설 웹 컨트롤러
 *
 * Copyright Copyright (c) 2016
 * Company swyp
 *
 * @author yhkim
 * @version	1.0,
 * @see
 * @date 2016.09.30
 */

@Controller
@RequestMapping("/")
public class AFCT001WebController {

	private Logger ll = LoggerFactory.getLogger(AFCT001WebController.class);
	
	@Autowired
	AFCT001Service afct001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/stores/facilities"
			, method = RequestMethod.GET)
	public ModelAndView getFacilities(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			) {
		ModelAndView mv = new ModelAndView();

//		mv.addObject("tenantType", CommonCode.Tenant.TenantType.getList());
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("stores/AFCTW001");
		
		aact001service.regAdminAction(req, session, "편의시설 그룹 조회 페이지 이동", null);
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/stores/facilities/reg"
			, method = RequestMethod.GET)
	public ModelAndView regFacilities(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="conv_faci_seq", required=false) String faciSeq
			) {
		ModelAndView mv = new ModelAndView();

		mv.addObject("facilityType", CommonCode.Facility.FacilityType.getList());
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("conv_faci_seq", faciSeq);
		mv.setViewName("stores/AFCTW002");
		
		aact001service.regAdminAction(req, session, "편의시설 그룹 등록/수정 페이지 이동", null);
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/stores/facilities/detail"
			, method = RequestMethod.GET)
	public ModelAndView getFacility(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="conv_faci_seq", required=false) String faciSeq
			, @PathVariable(value="bcn_cd") String bcn_cd
			) {
		ModelAndView mv = new ModelAndView();

		mv.addObject("conv_faci_seq", faciSeq);
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("stores/AFCTW003");
		
		aact001service.regAdminAction(req, session, "편의시설 디테일 조회 페이지 이동", null);
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/stores/facilities/detail/reg"
			, method = RequestMethod.GET)
	public ModelAndView regTenant(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="conv_faci_seq") String faciSeq
			, @RequestParam(value="conv_faci_dtl_seq", required=false) String faciDtlSeq
			) throws BaseException {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("conv_faci_seq", faciSeq);
		mv.addObject("conv_faci_dtl_seq", faciDtlSeq);
		mv.setViewName("stores/AFCTW004");
		
		aact001service.regAdminAction(req, session, "편의시설 디테일 등록/수정 페이지 이동", null);
		return mv;
	}

	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/stores/facilities/excel"
			, method = RequestMethod.GET
			, produces = "application/vnd.ms-excel")
	public ModelAndView getFacilityExcelDownload(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", defaultValue = "0") int offset
 			, @RequestParam(value="limit", defaultValue = "-1") int limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {

        String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
	 
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "facility_list_" + strToday+ ".xls\"");
		
		FacilityGroupFilter filter = new FacilityGroupFilter();
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		Utils.Str.qParser(filter, q);
		
		ll.info("getFacilityExcelDownload filter : {}", filter);

		ListResult<FacilityForExcel> list = afct001Service.getFacilityGroupListForExcel(filter);
		
		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = {"NO","시설구분", "편의시설 그룹명(국문)", "편의시설 그룹명(영문)", "편의시설 그룹명(중문)", "편의시설 그룹명(일문)"
				, "이용안내 대표(국문)", "이용안내 대표(영문)", "이용안내 대표(중문)", "이용안내 대표(일문)"
				, "이용안내 서브1(국문)", "이용안내 서브1(영문)", "이용안내 서브1(중문)", "이용안내 서브1(일문)"
				, "이용안내 서브2(국문)", "이용안내 서브2(영문)", "이용안내 서브2(중문)", "이용안내 서브2(일문)"
				, "이용안내 서브3(국문)", "이용안내 서브3(영문)", "이용안내 서브3(중문)", "이용안내 서브3(일문)"
				, "이용안내 서브4(국문)", "이용안내 서브4(영문)", "이용안내 서브4(중문)", "이용안내 서브4(일문)"
				, "이용안내 서브5(국문)", "이용안내 서브5(영문)", "이용안내 서브5(중문)", "이용안내 서브5(일문)"
				, "운영시작시간(평일)", "운영종료시간(평일)", "운영시작시간(휴일)", "운영종료시간(휴일)"
				, "전화번호1", "전화번호2", "전화번호3", "게시순서", "그룹노출여부"
				, "편의시설명", "층", "호수", "ZONE ID", "MAP ID", "X좌표", "Y좌표", "POI 레벨", "편의시설노출여부"};
		String[] colNameList = {"no", "faci_type", "conv_faci_group_nm_ko", "conv_faci_group_nm_en", "conv_faci_group_nm_cn", "conv_faci_group_nm_jp"
				, "conv_faci_desc_ko", "conv_faci_desc_en", "conv_faci_desc_cn", "conv_faci_desc_jp"
				, "conv_faci_desc_dtl_ko1", "conv_faci_desc_dtl_en1", "conv_faci_desc_dtl_cn1", "conv_faci_desc_dtl_jp1"
				, "conv_faci_desc_dtl_ko2", "conv_faci_desc_dtl_en2", "conv_faci_desc_dtl_cn2", "conv_faci_desc_dtl_jp2"
				, "conv_faci_desc_dtl_ko3", "conv_faci_desc_dtl_en3", "conv_faci_desc_dtl_cn3", "conv_faci_desc_dtl_jp3"
				, "conv_faci_desc_dtl_ko4", "conv_faci_desc_dtl_en4", "conv_faci_desc_dtl_cn4", "conv_faci_desc_dtl_jp4"
				, "conv_faci_desc_dtl_ko5", "conv_faci_desc_dtl_en5", "conv_faci_desc_dtl_cn5", "conv_faci_desc_dtl_jp5"
				, "reps_open_hr_min", "reps_end_hr_min", "irgu_open_hr_min", "irgu_end_hr_min"
				, "reps_tel_num1", "reps_tel_num2", "reps_tel_num3", "sort_ord", "group_open_yn"
				, "conv_faci_nm_ko", "fl", "room_num", "zone_id", "map_id", "x_ctn_cord", "y_ctn_cord", "poi_lev", "open_yn"
				};
		
		setting.put("sheetName", "편의시설목록");
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", list.list);
		
		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);
		
		aact001service.regAdminAction(req, session,  "편의시설 엑셀 다운로드", null);
		return mav;
	}
}
