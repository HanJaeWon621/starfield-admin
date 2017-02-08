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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.Event;
import kr.co.starfield.model.EventFilter;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.QNA;
import kr.co.starfield.model.QNAFilter;
import kr.co.starfield.model.SADM003;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACMS008Service;
import kr.co.starfield.service.ACMS010Service;

/**
 *  1:1메일 문의
 *
 * Copyright Copyright (c) 2016
 * Company 
 *
 * @author EEZY
 * @version	1.0,
 * @see
 * @date 2016.09.06
 */

@Controller
@RequestMapping("/")  
public class ACMS008WebController {

	private Logger ll = LoggerFactory.getLogger(ACMS008WebController.class);
	
	@Autowired
	ACMS008Service acms008Service;
	
	@Autowired
    AACT001Service aact001service;
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/service/qna"
			, method = RequestMethod.GET)
	public ModelAndView getQNAList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("service/ACMSW008");
		
		aact001service.regAdminAction(req, session, "1:1 이메일 문의 조회 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/service/qna/{qna_seq}"
			, method = RequestMethod.GET)
	public ModelAndView getQNAList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="qna_seq") String qna_seq) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("qna_seq", qna_seq);
		mv.setViewName("service/ACMSW008D");
		
		aact001service.regAdminAction(req, session, "1:1 이메일 문의 상세/답변 페이지 이동", null);
		
		return mv;
	}
	
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/service/qna/download"
			, method = RequestMethod.GET
			, produces = "application/vnd.ms-excel")
	public ModelAndView getQNAExcelDownload(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", defaultValue = "0") int offset
 			, @RequestParam(value="limit", defaultValue = "-1") int limit
	) throws Exception {
		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "QNA_list_01_" + strToday + ".xls\"");
		
		QNAFilter qnaFilter = new QNAFilter();
		
		qnaFilter.bcn_cd = bcn_cd;
		qnaFilter.offset = offset;
		qnaFilter.limit = limit;
		qnaFilter.sort_name = "rnum";
		qnaFilter.sort_order = "desc";
		
		ll.info("getQNAExcelDownload EventFilter : {}", qnaFilter);

		boolean masking = true;
		
		if(Utils.Adm.authTypeCheck(session, "handle_personal_info")) {
			masking = false;
		}
		
		ListResult<QNA> qnaList = acms008Service.getQNAList(qnaFilter, masking);
		
		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = {"NO", "고객 성명", "고객 이메일", "문의 제목", "문의 내용", "문의 등록일", "답변 여부", "관리자 ID", "답변 내용", "답변 등록일"};
		String[] colNameList = {"rnum", "cust_nm", "cust_email", "qutn_titl", "qutn_cont", "qutn_dttm", "reply_yn", "reply_usr", "reply_cont", "reply_dttm"};
		
		setting.put("sheetName", "문의사항 목록");
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", qnaList.list);
		
		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);
		
		aact001service.regAdminAction(req, session, "1:1 이메일 excel 다운로드", null);
		
		return mav;
	}
}
