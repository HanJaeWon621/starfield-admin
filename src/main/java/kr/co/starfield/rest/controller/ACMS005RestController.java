/*
 * NoticeRestController.java	1.00 2016/06/16
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
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Notice;
import kr.co.starfield.model.NoticeFilter;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACMS005Service;

/**
 *  Notice
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author eezy
 * @version 1.0,
 * @see
 * @date 2016.06.20
 */
@RestController
@RequestMapping("/admin/rest")
public class ACMS005RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(ACMS005RestController.class);
	
	@Autowired
	ACMS005Service acms005Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * 공지사항 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/notices"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<Notice> getNoticeList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="sort_name", required=false, defaultValue = "rnum") String sort_name
			, @RequestParam(value="sort_order", required=false, defaultValue = "desc") String sort_order
			, @RequestParam(value="searchType", required=false, defaultValue = "1") int searchType
			, @RequestParam(value="searchKeyword", required=false, defaultValue = "") String searchKeyword
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getNotiList bcn_cd : {}, offset : {}, limit : {}", new Object[] {bcn_cd, offset, limit});
		
		NoticeFilter filter = new NoticeFilter();
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		filter.sts = StatusCode.Common.USE.getCode();
		filter.sort_name = sort_name;
		filter.sort_order = sort_order;
		filter.searchType = searchType;
		filter.searchKeyword = searchKeyword;
		
		ListResult<Notice> notiList = acms005Service.getNoticeList(filter);
		
		aact001service.regAdminAction(req, session, "공지사항 목록 조회", null);
		
		return notiList;
	}
	
	/**
	 * 공지사항 상세
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/notices/{noti_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public Notice getNotice(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="noti_seq") String noti_seq) throws Exception {
		
		this.initHandler(req, res);
		ll.info("getNoti bcn_cd : {}, noti_seq : {}", bcn_cd, noti_seq);
		
		NoticeFilter filter = new NoticeFilter();
		filter.bcn_cd = bcn_cd;
		filter.noti_seq = noti_seq;
		filter.sts = StatusCode.Common.USE.getCode();
	
		Notice notice = acms005Service.getNotice(filter);
	
		aact001service.regAdminAction(req, session, "공지사항 상세 조회", null);
		
		return notice;
	}
	
	/**
	 * 공지사항 등록
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/notices"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regNotice(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody Notice notice
			, @PathVariable(value="bcn_cd") String bcn_cd) throws Exception {
		
		this.initHandler(req, res);
		ll.info("regNotice noti : {}, bcn_cd : {}", new Object[] {notice, bcn_cd});
		
		notice.bcn_cd = bcn_cd;
		
		notice.reg_usr = (String) session.getAttribute("adm_seq");;
		
		SimpleResult result = acms005Service.regNotice(notice);
				
		aact001service.regAdminAction(req, session, "공지사항 등록", null);
		
		return result;
	}
	
	
	/**
	 * 공지사항 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/notices/{noti_seq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyNotice(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody Notice notice
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="noti_seq") String noti_seq) throws Exception {
		
		this.initHandler(req, res);
		ll.info("modifyNoti noti : {}, bcn_cd : {}, noti_seq : {}", new Object[] {notice, bcn_cd, noti_seq});
		
		notice.bcn_cd = bcn_cd;
		notice.noti_seq = noti_seq;
		
		notice.mod_usr = (String) session.getAttribute("adm_seq");;
		
		SimpleResult result = acms005Service.modifyNotice(notice);
				
		aact001service.regAdminAction(req, session, "공지사항 수정", null);
		
		return result;
	}


	/**
	 * 공지사항 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/notices/{noti_seq_list}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteNotice(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="noti_seq_list") String[] noti_seq_list
	) throws Exception {
		
		this.initHandler(req, res);
		ll.info("deleteNotice noti_seq_list {}", Arrays.asList(noti_seq_list));
		
		if(noti_seq_list.length < 1){
			BaseException be = new BaseException(ErrorCode.Board.NOTICE_SELECTED_SIZE_ERROR);
			throw be;
		}
		
		CommonDeleteModel commonDeleteModel = new CommonDeleteModel();
		commonDeleteModel.bcn_cd = bcn_cd;
		commonDeleteModel.seq_arr = noti_seq_list;
		
		commonDeleteModel.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult simpleResult = acms005Service.deleteNotice(commonDeleteModel);
		
		aact001service.regAdminAction(req, session, "공지사항 삭제", null);
		
		return simpleResult;
	}
}
