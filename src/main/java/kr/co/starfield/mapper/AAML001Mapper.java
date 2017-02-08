/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.List;

import kr.co.starfield.model.AAML001;
import kr.co.starfield.model.AAML002;

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

public interface AAML001Mapper {

	// 전제방문자 통계 리스트(일별)
	public List<AAML001> getAllVisitorStatsDay(AAML001 vo);

	// 전제방문자 통계 리스트(주별)
	public List<AAML001> getAllVisitorStatsWeek(AAML001 vo);

	// 전제방문자 통계 리스트(월별)
	public List<AAML001> getAllVisitorStatsMonth(AAML001 vo);

	// 전제방문자 통계 리스트(년별)
	public List<AAML001> getAllVisitorStatsYear(AAML001 vo);
	
}
