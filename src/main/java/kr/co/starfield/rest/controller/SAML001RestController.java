/*
 * couponController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.rest.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.ResultPush;
import kr.co.starfield.model.SAML001;
import kr.co.starfield.model.SAML002;
import kr.co.starfield.model.SAML003;
import kr.co.starfield.model.SAML004;
import kr.co.starfield.model.SCPN005;
import kr.co.starfield.model.SCPN006;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SAML002Vo;
import kr.co.starfield.model.vo.SAML003Vo;
import kr.co.starfield.model.vo.SAML004Vo;
import kr.co.starfield.service.SAML001Service;

/**
 * 로그, 교환쿠폰 웰컴쿠폰 푸시
 *
 * @author ldh
 * @version	1.0,
 * @see
 * @date 2016.08.01
 */

@RestController
@RequestMapping("/rest")  
public class SAML001RestController extends BaseController {

	@Autowired
	SAML001Service sopr001service;
	
	private static final Logger ll = LoggerFactory.getLogger(SAML001RestController.class);
	
	
	/**
	 * 앱 실행 로그
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/appLog/appExecuteLog"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult appExecuteLog(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody SAML001 saml001
			) throws Exception {
		
		SimpleResult result = new SimpleResult();
		
		result = sopr001service.appExecuteLog(saml001);
		
		return result;
	}
	
	
	/**
	 * 앱 최초 로그인 로그
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/appLog/{uuid}/appFirstLoginLog"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SCPN005 appFirstLoginLog(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable String uuid
			) throws Exception {
		
		SCPN005 scpn005 = sopr001service.appFirstLoginLog(uuid);
		
		return scpn005;
	}
	
	
	/**
	 * 앱 액션 로그
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/appLog/{uuid}/appActionLog"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult appActionLog(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody SAML003 saml003
			, @PathVariable String uuid
			) throws Exception {
		
		SimpleResult result = new SimpleResult();
		
		result = sopr001service.appActionLog(saml003.log_type, saml003.evt_div_cd1, saml003.key, saml003.val, uuid,"");
		
		return result;
	}
	
	
	/**
	 * 이동 로그
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/appLog/appMove"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public List<SCPN006> appMoveLog(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody List<SAML002> saml002
			, @PathVariable String bcn_cd
			) throws Exception {
		
		
		List<SCPN006> scpn006 = sopr001service.appMoveLog(saml002);
		
		return scpn006;
	}

	
	/**
	 * 이동 로그
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/appLog/appMove2"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	/*@ResponseBody*/
	public ResultPush appMoveLog2(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody SAML002 saml002
			, @PathVariable String bcn_cd
			) throws Exception {
		
		
		ResultPush result = sopr001service.appMoveLog2(saml002);
		 
		return result;
	}
	
	
	/**
	 * 시나리오 아웃바운드 이동 로그
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/appLog/scenario/appMove"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public ResultPush appScenarioMoveLog(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody SAML002 saml002
			, @PathVariable String bcn_cd
			) throws Exception {
		//SAML002  saml002 = new SAML002();
		//saml002.uuid="UUID-201611091702502449-555751";
		//saml002.bcn_cd="01";
		
		ResultPush result = sopr001service.appScenarioMoveLog(saml002);
		
		return result;
	}
	
	
	/**
	 * 시나리오 최초 실행 로그
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/appLog/scenario/appExecution"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public ResultPush appExecution(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody SAML002 saml002
			, @PathVariable String bcn_cd
			) throws Exception {
		//SAML002  saml002 = new SAML002();
		//saml002.uuid="UUID-201611091702502449-555751";
		//saml002.bcn_cd="01";
		ResultPush result = sopr001service.appExecution(saml002);
		
		return result;
	}
	
	
	/**
	 * 위치정보 로그 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getLocationLogs"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<SAML002> getSAML002s(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
	) throws Exception {
		
		this.initHandler(req, res);

		SAML002Vo vo = new SAML002Vo();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
			
		ListResult<SAML002> list  = sopr001service.getLocationLogs(vo);
		
		return list;
	}
	
	
	/**
	 * 위치정보 로그 열람 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getLocationReadingLogs"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<SAML004> getLocationReadingLogs(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
	) throws Exception {
		
		this.initHandler(req, res);

		SAML004Vo vo = new SAML004Vo();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
			
		ListResult<SAML004> list  = sopr001service.getLocationReadingLogs(vo);
		
		return list;
	}
	
	
	/**
	 * 사용자 액션 로그 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getActionLogs"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<SAML003> getActionLogs(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
	) throws Exception {
		
		this.initHandler(req, res);

		SAML003Vo vo = new SAML003Vo();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
			
		ListResult<SAML003> list  = sopr001service.getActionLogs(vo);
		
		return list;
	}
	
	
	/**
	 * uuid 생성
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/getUuid"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult getUuid(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			) throws Exception {
		
		SimpleResult result = new SimpleResult();
		
		result = sopr001service.getUuid();
		
		return result;
	}
	
}
