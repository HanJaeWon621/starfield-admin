package kr.co.starfield.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.model.SADM002;
import kr.co.starfield.service.AATH001Service;


/**
 * 관리자 역할 / 권한 관리 RestController
 * @author jojong-gyun
 *
 */
@RestController
@RequestMapping("/admin/rest/auth")
public class AATH001RestController {
	
	private Logger ll = LoggerFactory.getLogger(AATH001RestController.class);
	
	@Autowired
	AATH001Service aath001Service;

	/**
	 * 관리자 역할 리스트 (combo box용)
	 * @param req
	 * @param res
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/roles"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SADM002[] adminRoles(HttpServletRequest req, HttpServletResponse res, HttpSession session) throws Exception {
		res.setHeader("Cache-Control", "no-cache");
		
		SADM002[] roles = aath001Service.getAdminRoles(session);
			
		return roles;
	}
	
}
