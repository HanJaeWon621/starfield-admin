/*
 * NoticeRestController.java	1.00 2016/06/16
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
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.QNA;
import kr.co.starfield.model.QNADelete;
import kr.co.starfield.model.QNAFilter;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACMS008Service;

/**
 *  ACMS008(QNA) REST 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author eezy
 * @version 1.0,
 * @see
 * @date 2016.08.11
 */
@RestController
@RequestMapping("/admin/rest")
public class ACMS008RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(ACMS008RestController.class);
	
	@Autowired
	ACMS008Service acms008Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * qna 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/service/qna"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<QNA> getQNAList(
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
		
		ll.info("getQNAList : {},  bcn_cd : {}, offset : {}, limit : {}", new Object[] {bcn_cd, offset, limit});
		
		QNAFilter filter = new QNAFilter();
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		filter.searchType = searchType;
		filter.searchKeyword = searchKeyword;
		filter.sort_name = sort_name;
		filter.sort_order = sort_order;
		
		boolean masking = true;
		
		if(Utils.Adm.authTypeCheck(session, "handle_personal_info")) {
			masking = false;
		}
		
		ListResult<QNA> qnaList = acms008Service.getQNAList(filter, masking);
		
		aact001service.regAdminAction(req, session, "1:1 이메일 문의 관리 목록 조회", null);
		
		return qnaList;
	}
	
	/**
	 * qna 상세
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/service/qna/{qna_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public QNA getQNAs(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="qna_seq") String qna_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getQNAs bcn_cd : {}, qna_seq : {}", bcn_cd, qna_seq);
		
		QNAFilter filter = new QNAFilter();
		filter.bcn_cd = bcn_cd;
		filter.qna_seq = qna_seq;
		
		boolean masking = true;
		
		if(Utils.Adm.authTypeCheck(session, "handle_personal_info")) {
			masking = false;
		}
		
		QNA qna = acms008Service.getQNA(filter, masking);
		
		if(qna.reply_yn.equals('N')) {
			qna.reply_usr = (String) session.getAttribute("adm_id");
		}
		
		aact001service.regAdminAction(req, session, "1:1 이메일 문의 관리 상세 조회", null);
		
		return qna;
	}
	
	/**
	 * qna 수정
	 * @return
	 */
	@AuthRequired(authArr={"starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/service/qna/{qna_seq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyQNA(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody QNA qna
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="qna_seq") String qna_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("modifyQNA qna : {}, bcn_cd : {}, qna_seq : {}", new Object[] {qna, bcn_cd, qna_seq});
		
		qna.bcn_cd = bcn_cd;
		qna.qna_seq = qna_seq;
		
		qna.reply_usr = (String) session.getAttribute("adm_seq");
		qna.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = acms008Service.modifyQNA(qna);
				
		aact001service.regAdminAction(req, session, "1:1 이메일 문의 관리 답변 등록", null);
		
		return result;
	}
	
	/**
	 * qna 삭제
	 * @return
	 */
	@AuthRequired(authArr={"starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/service/qna/{qna_seq}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteQNA(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="qna_seq") String[] qna_seq_list) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("deleteQNA qna : {}, bcn_cd : {}, qna_seq : {}", new Object[] {bcn_cd, qna_seq_list});
		
		QNADelete qnaDelete = new QNADelete();
		
		qnaDelete.bcn_cd = bcn_cd;
		qnaDelete.qna_seq_list = qna_seq_list;
		qnaDelete.mod_usr = (String) session.getAttribute("adm_seq");
		
		
		SimpleResult result = acms008Service.deleteQNA(qnaDelete);
				
		aact001service.regAdminAction(req, session, "1:1 이메일 문의 관리 삭제", null);
		
		return result;
	}
}
