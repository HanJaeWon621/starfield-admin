package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.EventFilter;
import kr.co.starfield.model.EventWeb;
import kr.co.starfield.model.EventWinner;
import kr.co.starfield.model.vo.SEVT003Vo;

/**
 *  SEVT001Mapper (Event) interface
 *
 * Copyright Copyright (c) 2016
 *
 * @author hhlee
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

public interface SEVT001Mapper {
	// Event Web 목록 조회
	public ArrayList<EventWeb> getEventWebList(EventFilter vo);
	
	// Event Web Total Count
	public int getTotalCount(EventFilter vo);
	
	// Event Web 상세
	public EventWeb getEventWeb(EventFilter vo);
	
	public void eventCount(EventFilter vo);
	
	// Event Web 목록 조회
	public ArrayList<EventWeb> getOldEventWebList(EventFilter vo);
	
	// Event Web Total Count
	public int getOldTotalCount(EventFilter vo);
	
	//참여한 이벤트 조회
	public ArrayList<EventWeb> getEventEntryWebList(EventFilter vo);
	
	//이벤트 참여
	public void regEventEntry(SEVT003Vo vo);
	
	//참여한 이벤트 지우기
	public void deleteEventEntry(SEVT003Vo vo);
	
	//이벤트 당첨자 리스
	public ArrayList<EventWinner> getEventWinnerList(EventFilter vo);
}
