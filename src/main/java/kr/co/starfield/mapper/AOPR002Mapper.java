/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.ArrayList;
import java.util.Map;

import kr.co.starfield.model.Holiday;
import kr.co.starfield.model.vo.SOPR002Vo;


/**
 *  AOPR002(Holiday)Mapper interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.06.21
 */

public interface AOPR002Mapper {
	
	// Holiday 등록
	public void regHoliday(Holiday model);
	
	// Holiday 목록 조회
	public ArrayList<Holiday> getHolidayList(Holiday model);
	
	// Holiday 상세
	public Holiday getHoliday(Holiday model);
	
	// Holiday 수정
	public void modifyHoliday(Holiday model);
	
	// Holiday 삭제
	public void deleteHoliday(Holiday model);
}
