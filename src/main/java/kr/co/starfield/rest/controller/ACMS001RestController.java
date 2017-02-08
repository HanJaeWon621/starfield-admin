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
import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.model.Blog;
import kr.co.starfield.model.BlogFilter;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.Theme;
import kr.co.starfield.model.ThemeFilter;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACMS001Service;

/**
 *  blog theme
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
public class ACMS001RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(ACMS001RestController.class);
	
	@Autowired
	ACMS001Service acms001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * Blog 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/blogs"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regBlog(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody Blog blog ) throws Exception {
		
		this.initHandler(req, res);
		
		blog.bcn_cd = bcn_cd;
		
		blog.reg_usr = (String) session.getAttribute("adm_seq");
		
		ll.info("regBlog bcn_cd : {}, blog : {}", bcn_cd, blog);
		
		SimpleResult result = acms001Service.regBlog(blog);
		
		aact001service.regAdminAction(req, session, "블로그 등록", null);
		
		return result;
	}
	
	/**
	 * Blog 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/blogs"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<Blog> getBlogList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="sort_name", required=false, defaultValue = "rnum") String sort_name
			, @RequestParam(value="sort_order", required=false, defaultValue = "desc") String sort_order
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getBlogList  bcn_cd : {}, offset : {}, limit : {}", new Object[] {bcn_cd, offset, limit});
		
		BlogFilter filter = new BlogFilter();
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		filter.sort_name = sort_name;
		filter.sort_order = sort_order;

		ListResult<Blog> blogList = acms001Service.getBlogList(filter);
		
		aact001service.regAdminAction(req, session, "블로그 목록 조회", null);
		
		return blogList;
	}
	
	/**
	 * Blog 상세
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/blogs/{blog_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public Blog getBlog(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="blog_seq") String blog_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getBlog bcn_cd : {}, blog_seq : {}", bcn_cd, blog_seq);
		
		BlogFilter filter = new BlogFilter();
		filter.bcn_cd = bcn_cd;
		filter.blog_seq = blog_seq;
		
		Blog blog = acms001Service.getBlog(filter);
	
		aact001service.regAdminAction(req, session, "블로그 상세 조회", null);
		
		return blog;
	}
	
	/**
	 * Blog 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/blogs/{blog_seq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyBlog(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody Blog blog
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="blog_seq") String blog_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("modifyBlog blog : {}, bcn_cd : {}, blog_seq : [}", new Object[] {blog, bcn_cd, blog_seq});
		
		blog.bcn_cd = bcn_cd;
		blog.blog_seq = blog_seq;
		
		blog.mod_usr = (String) session.getAttribute("adm_seq");;
		
		SimpleResult result = acms001Service.modifyBlog(blog);
				
		aact001service.regAdminAction(req, session, "블로그 수정", null);
		
		return result;
	}
	
	/**
	 * Blog 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/blogs/{blog_seq_list}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteBlog(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="blog_seq_list") String[] blog_seq_list) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info("deleteBlog bcn_cd : {}, blog_seq : {}", bcn_cd, blog_seq_list);
		
		if(blog_seq_list.length < 1){
			BaseException be = new BaseException(ErrorCode.Board.BLOG_SELECTED_SIZE_ERROR);
			throw be;
		}
		
		CommonDeleteModel commonDeleteModel = new CommonDeleteModel();
		commonDeleteModel.bcn_cd = bcn_cd;
		commonDeleteModel.seq_arr = blog_seq_list;
		
		commonDeleteModel.mod_usr = (String) session.getAttribute("adm_seq");;
		
		SimpleResult result = acms001Service.deleteBlog(commonDeleteModel);
		
		aact001service.regAdminAction(req, session, "블로그 삭제", null);
		
		return result;
	}
	
	/**
	 * theme 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/theme"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regTheme(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody Theme theme ) throws Exception {
		
		this.initHandler(req, res);
		
		theme.bcn_cd = bcn_cd;
		
		theme.reg_usr = (String) session.getAttribute("adm_seq");;
		
		ll.info("regBlog bcn_cd : {}, theme : {}", bcn_cd, theme);
		
		SimpleResult result = acms001Service.regTheme(theme);
		
		aact001service.regAdminAction(req, session, "테마 등록", null);
		
		return result;
	}
	
	/**
	 * theme 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/theme"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<Theme> getThemeList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="sort_name", required=false, defaultValue = "rnum") String sort_name
			, @RequestParam(value="sort_order", required=false, defaultValue = "desc") String sort_order
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getThemeList  bcn_cd : {}, offset : {}, limit : {}", new Object[] {bcn_cd, offset, limit});
		
		ThemeFilter filter = new ThemeFilter();
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		filter.sort_name = sort_name;
		filter.sort_order = sort_order;
		
		
		ListResult<Theme> themeList = acms001Service.getThemeList(filter);
		
		aact001service.regAdminAction(req, session, "테마 목록 조회", null);
		
		return themeList;
	}
	
	/**
	 * theme 상세
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/theme/{thme_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public Theme getTheme(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="thme_seq") String thme_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getBlog bcn_cd : {}, thme_seq : {}", bcn_cd, thme_seq);
		
		ThemeFilter filter = new ThemeFilter();
		filter.bcn_cd = bcn_cd;
		filter.thme_seq = thme_seq;
		
		Theme theme = acms001Service.getTheme(filter);
	
		aact001service.regAdminAction(req, session, "테마 상세 조회", null);
		
		return theme;
	}
	
	/**
	 * theme 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/theme/{thme_seq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyTheme(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody Theme theme
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="thme_seq") String thme_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("modifyBlog blog : {}, bcn_cd : {}, thme_seq : [}", new Object[] {theme, bcn_cd, thme_seq});
		
		theme.bcn_cd = bcn_cd;
		theme.thme_seq = thme_seq;

		theme.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = acms001Service.modifyTheme(theme);
				
		aact001service.regAdminAction(req, session, "테마 수정", null);
		
		return result;
	}
	
	/**
	 * theme 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/theme/{thme_seq_list}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteTheme(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="thme_seq_list") String[] thme_seq_list) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info("deleteTheme bcn_cd : {}, thme_seq_list : {}", bcn_cd, thme_seq_list);
		
		if(thme_seq_list.length < 1){
			BaseException be = new BaseException(ErrorCode.Board.THEME_SELECTED_SIZE_ERROR);
			throw be;
		}
		
		CommonDeleteModel commonDeleteModel = new CommonDeleteModel();
		commonDeleteModel.bcn_cd = bcn_cd;
		commonDeleteModel.seq_arr = thme_seq_list;
		
		commonDeleteModel.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = acms001Service.deleteTheme(commonDeleteModel);
		
		aact001service.regAdminAction(req, session, "테마 삭제", null);
		
		return result;
	}
}
