/*
 * AITF001RestController.java	1.00 2016/06/16
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.common.Utils;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.MegaBoxP200;
import kr.co.starfield.model.MegaBoxP201;
import kr.co.starfield.model.MegaBoxP202;
import kr.co.starfield.model.vo.AITF001Vo;
import kr.co.starfield.model.vo.AITF002Vo;
import kr.co.starfield.model.vo.AITF003Vo;
import kr.co.starfield.service.AITF001Service;

/**
 *  AITF001(megaBox-interface) REST 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author hhLee
 * @version 1.0,
 * @see
 * @date 2016.06.20
 */
@RestController
@RequestMapping("/rest")
public class AITF001RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(ACMS001RestController.class);
	
	@Autowired
	AITF001Service aitf001Service;

	/**
	 * P200 목록 조회(박스오피스-메가박스 전체 기준)
	 * 
	 * 전체 목록을 가져올 수 있기 때문에
	 * limit가 -1로 오면 전체 리스트를 보여준다.
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/megaboxs/boxoffice"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<MegaBoxP200> getP200List(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset") int offset
			, @RequestParam(value="limit") int limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getP200List q : {},  bcn_cd : {}, offset : {}, limit : {}", new Object[] {q, bcn_cd, offset, limit});
		
		AITF001Vo vo = new AITF001Vo();
		vo.offset = offset;
		vo.limit = limit;
		Utils.Str.qParser(vo, q);
		
		ListResult<MegaBoxP200> P200List = aitf001Service.getP200List(vo);
		
		return P200List;
	}
	
	/**
	 * P201 목록 조회(현재 상영작-하남 개별 기준)
	 * 
	 * 전체 목록을 가져올 수 있기 때문에
	 * limit가 -1로 오면 전체 리스트를 보여준다.
	 * 
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/megaboxs/current"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<MegaBoxP201> getP201List(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset") int offset
			, @RequestParam(value="limit") int limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		
		this.initHandler(req, res);
		ll.info("getP201List q : {},  bcn_cd : {}, offset : {}, limit : {}", new Object[] {q, bcn_cd, offset, limit});
		
		AITF002Vo vo = new AITF002Vo();
		vo.offset = offset;
		vo.limit = limit;
		Utils.Str.qParser(vo, q);
		
		ListResult<MegaBoxP201> P201List = aitf001Service.getP201List(vo);
		
		return P201List;
	}
	
	/**
	 * P200 목록 조회(상영 예정작-메가박스 전체 기준)
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/megaboxs/plan"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<MegaBoxP202> getP202List(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset") int offset
			, @RequestParam(value="limit") int limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		
		this.initHandler(req, res);
		ll.info("getP202List q : {},  bcn_cd : {}, offset : {}, limit : {}", new Object[] {q, bcn_cd, offset, limit});
		
		AITF003Vo vo = new AITF003Vo();
		vo.offset = offset;
		vo.limit = limit;
		Utils.Str.qParser(vo, q);
		
		ListResult<MegaBoxP202> P202List = aitf001Service.getP202List(vo);
		
		return P202List;
	}
	
}
