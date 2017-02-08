package kr.co.starfield.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import kr.co.starfield.mapper.ACMS003Mapper;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.FAQ;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.News;
import kr.co.starfield.model.NewsCategory;
import kr.co.starfield.model.NewsFilter;
import kr.co.starfield.model.NewsWeb;
import kr.co.starfield.model.Notice;
import kr.co.starfield.model.NoticeFilter;
import kr.co.starfield.model.NoticeWeb;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SCMS006Vo;


/**
 * ACMS003(News) 서비스 클래스
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
public class ACMS003Service {

	@Autowired
	private ACMS003Mapper amcs003Mapper;
	
	@Autowired
	ACTG001Service actg001Service;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	private static final Logger ll = LoggerFactory.getLogger(ACMS003Service.class);
	
	/**
	 * News 등록
	 * @param model
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regNews(News news) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		amcs003Mapper.regNews(news);
		
		result.extra = news.news_seq;
		
		syncNews(news.bcn_cd);
		
		return result;
	}
	
	/**
	 * News 목록 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<News> getNewsList(NewsFilter filter) throws BaseException {
		
		ListResult<News> result = new ListResult<News>();
			
		result.list.addAll(amcs003Mapper.getNewsList(filter));
			
		if(filter.limit > 0){
			int tot_cnt = amcs003Mapper.getTotalCount(filter);
	
			Paging paging = new Paging(filter.offset, filter.limit, tot_cnt);
				
			result.paging = paging;
		}
	
		return result;
	}
	
	/**
	 * News 카테고리 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public ArrayList<NewsCategory> getNewsCategory(NewsFilter filter) throws BaseException {
	
		return amcs003Mapper.getNewsCategory(filter);
	}
	
	/**
	 * News 상세
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	public News getNews(NewsFilter filter) throws BaseException {
		
		News news = amcs003Mapper.getNews(filter);
		
		if(news == null) {
			BaseException be = new BaseException(ErrorCode.Board.NEWS_NOT_FOUND_DATA);
			throw be;
		} 
		
		return news;
	}
	
	/**
	 * News 수정
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyNews(News news) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		amcs003Mapper.modifyNews(news);

		syncNews(news.bcn_cd);
		
		return result;
	}
	
	/**
	 * News 삭제 
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteNews(CommonDeleteModel commonDeleteModel) throws BaseException {
		SimpleResult result = new SimpleResult();	
				
		amcs003Mapper.deleteNews(commonDeleteModel);
		
		syncNews(commonDeleteModel.bcn_cd);
		
		return result;
	}
	
	
	
	public List<NewsWeb> redisNewsList(String bcn_cd) throws BaseException {
		
		NewsFilter filter = new NewsFilter();
		filter.bcn_cd = bcn_cd;
		return amcs003Mapper.getNewsWebList(filter);
	}
	
	/**
	 * News redis sync
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	
	@Transactional
	public SimpleResult syncNews(String bcn_cd) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
		
		
		List<NewsWeb> newsList = redisNewsList(bcn_cd);
		
		
		try {
			int count = 0;
			
			stringRedisTemplate.delete(zOps.range("keys:news", 0, -1));
			stringRedisTemplate.delete("keys:news");
			for(NewsWeb news : newsList){
				vOps.set("news:" + news.news_seq, new ObjectMapper().writeValueAsString(news));
				zOps.add("keys:news", "news:" + news.news_seq, count);
				count++;
			}
		} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.Board.NEWS_SYNC_FAIL_REDIS);
			throw be;
		}
		
		actg001Service.syncRedisCategories("NEWS");
		
		return result;
	}
	
	
}
