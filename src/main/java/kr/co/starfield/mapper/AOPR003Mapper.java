/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.StarFieldHoliday;
import kr.co.starfield.model.vo.SOPR003Vo;


/**
 *  AOPR003(StarFieldHoliday)Mapper interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.07.11
 */

public interface AOPR003Mapper {
	
	// StarFieldHoliday 등록
	public void regStarFieldHoliday(StarFieldHoliday vo);
	
	// StarFieldHoliday 목록 조회
	public ArrayList<StarFieldHoliday> getStarFieldHolidayList(StarFieldHoliday vo);
	
	// StarFieldHoliday 상세
	public StarFieldHoliday getStarFieldHoliday(StarFieldHoliday vo);
	
	// StarFieldHoliday 수정
	public void modifyStarFieldHoliday(StarFieldHoliday vo);
	
	// StarFieldHoliday 삭제
	public void deleteStarFieldHoliday(StarFieldHoliday vo);
}
