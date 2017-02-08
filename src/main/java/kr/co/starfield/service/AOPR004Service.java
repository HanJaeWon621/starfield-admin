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
import kr.co.starfield.mapper.AOPR004Mapper;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.TenantHoliday;
import kr.co.starfield.model.vo.SOPR004Vo;


/**
 * AOPR003(TenantHoliday) 서비스 클래스
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
public class AOPR004Service {

	@Autowired
	private AOPR004Mapper aopr004Mapper;
	
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	private static final Logger ll = LoggerFactory.getLogger(AOPR004Service.class);
		
	/**
	 * TenantHoliday 등록
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */

	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regTenantHolidays(ArrayList<SOPR004Vo> voList) throws BaseException {
		SimpleResult result = new SimpleResult();

		for(SOPR004Vo vo : voList){
			aopr004Mapper.regTenantHoliday(vo);
		}
		
		result.extra = voList.get(0).tnt_irgu_opr_seq;
		
		if(voList.size() > 0){
			syncTenantHoliday(voList.get(0).bcn_cd);
		}
		
		return result;
	}
	
	/**
	 * TenantHoliday 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<TenantHoliday> getTenantHolidayList(SOPR004Vo vo) throws BaseException {
		
		ListResult<TenantHoliday> result = new ListResult<TenantHoliday>();
			
		List<SOPR004Vo> voList = aopr004Mapper.getTenantHolidayList(vo);
			
		for(SOPR004Vo resultVo : voList){
			result.list.add(resultVo.toModel());
		}
	
		return result;
	}
	
	/**
	 * TenantHoliday 상세
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	public TenantHoliday getTenantHoliday(SOPR004Vo vo) throws BaseException {
		
		SOPR004Vo resultVo = aopr004Mapper.getTenantHoliday(vo);
		
		if(resultVo == null){
			BaseException be = new BaseException(ErrorCode.Operation.TENANT_HOLIDAY_NOT_FOUND_DATA);
			throw be;
		} 
		
		return resultVo.toModel();
	}
	
	/**
	 * TenantHoliday 수정
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyTenantHoliday(SOPR004Vo vo) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		aopr004Mapper.modifyTenantHoliday(vo);

		syncTenantHoliday(vo.bcn_cd);
		return result;
	}
	
	/**
	 * TenantHoliday 삭제 
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteTenantHoliday(SOPR004Vo vo) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		vo.sts = StatusCode.Common.DELETE.getCode();
		aopr004Mapper.modifyTenantHoliday(vo);
		
		syncTenantHoliday(vo.bcn_cd);
		return result;
	}

	
	/**
	 * TenantHoliday redis sync
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	
	@Transactional
	public SimpleResult syncTenantHoliday(String bnc_cd) throws BaseException {
		
		SimpleResult result = new SimpleResult();
		
		HashOperations<String, String, String> hOps = stringRedisTemplate.opsForHash();
		
		stringRedisTemplate.delete(stringRedisTemplate.keys("holiday:tnt*"));

		SOPR004Vo vo = new SOPR004Vo();
		vo.bcn_cd = bnc_cd;
		vo.sts = StatusCode.Common.USE.getCode();
			
		List<SOPR004Vo> holidayList = aopr004Mapper.getTenantHolidayList(vo);
		
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		for(SOPR004Vo holidayVo : holidayList){
			Map<String, Object> map = new HashMap<>();
			map.put("tnt_irgu_opr_seq", holidayVo.tnt_irgu_opr_seq);
			map.put("tnt_seq", holidayVo.tnt_seq);
			map.put("bcn_cd", holidayVo.bcn_cd);
			map.put("year", holidayVo.year);
			map.put("mont", holidayVo.mont);
			map.put("day", holidayVo.day);
			map.put("open_hr_min", holidayVo.open_hr_min);
			map.put("end_hr_min", holidayVo.end_hr_min);
			resultList.add(map);
		}
		
		try {
			for(Map<String, Object> resultMap : resultList){
				hOps.put("holiday:tnt:" + resultMap.get("tnt_seq"), resultMap.get("year") + "-" + resultMap.get("mont") + "-" + resultMap.get("day"), new ObjectMapper().writeValueAsString(resultMap));
			}
		} catch (Exception e) {
			e.printStackTrace();
			BaseException be = new BaseException(ErrorCode.Operation.TENANT_HOLIDAY_SYNC_FAIL_REDIS);
			throw be;
		}
		
		return result;
	}
}
