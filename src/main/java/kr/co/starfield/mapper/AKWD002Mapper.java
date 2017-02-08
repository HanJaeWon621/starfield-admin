/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.vo.SKWD002Vo;


/**
 *  AKWD002Mapper(RecommendTag) Mapper interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.06.21
 */

public interface AKWD002Mapper {
	
	// RecommendTag 등록
	public void regRecommendTag(SKWD002Vo vo);
	
	// RecommendTag 목록 조회
	public ArrayList<SKWD002Vo> getRecommendTagList(SKWD002Vo vo);
	
	// RecommendTag 상세
	public SKWD002Vo getRecommendTag(SKWD002Vo vo);
	
	// RecommendTag 수정
	public void modifyRecommendTag(SKWD002Vo vo);
	
	// RecommendTag 삭제
	public void deleteRecommendTag(SKWD002Vo vo);
}
