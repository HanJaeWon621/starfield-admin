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
import kr.co.starfield.mapper.AEVT001Mapper;
import kr.co.starfield.mapper.APSH001Mapper;
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
import kr.co.starfield.model.Push;
import kr.co.starfield.model.PushFilter;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.APSH001Vo;
import kr.co.starfield.model.vo.SEVT001Vo;
import kr.co.starfield.model.vo.SEVT003_DVo;
import scala.util.Random;


/**
 * APSH001(push) 서비스 클래스
 *
 * @author yhkim
 * @version	1.0,
 * @see
 * @date 2016.10.10
 */

@Service
public class APSH001Service {

	@Autowired
	private APSH001Mapper apsh001Mapper;
	
	private static final Logger ll = LoggerFactory.getLogger(APSH001Service.class);
	
	/**
	 * Push 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	
	public ListResult<Push> getPushList(PushFilter filter) throws BaseException {
		
		ListResult<Push> result = new ListResult<Push>();
			
		List<Push> list = apsh001Mapper.getPushList(filter);
			
		result.list = list;
		
		int tot_cnt = list.size() > 0 ? list.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(filter.offset, filter.limit, tot_cnt);
			
		result.paging = paging;
		
		return result;
	}

	public SimpleResult deletePushes(CommonDeleteModel del_seq_arr) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		int cnt = apsh001Mapper.getSendCnt(Arrays.asList(del_seq_arr.seq_arr));
		
		if(cnt > 0) {
			BaseException be = new BaseException(ErrorCode.Push.PUSH_SELECTED_SIZE_ERROR_FOR_DELETE);
			throw be;
		}
		
		apsh001Mapper.deletePushes(del_seq_arr);
		return result;
	}

	public SimpleResult updatePush(Push push) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		int cnt = apsh001Mapper.getSendCnt(Arrays.asList(push.push_mng_seq));
		
		if(cnt > 0) {
			BaseException be = new BaseException(ErrorCode.Push.PUSH_SELECTED_SIZE_ERROR_FOR_MODIFY);
			throw be;
		}
		
		APSH001Vo vo = new APSH001Vo();
		
		try {
			vo = push.toPushVo();
		} catch (ParseException e) {
			BaseException be = new BaseException(ErrorCode.Push.PUSH_DATE_PARSE_ERROR);
			throw be;
		}
		
		apsh001Mapper.updatePush(vo);
		return result;
	}
	
	/**
	 * Push 상세
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	public Push getPush(PushFilter filter) throws BaseException {
		
		Push push = apsh001Mapper.getPush(filter);
		
		if(push == null){
			BaseException be = new BaseException(ErrorCode.Push.PUSH_NOT_FOUND_DATA);
			throw be;
		} 
		
		return push;
	}
	
	/**
	 * Push 등록
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regPush(Push push) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		APSH001Vo vo = new APSH001Vo();
		
		try {
			vo = push.toPushVo();
		} catch (ParseException e) {
			BaseException be = new BaseException(ErrorCode.Push.PUSH_DATE_PARSE_ERROR);
			throw be;
		}
		
		apsh001Mapper.regPush(vo);
		
		result.extra = vo.push_mng_seq;
		
		return result;
	}

}
