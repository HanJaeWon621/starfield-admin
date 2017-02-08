package kr.co.starfield.rest.controller;

import java.util.ArrayList;

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
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.model.FAQ;
import kr.co.starfield.model.FAQCategory;
import kr.co.starfield.model.FAQFilter;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACMS006Service;

/**
 *  FAQ REST 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author eezy
 * @version 1.0,
 * @see
 * @date 2016.10.05
 */
@RestController
@RequestMapping("/admin/rest")
public class ACMS006RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(ACMS006RestController.class);
	
	@Autowired
	ACMS006Service acms006Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * FAQ카테고리 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/faqs/cate"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ArrayList<FAQCategory> getFAQCategoryList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getFAQCategoryList bcn_cd : {}", new Object[] {bcn_cd});
		
		FAQFilter filter = new FAQFilter();
		
		filter.bcn_cd = bcn_cd;
		
		ArrayList<FAQCategory> faqCateList = acms006Service.getFAQCategoryList(filter);
		
		aact001service.regAdminAction(req, session, "FAQ 카테고리 목록 조회", null);
		
		return faqCateList;
	}
	
	/**
	 * FAQ 카테고리 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/faqs/cate"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"	// 생산 가능한 미디어 타입으로 매핑 제한. 요청 헤더가 일치 할 때만 요청 매
			, headers = "content-type=application/json" // 요청 헤더의 파라미터 값 일치로 매핑 제한.
		)
	public SimpleResult regFAQCategory(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody FAQCategory[] faqCateList ) throws Exception {
		
		this.initHandler(req, res);
		
		FAQFilter filter = new FAQFilter();
		
		filter.bcn_cd = bcn_cd;

		for(FAQCategory faqCate : faqCateList) {
			faqCate.reg_usr = (String) session.getAttribute("adm_seq");
			faqCate.mod_usr = (String) session.getAttribute("adm_seq");
		}
		
		SimpleResult result = acms006Service.regCategory(faqCateList, filter);
		
		aact001service.regAdminAction(req, session, "FAQ 카테고리 등록/수정", null);
		
		return result;
	}
	
	/**
	 * FAQ카테고리 상세
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/faqs/cate/{cate_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public FAQCategory getFAQCategory (
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="cate_seq") String cate_seq
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getFAQCategory bcn_cd : {}, cate_seq : {}", new Object[] {bcn_cd, cate_seq});
		
		FAQFilter filter = new FAQFilter();
		
		filter.bcn_cd = bcn_cd;
		filter.cate_seq = cate_seq;
		
		FAQCategory faqCate = acms006Service.getFAQCategory(filter);
		
		aact001service.regAdminAction(req, session, "FAQ 카테고리 상세 조회", null);
		
		return faqCate;
	}
	
	
	/**
	 * FAQ 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/faqs/{cate_seq}"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"	// 생산 가능한 미디어 타입으로 매핑 제한. 요청 헤더가 일치 할 때만 요청 매
			, headers = "content-type=application/json" // 요청 헤더의 파라미터 값 일치로 매핑 제한.
		)
	public SimpleResult regFAQ(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="cate_seq") String cate_seq
			, @RequestBody FAQ[] faqList ) throws Exception {
		
		this.initHandler(req, res);

		ll.info("regFaq bcn_cd : {}, faq : {}", bcn_cd, faqList);
		
		
		for(FAQ faq : faqList) {
			faq.reg_usr = (String) session.getAttribute("adm_seq");
			faq.mod_usr = (String) session.getAttribute("adm_seq");
		}
		
		SimpleResult result = acms006Service.regFAQ(faqList);
		
		aact001service.regAdminAction(req, session, "FAQ 등록/수정", null);
		
		return result;
	}
	
	/**
	 * FAQ 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/faqs/{cate_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ArrayList<FAQ> getFAQList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="cate_seq") String cate_seq
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getFAQList cate_seq : {},  bcn_cd : {}", new Object[] {cate_seq, bcn_cd});
		
		FAQFilter filter = new FAQFilter();
		filter.bcn_cd = bcn_cd;
		filter.cate_seq = cate_seq;

		
		ArrayList<FAQ> faqList = acms006Service.getFAQList(filter);
		
		aact001service.regAdminAction(req, session, "FAQ 목록 조회", null);
		
		return faqList;
	}
}
