package kr.co.starfield.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.common.Utils;
import kr.co.starfield.mapper.AEVT001Mapper;
import kr.co.starfield.model.Event;
import kr.co.starfield.model.EventApply;
import kr.co.starfield.model.EventSimpleFilter;
import kr.co.starfield.model.EventWinner;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.EventFilter;
import kr.co.starfield.model.EventSelect;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.PickInfo;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SEVT001Vo;
import kr.co.starfield.model.vo.SEVT003_DVo;
import scala.util.Random;


/**
 * AVT001(event) 서비스 클래스
 *
 * @author hhlee
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

@Service
public class AEVT001Service {

	@Autowired
	private AEVT001Mapper aevt001Mapper;
		
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	private static final Logger ll = LoggerFactory.getLogger(AEVT001Service.class);
	
	/**
	 * Event 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	
	public ListResult<Event> getEventList(EventFilter eventFilter) throws BaseException {
		
		ListResult<Event> result = new ListResult<Event>();
			
		List<Event> eventList = aevt001Mapper.getEventList(eventFilter);
			
		result.list = eventList;
		
		int tot_cnt = eventList.size() > 0 ? eventList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(eventFilter.offset, eventFilter.limit, tot_cnt);
			
		result.paging = paging;
		
		return result;
	}

	public SimpleResult deleteEvents(CommonDeleteModel eventDelete) {
		SimpleResult result = new SimpleResult();
		aevt001Mapper.deleteEvents(eventDelete);
		return result;
	}

	public SimpleResult updateEvents(Event event) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		SEVT001Vo eventVo = new SEVT001Vo();
		try {
			eventVo = event.toEventVo();
		} catch (ParseException e) {
			BaseException be = new BaseException(ErrorCode.Event.EVENT_DATE_PARSE_ERROR);
			throw be;
		}
		
		aevt001Mapper.updateEvents(eventVo);
		return result;
	}
	
	/**
	 * EventWeb 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
//	public ListResult<EventWeb> getEventWebList(SEVT001Vo vo) throws BaseException {
//		
//		ListResult<EventWeb> result = new ListResult<EventWeb>();
//			
//		result.list.addAll(aevt001Mapper.getEventWebList(vo));
//
//		int tot_cnt = aevt001Mapper.getTotalCount(vo);
//		
//		Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
//			
//		result.paging = paging;
//		
//		return result;
//	}
	
	
	/**
	 * event 상세
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	public Event getEvent(EventFilter eventFilter) throws BaseException {
		
		Event event = aevt001Mapper.getEvent(eventFilter);
		
		if(event == null){
			BaseException be = new BaseException(ErrorCode.Event.EVENT_NOT_FOUND_DATA);
			throw be;
		} 
		
		return event;
	}
	
	/**
	 * 이벤트 등록
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regEvent(Event event) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		SEVT001Vo eventVo = new SEVT001Vo();
		try {
			eventVo = event.toEventVo();
		} catch (ParseException e) {
			BaseException be = new BaseException(ErrorCode.Event.EVENT_DATE_PARSE_ERROR);
			throw be;
		}
		aevt001Mapper.regEvent(eventVo);
		
		result.extra = eventVo.evt_seq;
		
		return result;
	}

	public ListResult<EventApply> getEventApplyMemberList(EventSimpleFilter eventApplyFilter, boolean masking) {
		ListResult<EventApply> result = new ListResult<EventApply>();
		
		List<EventApply> eventApplyList = aevt001Mapper.getEventApplyMemberList(eventApplyFilter);
			
		result.list = eventApplyList;
		
		if(masking) {
			for(EventApply list : result.list) {
				list.mbr_nm = Utils.Str.maskingName(list.mbr_nm);
				list.mbr_cel_num2 = Utils.Str.allMasking(list.mbr_cel_num2);
			}
		}
		
		int tot_cnt = eventApplyList.size() > 0 ? eventApplyList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(eventApplyFilter.offset, eventApplyFilter.limit, tot_cnt);
			
		result.paging = paging;
		
		return result;
	}

	/**
	 * 스탬프 이벤트 응모자정보 상세 조회
	 * @param  vo
	 * @return
	 */
