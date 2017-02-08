/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.List;

import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.StyleSet;

/**
 * HomeMapper interface
 *
 * Copyright Copyright (c) 2016 Company Paprika
 *
 * @author xxx
 * @version 1.0,
 * @see
 * @date 2016.04.11
 */

public interface StyleSetMapper {

	// 스타일 세트 리스트
	public List<StyleSet> getStyleSets(StyleSet vo);
	
	// 스타일 세트 상세
	public StyleSet getStyleSet(StyleSet vo);
	
	// 스타일 세트 등록
	public void regStyleSet(StyleSet vo);
		
	// 스타일 세트 수정
	public void modifyStyleSet(StyleSet vo);
	
	// 스타일 세트 삭제
	public void deleteStyleSet(StyleSet vo);
}
