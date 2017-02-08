package kr.co.starfield.mapper;

import java.util.ArrayList;
import java.util.List;

import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.Push;
import kr.co.starfield.model.PushFilter;
import kr.co.starfield.model.vo.APSH001Vo;

/**
 *  AEVT001Mapper (Event) interface
 *
 * Copyright Copyright (c) 2016
 *
 * @author hhlee
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

public interface APSH001Mapper {
	
	// Push 목록 조회
	public ArrayList<Push> getPushList(PushFilter filter);
	
	// Push 상세
	public Push getPush(PushFilter filter);
	
	public void regPush(APSH001Vo vo);

	// Push 수정
	public void updatePush(APSH001Vo vo);
		
	// Push 삭제
	public int deletePushes(CommonDeleteModel del_seq_arr);
	
	// Push Send 건수
	public int getSendCnt(List<String> seq_arr);

}
