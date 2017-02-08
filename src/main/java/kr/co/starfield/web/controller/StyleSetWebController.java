/*
 * AuthorizationController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.ACPN005;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.service.ACPN001Service;

/**
 * 로그인 및 사용자인증 관련 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016 Company Paprika
 *
 * @author xxx
 * @version 1.0,
 * @see
 * @date 2016.04.11
 */

@Controller
@RequestMapping("/")
public class StyleSetWebController {

	private Logger ll = LoggerFactory.getLogger(StyleSetWebController.class);

	@Autowired
	ACPN001Service acpn001Service;

	// 스타일 세트 리스트
	@RequestMapping(value = "/styleSets")
	public ModelAndView styleSets() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("style/StyleSets");
		return mv;
	}

	
}
