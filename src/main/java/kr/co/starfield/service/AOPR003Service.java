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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.mapper.AOPR003Mapper;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.StarFieldHoliday;
import kr.co.starfield.model.vo.SOPR003Vo;


/**
 * AOPR003(StarFieldHoliday) 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.07. 11
 */

@Service
public class AOPR003Service {

	@Autowired
	private AOPR003Mapper aopr003Mapper;
	
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	private static final Logger ll = LoggerFactory.getLogger(AOPR003Service.class);
		
	/**
	 * StarFieldHoliday 등록
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */

	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regStarFieldHolidays(StarFieldHoliday[] modelList) throws BaseException {
		SimpleResult result = new SimpleResult();

		for(StarFieldHoliday model : modelList){
			aopr003Mapper.regStarFieldHoliday(model);
		}
		
		result.extra = modelList[0].stf_irgu_opr_seq;
		
		syncStarFieldHoliday(modelList[0].bcn_cd);
		
		return result;
	}
	
	/**
	 * StarFieldHoliday 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<StarFieldHoliday> getStarFieldHolidayList(StarFieldHoliday model) throws BaseException {
		
		ListResult<StarFieldHoliday> result = new ListResult<StarFieldHoliday>();
			
		result.list.addAll(aopr003Mapper.getStarFieldHolidayList(model));
	
		return result;
	}
	
	/**
	 * StarFieldHoliday 상세
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	public StarFieldHoliday getStarFieldHoliday(StarFieldHoliday model) throws BaseException {
		
		StarFieldHoliday starFieldHoliday = aopr003Mapper.getStarFieldHoliday(model);
		
		if(starFieldHoliday == null){
			BaseException be = new BaseException(ErrorCode.Operation.STARFIELD_HOLIDAY_NOT_FOUND_DATA);
			throw be;
		} 
		
		return starFieldHoliday;
	}
	
	/**
	 * StarFieldHoliday 수정
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyStarFieldHoliday(StarFieldHoliday model) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		aopr003Mapper.modifyStarFieldHoliday(model);

		return result;
	}
	
	/**
	 * StarFieldHoliday 삭제 
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteStarFieldHoliday(StarFieldHoliday model) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		model.sts = StatusCode.Common.DELETE.getCode();
		aopr003Mapper.modifyStarFieldHoliday(model);
		
		return result;
	}

	
	/**
	 * StarFieldHoliday redis sync
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	
	@Transactional
	public SimpleResult syncStarFieldHoliday(String bcn_cd) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		HashOperations<String, String, String> hOps = stringRedisTemplate.opsForHash();
		
		stringRedisTemplate.delete(stringRedisTemplate.keys("holiday:bcn:" + bcn_cd));

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
}
