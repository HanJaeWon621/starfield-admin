/*
 * couponController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.service.HomeService;

/**
 *  특정 메뉴 이동 시, 인터셉터 로그인 여부 체크 테스트용 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

@Controller
@RequestMapping("/coupon")
public class CouponController {
	
	private static final Logger ll = LoggerFactory.getLogger(CouponController.class);
	
	@AuthRequired	//해당 메뉴이동 시, 로그인을 강제하는 어노테이션
	@RequestMapping(value = "/emps/post", method = RequestMethod.GET)
	public String empRegPage() {
		
		return "coupon/couponEmpPost";
	}
	
}
