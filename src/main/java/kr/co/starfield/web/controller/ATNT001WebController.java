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
import kr.co.starfield.common.CommonCode;
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleTenantForList;
import kr.co.starfield.model.TenantFilter;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ATNT001Service;

/**
 *  테넌트 웹 컨트롤러
 *
 * Copyright Copyright (c) 2016
 * Company swyp
 *
 * @author yhkim
 * @version	1.0,
 * @see
 * @date 2016.09.26
 */

@Controller
@RequestMapping("/")
public class ATNT001WebController {

	private Logger ll = LoggerFactory.getLogger(ATNT001WebController.class);
	
	@Autowired
	ATNT001Service atnt001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/stores/tenants"
			, method = RequestMethod.GET)
	public ModelAndView getTenants(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			) {
		ModelAndView mv = new ModelAndView();

		mv.addObject("tenantType", CommonCode.Tenant.TenantType.getList());
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("stores/ATNTW001");
		
		aact001service.regAdminAction(req, session, "테넌트 조회 페이지 이동", null);
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/stores/tenants/reg"
			, method = RequestMethod.GET)
	public ModelAndView regTenant(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="tnt_seq", required=false) String tntSeq
			) {
		ModelAndView mv = new ModelAndView();

		mv.addObject("tenantType", CommonCode.Tenant.TenantType.getList());
		mv.addObject("operationStatus", CommonCode.Tenant.OperationStatus.getList());
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("tntSeq", tntSeq);
		mv.setViewName("stores/ATNTW002");
		
		aact001service.regAdminAction(req, session, "테넌트 등록/수정 페이지 이동", null);
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/stores/teannts/excel"
			, method = RequestMethod.GET
			, produces = "application/vnd.ms-excel")
	public ModelAndView getTenantExcelDownload(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", defaultValue = "0") int offset
 			, @RequestParam(value="limit", defaultValue = "-1") int limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {

        String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
	 
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "tenant_list_" + strToday+ ".xls\"");
		
		TenantFilter tenantFilter = new TenantFilter();
		
		Utils.Str.qParser(tenantFilter, q);
		tenantFilter.bcn_cd = bcn_cd;
		tenantFilter.offset = offset;
		tenantFilter.limit = limit;
		
		ll.info("getTenantExcelDownload tenantFilter : {}", tenantFilter);

		ListResult<SimpleTenantForList> tenantList = atnt001Service.getTenantsInCategory(tenantFilter);
		
		for(SimpleTenantForList tenant : tenantList.list) {
			if(tenant.cate_path != null) {
				tenant.cate_path = tenant.cate_path.replaceAll("<br/>", "\n");
			}
		}
		
		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = {"NO","카테고리", "매장명", "구분", "호수", "이벤트", "쿠폰", "운영상태", "노출여부"};
		String[] colNameList = {"no", "cate_path", "tnt_nm_ko", "tnt_type", "room_num", "evt_cnt", "cp_cnt", "opr_sts", "open_yn"};
		
		setting.put("sheetName", "매장목록");
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", tenantList.list);
		
		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);
		
		aact001service.regAdminAction(req, session,  "테넌트 엑셀 다운로드", null);
		return mav;
	}
}
