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
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.APLI002Service;

/**
 *  위치정보 열람 요청 및 내역
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author eezy
 * @author ldh
 * @version 1.0,
 * @see
 * @date 2016.10.10
 */
@RestController
@RequestMapping("/rest")
public class APLI002RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(APLI002RestController.class);
	
	@Autowired
	APLI002Service apli002Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * LocInfoManage 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/ITO/locInfoManagePrc"
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
			, @RequestParam(value="sh_div", required=false) String sh_div
			, @RequestParam(value="sh_text_type", required=false) String sh_text_type
			, @RequestParam(value="sh_text", required=false) String sh_text
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getLocInfoManageList  bcn_cd : {}, offset : {}, limit : {}", new Object[] {bcn_cd, offset, limit});
		
		LocInfoManage filter = new LocInfoManage();
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		filter.sort_name = sort_name;
		filter.sort_order = sort_order;
		//filter.date_search = date_search;
		filter.date_type = date_type;
		filter.stat_date = stat_date;
		filter.end_date = end_date;
		
		filter.sh_div = sh_div;
		filter.sh_text_type = sh_text_type;
		filter.sh_text = sh_text;
		
		boolean masking = true;
		
		if(Utils.Adm.authTypeCheck(session, "handle_personal_info")) {
			masking = false;
		}
		
		ListResult<LocInfoManage> locInfoManageList = apli002Service.getLocInfoManageList(filter, masking);
		
		aact001service.regAdminAction(req, session, "APP 개인 위치정보 삭제접수 관리 목록 조회", null);
		
		return locInfoManageList;
	}
	
	/**
	 * LocInfoManage 수정
	 * @return
	 */
	//@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/ITO/deletePersonLocInfoPrc"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deletePersonLocInfo(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody LocInfoManage locInfoManage
			, @PathVariable(value="bcn_cd") String bcn_cd
			) throws Exception {
		
		this.initHandler(req, res);
		
		//ll.info("modifyLocInfoManageg locInfoManage : {}, bcn_cd : {}, plid_mng_seq : [}", new Object[] {locInfoManage, bcn_cd, plid_mng_seq});
		
		locInfoManage.bcn_cd = bcn_cd;
		
		locInfoManage.mod_usr = (String) session.getAttribute("adm_seq");
		locInfoManage.act_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = apli002Service.deletePersonLocInfo(locInfoManage);
				
		aact001service.regAdminAction(req, session, "APP 개인 위치정보 삭제 처리", null);
		result.extra = locInfoManage.act_dttm+"//"+session.getAttribute("adm_id");
		return result;
	}
}
