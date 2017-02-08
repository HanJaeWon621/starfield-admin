/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.List;
import java.util.Set;

import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.TenantZone;
import kr.co.starfield.model.vo.SCTG001Vo;

/**
 *  CategoryMapper interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author yhkim
 * @version	1.0,
 * @see
 * @date 2016.04.14
 */

public interface ACTG001Mapper {

	// 카테고리 조회
	public SCTG001Vo getCategory(SCTG001Vo vo);

	// 카테고리 등록
	public void regCategory(SCTG001Vo vo);

	// 카테고리 수정
	public void modifyCategory(SCTG001Vo vo);
	
	// 카테고리 삭제
	public void deleteCategory(CommonDeleteModel seq_arr);
	
	public List<SCTG001Vo> getCategories(SCTG001Vo vo);
	
	// 계층형 카테고리 목록
	public List<SCTG001Vo> getHierachyCategories(SCTG001Vo vo);
	
	//카테고리 seq로 테넌트 정보 조회
	public List<TenantZone> getTenantByCategorySeq(SCTG001Vo vo);
	

	// 상위 카테고리 목록
//	public List<String> getSimpleHierachyCategories(List<String> relationCateSeqList);

}
