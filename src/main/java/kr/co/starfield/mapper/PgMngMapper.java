/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.List;

import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.PgMng;

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

public interface PgMngMapper {

	// 프로그램 리스트
	public List<PgMng> getPgMngs(PgMng vo);
	
	// 프로그램 상세
	public PgMng getPgMng(PgMng vo);
	
	// 프로그램 등록
	public void regPgMng(PgMng vo);
		
	// 프로그램 수정
	public void modifyPgMng(PgMng vo);
	
	// 프로그램 삭제
	public void deletePgMng(PgMng vo);
}
