
package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.News;
import kr.co.starfield.model.NewsCategory;
import kr.co.starfield.model.NewsFilter;
import kr.co.starfield.model.NewsWeb;
import kr.co.starfield.model.vo.SCMS006Vo;

/**
 *  ACMS003Mapper(News) interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

public interface ACMS003Mapper {
	
	// NEWS 입력
	public void regNews(News news);
	
	// NEWS 목록 조회
	public ArrayList<News> getNewsList(NewsFilter vo);
	
	// NEWS TOTAL COUNT
	public int getTotalCount(NewsFilter vo);
	
	// NEWS 상세
	public News getNews(NewsFilter vo);
	
	// NEWS 수정
	public void modifyNews(News vo);
	
	// NEWS 삭제
	public void deleteNews(CommonDeleteModel commonDeleteModel);
	
	// NEWS 카테고리 리스트
	public ArrayList<NewsCategory> getNewsCategory(NewsFilter filter);
	
	// NEWS REIDS
	public ArrayList<NewsWeb> getNewsWebList(NewsFilter filter);
}
