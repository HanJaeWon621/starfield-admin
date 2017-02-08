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
public class IndoorMapWebController {

	private Logger ll = LoggerFactory.getLogger(IndoorMapWebController.class);

	@Autowired
	ACPN001Service acpn001Service;

	// 레이아웃관리 화면
	@RequestMapping(value = "/map/layout")
	public ModelAndView layout() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("map/Layout");
		return mv;
	}

	// 맵 화면
	@RequestMapping(value = "/{bcn_cd}/map/index")
	public ModelAndView index3(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("map/index3");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	// 맵 화면
	@RequestMapping(value = "/{bcn_cd}/map/indoor/view")
	public ModelAndView indexView(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("map/index");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	// 맵 화면
	@RequestMapping(value = "/{bcn_cd}/map/indoor/edit")
	public ModelAndView indexEdit(@PathVariable(value = "bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("map/index");
		mv.addObject("bcn_cd", bcn_cd);
		return mv;
	}

	// 지점관리 화면
	@RequestMapping(value = "/map/bcns")
	public ModelAndView Bcns() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("map/Bcns");
		return mv;
	}

	// 층관리 화면
	@RequestMapping(value = "/map/floors")
	public ModelAndView Floor() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("map/Floor");
		return mv;
	}

	// FloorBlockList
	@RequestMapping(value = "/map/floorblocksingle/{fb_seq}")
	public ModelAndView FloorBlockSingle() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("map/FloorBlockSingle");
		return mv;
	}

	// FloorBlockList
	@RequestMapping(value = "/map/floorblocks")
	public ModelAndView FloorBlocks() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("map/FloorBlocks");
		return mv;
	}

	// TenantPoisList
	@RequestMapping(value = "/map/tenantpois")
	public ModelAndView TenantPois() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("map/TenantPois");
		return mv;
	}

	// FloorBlockList
	@RequestMapping(value = "/{bcn_cd}/map/floorblocksup/{fl}")
	public ModelAndView FloorBlocksUp(@PathVariable(value = "bcn_cd") String bcn_cd,
			@PathVariable(value = "fl") String fl) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("map/FloorBlockUpload");
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("fl", fl);
		return mv;
	}

	// 배포관리
	@RequestMapping(value = "/map/distmng")
	public ModelAndView DistMng() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("map/DistMng");
		return mv;
	}
}
