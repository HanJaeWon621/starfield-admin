package kr.co.starfield.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.model.SADM003;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.AATH001Service;


/**
 * 관리자 웹 메인 컨트롤러
 *
 * Copyright Copyright (c) 2016
 *
 * @author jg.jo
 * @version	1.0,
 * @see
 * @date 2016.10.03
 */

@Controller
public class AMAN001WebController {
	
	@Autowired
	AACT001Service aact001service;
	
	@Autowired
	AATH001Service aath001service;

	private Logger ll = LoggerFactory.getLogger(AMAN001WebController.class);
	
	@RequestMapping("/")
	public ModelAndView root(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		
		ModelAndView mv = new ModelAndView();
		
		String adm_seq = (String) session.getAttribute("adm_seq");
		
		Object sts = session.getAttribute("sts");
		
		String viewName = null;

		if(adm_seq != null && sts != null && (Integer) sts == 101) {
			
			SADM003[] auths = (SADM003[]) session.getAttribute("authTypeArr");
			
			if(auths == null) {
				auths = aath001service.getAdminAuthTypes(adm_seq);
				session.setAttribute("authTypeArr", auths);
			}
			
			viewName = "redirect:/01/dashboard";
			
		} else {
			viewName = "redirect:/login";
		}
		
		mv.setViewName(viewName);
		
		return mv;
	}
	
	@RequestMapping("/login")
	public ModelAndView empLogin(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value="returnURI", required=false) String returnURI) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("account/AACTW001");
		
		ll.info("returnURI : {}", returnURI);
		
		if(returnURI != null) {
			mv.addObject("returnURI", returnURI);
		}
		
		// 사용자 액션 로그 남기기
		aact001service.regAdminAction(req, session, "로그인 페이지", null);
		
		return mv;
	}
	
	@RequestMapping("/logout")
	public void empLogout(HttpServletRequest req, HttpServletResponse res, HttpSession session) throws Exception {
		
		// 사용자 액션 로그 남기기
		
		if(session != null) {
			
			aact001service.regAdminAction(req, session, "로그아웃", null);
		
			session.invalidate();
		}
		
		res.sendRedirect("/login");
	}
	
	
	/**
	 * haproxy에서 rest server의 생존여부를 확인하기 위해 /check라는 url을 둔다.
	 * @return String
	 */
	@RequestMapping("/access/web/auth/fail")
	public ModelAndView authFail() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("common/alertAndRedirect");
		
		mv.addObject("message", "해당 페이지에 대한 권한이 없습니다. 메인페이지로 이동합니다.");
		mv.addObject("returnUrl", "/");
		
		return mv;
	}
	
	/**
	 * haproxy에서 rest server의 생존여부를 확인하기 위해 /check라는 url을 둔다.
	 * @return String
	 */
	@RequestMapping("/check")
	public String checkPage() {
		return "welcome";
	}
	
	
	
}
