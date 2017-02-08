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
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.common.StatusCode;
import kr.co.starfield.model.QNAWeb;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.SCMS008Service;

/**
 *  SCMS008(qna) REST 컨트롤러 클래스
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
@RequestMapping("/rest")
public class SCMS008RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(SCMS008RestController.class);
	
	@Autowired
	SCMS008Service scms008Service;
	
	/**
	 * QNA 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcn_cd}/qna"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regQNA(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody QNAWeb qna ) throws Exception {
		
		this.initHandler(req, res);
		
		qna.bcn_cd = bcn_cd;
		
		qna.sts = StatusCode.Common.USE.getCode();
				
		//TODO reg_usr 로그인 완료시 세션 연동
		qna.reg_usr = "";
		
		
		ll.info("regQNA bcn_cd : {}, qna : {}", bcn_cd, qna);
		
		SimpleResult result = scms008Service.regQNA(qna);
		
		return result;
	}
}
