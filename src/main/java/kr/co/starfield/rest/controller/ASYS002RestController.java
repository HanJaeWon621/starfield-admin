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
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.model.Language;
import kr.co.starfield.model.LanguageDelete;
import kr.co.starfield.model.LanguageFilter;
import kr.co.starfield.model.LanguageGroup;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ASYS002Service;

/**
 * ASYS002(locale) REST 컨트롤러 클래스
 *
 * @author eezy
 * @version 1.0,
 * @see
 * @date 2016.08.31
 */
@RestController
@RequestMapping("/admin/rest")
public class ASYS002RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(ASYS002RestController.class);
	
	@Autowired
	ASYS002Service asys002Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * Locale 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcn_cd}/language"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<LanguageGroup> getLocaleGroupList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="sort_name", required=false, defaultValue = "rnum") String sort_name
			, @RequestParam(value="sort_order", required=false, defaultValue = "desc") String sort_order
			, @RequestParam(value="searchType", required=false, defaultValue = "0") int searchType
			, @RequestParam(value="searchKeyword", required=false, defaultValue = "") String searchKeyword
	) throws Exception {
		
		this.initHandler(req, res);
		
		LanguageFilter filter = new LanguageFilter();
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		filter.sort_name = sort_name;
		filter.sort_order = sort_order;
		filter.searchType = searchType;
		filter.searchKeyword = searchKeyword;
		filter.sts = StatusCode.Common.USE.getCode();
		
		ListResult<LanguageGroup> localeList = asys002Service.getLocaleGroupList(filter);
		
		aact001service.regAdminAction(req, session, "다국어 PAGE 관리 목록 조회", null);
		
		return localeList;
	}
	

	/**
	 * Locale 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcn_cd}/language/detail"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<Language> getLocaleList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="pg_id") String pg_id
	) throws Exception {
		
		this.initHandler(req, res);
		
		LanguageFilter filter = new LanguageFilter();
		filter.bcn_cd = bcn_cd;
		filter.pg_id = pg_id;
		filter.sts = StatusCode.Common.USE.getCode();
		
		ListResult<Language> localeList = asys002Service.getLocaleList(filter);
		
		aact001service.regAdminAction(req, session, "다국어 PAGE 관리 상세 조회", null);
		
		return localeList;
	}
	
	
	/**
	 * 다국어 등록
	 * @return
	 */
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcn_cd}/language"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regLocale(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody Language[] languageList
			, @PathVariable(value="bcn_cd") String bcn_cd) throws Exception {
		this.initHandler(req, res);
		
		for(Language language : languageList) {
			language.reg_usr = (String) session.getAttribute("adm_seq");
			language.mod_usr = (String) session.getAttribute("adm_seq");
		}
		
		SimpleResult simpleResult = asys002Service.regLoclae(languageList);
		
		aact001service.regAdminAction(req, session, "다국어 PAGE 관리 등록", null);
			
		return simpleResult;
	}

	/**
	 * 다국어 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system"})
	@RequestMapping(value = "/{bcn_cd}/language/delete"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteLocale(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody String[] pg_id_list
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("deleteLocale bcn_cd : {}, pg_id_list : {}", new Object[] {bcn_cd, pg_id_list});
		
		
		LanguageDelete languageDelete = new LanguageDelete();
		
		languageDelete.bcn_cd = bcn_cd;
		languageDelete.pg_id_list = pg_id_list;
		languageDelete.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult simpleResult = asys002Service.deleteLocale(languageDelete);
		
		aact001service.regAdminAction(req, session, "다국어 PAGE 관리 삭제", null);
		
		return simpleResult;
	}
}
