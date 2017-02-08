/*
 * NoticeRestController.java	1.00 2016/06/16
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.News;
import kr.co.starfield.model.NewsCategory;
import kr.co.starfield.model.NewsFilter;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACMS003Service;

/**
 *  ACMS003(News) REST 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author eezy
 * @version 1.0,
 * @see
 * @date 2016.06.20
 */
@RestController
@RequestMapping("/admin/rest")
public class ACMS003RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(ACMS003RestController.class);
	
	@Autowired
	ACMS003Service acms003Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * News 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/news"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regNews(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody News news ) throws Exception {
		
		this.initHandler(req, res);
		
		news.bcn_cd = bcn_cd;
		
		news.reg_usr = (String) session.getAttribute("adm_seq");;
		
		ll.info("regNews bcn_cd : {}, news : {}", bcn_cd, news);
		
		SimpleResult result = acms003Service.regNews(news);
		
		aact001service.regAdminAction(req, session, "뉴스 등록", null);
		
		return result;
	}
	
	/**
	 * News 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/news"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<News> getNewsList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="sort_name", required=false, defaultValue = "rnum") String sort_name
			, @RequestParam(value="sort_order", required=false, defaultValue = "desc") String sort_order
			, @RequestParam(value="searchType", required=false, defaultValue = "1") int searchType
			, @RequestParam(value="searchKeyword", required=false, defaultValue = "") String searchKeyword
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getNewsList bcn_cd : {}, offset : {}, limit : {}", new Object[] {bcn_cd, offset, limit});
		
		NewsFilter filter = new NewsFilter();
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		
		filter.sort_name = sort_name;
		filter.sort_order = sort_order;
		filter.searchType = searchType;
		filter.searchKeyword = searchKeyword;
		
		filter.sts = StatusCode.Common.USE.getCode();
		
		ListResult<News> newsList = acms003Service.getNewsList(filter);
		
		aact001service.regAdminAction(req, session, "뉴스 목록 조회", null);
		
		return newsList;
	}
	
	/**
	 * News 카테고리 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/news/category"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ArrayList<NewsCategory> getNewsCategoryList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getNewsCategoryList bcn_cd : {}", new Object[] {bcn_cd});
		
		NewsFilter filter = new NewsFilter();
		filter.bcn_cd = bcn_cd;
		
		ArrayList<NewsCategory> newsCategoryList = acms003Service.getNewsCategory(filter);
		
		aact001service.regAdminAction(req, session, "뉴스 카테고리 목록 조회", null);
		
		return newsCategoryList;
	}
	
	/**
	 * News 상세
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/news/{news_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public News getNews(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="news_seq") String news_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getNews bcn_cd : {}, news_seq : {}", bcn_cd, news_seq);
		
		NewsFilter filter = new NewsFilter();
		filter.bcn_cd = bcn_cd;
		filter.news_seq = news_seq;
		filter.sts = StatusCode.Common.USE.getCode();
		
		News news = acms003Service.getNews(filter);
	
		aact001service.regAdminAction(req, session, "뉴스 상세 조회", null);
		
		return news;
	}
	
	/**
	 * News 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/news/{news_seq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyNews(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody News news
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="news_seq") String news_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("modifyNews news : {}, bcn_cd : {}, news_seq : {}", new Object[] {news, bcn_cd, news_seq});
		
		news.bcn_cd = bcn_cd;
		news.news_seq = news_seq;
		
		news.mod_usr = (String) session.getAttribute("adm_seq");;
		
		SimpleResult result = acms003Service.modifyNews(news);
				
		aact001service.regAdminAction(req, session, "뉴스 수정", null);
		
		return result;
	}
	
	/**
	 * News 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/news/{news_seq_list}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteNews(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="news_seq_list") String[] news_seq_list) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info("deleteNews bcn_cd : {}, news_seq_list : {}", bcn_cd, news_seq_list);
		
		if(news_seq_list.length < 1){
			BaseException be = new BaseException(ErrorCode.Board.NEWS_SELECTED_SIZE_ERROR);
			throw be;
		}
		
		CommonDeleteModel commonDeleteModel = new CommonDeleteModel();
		commonDeleteModel.bcn_cd = bcn_cd;
		commonDeleteModel.seq_arr = news_seq_list;
		
		commonDeleteModel.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = acms003Service.deleteNews(commonDeleteModel);
		
		aact001service.regAdminAction(req, session, "뉴스 삭제", null);
		
		return result;
	}
}
