package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.InstagramFilter;
import kr.co.starfield.model.InstagramImage;
import kr.co.starfield.model.InstagramImageWeb;
import kr.co.starfield.model.InstagramTag;
import kr.co.starfield.model.InstagramTagWeb;


/**
 *  ACMS002Mapper(instagram) interface
 *  1. 태그 및 게시물의 수정 불가
 *  2. 태그 삭제 시, 연결된 게시물 함께 삭제(sts:9 update)
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author hhLee
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

public interface ACMS002Mapper {
	
	// 인스타그램 태그 입력
	public void regInstagramTag(InstagramTag instagramTag);
	
	// 인스타그램 태그 수정
	public void modifyInstagramTag(InstagramTag instagramTag);
	
	// 인스타그램 태그 목록 조회
	public ArrayList<InstagramTag> getInstagramTagList(InstagramFilter filter);
	
	// 인스타그램 태그 상세
	public InstagramTag getInstagramTag(InstagramFilter filter);
	
	// 인스타그램 이미지 목록 조회
	public ArrayList<InstagramImage> getInstagramImageList(InstagramFilter filter);
	
	// 인스타그램 입력
	public void regInstagramImage(InstagramImage instagramImage);
	
	// 인스타그램 수정
	public void modifyInstagramImage(InstagramImage instagramImage);
	
	// 인스타그램 태그 REIDS 목록 조회
	public ArrayList<InstagramTagWeb> getInstagramTagWebList(String bcn_cd);
	
	// 인스타그램 이미지 REIDS 목록 조회
	public ArrayList<InstagramImageWeb> getInstagramImageWebList(InstagramTagWeb instagramTagWeb);
}
