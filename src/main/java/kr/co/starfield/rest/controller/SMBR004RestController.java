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

import kr.co.starfield.model.LoginLog;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.SMBR004Service;

/**
 *  로그인 로그
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author eezy
 * @version 1.0,
 * @see
 * @date 2016.07.20
 */

@RestController
@RequestMapping("/rest")
public class SMBR004RestController extends BaseController {

private Logger ll = LoggerFactory.getLogger(ACTG001RestController.class);
	
	@Autowired
	SMBR004Service smbr004Service;
	
	
	/**
	 * 로그인 로그 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcn_cd}/loginLog"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"	// 생산 가능한 미디어 타입으로 매핑 제한. 요청 헤더가 일치 할 때만 요청 매
			, headers = "content-type=application/json" // 요청 헤더의 파라미터 값 일치로 매핑 제한.
		)
	public SimpleResult regLikeEvent(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody LoginLog loginLog ) throws Exception {
		
		this.initHandler(req, res);
		
		
		return smbr004Service.regLoginLog(loginLog.toVo());
	}
	
}

