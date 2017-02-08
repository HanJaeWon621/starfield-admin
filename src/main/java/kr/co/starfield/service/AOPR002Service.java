package kr.co.starfield.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.mapper.AOPR002Mapper;
import kr.co.starfield.model.Holiday;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SOPR002Vo;


/**
 * AOPR002(Holiday) 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.06.23
 */

@Service
public class AOPR002Service {

	@Autowired
	private AOPR002Mapper aopr002Mapper;
	
	@Autowired
	private AOPR001Service aopr001Service;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	private static final Logger ll = LoggerFactory.getLogger(AOPR002Service.class);
		
	/**
	 * Holiday 등록
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */

	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regHolidays(Holiday[] modelList) throws BaseException {
		SimpleResult result = new SimpleResult();

		for(Holiday holiday : modelList){
			aopr002Mapper.regHoliday(holiday);
		}
		
		result.extra = modelList[0].holiday_seq;
		
		aopr001Service.syncOperation(modelList[0].bcn_cd);
		
		return result;
	}
	
	/**
	 * Holiday 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<Holiday> getHolidayList(Holiday model) throws BaseException {
		
		ListResult<Holiday> result = new ListResult<Holiday>();
			
		result.list.addAll(aopr002Mapper.getHolidayList(model));
	
		return result;
	}
	
	/**
	 * Holiday 상세
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	public Holiday getHoliday(Holiday model) throws BaseException {
		
		Holiday holiday = aopr002Mapper.getHoliday(model);
		
		if(holiday == null){
			BaseException be = new BaseException(ErrorCode.Operation.HOLIDAY_NOT_FOUND_DATA);
			throw be;
		} 
		
		return holiday;
	}
	
	/**
	 * Holiday 수정
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyHoliday(Holiday model) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		aopr002Mapper.modifyHoliday(model);

		aopr001Service.syncOperation(model.bcn_cd);
		
		return result;
	}
	
	/**
	 * Holiday 삭제 
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteHoliday(Holiday[] modelList) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		
		for(Holiday model: modelList) {
			aopr002Mapper.deleteHoliday(model);
		}
		
		aopr001Service.syncOperation(modelList[0].bcn_cd);
		
		return result;
	}

}
