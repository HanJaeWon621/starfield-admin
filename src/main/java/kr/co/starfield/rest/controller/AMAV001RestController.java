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
import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.model.AppVer;
import kr.co.starfield.model.AppVerFilter;
import kr.co.starfield.model.AppVerListResult;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.AMAV001Service;

/**
 *  앱 버전 관리
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author eezy
 * @version 1.0,
 * @see
 * @date 2016.10.12
 */
@RestController
@RequestMapping("/admin/rest")
public class AMAV001RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(AMAV001RestController.class);
	
	@Autowired
	AMAV001Service amav001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * AppVer 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcn_cd}/operation/appVer"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regAppVer(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody AppVer appVer ) throws Exception {
		
		this.initHandler(req, res);
		
		appVer.bcn_cd = bcn_cd;
		
		appVer.reg_usr = (String) session.getAttribute("adm_seq");
		
		ll.info("AppVer bcn_cd : {}, AppVer : {}", bcn_cd, appVer);
		
		SimpleResult result = amav001Service.regAppVer(appVer);
		
		aact001service.regAdminAction(req, session, "APP 버전 관리 등록", null);
		
		return result;
	}
	
	/**
	 * AppVer 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcn_cd}/operation/appVer"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public AppVerListResult<AppVer> getAppVerList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="sort_name", required=false, defaultValue = "rnum") String sort_name
			, @RequestParam(value="sort_order", required=false, defaultValue = "desc") String sort_order
	) throws Exception {
		
		
		this.initHandler(req, res);
		
		ll.info("getAppVerList  bcn_cd : {}, offset : {}, limit : {}", new Object[] {bcn_cd, offset, limit});
		
		AppVerFilter filter = new AppVerFilter();
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		filter.sort_name = sort_name;
		filter.sort_order = sort_order;
		
		AppVerListResult<AppVer> appVerList = amav001Service.getAppVerList(filter);
		
		aact001service.regAdminAction(req, session, "APP 버전 관리 목록 조회", null);
		
		return appVerList;
	}
	
	/**
	 * AppVer 수정
	 * @return
	 */
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcn_cd}/operation/appVer/{amav_seq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyAppVer(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody AppVer appVer
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="amav_seq") String amav_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("modifyAppVer appVer : {}, bcn_cd : {}, amav_seq : [}", new Object[] {appVer, bcn_cd, amav_seq});
		
		appVer.bcn_cd = bcn_cd;
		appVer.amav_seq = amav_seq;
		
		appVer.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = amav001Service.modifyAppVer(appVer);
				
		aact001service.regAdminAction(req, session, "APP 버전 관리 수정", null);
		
		return result;
	}
	
	/**
	 * appVer 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcn_cd}/operation/appVer/{amav_seq_list}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteAppVer(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="amav_seq_list") String[] amav_seq_list) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info("deleteAppVer bcn_cd : {}, amav_seq_list : {}", bcn_cd, amav_seq_list);
		
		if(amav_seq_list.length < 1){
			BaseException be = new BaseException(ErrorCode.Operation.APPVER_SELECTED_SIZE_ERROR);
			throw be;
		}
		
		CommonDeleteModel commonDeleteModel = new CommonDeleteModel();
		commonDeleteModel.bcn_cd = bcn_cd;
		commonDeleteModel.seq_arr = amav_seq_list;

		commonDeleteModel.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = amav001Service.deleteAppVer(commonDeleteModel);
		
		aact001service.regAdminAction(req, session, "APP 버전 관리 삭제", null);
		
		return result;
	}
	
}
