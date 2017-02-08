package kr.co.starfield.mapper;

import java.util.ArrayList;
import java.util.List;

import kr.co.starfield.model.Event;
import kr.co.starfield.model.EventApply;
import kr.co.starfield.model.EventSimpleFilter;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.EventFilter;
import kr.co.starfield.model.EventSelect;
import kr.co.starfield.model.EventWeb;
import kr.co.starfield.model.EventWinner;
import kr.co.starfield.model.vo.SEVT001Vo;
import kr.co.starfield.model.vo.SEVT003_DVo;

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

public interface AEVT001Mapper {
	
	// Event 목록 조회
	public ArrayList<Event> getEventList(EventFilter eventFilter);
	
	// Event 상세
	public Event getEvent(EventFilter eventFilter);
	
	// Event Web 목록 조회
	public ArrayList<EventWeb> getEventWebList(SEVT001Vo vo);
	
	// Event Web Total Count
	public int getTotalCount(SEVT001Vo vo);

	public void regEvent(SEVT001Vo eventVo);

	// Event 삭제
	public int deleteEvents(CommonDeleteModel eventDelete);

	// Event 수정
	public void updateEvents(SEVT001Vo sevt001Vo);

	// Event 응모자 상세 조회
	public List<EventApply> getEventApplyMemberList(EventSimpleFilter filter);
	
	// 스탬프 이벤트 응모자정보 상세 조회
	public List<EventApply> getStempApplyMemberList(EventSimpleFilter filter);
	
	// Event 당첨자
	public List<EventWinner> getEventWinnerList(EventSimpleFilter filter);
	
	// 이벤트 선택 리스트
	public ArrayList<EventSelect> getEventSelectList(EventFilter filter);

	public void regEventWinner(SEVT003_DVo vo);
}
