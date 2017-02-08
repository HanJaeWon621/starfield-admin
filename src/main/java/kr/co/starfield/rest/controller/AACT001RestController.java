/*
 * AuthorizationRestController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
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
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.AdminActionLog;
import kr.co.starfield.model.AdminActionLogFilter;
import kr.co.starfield.model.AdminFilter;
import kr.co.starfield.model.AdminRoleCheckFilter;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.LoginResult;
import kr.co.starfield.model.SADM001;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;

/**
 *  관리자 계정 인증 / 관리 컨트롤러
 *
 * Copyright Copyright (c) 2016
 *
 * @author jg.jo
 * @version	1.0,
 * @see
 * @date 2016.10.05
 */

@RestController
@RequestMapping("/admin/rest")
public class AACT001RestController {

	private Logger ll = LoggerFactory.getLogger(AACT001RestController.class);
	
	@Autowired
	AACT001Service aact001service;
	
	/**
	 * 관리자 로그인
	 * @param req
	 * @param res
	 * @param session
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auth/login"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public LoginResult loginCheck(HttpServletRequest req, HttpServletResponse res, HttpSession session, 
			@RequestBody SADM001 param) throws Exception {
		res.setHeader("Cache-Control", "no-cache");
		
		if(Utils.Str.isEmpty(param.adm_id) || Utils.Str.isEmpty(param.adm_pw)) {
			ll.debug("admin id or password empty {}", param);
			throw new BaseException(ErrorCode.Auth.ADM_LOGIN_PARAM_EMPTY);
		}
		
		LoginResult result = new LoginResult();
		result = aact001service.loginCheck(param, session);
		
		//로그인 전에 열렸던 이전 페이지가 있는 경우
//		if(param.returnURI != null || param.returnURI != "") {
//			result.returnURI = param.returnURI;
//		}
		
/*		if(session.getAttribute("returnURI") != null) {
			result.returnURI = (String) session.getAttribute("returnURI");
		}*/
		
		// 사용자 액션 로그 남기기
		aact001service.regAdminAction(req, session, "관리자 로그인", null);
		
		ll.info(" login result is {}", result);
		