//	public ListResult<EventApply> getStempApplyMemberList(EventSimpleFilter vo) {
//		
//		ListResult<EventApply> result = new ListResult<EventApply>();
//		
//		result.list.addAll(aevt001Mapper.getStempApplyMemberList(vo));
//		
//		int tot_cnt = result.list.size() > 0 ? result.list.size() : 0;
//		
//		Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
//			
//		result.paging = paging;
//		
//		return result;
//		
//	}
	public ListResult<EventApply> getStempApplyMemberList(EventSimpleFilter vo) {
		
		ListResult<EventApply> result = new ListResult<EventApply>();
		
		List<EventApply> eventApplyList = aevt001Mapper.getStempApplyMemberList(vo);
		
		result.list = eventApplyList;
		
		int tot_cnt = result.list.size() > 0 ? eventApplyList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
			
		result.paging = paging;
		
		return result;
		
	}
	
	public ListResult<EventWinner> getEventWinnerList(EventSimpleFilter eventApplyFilter, boolean masking) {
		ListResult<EventWinner> result = new ListResult<EventWinner>();
		
		List<EventWinner> eventApplyList = aevt001Mapper.getEventWinnerList(eventApplyFilter);
			
		result.list = eventApplyList;
		
		if(masking) {
			for(EventWinner list : result.list) {
				list.mbr_nm = Utils.Str.maskingName(list.mbr_nm);
				list.mbr_cel_num2 = Utils.Str.allMasking(list.mbr_cel_num2);
			}
		}
		
		
		int tot_cnt = eventApplyList.size() > 0 ? eventApplyList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(eventApplyFilter.offset, eventApplyFilter.limit, tot_cnt);
			
		result.paging = paging;
		
		return result;
	}
	
	/**
	 * Event select 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	
	public ArrayList<EventSelect> getEventSelectList(EventFilter eventFilter) throws BaseException {
		return aevt001Mapper.getEventSelectList(eventFilter);
	}

	/**
	 * 이벤트 추첨
	 * @param pickinfo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult pick(PickInfo pickInfo) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		EventSimpleFilter filter = new EventSimpleFilter();
		filter.evt_seq = pickInfo.evt_seq;
		filter.mbr_sts = StatusCode.Common.USE.getCode();
		
		List<EventApply> eventApplyList = aevt001Mapper.getEventApplyMemberList(filter);
		
		if(eventApplyList.size() < pickInfo.pick_count) {
			BaseException be = new BaseException(ErrorCode.Event.EVENT_APPLY_SIZE_ERROR);
			throw be;
		}
		
		List<SEVT003_DVo> eventWinnerList = new ArrayList<>();
		
		Integer[] winnerIndex = getWinnerIndex(pickInfo.pick_count, eventApplyList.size());
		
		int winIdx = 0;
		
		for(int i = 0; i < pickInfo.mult_pick_info.size(); i++) {

			int pickCntByItem = Integer.parseInt(pickInfo.mult_pick_info.get(i).get("pick_count"));
			String winItem = pickInfo.mult_pick_info.get(i).get("win_item");
			
			for(int j = 0; j < pickCntByItem; j++) {
				EventApply winner = eventApplyList.get(winnerIndex[winIdx]);
				SEVT003_DVo vo = new SEVT003_DVo();
				vo.evt_seq = pickInfo.evt_seq;
				vo.won_idx = ++winIdx;
				vo.mbr_seq = winner.mbr_seq;
				vo.pick_div = pickInfo.pick_div;
				vo.pick_div_cnt =  pickInfo.pick_div_cnt;
				vo.pick_count = pickCntByItem;
				vo.win_item = winItem;
				vo.sts = StatusCode.Common.USE.getCode();
				vo.reg_usr = pickInfo.user;
				vo.mod_usr = pickInfo.user;
				eventWinnerList.add(vo);
			}
		}

		for(SEVT003_DVo vo : eventWinnerList) 
		{
			aevt001Mapper.regEventWinner(vo);
		}
		
		// 추첨일자 추가 TODO mod user 추가
		SEVT001Vo sevt001Vo = new SEVT001Vo();
		sevt001Vo.evt_seq_arr= new String[]{pickInfo.evt_seq};
		sevt001Vo.evt_pick_dt = new Date();
		sevt001Vo.mod_usr = pickInfo.user;
		
		aevt001Mapper.updateEvents(sevt001Vo);

		return result;
	}
	
	public Integer[] getWinnerIndex(int pickCnt, int applyCnt) {
		Random rn = new Random();
		
		Integer[] applyIndex = new Integer[applyCnt];
		
		for(int i = 0; i < applyCnt; i++) {
			applyIndex[i] = i;
		}
		
		for(int i = 0; i < rn.nextInt(10000); i++) {
			int idx1 = rn.nextInt(applyCnt);
			int idx2 = rn.nextInt(applyCnt);
			
			int temp = applyIndex[idx1];
			applyIndex[idx1] = applyIndex[idx2];
			applyIndex[idx2] = temp;
		}
		
		Integer[] winnerIndex = new Integer[pickCnt];
		
		System.arraycopy(applyIndex, 0, winnerIndex, 0, pickCnt);
		
		return winnerIndex;
	}
	
	public static void main(String[] args) {
		AEVT001Service  aa = new AEVT001Service();
		
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
		System.out.println(Arrays.toString(aa.getWinnerIndex(5, 20)));
	}
}
