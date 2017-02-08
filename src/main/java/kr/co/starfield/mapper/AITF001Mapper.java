/*
 * AITF001Mapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.ArrayList;
import java.util.List;

import kr.co.starfield.model.vo.AITF001Vo;
import kr.co.starfield.model.vo.AITF002Vo;
import kr.co.starfield.model.vo.AITF003Vo;

/**
 *  AITF001Mapper(megaBox) interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author hhLee
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

public interface AITF001Mapper {
	
	// megaBox-P200목록 조회 (박스오피스-메가박스 전체 기준)
	public List<AITF001Vo> getP200List(AITF001Vo vo);
	
	// megaBox-P201목록 조회 (현재 상영작-하남 개별 기준)
	public List<AITF002Vo> getP201List(AITF002Vo vo);
	
	// megaBox-P202목록 조회 (상영 예정작-메가박스 전체 기준)
	public List<AITF003Vo> getP202List(AITF003Vo vo);
	
}