		return result;
	}
	

	/**
	 * 관리자가 다른 관리자 패스워드 재생성
	 * @param req
	 * @param res
	 * @param session
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master"})
	@RequestMapping(value = "/account/password"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult resetPassword(HttpServletRequest req, HttpServletResponse res, HttpSession session, 
			@RequestBody SADM001 param) throws Exception {
		res.setHeader("Cache-Control", "no-cache");
		
		if(Utils.Str.isEmpty(param.adm_seq)) {
			ll.debug("reset admin password adm_seq is empty");
			throw new BaseException(ErrorCode.Common.MUST_REQUIRE_PARAMETER_EMPTY);
		}
		
		SimpleResult result = new SimpleResult();
		result = aact001service.resetPassword(param, session);
		
		// 사용자 액션 로그 남기기
		aact001service.regAdminAction(req, session, "관리자 비밀번호 재생성", null);
			
		return result;
	}
	
	
	/**
	 * 관리자 패스워드 변경
	 * @param req
	 * @param res
	 * @param session
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/account/password/self"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult changePassword(HttpServletRequest req, HttpServletResponse res, HttpSession session, 
			@RequestBody SADM001 param) throws Exception {
		res.setHeader("Cache-Control", "no-cache");
		
		if(Utils.Str.isEmpty(param.adm_pw) || Utils.Str.isEmpty(param.adm_new_pw)) {
			ll.debug("admin password or new password empty {}", param);
			throw new BaseException(ErrorCode.Auth.ADM_LOGIN_PARAM_EMPTY);
		}
		
		SimpleResult result = new SimpleResult();
		result = aact001service.changePassword(param, session);
		
		// 사용자 액션 로그 남기기
		aact001service.regAdminAction(req, session, "관리자 비밀번호 수정", null);
			
		return result;
	}
	
	
	/**
	 * 자기정보 조회
	 * @param req
	 * @param res
	 * @param session
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/account/self"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SADM001 getAdminInfo(HttpServletRequest req, HttpServletResponse res, HttpSession session) throws Exception {
		res.setHeader("Cache-Control", "no-cache");
		
		SADM001 sadm001 = aact001service.getAdminInfo(session);
		
		// 사용자 액션 로그 남기기
		aact001service.regAdminAction(req, session, "관리자 개인정보 조회", null);
			
		return sadm001;
	}
	
	/**
	 * 자신 정보 수정
	 * @param req
	 * @param res
	 * @param session
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/account/self"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult putAdminInfo(HttpServletRequest req, HttpServletResponse res, HttpSession session, 
			@RequestBody SADM001 param) throws Exception {
		res.setHeader("Cache-Control", "no-cache");
		
		if(Utils.Str.isEmpty(param.adm_nm)
				|| Utils.Str.isEmpty(param.adm_dept)
				|| Utils.Str.isEmpty(param.adm_tel_num1)
				|| Utils.Str.isEmpty(param.adm_tel_num2)
				|| Utils.Str.isEmpty(param.adm_tel_num3)
				|| Utils.Str.isEmpty(param.adm_cel_num1)
				|| Utils.Str.isEmpty(param.adm_cel_num2)
				|| Utils.Str.isEmpty(param.adm_cel_num3)
				|| Utils.Str.isEmpty(param.adm_email)
				|| Utils.Str.isEmpty(param.term_agre_yn)
				) {
			ll.debug("admin info change compulsory data is null {}", param);
			throw new BaseException(ErrorCode.Auth.ADM_CHANGE_INFO_COMPULSORY_DATA_EMPTY);
		}
		
		SimpleResult result = aact001service.changeAdminInfo(param, session);
		
		ll.info("result is {}", result);
		
		// 사용자 액션 로그 남기기
		aact001service.regAdminAction(req, session, "관리자 개인정보 수정", null);
			
		return result;
	}
	
	
	/**
	 * 관리자 아이디 중복 체크
	 * @param req
	 * @param res
	 * @param session
	 * @param adm_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/account/has/admin"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult hasAdmin(HttpServletRequest req, HttpServletResponse res, HttpSession session 
			, @RequestParam(value="adm_id", required=true) String adm_id) throws Exception {
		res.setHeader("Cache-Control", "no-cache");
		
		if(Utils.Str.isEmpty(adm_id)) {
			ll.debug("admin id is empty {}", adm_id);
			throw new BaseException(ErrorCode.Auth.ADM_ID_EMPTY);
		}
		
		SimpleResult result = aact001service.overlapCheckAdminId(adm_id);
		
		ll.info("result is {}", result);
		
		// 사용자 액션 로그 남기기
				aact001service.regAdminAction(req, session, "관리자 아이디 중복 체크", null);
			
		return result;
	}
	
	/**
	 * 관리자 비밀번호 임시 생성
	 * @param req
	 * @param res
	 * @param session
	 * @param adm_id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/account/password/temp"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult makeTempPassword(HttpServletRequest req, HttpServletResponse res, HttpSession session) throws Exception {
		res.setHeader("Cache-Control", "no-cache");
		
		SimpleResult result = aact001service.makeTempPassword();
		
		ll.info("make temp password result is {}", result);
		
		// 사용자 액션 로그 남기기
		aact001service.regAdminAction(req, session, "관리자 임시 비밀번호 생성", null);
			
		return result;
	}
	
	/**
	 * 관리자 계정 생성
	 * @param req
	 * @param res
	 * @param session
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/account/admins"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	@AuthRequired(authArr={"system", "starfield", "store_master"})
	public SimpleResult regAccount(HttpServletRequest req, HttpServletResponse res, HttpSession session, @RequestBody SADM001 param) throws Exception {
		res.setHeader("Cache-Control", "no-cache");
		
		if(Utils.Str.isEmpty(param.adm_id) 
				|| Utils.Str.isEmpty(param.adm_pw)
				|| Utils.Str.isEmpty(param.role_seq)) {
			ll.debug("reg account parameter empty {}", param);
			throw new BaseException(ErrorCode.Common.MUST_REQUIRE_PARAMETER_EMPTY);
		}
		
		// TODO : 지점코드 정적으로 넣어둔다.!!
		param.bcn_cd = "01";
		param.reg_usr = (String) session.getAttribute("adm_seq");
		param.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = aact001service.regAccount(param);
		
		// 사용자 액션 로그 남기기
		aact001service.regAdminAction(req, session, "관리자 계정 생성", null);
			
		return result;
	}
	
	
	/**
	 * 관리자 계정 수정
	 * @param req
	 * @param res
	 * @param session
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master"})
	@RequestMapping(value = "/account/admins"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modAccount(HttpServletRequest req, HttpServletResponse res, HttpSession session, @RequestBody SADM001 param) throws Exception {
		res.setHeader("Cache-Control", "no-cache");
		
		if(Utils.Str.isEmpty(param.adm_seq) 
				|| Utils.Str.isEmpty(param.role_seq)) {
			ll.debug("modify account parameter empty {}", param);
			throw new BaseException(ErrorCode.Common.MUST_REQUIRE_PARAMETER_EMPTY);
		}
				
		param.sts = param.sts == 101 ? 1 : param.sts == 102 ? 3 : 777;
		param.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = aact001service.modAccount(param);
		
		// 사용자 액션 로그 남기기
		aact001service.regAdminAction(req, session, "관리자 계정 수정", param.adm_seq);
			
		return result;
	}
	
	
	/**
	 * 관리자 목록 정보
	 * @param req
	 * @param res
	 * @param session
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "handle_personal_info"})
	@RequestMapping(value = "/account/{bcn_cd}/admins"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SADM001[] getAdmins(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd ) throws Exception {
		res.setHeader("Cache-Control", "no-cache");
		
		AdminFilter filter = new AdminFilter();
		filter.bcn_cd = bcn_cd;
		filter.adm_seq = (String) session.getAttribute("adm_seq");
		
		ll.info("admin list filter data is {}", filter);
		
		SADM001[] result = aact001service.getAdmins(filter);
		
		return result;
	}
	
	@RequestMapping(value = "/account/{bcn_cd}/admins/{adm_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SADM001 getAdmin(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="adm_seq") String adm_seq ) throws Exception {
		res.setHeader("Cache-Control", "no-cache");
		
		if(Utils.Str.isEmpty(adm_seq)) {
			ll.debug("get change target account parameter empty {}", adm_seq);
			throw new BaseException(ErrorCode.Common.MUST_REQUIRE_PARAMETER_EMPTY);
		}
		
		AdminRoleCheckFilter filter = new AdminRoleCheckFilter();
		filter.bcn_cd = bcn_cd;
		filter.actor_adm_seq = (String) session.getAttribute("adm_seq");
		filter.target_adm_seq = adm_seq;
		
		ll.info("admin list filter data is {}", filter);
		
		int canIDoThat = aact001service.canIChangeOtherAccount(filter);
		
		SADM001 result = null;
		
		if(canIDoThat == 101) {
			
			result = aact001service.getAdminInfo(adm_seq);
			
		} else {
		
			ll.debug(" modify admin account permission denied actor seq is {}", adm_seq);
			throw new BaseException(ErrorCode.Auth.PERMISSION_DENIED);
		}
		
		return result;
	}
	
	
	/**
	 * 관리자 액션 로그 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/account/actionLog"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<AdminActionLog> getAdminActionLogList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="sort_name", required=false, defaultValue = "rnum") String sort_name
			, @RequestParam(value="sort_order", required=false, defaultValue = "desc") String sort_order
			, @RequestParam(value="start_date") String start_date
			, @RequestParam(value="end_date") String end_date
			, @RequestParam(value="searchKeyword") String searchKeyword
	) throws Exception {
		res.setHeader("Cache-Control", "no-cache");
		
		ll.info("getAdminActionLogList offset : {}, limit : {}", new Object[] {offset, limit, sort_name, sort_order, start_date, end_date});
		
		AdminActionLogFilter filter = new AdminActionLogFilter();
		filter.offset = offset;
		filter.limit = limit;
		filter.sort_name = sort_name;
		filter.sort_order = sort_order;
		filter.start_date = start_date;
		filter.end_date = end_date;
		filter.searchKeyword = searchKeyword;
		
		boolean masking = true;
		
		if(Utils.Adm.authTypeCheck(session, "handle_personal_info")) {
			masking = false;
		}
		
		ListResult<AdminActionLog> adminActionLogList = aact001service.getAdminActionLogList(filter, masking);
		
		aact001service.regAdminAction(req, session, "관리자 활동이력 조회", null);
		
		return adminActionLogList;
	}
}
