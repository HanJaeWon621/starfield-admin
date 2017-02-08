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
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.WhatsNew;
import kr.co.starfield.model.WhatsNewDelete;
import kr.co.starfield.model.WhatsNewFilter;
import kr.co.starfield.model.WhatsNewGroup;
import kr.co.starfield.model.WhatsNewGroupDelete;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACMS011Service;

/**
 *  ACMS011(WhatsNew) REST 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author eezy
 * @version 1.0,
 * @see
 * @date 2016.09.07
 */
@RestController
@RequestMapping("/admin/rest")
public class ACMS011RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(ACMS011RestController.class);
	
	@Autowired
	ACMS011Service acms011Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * 왓츠뉴그룹 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/whatsNewGroups"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<WhatsNewGroup> getWhatsNewGroupList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="sort_name", required=false, defaultValue = "rnum") String sort_name
			, @RequestParam(value="sort_order", required=false, defaultValue = "desc") String sort_order
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getWhatsNewGroupList bcn_cd : {}", new Object[] {bcn_cd});
		
		WhatsNewFilter filter = new WhatsNewFilter();
		
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		filter.sort_name = sort_name;
		filter.sort_order = sort_order;
		
		ListResult<WhatsNewGroup> whatsNewGroupList = acms011Service.getWhatsNewGroupList(filter);
		
		aact001service.regAdminAction(req, session, "STARFIELD NOW 그룹 목록 조회", null);
		
		return whatsNewGroupList;
	}
	
	/**
	 * 왓츠뉴그룹 등록
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/whatsNewGroups"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regWhatsNewGroup(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody WhatsNewGroup whatsNewGroup
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("regWhatsNewGroup bcn_cd : {}", new Object[] {bcn_cd});
		
		whatsNewGroup.bcn_cd = bcn_cd;
		whatsNewGroup.sts = StatusCode.Common.USE.getCode();
		whatsNewGroup.reg_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult simpleResult = acms011Service.regWhatsNewGroup(whatsNewGroup);
		
		aact001service.regAdminAction(req, session, "STARFIELD NOW 그룹 등록", null);
		
		return simpleResult;
	}
	
	/**
	 * 왓츠뉴그룹 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/whatsNewGroups/{what_group_seq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyWhatsNewGroup(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="what_group_seq") String what_group_seq
			, @RequestBody WhatsNewGroup whatsNewGroup
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("modifyWhatsNewGroup bcn_cd : {}, modifyWhatsNewGroup : {}", new Object[] {bcn_cd, what_group_seq});
		
		whatsNewGroup.what_group_seq = what_group_seq;
		whatsNewGroup.bcn_cd = bcn_cd;
		whatsNewGroup.sts = StatusCode.Common.USE.getCode();
		whatsNewGroup.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult simpleResult = acms011Service.modifyWhatsNewGroup(whatsNewGroup);
		
		aact001service.regAdminAction(req, session, "STARFIELD NOW 그룹 수정", null);
		
		return simpleResult;
	}
	
	/**
	 * 왓츠뉴그룹 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/whatsNewGroups/{what_group_seq}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteWhatsNewGroup(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="what_group_seq") String[] what_group_seq_list
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("deleteWhatsNewGroup what_group_seq_list {}", Arrays.asList(what_group_seq_list));
		
		if(what_group_seq_list.length < 1){
			BaseException be = new BaseException(ErrorCode.Contents.WHATNEW_GROUP_SELECTED_SIZE_ERROR);
			throw be;
		}
		
		WhatsNewGroupDelete whatsNewGroupDelete = new WhatsNewGroupDelete();
		whatsNewGroupDelete.bcn_cd = bcn_cd;
		whatsNewGroupDelete.what_group_seq_list = what_group_seq_list;
		whatsNewGroupDelete.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult simpleResult = acms011Service.deleteWhatsNewGroup(whatsNewGroupDelete);
		
		aact001service.regAdminAction(req, session, "STARFIELD NOW 그룹 삭제", null);
		
		return simpleResult;
	}
	
	/**
	 * 왓츠뉴그룹 상세 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/whatsNewGroups/{what_group_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public WhatsNewGroup getWhatsNewGroupList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="what_group_seq") String what_group_seq
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getWhatsNewGroupList bcn_cd : {}, what_group_seq : {}", new Object[] {bcn_cd, what_group_seq});
		
		WhatsNewFilter filter = new WhatsNewFilter();
		
		filter.bcn_cd = bcn_cd;
		filter.what_group_seq = what_group_seq;
		
		WhatsNewGroup whatsNewGroup = acms011Service.getWhatsNewGroup(filter);
		
		aact001service.regAdminAction(req, session, "STARFIELD NOW 상세 조회", null);
		
		return whatsNewGroup;
	}
	
	
	/**
	 * WhatsNew 등록
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/whatsNewGroups/{what_group_seq}"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regWhatsNew(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="what_group_seq") String what_group_seq
			, @RequestBody WhatsNew whatsNew
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("regWhatsNew bcn_cd : {}, what_group_seq : {}", new Object[] {bcn_cd, what_group_seq});
		
		whatsNew.what_group_seq = what_group_seq;
		whatsNew.sts = StatusCode.Common.USE.getCode();
		whatsNew.reg_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult simpleResult = acms011Service.regWhatsNew(whatsNew);
		
		aact001service.regAdminAction(req, session, "STARFIELD NOW 등록", null);
		
		return simpleResult;
	}
	
	/**
	 * WhatsNew 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/whatsNewGroups/{what_group_seq}/{what_new_seq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyWhatsNew(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="what_group_seq") String what_group_seq
			, @PathVariable(value="what_new_seq") String what_new_seq
			, @RequestBody WhatsNew whatsNew
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("modifyWhatsNew bcn_cd : {}, what_group_seq : {}, what_new_seq : {}", new Object[] {bcn_cd, what_group_seq, what_new_seq});
		
		whatsNew.what_group_seq = what_group_seq;
		whatsNew.what_new_seq = what_new_seq;
		whatsNew.sts = StatusCode.Common.USE.getCode();
		whatsNew.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult simpleResult = acms011Service.modifyWhatsNew(whatsNew);
		
		aact001service.regAdminAction(req, session, "STARFIELD NOW 수정", null);
		
		return simpleResult;
	}
	
	/**
	 * WhatsNew 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/whatsNewGroups/{what_group_seq}/{what_new_seq}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteWhatsNew(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="what_group_seq") String what_group_seq
			, @PathVariable(value="what_new_seq") String[] what_new_seq_list
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("deleteWhatsNew bcn_cd : {}, what_group_seq : {}, what_new_seq_list : {}", new Object[] {bcn_cd, what_group_seq, what_new_seq_list});
		
		if(what_new_seq_list.length < 1){
			BaseException be = new BaseException(ErrorCode.Contents.BANNER_SELECTED_SIZE_ERROR);
			throw be;
		}
		
		WhatsNewDelete whatsNewDelete = new WhatsNewDelete();
		
		whatsNewDelete.what_group_seq = what_group_seq;
		whatsNewDelete.what_new_seq_list = what_new_seq_list;
		whatsNewDelete.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult simpleResult = acms011Service.deleteWhatsNew(whatsNewDelete);
		
		aact001service.regAdminAction(req, session, "STARFIELD NOW 삭제", null);
		
		return simpleResult;
	}
}
