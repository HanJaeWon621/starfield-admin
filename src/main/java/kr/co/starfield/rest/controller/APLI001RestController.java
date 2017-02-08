package kr.co.starfield.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.LocInfoManage;
import kr.co.starfield.model.LocInfoManageFilter;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.APLI001Service;

/**
 *  개인 위치정보 삭제 관리
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author eezy
 * @version 1.0,
 * @see
 * @date 2016.10.10
 */
@RestController
@RequestMapping("/admin/rest")
public class APLI001RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(APLI001RestController.class);
	
	@Autowired
	APLI001Service apli001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * LocInfoManage 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/ITO/locInfoManage"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regLocInfoManage(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody LocInfoManage locInfoManage ) throws Exception {
		
		this.initHandler(req, res);
		
		locInfoManage.bcn_cd = bcn_cd;
		
		locInfoManage.reg_usr = (String) session.getAttribute("adm_seq");
		locInfoManage.act_usr = (String) session.getAttribute("adm_seq");
		
		
		ll.info("locInfoManage bcn_cd : {}, locInfoManage : {}", bcn_cd, locInfoManage);
		
		SimpleResult result = apli001Service.regLocInfoManage(locInfoManage);
		
		aact001service.regAdminAction(req, session, "APP 개인 위치정보 삭제접수 관리 등록", null);
		
		return result;
	}
	
	/**
	 * LocInfoManage 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/ITO/locInfoManage"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<LocInfoManage> getLocInfoManageList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="sort_name", required=false, defaultValue = "rnum") String sort_name
			, @RequestParam(value="sort_order", required=false, defaultValue = "desc") String sort_order
			, @RequestParam(value="date_search", required=false, defaultValue = "false") boolean date_search
			, @RequestParam(value="date_type", required=false, defaultValue = "REQ_DEL_DTTM") String date_type
			, @RequestParam(value="stat_date", required=false) String stat_date
			, @RequestParam(value="end_date", required=false) String end_date
			, @RequestParam(value="act_yn", required=false) String act_yn
			, @RequestParam(value="sts", required=false, defaultValue = "-1") int sts
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getLocInfoManageList  bcn_cd : {}, offset : {}, limit : {}", new Object[] {bcn_cd, offset, limit});
		
		LocInfoManageFilter filter = new LocInfoManageFilter();
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		filter.sort_name = sort_name;
		filter.sort_order = sort_order;
		filter.date_search = date_search;
		filter.date_type = date_type;
		filter.stat_date = stat_date;
		filter.end_date = end_date;
		filter.act_yn = act_yn;
		filter.sts = sts;
		
		boolean masking = true;
		
		if(Utils.Adm.authTypeCheck(session, "handle_personal_info")) {
			masking = false;
		}
		
		ListResult<LocInfoManage> locInfoManageList = apli001Service.getLocInfoManageList(filter, masking);
		
		aact001service.regAdminAction(req, session, "APP 개인 위치정보 삭제접수 관리 목록 조회", null);
		
		return locInfoManageList;
	}
	
	/**
	 * LocInfoManage 상세
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/ITO/locInfoManage/{plid_mng_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public LocInfoManage getLocInfoManage(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="plid_mng_seq") String plid_mng_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getLocInfoManage bcn_cd : {}, plid_mng_seq : {}", bcn_cd, plid_mng_seq);
		
		LocInfoManageFilter filter = new LocInfoManageFilter();
		filter.bcn_cd = bcn_cd;
		filter.plid_mng_seq = plid_mng_seq;
		
		boolean masking = true;
		
		if(Utils.Adm.authTypeCheck(session, "handle_personal_info")) {
			masking = false;
		}
		
		LocInfoManage locInfoManage = apli001Service.getLocInfoManage(filter, masking);
	
		aact001service.regAdminAction(req, session, "APP 개인 위치정보 삭제접수 관리 상세 조회", null);
		
		return locInfoManage;
	}
	
	/**
	 * LocInfoManage 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/ITO/locInfoManage/{plid_mng_seq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyLocInfoManageg(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody LocInfoManage locInfoManage
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="plid_mng_seq") String plid_mng_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("modifyLocInfoManageg locInfoManage : {}, bcn_cd : {}, plid_mng_seq : [}", new Object[] {locInfoManage, bcn_cd, plid_mng_seq});
		
		locInfoManage.bcn_cd = bcn_cd;
		locInfoManage.plid_mng_seq = plid_mng_seq;
		
		locInfoManage.mod_usr = (String) session.getAttribute("adm_seq");
		locInfoManage.act_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = apli001Service.modifyLocInfoManage(locInfoManage);
				
		aact001service.regAdminAction(req, session, "APP 개인 위치정보 삭제접수 관리 수정", null);
		
		return result;
	}
}
