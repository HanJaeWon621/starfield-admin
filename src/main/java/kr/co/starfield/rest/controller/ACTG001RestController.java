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
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.Category;
import kr.co.starfield.model.CategoryForList;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.TenantCategoryForAdmin;
import kr.co.starfield.model.vo.SCTG001Vo;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACTG001Service;

/**
 *  카테고리 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author yhkim
 * @version 1.0,
 * @see
 * @date 2016.06.14
 */

@RestController
@RequestMapping("/admin/rest")
public class ACTG001RestController extends BaseController {

	private Logger ll = LoggerFactory.getLogger(ACTG001RestController.class);
	
	@Autowired
	ACTG001Service actg001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * 카테고리 조회
	 * @return Category
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = {"/{bcnCd}/categories/{rootCateCd}/any/{cateSeq}"}
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public Category getCategory(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="cateSeq") String cateSeq
	) throws Exception {
		this.initHandler(req, res);
		
		ll.info("Category cd is {}", cateSeq);
		SCTG001Vo vo = new SCTG001Vo();
		vo.cate_seq = cateSeq;
		
		Category category = actg001Service.getCategory(vo);
		
		aact001service.regAdminAction(req, session, "카테고리 상세 조회", null);
		
		return category;
	}
	
	/**
	 * 최상위 카테고리 조회
	 * @return Category
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = {"/{bcnCd}/categories/{rootCateCd}"}
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public Category getRootCategory(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="rootCateCd") String rootCateCd
	) throws Exception {
		this.initHandler(req, res);
		
		ll.info("Category cd is {}", rootCateCd);
		SCTG001Vo vo = new SCTG001Vo();
		vo.cate_cd = rootCateCd;
		
		Category category = actg001Service.getCategory(vo);
		
//		aact001service.regAdminAction(req, session, "최상위 카테고리 상세 조회", null);
		
		return category;
	}
	
	/**
	 * 카테고리 수정
	 * @return
	 */
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcnCd}/categories/{rootCateCd}/any/{cateSeq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyCategory(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @PathVariable(value="rootCateCd") String rootCateCd
			, @PathVariable(value="cateSeq") String cateSeq
			, @RequestBody SCTG001Vo category ) throws Exception {
		
		this.initHandler(req, res);
		
		category.bcn_cd = bcnCd;
		category.cate_seq = cateSeq;
		
		ll.info("Category is {}", category);
		
		category.mod_usr = (String) session.getAttribute("adm_seq");

		SimpleResult result = actg001Service.modifyCategory(category, rootCateCd);
				
		aact001service.regAdminAction(req, session, "카테고리 수정", null);
		return result;
	}

	/**
	 * 카테고리 목록 조회
	 * @return Category List
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcnCd}/categories/{rootCateCd}/{mamaCateSeq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<CategoryForList> getCategories(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="rootCateCd") String rootCateCd
			, @PathVariable(value="mamaCateSeq") String mamaCateSeq
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		
		this.initHandler(req, res);

		SCTG001Vo vo = new SCTG001Vo();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
			
		Utils.Str.qParser(vo, q);
		vo.cate_cd = rootCateCd;
		vo.mama_cate_seq = mamaCateSeq;
		
		ll.info("offset is {}", offset);
		ll.info("limit is {}", limit);
		ll.info("q is {}", q);
		
		ListResult<CategoryForList> categories = actg001Service.getCategories(vo);
		
//		aact001service.regAdminAction(req, session, "카테고리 목록 조회", null);
		
		return categories;
	}

	/**
	 * 테넌트 카테고리 등록/수정/삭제
	 * @return
	 */
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcnCd}/categories/TENANT"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult saveCategory(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestBody TenantCategoryForAdmin category ) throws Exception {
		
		this.initHandler(req, res);
		
		category.bcn_cd = bcnCd;
		category.user = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = actg001Service.saveCategory(category);
		
		aact001service.regAdminAction(req, session, "카테고리 전체 등록/수정/삭제", null);
		
		return result;
	}
	
}

