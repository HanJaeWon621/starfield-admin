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

import kr.co.starfield.model.Keyword;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.SKWD003Service;

/**
 *  SKWD003(테넌트 검색어 로그) REST 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author yhkim
 * @version 1.0,
 * @see
 * @date 2016.08.17
 */
@RestController
@RequestMapping("/rest")
public class SKWD003RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(SKWD003RestController.class);
	
	@Autowired
	SKWD003Service skwd003Service;
	
	/**
	 * 테넌트 검색어 로그
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/logs/keywords"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regKeywordLog(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestBody Keyword keyword ) throws Exception {
		
		this.initHandler(req, res);
		
		keyword.bcn_cd = bcnCd;
		
		ll.info("regKeyword bcn_cd : {}, keyword : {}", bcnCd, keyword);
		
		SimpleResult result = skwd003Service.regKeywordLog(keyword.toVo());
		
		return result;
	}
}
