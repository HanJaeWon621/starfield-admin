/*
 * couponController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.model.Coffee;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.PgMng;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.PgMngService;

/**
 * 쿠폰 관리
 * 
 * @author dhlee
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

@RestController
@RequestMapping("/rest")  
public class PgMngRestController extends BaseController {

	@Autowired
	PgMngService service;

	private static final Logger ll = LoggerFactory.getLogger(PgMngRestController.class);
	
	/**
	 * 프로그램 리스트
	 * @return
	 */
	@RequestMapping(value = "/getPgMngsAPI"
			, method = RequestMethod.GET
		)
	@ResponseBody
	public List<Coffee> getPgMngs(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			
	) throws Exception {
		
		
		this.initHandler(req, res);

		Coffee coffee1 = new Coffee("Latte", 100);
		Coffee coffee2 = new Coffee("Americano", 100);
		List<Coffee> list = new ArrayList();
		list.add(coffee1);
		list.add(coffee2);
		return list;
		
	}
	
	/**
	 * 프로그램 리스트
	 * @return
	 */
	@RequestMapping(value = "/getPgMngs"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<PgMng> getPgMngs(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_pg_nm" ) String sh_pg_nm
			
	) throws Exception {
		
		
		this.initHandler(req, res);

		PgMng vo = new PgMng();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.sh_pg_nm = sh_pg_nm;

		ListResult<PgMng> cplist  = service.getPgMngs(vo);
		
		return cplist;
	}
	
	/**
	 * 프로그램 상세
	 * @return
	 */
	@RequestMapping(value = "/getPgMng"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	@ResponseBody
	public PgMng getPgMng(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="sh_pg_info_seq") String sh_pg_info_seq
			
	) throws Exception {
		
		System.out.println("1style_set_seq>>>"+sh_pg_info_seq);
		this.initHandler(req, res);
		
		PgMng vo = new PgMng();
		vo.sh_pg_info_seq = sh_pg_info_seq;
		
		
		PgMng style  = service.getPgMng(vo);
		
		return style;
	}
	/**
	 * 프로그램 등록
	 * @return
	 */
	@RequestMapping(value = "/regPgMng"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult regPgMng(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody PgMng vo ) throws Exception {
		//String login_id = (String) session.getAttribute("login_id");

 		SimpleResult result = new SimpleResult();
		result = service.regPgMng(vo);
		
		
 		return result;
	}
	
	/**
	 * 프로그램 수정
	 * @return
	 */
	@RequestMapping(value = "/modifyPgMng"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult modifyPgMng(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody PgMng vo ) throws Exception {
		//String login_id = (String) session.getAttribute("login_id");

 		SimpleResult result = new SimpleResult();
		result = service.modifyPgMng(vo);
		
 		return result;
	}
	
	/**
	 * 프로그램 삭제
	 * @return
	 */
	@RequestMapping(value = "/deletePgMng"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult deletePgMng(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="sh_pg_info_seq") String sh_pg_info_seq
			) throws Exception {
		//String login_id = (String) session.getAttribute("login_id");

 		SimpleResult result = new SimpleResult();
 		PgMng vo = new PgMng();
 		vo.sh_pg_info_seq = sh_pg_info_seq;
		result = service.deletePgMng(vo);
		
		
 		return result;
	}
	

}
