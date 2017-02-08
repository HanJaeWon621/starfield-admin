package kr.co.starfield.rest.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AMBR010Service;

/**
 *  회원 포인트 바코드 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author ldh
 * @version 1.0,
 * @see
 * @date 2016.06.14
 */

@RestController
@RequestMapping("/rest")
public class AMBR010RestController extends BaseController {

private Logger ll = LoggerFactory.getLogger(AMBR010RestController.class);
	
	@Autowired
	AMBR010Service ambr010Service;
	
	
	/**
	 * 회원 포인트 바코드 생성
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/barcode/memberPoint/{cardNum}"
		, method = RequestMethod.GET
		, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
	)
	public SimpleResult memberPointBc(
			HttpServletRequest req, HttpServletResponse res, 
			@PathVariable(value="cardNum") String cardNum
	) throws Exception {
		
		this.initHandler(req, res); 
    	
    	SimpleResult result = ambr010Service.memberPointBc(cardNum);
    	
		return result;
	}
	
	
}

