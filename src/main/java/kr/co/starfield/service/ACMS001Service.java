package kr.co.starfield.service;

import java.io.IOException;
import java.util.*;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.mapper.ACMS001Mapper;
import kr.co.starfield.model.Blog;
import kr.co.starfield.model.BlogExcel;
import kr.co.starfield.model.BlogFilter;
import kr.co.starfield.model.BlogWeb;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.Theme;
import kr.co.starfield.model.ThemeExcel;
import kr.co.starfield.model.ThemeFilter;
import kr.co.starfield.model.ThemeWeb;


/**
 * blog, theme
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

@Service
public class ACMS001Service {

	@Autowired
	private ACMS001Mapper acms001Mapper;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	private static final Logger ll = LoggerFactory.getLogger(ACMS001Service.class);
	
	/**
	 * Blog 등록
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regBlog(Blog blog) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms001Mapper.regBlog(blog);
		
		result.extra = blog.blog_seq;
		
		syncBlog(blog.bcn_cd);
		
		return result;
	}
	
	/**
	 * Blog 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	
	public ListResult<Blog> getBlogList(BlogFilter filter) throws BaseException {
		
		ListResult<Blog> result = new ListResult<Blog>();
			
		result.list.addAll(acms001Mapper.getBlogList(filter));
			
		if(filter.limit > 0){
			int tot_cnt = acms001Mapper.getTotalCount(filter);
	
			Paging paging = new Paging(filter.offset, filter.limit, tot_cnt);
				
			result.paging = paging;
		}
		
		return result;
	}
	
	
	/**
	 * Blog 상세
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	public Blog getBlog(BlogFilter filter) throws BaseException {
		
		Blog blog = acms001Mapper.getBlog(filter);
		
		if(blog == null) {
			BaseException be = new BaseException(ErrorCode.SNS.BLOG_NOT_FOUND_DATA);
			throw be;
		}

		return blog;
	}
	
	/**
	 * Blog 수정
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyBlog(Blog blog) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms001Mapper.modifyBlog(blog);
		
		syncBlog(blog.bcn_cd);
		
		return result;
	}
	
	/**
	 * Blog 삭제 
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteBlog(CommonDeleteModel commonDeleteModel) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms001Mapper.deleteBlog(commonDeleteModel);
		
		syncBlog(commonDeleteModel.bcn_cd);
		
		return result;
	}
	
	/**
	 * Theme 등록
	 * @param theme
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regTheme(Theme theme) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms001Mapper.regTheme(theme);
		
		result.extra = theme.thme_seq;
		
		syncTheme(theme.bcn_cd);
		
		return result;
	}
	
	/**
	 * Theme 목록 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<Theme> getThemeList(ThemeFilter filter) throws BaseException {
		
		ListResult<Theme> result = new ListResult<Theme>();
			
		result.list.addAll(acms001Mapper.getThemeList(filter));
			
		if(filter.limit > 0){
			int tot_cnt = acms001Mapper.getThemeTotalCount(filter);
	
			Paging paging = new Paging(filter.offset, filter.limit, tot_cnt);
				
			result.paging = paging;
		}
	
		return result;
	}
	
	/**
	 * Theme 상세
	 * @param filter
	 * @return
	 * @throws BaseException 
	 */
	public Theme getTheme(ThemeFilter filter) throws BaseException {
		
		Theme theme = acms001Mapper.getTheme(filter);
		
		if(theme == null){
			BaseException be = new BaseException(ErrorCode.SNS.THEME_NOT_FOUND_DATA);
			throw be;
		} 
		
		return theme;
	}
	
	/**
	 * Theme 수정
	 * @param theme
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyTheme(Theme theme) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms001Mapper.modifyTheme(theme);
		
		syncTheme(theme.bcn_cd);

		return result;
	}
	
	
	/**
	 * Theme 삭제 
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteTheme(CommonDeleteModel commonDeleteModel) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms001Mapper.deleteTheme(commonDeleteModel);
		
		syncTheme(commonDeleteModel.bcn_cd);
		
		return result;
	}
	
	/**
	 * BlogExcel 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	
	public ArrayList<BlogExcel> getBlogExcelList(String bcn_cd) throws BaseException {
		
		
		BlogFilter filter = new BlogFilter();
		filter.bcn_cd = bcn_cd;
		
		return acms001Mapper.getBlogExcelList(filter);
	}
	
	/**
	 * BlogWeb 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	
	public ArrayList<BlogWeb> getBlogWebList(String bcn_cd) throws BaseException {
		
		
		BlogFilter filter = new BlogFilter();
		filter.bcn_cd = bcn_cd;
		
		return acms001Mapper.getBlogWebList(filter);
	}
	
	/**
	 * ThemeExcel 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	
	public ArrayList<ThemeExcel> getThemeExcelList(String bcn_cd) throws BaseException {
		
		
		ThemeFilter filter = new ThemeFilter();
		filter.bcn_cd = bcn_cd;
		
		return acms001Mapper.getThemeExcelList(filter);
	}
	
	/**
	 * ThemeWeb 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	
	public ArrayList<ThemeWeb> getThemeWebList(String bcn_cd) throws BaseException {
		
		
		ThemeFilter filter = new ThemeFilter();
		filter.bcn_cd = bcn_cd;
		
		return acms001Mapper.getThemeWebList(filter);
	}
	
	
	
//	/**
//	 * admin 반영시 레디스 데이터 수정을 위한 메소드
//	 * @param
//	 * @return 
//	 * @throws BaseException 
//	 */
//	@PostConstruct
//	public void initBlog() throws BaseException {
//		syncBlog("01");
//	}
	
	/**
	 * Blog redis sync
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	
	@Transactional
	public SimpleResult syncBlog(String bcn_cd) throws BaseException {
		
		SimpleResult result = new SimpleResult();

		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
		
		List<BlogWeb> blogList = getBlogWebList(bcn_cd);
		
		try {
			stringRedisTemplate.delete(zOps.range("keys:blog", 0, -1));
			stringRedisTemplate.delete("keys:blog");
			
			int count = 0;
			for(BlogWeb blog : blogList){
				vOps.set("blog:" + blog.blog_seq, new ObjectMapper().writeValueAsString(blog));
				zOps.add("keys:blog", "blog:" + blog.blog_seq, count);
				count++;
			}			
		} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.SNS.BLOG_SYNC_FAIL_REDIS);
			throw be;
		}
		
		return result;
	}

	/**
	 * Theme redis sync
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	
	@Transactional
	public SimpleResult syncTheme(String bcn_cd) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
		
		
		List<ThemeWeb> themeList = getThemeWebList(bcn_cd);
		
		try {
			stringRedisTemplate.delete(zOps.range("keys:theme", 0, -1));
			stringRedisTemplate.delete("keys:theme");
			
			int count = 0;
			
			for(ThemeWeb theme : themeList){
				
				vOps.set("theme:" + theme.thme_seq, new ObjectMapper().writeValueAsString(theme));
				zOps.add("keys:theme", "theme:" + theme.thme_seq, count);
				
				count++;
			}
		} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.SNS.THEME_SYNC_FAIL_REDIS);
			throw be;
		}
		return result;
		
	}
}
