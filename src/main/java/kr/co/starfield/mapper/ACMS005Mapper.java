/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.NewsWeb;
import kr.co.starfield.model.Notice;
import kr.co.starfield.model.NoticeFilter;
import kr.co.starfield.model.NoticeWeb;
import kr.co.starfield.model.vo.SCMS006Vo;

/**
 *  ACMS003Mapper(News, Notice, FAQ) interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

public interface ACMS005Mapper {
	
	
	// 공지사항 입력
	public void regNotice(Notice notice);
	
	// 공지사항 수정
	public void modifyNotice(Notice notice);
	
	// 공지사항 삭제
	public void deleteNotice(CommonDeleteModel delete);
	
	// 공지사항 목록 조회
	public ArrayList<Notice> getNoticeList(NoticeFilter filter);
	
	// 공지사항 목록 카운트
	public int getTotalCount(NoticeFilter filter);
	
	// 공지사항 상세
	public Notice getNotice(NoticeFilter filter);
	
	// 공지사항 redis
	public ArrayList<NoticeWeb> getNoticeWebList(NoticeFilter filter);

}
