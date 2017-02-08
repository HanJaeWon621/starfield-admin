/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.Language;
import kr.co.starfield.model.LanguageDelete;
import kr.co.starfield.model.LanguageFilter;
import kr.co.starfield.model.LanguageGroup;

/**
 *  AOPR001(Operation)Mapper interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author hhLee
 * @version	1.0,
 * @see
 * @date 2016.04.14
 */

public interface ASYS002Mapper {
	//다국어 그룹 목록 조회
	public ArrayList<LanguageGroup> getLocaleGroupList(LanguageFilter filter);
	
	//다국어 count
	public int getTotalCount(LanguageFilter filter);
	
	//다국어 등록
	public void regLocale(Language language);
	
	//다국어 목록 조회
	public ArrayList<Language> getLocaleList(LanguageFilter filter);
	
	//다국어 삭제
	public void deleteLocale(LanguageDelete languageDelete);
}
