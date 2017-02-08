/*
 * HomeRestController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.rest.controller;

import java.util.Arrays;

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
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.SimpleTenantForList;
import kr.co.starfield.model.Tenant;
import kr.co.starfield.model.TenantFilter;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ATNT001Service;

/**
 *  테넌트 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author yhkim
 * @version 1.0,
 * @see
 * @date 2016.06.10
 */

@RestController
@RequestMapping("/admin/rest")
public class ATNT001RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(ATNT001RestController.class);
	
	@Autowired
	ATNT001Service atnt001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * 테넌트 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/tenants"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"	// 생산 가능한 미디어 타입으로 매핑 제한. 요청 헤더가 일치 할 때만 요청 매핑
			, headers = "content-type=application/json" // 요청 헤더의 파라미터 값 일치로 매핑 제한.
		)
	public SimpleResult regTenant(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestBody Tenant tenant ) throws Exception {
		
		this.initHandler(req, res);

		tenant.bcn_cd = bcnCd;
		tenant.user = (String) session.getAttribute("adm_seq");
		
		ll.info("Tenant : {}", tenant);
		
		SimpleResult result = atnt001Service.regTenant(tenant);
		
		aact001service.regAdminAction(req, session, "테넌트 정보 등록", null);
		return result;
		
	}
	
	/**
	 * 테넌트 상세 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/tenants/{tntSeq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public Tenant getTenant(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="tntSeq") String tntSeq
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info(String.format("Request target tenant is %s", tntSeq));
		
		Tenant tenant = atnt001Service.getTenant(tntSeq);
		
		aact001service.regAdminAction(req, session, "테넌트 정보 상세 조회", null);
		return tenant;
	}

	/**
	 * 테넌트 목록 조회
	 * @return Tenants List
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/tenants"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<SimpleTenantForList> getTenants(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		
		this.initHandler(req, res);

		TenantFilter tenantFilter = new TenantFilter();
		tenantFilter.offset = Integer.parseInt(offset);
		tenantFilter.limit = Integer.parseInt(limit);
			
		Utils.Str.qParser(tenantFilter, q);
		
		ListResult<SimpleTenantForList> tenants  = atnt001Service.getTenantsInCategory(tenantFilter);
		
		aact001service.regAdminAction(req, session, "테넌트 정보 목록 조회", null);
		return tenants;
	}
	
	/**
	 * 테넌트 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/tenants/{tntSeq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyTenant(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="tntSeq") String tntSeq
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestBody Tenant tenant ) throws Exception {
		
		this.initHandler(req, res);
		
		tenant.tnt_seq = tntSeq;
		tenant.bcn_cd = bcnCd;
		tenant.user = (String) session.getAttribute("adm_seq");
		
		ll.info("Modify Target Tenant is {}", tenant);
		
		SimpleResult result = atnt001Service.modifyTenant(tenant);
		
		aact001service.regAdminAction(req, session, "테넌트 정보 수정", null);
		return result;
	}
	
	/**
	 * 테넌트 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/tenants/{tntSeqs}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteTenants(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @PathVariable(value="tntSeqs") String[] tntSeqs
	) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info("delete target is {}", Arrays.asList(tntSeqs));
		
		if(tntSeqs.length < 1){
			BaseException be = new BaseException(ErrorCode.Tenant.TENANT_SELECTED_SIZE_ERROR);
			throw be;
		}
		
		CommonDeleteModel eventDelete = new CommonDeleteModel();
		eventDelete.bcn_cd = bcnCd;
		eventDelete.seq_arr = tntSeqs;
		eventDelete.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = atnt001Service.deleteTenants(eventDelete);
		
		aact001service.regAdminAction(req, session, "테넌트 정보 삭제", null);
		return result;
	}
	
	/**
	 * 테넌트 카테고리 목록 조회 (팝업용)
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/tenants/in/category"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<SimpleTenantForList> getTenantsInCategory(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		
		this.initHandler(req, res);
		
		TenantFilter tenantInCategoryFilter = new TenantFilter();
		tenantInCategoryFilter.offset = Integer.parseInt(offset);
		tenantInCategoryFilter.limit = Integer.parseInt(limit);
			
		Utils.Str.qParser(tenantInCategoryFilter, q);
		
		tenantInCategoryFilter.sts = StatusCode.Common.USE.getCode();
		
		ListResult<SimpleTenantForList> tenants  = atnt001Service.getTenantsInCategory(tenantInCategoryFilter);
		
		aact001service.regAdminAction(req, session, "테넌트 정보 목록 조회 (팝업)", null);
		return tenants;
	}
	
	/**
	 * 테넌트 레디스 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/tenants/redis"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"	// 생산 가능한 미디어 타입으로 매핑 제한. 요청 헤더가 일치 할 때만 요청 매
			, headers = "content-type=application/json" // 요청 헤더의 파라미터 값 일치로 매핑 제한.
		)
	public SimpleResult syncTenant(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd) throws Exception {

		SimpleResult result = atnt001Service.syncRedisTenantSuite();
		aact001service.regAdminAction(req, session, "테넌트 레디스 동기화", null);
		return result;
	}
}
