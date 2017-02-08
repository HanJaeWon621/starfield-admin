package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.vo.SOPR004Vo;



/**
 *  AOPR004(TenantHoliday)Mapper interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.07.11
 */

public interface AOPR004Mapper {
	
	// TenantHoliday 등록
	public void regTenantHoliday(SOPR004Vo vo);
	
	// TenantHoliday 목록 조회
	public ArrayList<SOPR004Vo> getTenantHolidayList(SOPR004Vo vo);
	
	// TenantHoliday 상세
	public SOPR004Vo getTenantHoliday(SOPR004Vo vo);
	
	// TenantHoliday 수정
	public void modifyTenantHoliday(SOPR004Vo vo);
	
	// TenantHoliday 삭제
	public void deleteTenantHoliday(SOPR004Vo vo);
}
