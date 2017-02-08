package kr.co.starfield.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.rest.controller.BaseController;
import kr.co.starfield.service.LocaleService;

/**
 *  locale 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

@Controller
public class LocaleController extends BaseController {

	
	@Autowired
	LocaleService localeService;
	
	@RequestMapping(value = "/locale"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8")
	@ResponseBody
    public SimpleResult regLanguage(
    		HttpServletRequest req, HttpServletResponse res, HttpSession session
    		, @RequestParam("file") MultipartFile file) throws BaseException, Exception {
		
		SimpleResult result = localeService.regLanguage(file);
			
		return result;
	}
	
}
