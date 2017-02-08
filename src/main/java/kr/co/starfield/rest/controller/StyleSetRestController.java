/*
 * couponController.java	1.00 2016/04/04
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.StyleSet;
import kr.co.starfield.service.ACPN002Service;
import kr.co.starfield.service.StyleSetService;

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
public class StyleSetRestController extends BaseController {

	@Autowired
	StyleSetService styleSetService;
	@Autowired
	ACPN002Service acpn002Service;
	
	private static final Logger ll = LoggerFactory.getLogger(StyleSetRestController.class);
	
	/**
	 * 스타일 세트 리스트
	 * @return
	 */
	@RequestMapping(value = "/getStyleSets"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<StyleSet> getStyleSets(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_bcn_cd" ) String sh_bcn_cd
			, @RequestParam(value="sh_style_nm" ) String sh_style_nm
			
	) throws Exception {
		
		
		this.initHandler(req, res);

		StyleSet vo = new StyleSet();
		vo.sh_bcn_cd = sh_bcn_cd;
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.sh_style_nm = sh_style_nm;

		ListResult<StyleSet> cplist  = styleSetService.getStyleSets(vo);
		
		return cplist;
	}
	
	/**
	 * 스타일 세트 상세
	 * @return
	 */
	@RequestMapping(value = "/getStyleSet"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	@ResponseBody
	public StyleSet getStyleSet(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="sh_bcn_cd" ) String sh_bcn_cd
			, @RequestParam(value="sh_style_set_seq") String sh_style_set_seq
			
	) throws Exception {
		
		System.out.println("1style_set_seq>>>"+sh_style_set_seq);
		this.initHandler(req, res);
		
		StyleSet vo = new StyleSet();
		vo.bcn_cd=sh_bcn_cd;
		vo.sh_style_set_seq = sh_style_set_seq;
		
		
		StyleSet style  = styleSetService.getStyleSet(vo);
		
		return style;
	}
	/**
	 * 스타일셋 등록
	 * @return
	 */
	@RequestMapping(value = "/regStyleSet"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult regStyleSet(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody StyleSet vo ) throws Exception {
		//String login_id = (String) session.getAttribute("login_id");

 		SimpleResult result = new SimpleResult();
		result = styleSetService.regStyleSet(vo);
		
		
 		return result;
	}
	
	/**
	 * 스타일셋 수정
	 * @return
	 */
	@RequestMapping(value = "/modifyStyleSet"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult modifyStyleSet(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody StyleSet vo ) throws Exception {
		//String login_id = (String) session.getAttribute("login_id");

 		SimpleResult result = new SimpleResult();
		result = styleSetService.modifyStyleSet(vo);
		
 		return result;
	}
	
	/**
	 * 스타일셋 삭제
	 * @return
	 */
	@RequestMapping(value = "/deleteStyleSet"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult deleteStyleSet(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="sh_style_set_seq") String sh_style_set_seq
			) throws Exception {
		//String login_id = (String) session.getAttribute("login_id");

 		SimpleResult result = new SimpleResult();
 		StyleSet vo = new StyleSet();
 		vo.sh_style_set_seq = sh_style_set_seq;
		result = styleSetService.deleteStyleSet(vo);
		
		
 		return result;
	}
	
	/**
	 * 스타일 세트 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/saveCss"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<StyleSet> saveCss(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestParam(value="sh_style_nm" ) String sh_style_nm
			
	) throws Exception {
		
		
		this.initHandler(req, res);

		StyleSet vo = new StyleSet();
		vo.bcn_cd = bcnCd;
		vo.limit = -1;
		vo.sh_style_nm = sh_style_nm;
		
		ListResult<StyleSet> list  = null;
		list  = styleSetService.saveCss(vo);
		return list;
	}
}
