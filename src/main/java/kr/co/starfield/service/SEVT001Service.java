package kr.co.starfield.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.IErrorCode;
import kr.co.starfield.mapper.SEVT001Mapper;
import kr.co.starfield.model.EventFilter;
import kr.co.starfield.model.EventWeb;
import kr.co.starfield.model.EventWinner;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SEVT001Vo;
import kr.co.starfield.model.vo.SEVT003Vo;


/**
 * AVT001(event) 서비스 클래스
 *
 * @author hhlee
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

@Service
public class SEVT001Service {

	@Autowired
	private SEVT001Mapper sevt001Mapper;
		
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	private static final Logger ll = LoggerFactory.getLogger(SEVT001Service.class);
	
	/**
	 * EventWeb 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<EventWeb> getEventWebList(EventFilter filter) throws BaseException {
		
		ListResult<EventWeb> result = new ListResult<EventWeb>();
			
		result.list.addAll(sevt001Mapper.getEventWebList(filter));

		if(filter.limit > 0){
			int tot_cnt = sevt001Mapper.getTotalCount(filter);
	
			Paging paging = new Paging(filter.offset, filter.limit, tot_cnt);
				
			result.paging = paging;
		}
		
		return result;
	}
	
	/**
	 * oldEventWeb 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<EventWeb> getOldEventWebList(EventFilter filter) throws BaseException {

		ListResult<EventWeb> result = new ListResult<EventWeb>();
			
		result.list.addAll(sevt001Mapper.getOldEventWebList(filter));

		if(filter.limit > 0){
			int tot_cnt = sevt001Mapper.getOldTotalCount(filter);
	
			Paging paging = new Paging(filter.offset, filter.limit, tot_cnt);
				
			result.paging = paging;
		}
		
		return result;
	}
	
	/**
	 * EventWeb 상세
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	public EventWeb getEventWeb(EventFilter filter) throws BaseException {
		
		EventWeb result = sevt001Mapper.getEventWeb(filter);
		sevt001Mapper.eventCount(filter);
		if(result == null){
			BaseException be = new BaseException(ErrorCode.Event.EVENT_NOT_FOUND_DATA);
			throw be;
		} 
		return result;
	}
	
	
	
	/**
	 * 참여한 EventWeb 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<EventWeb> getEventEntryWebList(EventFilter filter) throws BaseException {
		
		ListResult<EventWeb> result = new ListResult<EventWeb>();
			
		result.list.addAll(sevt001Mapper.getEventEntryWebList(filter));
		
		return result;
	}
	
	/**
	 * Event 응모
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regEventEntry(SEVT003Vo vo) throws BaseException {
		
		SimpleResult result = new SimpleResult();
		
		sevt001Mapper.regEventEntry(vo);
		
		return result;
	}
	
	/**
	 * 참여한 이벤트 삭제
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteEventEntry(SEVT003Vo vo) throws BaseException {
		
		SimpleResult result = new SimpleResult();
		
		sevt001Mapper.deleteEventEntry(vo);
		
		return result;
	}
	
	/**
	 * 당첨자 리스트 조회
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<EventWinner> getEventWinnerList(EventFilter filter) throws BaseException {
		
		ListResult<EventWinner> result = new ListResult<EventWinner>();
		
		result.list.addAll(sevt001Mapper.getEventWinnerList(filter));
		
		return result;
	}
}
