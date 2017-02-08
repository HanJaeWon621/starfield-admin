package kr.co.starfield.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import kr.co.starfield.mapper.AOPR001Mapper;
import kr.co.starfield.mapper.AOPR003Mapper;
import kr.co.starfield.model.Operation;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.StarFieldHoliday;
import kr.co.starfield.model.StarFieldOperation;
import kr.co.starfield.model.vo.SOPR001Vo;

/**
 * AOPR001(운영정보) 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author hhLee
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

@Service
public class AOPR001Service {
	
	private Logger ll = LoggerFactory.getLogger(AOPR001Service.class);
	
	@Autowired
	AOPR001Mapper aopr001Mapper;
	
	@Autowired
	private AOPR003Mapper aopr003Mapper;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	/**
	 * operation get 
	 * @param 
	 * @return Operation
	 * @throws BaseException 
	 */
	public Operation getOperation(String bcn_cd) throws BaseException {
		Operation operation = new  Operation();
		
		operation.bcn_cd = bcn_cd;
		operation = aopr001Mapper.getOperation(operation);
		if(operation == null) {
			BaseException be = new BaseException(ErrorCode.Operation.OPERATION_NOT_FOUND_DATA);
			throw be;
		}
		
		return operation;
	}

	/**
	 * operation modify
	 * @param vo
	 * @return 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyOperation(StarFieldOperation starFieldOperation) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		aopr001Mapper.modifyOperation(starFieldOperation.operation);
			
		for(StarFieldHoliday starFieldHoliday : starFieldOperation.starFieldHolidayList) {
			aopr003Mapper.regStarFieldHoliday(starFieldHoliday);
		}

		syncOperation(starFieldOperation.operation.bcn_cd);
		return result;	
	}

	
	@Transactional
	public SimpleResult syncOperation(String bcn_cd) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		SyncRun(bcn_cd);
		syncStarFieldHoliday(bcn_cd);
		
		return result;
	}
	
	
	
	/**
	 * StarFieldHoliday redis sync
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	
	@Transactional
	private SimpleResult syncStarFieldHoliday(String bcn_cd) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		HashOperations<String, String, String> hOps = stringRedisTemplate.opsForHash();
		
		

		StarFieldHoliday starFieldHoliday = new StarFieldHoliday();
		starFieldHoliday.bcn_cd = bcn_cd;
		starFieldHoliday.sts = StatusCode.Common.USE.getCode();
			
		ArrayList<StarFieldHoliday> holidayList = aopr003Mapper.getStarFieldHolidayList(starFieldHoliday);
		
		ArrayList<Map<String, Object>> resultList = new ArrayList<>();
		
		for(StarFieldHoliday holiday : holidayList){
			Map<String, Object> map = new HashMap<>();
			map.put("bcn_cd", holiday.bcn_cd);
			map.put("year", holiday.year);
			map.put("mont", holiday.mont);
			map.put("day", holiday.day);
			map.put("open_hr_min", holiday.open_hr_min);
			map.put("end_hr_min", holiday.end_hr_min);
			resultList.add(map);
		}
		
		try {
			stringRedisTemplate.delete(stringRedisTemplate.keys("holiday:bcn:" + bcn_cd));
			
			for(Map<String, Object> resultMap : resultList){
				hOps.put("holiday:bcn:" + bcn_cd, resultMap.get("year") + "-" + resultMap.get("mont") + "-" + resultMap.get("day"), new ObjectMapper().writeValueAsString(resultMap));
			}
		} catch (Exception e) {
			e.printStackTrace();
			BaseException be = new BaseException(ErrorCode.Operation.STARFIELD_HOLIDAY_SYNC_FAIL_REDIS);
			throw be;
		}
		
		return result;
	}
	
	/**
	 * operation redis sync
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	private SimpleResult SyncRun(String bcn_cd) throws BaseException {
		
		SimpleResult result = new SimpleResult();
		
		
		try{
			
			String keysName = "operation";
			//String keysName2 = "instaToken";
		
			//stringRedisTemplate.delete(stringRedisTemplate.keys(keysName2 + ":*"));
			
			Operation oprVo = new Operation();
			oprVo.bcn_cd = bcn_cd;
			
			oprVo =  aopr001Mapper.getOperation(oprVo);
			Map<String, Object> OprMap = new LinkedHashMap<>();
			
			OprMap.put("opr_info_seq", oprVo.opr_info_seq);
			OprMap.put("bcn_cd", oprVo.bcn_cd);
			OprMap.put("norm_day_open_hr_min", oprVo.norm_day_open_hr_min);
			OprMap.put("norm_day_end_hr_min", oprVo.norm_day_end_hr_min);
			OprMap.put("weekend_open_hr_min", oprVo.weekend_open_hr_min);
			OprMap.put("weekend_end_hr_min", oprVo.weekend_end_hr_min);
			OprMap.put("reps_num1", oprVo.reps_num1);
			OprMap.put("reps_num2", oprVo.reps_num2);
			OprMap.put("reps_num3", oprVo.reps_num3);
			OprMap.put("srvc_cntr_num1", oprVo.srvc_cntr_num1);
			OprMap.put("srvc_cntr_num2", oprVo.srvc_cntr_num2);
			OprMap.put("srvc_cntr_num3", oprVo.srvc_cntr_num3);
			OprMap.put("reps_fax_num1", oprVo.reps_fax_num1);
			OprMap.put("reps_fax_num2", oprVo.reps_fax_num2);
			OprMap.put("reps_fax_num3", oprVo.reps_fax_num3);
			OprMap.put("reps_email", oprVo.reps_email);
			OprMap.put("reps_nm", oprVo.reps_nm);
			OprMap.put("reps_addr1", oprVo.reps_addr1);
			OprMap.put("reps_addr2", oprVo.reps_addr2);
			
			//운영정보 레디스 업로드
			SyncApply(OprMap, keysName, "bcn_cd");

			//인스타그램 토큰 레디스 업로드
			//Map<String, Object> instaMap = new HashMap<>();
			//instaMap.put("token", oprVo.insta_token);
			
			//SyncApply(instaMap, keysName2, "");
			
		} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.Operation.OPERATION_SYNC_DATA_PARSING_FAILED);
			throw be;
		}
		
		return result;	
		
	}
	
	public void SyncApply(Map<String, Object> OprResultMap, String keysName, String keyValue) throws BaseException {
		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
		
		try {
			vOps.set(keysName + ":" + OprResultMap.get(keyValue), new ObjectMapper().writeValueAsString(OprResultMap));
			
		} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.Operation.OPERATION_SYNC_FAIL_REDIS);
			throw be;
			
		}
	}
	
}


