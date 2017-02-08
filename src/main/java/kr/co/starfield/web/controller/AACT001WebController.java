package kr.co.starfield.web.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.common.BaseException;
import kr.co.starfield.model.AdminFilter;
import kr.co.starfield.model.SADM001;
import kr.co.starfield.service.AACT001Service;

/**
 * 관리자 계정관리 웹 컨트롤러
 *
 * Copyright Copyright (c) 2016
 *
 * @author jg.jo
 * @version	1.0,
 * @see
 * @date 2016.10.06
 */

@Controller
@RequestMapping("/")
public class AACT001WebController {
	
	private Logger ll = LoggerFactory.getLogger(AACT001WebController.class);

	@Autowired
	AACT001Service aact001service;
	
	/**
	 * 패스워드 변경
	 * @param req
	 * @param res
	 * @param session
	 * @return
	 */
	@RequestMapping("/{bcn_cd}/account/password/self")
	public ModelAndView changePassword(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) {
		
		ModelAndView mv = new ModelAndView();
		
		String adm_id = (String) session.getAttribute("adm_id");
		
		String viewName = null;

		if(adm_id != null) {
			viewName = "account/AACTW002";
		} else {
			viewName = "redirect:/login";
		}
		
		mv.setViewName(viewName);
		mv.addObject("bcn_cd", bcn_cd);
		
		// 사용자 액션 로그 남기기
		aact001service.regAdminAction(req, session, "패스워드 변경 페이지", null);
		
		return mv;
	}
	
	/**
	 * 개인 정보 수정
	 * @param req
	 * @param res
	 * @param session
	 * @return
	 */
	@RequestMapping("/{bcn_cd}/account/self")
	public ModelAndView modifyAccount(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) {
		
		ModelAndView mv = new ModelAndView();
		
		String adm_id = (String) session.getAttribute("adm_id");
		
		String viewName = null;

		if(adm_id != null) {
			viewName = "account/AACTW003";
		} else {
			viewName = "redirect:/login";
		}
		
		mv.setViewName(viewName);
		mv.addObject("bcn_cd", bcn_cd);
		
		
		// 사용자 액션 로그 남기기
		aact001service.regAdminAction(req, session, "관리자 개인정보 수정 페이지", null);
		
		return mv;
	}
	
	/**
	 * 관리자 계정 관리 목록 페이지
	 * @param req
	 * @param res
	 * @param session
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "handle_personal_info"})
	@RequestMapping("/{bcn_cd}/account/admins")
	public ModelAndView adminList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) {

		ModelAndView mv = new ModelAndView();
		
		String viewName = "account/AACTW004";
		
		mv.setViewName(viewName);
		mv.addObject("bcn_cd", bcn_cd);
		
		// 사용자 액션 로그 남기기
		aact001service.regAdminAction(req, session, "관리자 계정 관리 페이지", null);
		
		return mv;
	}
	
	
	/**
	 * 관리자 계정 생성 페이지
	 * @param req
	 * @param res
	 * @param session
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master"})
	@RequestMapping("/{bcn_cd}/account/admin/reg")
	public ModelAndView addAccount(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) {
		
		ModelAndView mv = new ModelAndView();
		
		String viewName = "account/AACTW005";
		
		mv.setViewName(viewName);
		mv.addObject("bcn_cd", bcn_cd);
		
		return mv;
	}
	
	/**
	 * 관리자 수정 페이지
	 * @param req
	 * @param res
	 * @param session
	 * @param bcn_cd
	 * @param adm_seq
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master"})
	@RequestMapping("/{bcn_cd}/account/admins/{adm_seq}")
	public ModelAndView admin(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="adm_seq") String adm_seq) {

		ModelAndView mv = new ModelAndView();
		
		String viewName = "account/AACTW006";
		
		ll.info("account modify target admin seq is {}", adm_seq);
		
		mv.setViewName(viewName);
		mv.addObject("bcn_cd", bcn_cd);
		mv.addObject("adm_seq", adm_seq);
		
		// 사용자 액션 로그 남기기
		aact001service.regAdminAction(req, session, "관리자 계정 수정 페이지", null);
		
		return mv;
	}
	
	
	/**
	 * 관리자 활동이력 조회 페이지
	 * @param req
	 * @param res
	 * @param session
	 * @param bcn_cd
	 * @return
	 * @throws BaseException
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/account/actionLog"
			, method = RequestMethod.GET)
	public ModelAndView getBlogList(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd) throws BaseException {
		
		ModelAndView mv = new ModelAndView();	
		
		mv.setViewName("account/AADMW004");
		mv.addObject("bcn_cd", bcn_cd);
		
		aact001service.regAdminAction(req, session, "관리자 활동이력 조회 페이지 이동", null);
		
		return mv;
	}
	
	/**
	 * 관리자 리스트 엑셀 다운로드
	 * @param req
	 * @param res
	 * @param session
	 * @param bcn_cd
	 * @return
	 * @throws Exception
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master"})
	@RequestMapping(value = "/{bcn_cd}/account/admins/excel"
			, method = RequestMethod.GET
			, produces = "application/vnd.ms-excel")
	public ModelAndView getAdminsExcelDownload(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		
		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "admin_excel_list_" + strToday + ".xls\"");
		
		ll.info("getAdminsExcelDownload bcn_cd : {}", bcn_cd);
		
		AdminFilter filter = new AdminFilter();
		filter.bcn_cd = bcn_cd;
		filter.adm_seq = (String) session.getAttribute("adm_seq");
		
		ll.info("admin list filter data is {}", filter);
		
		SADM001[] result = aact001service.getAdmins(filter);
		
		List<SADM001> admins = Arrays.asList(result);
		
		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = {"번호", "지점코드", "아이디", "소속", "등록일자", "상태"};
		String[] colNameList = {"rnum", "bcn_cd", "adm_id", "adm_dept", "reg_dttm", "sts_desc"};
		
		setting.put("sheetName", "관리자 리스트");
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", admins);
		
		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);
		
		aact001service.regAdminAction(req, session, "관리자 excel 다운로드", null);
		
		return mav;
	}
}
