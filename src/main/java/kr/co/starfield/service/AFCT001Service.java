package kr.co.starfield.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.mapper.AFCT001Mapper;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.Facility;
import kr.co.starfield.model.FacilityDetail;
import kr.co.starfield.model.FacilityFilter;
import kr.co.starfield.model.FacilityForExcel;
import kr.co.starfield.model.FacilityForList;
import kr.co.starfield.model.FacilityGroup;
import kr.co.starfield.model.FacilityGroupFilter;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SFCT001Vo;
import kr.co.starfield.model.vo.SFCT001_DVo;


/**
 * AFCT001Service(facility) 서비스 클래스
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
public class AFCT001Service {

	@Autowired
	private AFCT001Mapper afct001Mapper;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	private static final Logger ll = LoggerFactory.getLogger(AFCT001Service.class);
		
	String bcn_no = "01";
	
	/**
	 * 편의시설 레디스 동기화 세트
	 * @throws BaseException 
	 */
	public SimpleResult syncRedisFacilitySuite() throws BaseException{
		
		SimpleResult result = new SimpleResult();
		
		ll.info("====================== syncRedisFacilitySuite start ====================== ");
		syncRun("01");
		syncFacilityDetail("01");
		syncFacilityExp("01");
		syncFacilityFloor("01");
		ll.info("======================  syncRedisFacilitySuite end ====================== ");
		
		return result;
	}
	

	
	
	/**
	 * facility redis sync
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public void syncRun(String bcn_cd) throws BaseException {
		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
		
		try{
			String keysName = "facility";
			stringRedisTemplate.delete(zOps.range("keys:" + keysName, 0, -1));
			stringRedisTemplate.delete("keys:"+ keysName);
			
			SFCT001Vo vo = new SFCT001Vo();
			vo.bcn_cd = bcn_cd;
			vo.sts = StatusCode.Common.USE.getCode();

			List<Map<String, Object>> resultList = new ArrayList<>();
			List<Facility> facilityList = afct001Mapper.getFacilityList(vo);

			for(Facility facilityListVo : facilityList) {				
				SFCT001_DVo facilityDetailVo = new SFCT001_DVo();
				
				facilityDetailVo.conv_faci_seq = facilityListVo.conv_faci_seq;
				
				Map<String, Object> instaMap = new LinkedHashMap<>();
				Map<String, Object> facilityMap = new LinkedHashMap<>();
				List<Map<String, String>> facilityDetails = new ArrayList<>();
				List<SFCT001_DVo> facilityDetailList = afct001Mapper.getFacilityDetailList(facilityDetailVo);
				
				if(facilityDetailList.size() == 0) {
					continue;
				}
				
				for(SFCT001_DVo detailVo : facilityDetailList) {
					Map<String, String> facilityDetailMap = new HashMap<>();
					
					facilityDetailMap.put("conv_faci_dtl_seq", detailVo.conv_faci_dtl_seq);
					facilityDetailMap.put("conv_faci_seq", detailVo.conv_faci_seq);
					facilityDetailMap.put("conv_faci_nm_ko", detailVo.conv_faci_nm_ko);
					facilityDetailMap.put("conv_faci_nm_en", detailVo.conv_faci_nm_en);
					facilityDetailMap.put("conv_faci_nm_cn", detailVo.conv_faci_nm_cn);
					facilityDetailMap.put("conv_faci_nm_jp", detailVo.conv_faci_nm_jp);
					facilityDetailMap.put("fl", detailVo.fl);

					facilityDetailMap.put("room_num", detailVo.room_num);
					facilityDetailMap.put("plce_ko", detailVo.plce_ko);
					facilityDetailMap.put("plce_en", detailVo.plce_en);
					
					facilityDetailMap.put("tel_num1", detailVo.tel_num1);
					facilityDetailMap.put("tel_num2", detailVo.tel_num2);
					facilityDetailMap.put("tel_num3", detailVo.tel_num3);
					facilityDetailMap.put("open_hr_min", detailVo.open_hr_min);
					facilityDetailMap.put("end_hr_min", detailVo.end_hr_min);
					facilityDetailMap.put("opr_sts", String.valueOf(detailVo.opr_sts));
					facilityDetailMap.put("map_id", detailVo.map_id);
					facilityDetailMap.put("zone_id", detailVo.zone_id);
					facilityDetailMap.put("x_ctn_cord", String.valueOf(detailVo.x_ctn_cord));
					facilityDetailMap.put("y_ctn_cord", String.valueOf(detailVo.y_ctn_cord));
					
					facilityDetails.add(facilityDetailMap);
				}
				
				facilityMap.put("conv_faci_seq", facilityListVo.conv_faci_seq);
				facilityMap.put("bcn_cd", facilityListVo.bcn_cd);
				facilityMap.put("img_seq_icon", facilityListVo.img_seq_icon);
				facilityMap.put("img_icon_pys_loc", facilityListVo.img_icon_pys_loc);
				facilityMap.put("img_icon_uri", facilityListVo.img_icon_uri);
				facilityMap.put("img_icon_thmb", facilityListVo.img_icon_thmb);
				facilityMap.put("img_icon_thmb_uri", facilityListVo.img_icon_thmb_uri);
				facilityMap.put("img_seq_facl_image", facilityListVo.img_seq_facl_image);
				facilityMap.put("img_facl_pys_loc", facilityListVo.img_facl_pys_loc);
				facilityMap.put("img_facl_uri", facilityListVo.img_facl_uri);
				facilityMap.put("img_facl_thmb", facilityListVo.img_facl_thmb);
				facilityMap.put("img_facl_thmb_uri", facilityListVo.img_facl_thmb_uri);
				facilityMap.put("conv_faci_nm_ko", facilityListVo.conv_faci_nm_ko);
				facilityMap.put("conv_faci_nm_en", facilityListVo.conv_faci_nm_en);
				facilityMap.put("conv_faci_nm_cn", facilityListVo.conv_faci_nm_cn);
				facilityMap.put("conv_faci_nm_jp", facilityListVo.conv_faci_nm_jp);
				facilityMap.put("conv_faci_desc_ko", facilityListVo.conv_faci_desc_ko);
				facilityMap.put("conv_faci_desc_en", facilityListVo.conv_faci_desc_en);
				facilityMap.put("conv_faci_desc_cn", facilityListVo.conv_faci_desc_cn);
				facilityMap.put("conv_faci_desc_jp", facilityListVo.conv_faci_desc_jp);
				
				facilityMap.put("conv_faci_desc_dtl_ko1", facilityListVo.conv_faci_desc_dtl_ko1);
				facilityMap.put("conv_faci_desc_dtl_en1", facilityListVo.conv_faci_desc_dtl_en1);
				facilityMap.put("conv_faci_desc_dtl_cn1", facilityListVo.conv_faci_desc_dtl_cn1);
				facilityMap.put("conv_faci_desc_dtl_jp1", facilityListVo.conv_faci_desc_dtl_jp1);
				
				facilityMap.put("conv_faci_desc_dtl_ko2", facilityListVo.conv_faci_desc_dtl_ko2);
				facilityMap.put("conv_faci_desc_dtl_en2", facilityListVo.conv_faci_desc_dtl_en2);
				facilityMap.put("conv_faci_desc_dtl_cn2", facilityListVo.conv_faci_desc_dtl_cn2);
				facilityMap.put("conv_faci_desc_dtl_jp2", facilityListVo.conv_faci_desc_dtl_jp2);
				
				facilityMap.put("conv_faci_desc_dtl_ko3", facilityListVo.conv_faci_desc_dtl_ko3);
				facilityMap.put("conv_faci_desc_dtl_en3", facilityListVo.conv_faci_desc_dtl_en3);
				facilityMap.put("conv_faci_desc_dtl_cn3", facilityListVo.conv_faci_desc_dtl_cn3);
				facilityMap.put("conv_faci_desc_dtl_jp3", facilityListVo.conv_faci_desc_dtl_jp3);
				
				facilityMap.put("conv_faci_desc_dtl_ko4", facilityListVo.conv_faci_desc_dtl_ko4);
				facilityMap.put("conv_faci_desc_dtl_en4", facilityListVo.conv_faci_desc_dtl_en4);
				facilityMap.put("conv_faci_desc_dtl_cn4", facilityListVo.conv_faci_desc_dtl_cn4);
				facilityMap.put("conv_faci_desc_dtl_jp4", facilityListVo.conv_faci_desc_dtl_jp4);
				
				facilityMap.put("conv_faci_desc_dtl_ko5", facilityListVo.conv_faci_desc_dtl_ko5);
				facilityMap.put("conv_faci_desc_dtl_en5", facilityListVo.conv_faci_desc_dtl_en5);
				facilityMap.put("conv_faci_desc_dtl_cn5", facilityListVo.conv_faci_desc_dtl_cn5);
				facilityMap.put("conv_faci_desc_dtl_jp5", facilityListVo.conv_faci_desc_dtl_jp5);
				
				facilityMap.put("exp_yn", facilityListVo.exp_yn);
				facilityMap.put("use_amt_ko1", facilityListVo.use_amt_ko1);
				facilityMap.put("use_amt_ko2", facilityListVo.use_amt_ko2);
				facilityMap.put("use_amt_en1", facilityListVo.use_amt_en1);
				facilityMap.put("use_amt_en2", facilityListVo.use_amt_en2);
				
				facilityMap.put("irgu_open_hr_min", facilityListVo.irgu_open_hr_min);
				facilityMap.put("irgu_end_hr_min", facilityListVo.irgu_end_hr_min);
				
				facilityMap.put("sort_ord", facilityListVo.sort_ord);
				facilityMap.put("reps_open_hr_min", facilityListVo.reps_open_hr_min);
				facilityMap.put("reps_end_hr_min", facilityListVo.reps_end_hr_min);
				facilityMap.put("reps_tel_num1", facilityListVo.reps_tel_num1);
				facilityMap.put("reps_tel_num2", facilityListVo.reps_tel_num2);
				facilityMap.put("reps_tel_num3", facilityListVo.reps_tel_num3);
				facilityMap.put("facility_Details", facilityDetails);
			
				resultList.add(facilityMap);
			}

			syncApply(resultList, keysName, "conv_faci_seq");
			FacilityRedisSyncVersion();
			
		} catch (Exception e) {
			ll.error("exception", e);
			BaseException be = new BaseException(ErrorCode.Facility.FACILITY_SYNC_DATA_PARSING_FAILED);
			throw be;
		}
	}
	
	public void syncApply(List<Map<String, Object>> resultList, String keysName, String keyValue) throws BaseException {
		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
		
		try {
			int count = 0;
			
			for(Map<String, Object> resultMap : resultList){
				vOps.append(keysName + ":" + resultMap.get(keyValue), new ObjectMapper().writeValueAsString(resultMap));
				zOps.add("keys:" + keysName, keysName + ":" + resultMap.get(keyValue), count);
				count++;
			}
			
		} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.Facility.FACILITY_SYNC_FAIL_REDIS);
			throw be;
		}

	}

	@Transactional
	public void FacilityRedisSyncVersion() {
		String key = "version:facility";
		stringRedisTemplate.delete(key);
		stringRedisTemplate.opsForValue().append(key, String.valueOf(new Date().getTime()));
	}
	
	/**
	 * 편의시설 디테일 시퀀스 단일조회 및 존아이디 단일조회 redis sync
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	
	@Transactional
	public void syncFacilityDetail(String bcn_cd) throws BaseException {
		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
		
		String keysName = "facilityDetail";
		String keysName2 = "facilityDetailZone";
		List<Map<String, Object>> resultList = new ArrayList<>();
		//stringRedisTemplate.delete(zOps.range("keys:" + keysName, 0, -1));
		//stringRedisTemplate.delete("keys:"+ keysName);
		
		try {
			
			stringRedisTemplate.delete(stringRedisTemplate.keys(keysName + ":*"));
			stringRedisTemplate.delete(stringRedisTemplate.keys(keysName2 + ":*"));
			
			SFCT001Vo sfct001vo = new SFCT001Vo();
			sfct001vo.bcn_cd = bcn_cd;
			sfct001vo.sts = StatusCode.Common.USE.getCode();
			
			List<Facility> facilityList = afct001Mapper.getFacilityList(sfct001vo);
			
			SFCT001_DVo vo = new SFCT001_DVo();
			vo.sts = StatusCode.Common.USE.getCode();
			
			List<SFCT001_DVo> facilityDetailList = afct001Mapper.getFacilityDetailList(vo);
			
			for(SFCT001_DVo facilityDtlVo : facilityDetailList){
				Map<String, Object> facilityDtlMap = new HashMap<>();
				facilityDtlMap.put("conv_faci_dtl_seq", facilityDtlVo.conv_faci_dtl_seq);
				facilityDtlMap.put("conv_faci_seq", facilityDtlVo.conv_faci_seq);
				facilityDtlMap.put("conv_faci_nm_ko", facilityDtlVo.conv_faci_nm_ko);
				facilityDtlMap.put("conv_faci_nm_en", facilityDtlVo.conv_faci_nm_en);
				facilityDtlMap.put("fl", facilityDtlVo.fl);
				facilityDtlMap.put("room_num", facilityDtlVo.room_num);
				facilityDtlMap.put("plce_ko", facilityDtlVo.plce_ko);
				facilityDtlMap.put("plce_en", facilityDtlVo.plce_en);
				facilityDtlMap.put("tel_num1", facilityDtlVo.tel_num1);
				facilityDtlMap.put("tel_num2", facilityDtlVo.tel_num2);
				facilityDtlMap.put("tel_num3", facilityDtlVo.tel_num3);
				facilityDtlMap.put("open_hr_min", facilityDtlVo.open_hr_min);
				facilityDtlMap.put("end_hr_min", facilityDtlVo.end_hr_min);
				facilityDtlMap.put("opr_sts", facilityDtlVo.opr_sts);
				facilityDtlMap.put("zone_id", facilityDtlVo.zone_id);
				facilityDtlMap.put("map_id", facilityDtlVo.map_id);
				facilityDtlMap.put("x_ctn_cord", facilityDtlVo.x_ctn_cord);
				facilityDtlMap.put("y_ctn_cord", facilityDtlVo.y_ctn_cord);
	
				//마스터 테이블의 이미지 및 편의시설 설명을 디테일 목록에 넣음
				for(Facility facilityListVo : facilityList) {
					
					if(facilityListVo.conv_faci_seq.equals(facilityDtlVo.conv_faci_seq)) {
						facilityDtlMap.put("img_icon_uri", facilityListVo.img_icon_uri);
						facilityDtlMap.put("img_facl_uri", facilityListVo.img_facl_uri);
						facilityDtlMap.put("exp_yn", facilityListVo.exp_yn);
						facilityDtlMap.put("conv_faci_desc_ko", facilityListVo.conv_faci_desc_ko);
					}
				}
				
				resultList.add(facilityDtlMap);
			}
		} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.Facility.FACILITY_SYNC_DATA_PARSING_FAILED);
			throw be;
		}
		
		try {
			//int count = 0;
			for(Map<String, Object> resultMap : resultList){
				vOps.append("facilityDetail:" + resultMap.get("conv_faci_dtl_seq"), new ObjectMapper().writeValueAsString(resultMap));
				//zOps.add("keys:facilityDetail", "facilityDetail:" + resultMap.get("conv_faci_dtl_seq"), count);				
				//count++;
			}
			
			for(Map<String, Object> resultMap : resultList){
				vOps.append("facilityDetailZone:" + resultMap.get("zone_id"), new ObjectMapper().writeValueAsString(resultMap));
				//zOps.add("keys:facilityDetail", "facilityDetail:" + resultMap.get("conv_faci_dtl_seq"), count);				
				//count++;
			}
		} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.Facility.FACILITY_SYNC_FAIL_REDIS);
			throw be;
		}
	}
	
	/**
	 * facility Exp=Y redis sync (노출목록 리스트만 표기)
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	
	public void syncFacilityExp(String bcn_cd) throws BaseException {
		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
		
		try{
			String keysName = "facilityExp";
			stringRedisTemplate.delete(zOps.range("keys:" + keysName, 0, -1));
			stringRedisTemplate.delete("keys:"+ keysName);
			
			SFCT001Vo vo = new SFCT001Vo();
			vo.bcn_cd = bcn_cd;
			vo.exp_yn = "Y";
			vo.sts = StatusCode.Common.USE.getCode();

			List<Map<String, Object>> resultList = new ArrayList<>();
			List<Facility> facilityList = afct001Mapper.getFacilityList(vo);

			for(Facility facilityListVo : facilityList) {				
				SFCT001_DVo facilityDetailVo = new SFCT001_DVo();
				
				facilityDetailVo.conv_faci_seq = facilityListVo.conv_faci_seq;
				//facilityDetailVo.zone_id = "null";
				
				Map<String, Object> facilityMap = new LinkedHashMap<>();
				List<Map<String, String>> facilityDetails = new ArrayList<>();
				List<SFCT001_DVo> facilityDetailList = afct001Mapper.getFacilityDetailList(facilityDetailVo);
				
				if(facilityDetailList.size() == 0) {
					continue;
				}
				
				List<String> facilityDescKoList = new ArrayList<>();
				List<String> facilityDescEnList = new ArrayList<>();
				List<String> facilityDescCnList = new ArrayList<>();
				List<String> facilityDescJpList = new ArrayList<>();
				
				for(SFCT001_DVo detailVo : facilityDetailList) {
					Map<String, String> facilityDetailMap = new HashMap<>();
					
					facilityDetailMap.put("conv_faci_dtl_seq", detailVo.conv_faci_dtl_seq);
					facilityDetailMap.put("conv_faci_seq", detailVo.conv_faci_seq);
					facilityDetailMap.put("conv_faci_nm_ko", detailVo.conv_faci_nm_ko);
					facilityDetailMap.put("conv_faci_nm_en", detailVo.conv_faci_nm_en);
					facilityDetailMap.put("conv_faci_nm_cn", detailVo.conv_faci_nm_cn);
					facilityDetailMap.put("conv_faci_nm_jp", detailVo.conv_faci_nm_jp);
					facilityDetailMap.put("fl", detailVo.fl);

					facilityDetailMap.put("room_num", detailVo.room_num);
					facilityDetailMap.put("plce_ko", detailVo.plce_ko);
					facilityDetailMap.put("plce_en", detailVo.plce_en);
					
					facilityDetailMap.put("tel_num1", detailVo.tel_num1);
					facilityDetailMap.put("tel_num2", detailVo.tel_num2);
					facilityDetailMap.put("tel_num3", detailVo.tel_num3);
					facilityDetailMap.put("open_hr_min", detailVo.open_hr_min);
					facilityDetailMap.put("end_hr_min", detailVo.end_hr_min);
					facilityDetailMap.put("opr_sts", String.valueOf(detailVo.opr_sts));
					facilityDetailMap.put("map_id", detailVo.map_id);
					facilityDetailMap.put("zone_id", detailVo.zone_id);
					facilityDetailMap.put("poi_lev", detailVo.poi_lev);
					facilityDetailMap.put("x_ctn_cord", String.valueOf(detailVo.x_ctn_cord));
					facilityDetailMap.put("y_ctn_cord", String.valueOf(detailVo.y_ctn_cord));
					
					facilityDetails.add(facilityDetailMap);
				}
				
				facilityMap.put("conv_faci_seq", facilityListVo.conv_faci_seq);
				facilityMap.put("bcn_cd", facilityListVo.bcn_cd);
				facilityMap.put("img_seq_icon", facilityListVo.img_seq_icon);
				facilityMap.put("img_icon_pys_loc", facilityListVo.img_icon_pys_loc);
				facilityMap.put("img_icon_uri", facilityListVo.img_icon_uri);
				facilityMap.put("img_icon_thmb", facilityListVo.img_icon_thmb);
				facilityMap.put("img_icon_thmb_uri", facilityListVo.img_icon_thmb_uri);
				facilityMap.put("img_seq_facl_image", facilityListVo.img_seq_facl_image);
				facilityMap.put("img_facl_pys_loc", facilityListVo.img_facl_pys_loc);
				facilityMap.put("img_facl_uri", facilityListVo.img_facl_uri);
				facilityMap.put("img_facl_thmb", facilityListVo.img_facl_thmb);
				facilityMap.put("img_facl_thmb_uri", facilityListVo.img_facl_thmb_uri);
				facilityMap.put("conv_faci_nm_ko", facilityListVo.conv_faci_nm_ko);
				facilityMap.put("conv_faci_nm_en", facilityListVo.conv_faci_nm_en);
				facilityMap.put("conv_faci_nm_cn", facilityListVo.conv_faci_nm_cn);
				facilityMap.put("conv_faci_nm_jp", facilityListVo.conv_faci_nm_jp);
				facilityMap.put("conv_faci_desc_ko", facilityListVo.conv_faci_desc_ko);
				facilityMap.put("conv_faci_desc_en", facilityListVo.conv_faci_desc_en);
				facilityMap.put("conv_faci_desc_cn", facilityListVo.conv_faci_desc_cn);
				facilityMap.put("conv_faci_desc_jp", facilityListVo.conv_faci_desc_jp);
				
				facilityDescKoList.add(facilityListVo.conv_faci_desc_dtl_ko1);
				facilityDescKoList.add(facilityListVo.conv_faci_desc_dtl_ko2);
				facilityDescKoList.add(facilityListVo.conv_faci_desc_dtl_ko3);
				facilityDescKoList.add(facilityListVo.conv_faci_desc_dtl_ko4);
				facilityDescKoList.add(facilityListVo.conv_faci_desc_dtl_ko5);
				
				facilityDescEnList.add(facilityListVo.conv_faci_desc_dtl_en1);
				facilityDescEnList.add(facilityListVo.conv_faci_desc_dtl_en2);
				facilityDescEnList.add(facilityListVo.conv_faci_desc_dtl_en3);
				facilityDescEnList.add(facilityListVo.conv_faci_desc_dtl_en4);
				facilityDescEnList.add(facilityListVo.conv_faci_desc_dtl_en5);
				
				facilityDescCnList.add(facilityListVo.conv_faci_desc_dtl_cn1);
				facilityDescCnList.add(facilityListVo.conv_faci_desc_dtl_cn2);
				facilityDescCnList.add(facilityListVo.conv_faci_desc_dtl_cn3);
				facilityDescCnList.add(facilityListVo.conv_faci_desc_dtl_cn4);
				facilityDescCnList.add(facilityListVo.conv_faci_desc_dtl_cn5);
				
				facilityDescJpList.add((facilityListVo.conv_faci_desc_dtl_jp1));
				facilityDescJpList.add((facilityListVo.conv_faci_desc_dtl_jp2));
				facilityDescJpList.add((facilityListVo.conv_faci_desc_dtl_jp3));
				facilityDescJpList.add((facilityListVo.conv_faci_desc_dtl_jp4));
				facilityDescJpList.add((facilityListVo.conv_faci_desc_dtl_jp5));
				
				facilityMap.put("exp_yn", facilityListVo.exp_yn);
				facilityMap.put("use_amt_ko1", facilityListVo.use_amt_ko1);
				facilityMap.put("use_amt_ko2", facilityListVo.use_amt_ko2);
				facilityMap.put("use_amt_en1", facilityListVo.use_amt_en1);
				facilityMap.put("use_amt_en2", facilityListVo.use_amt_en2);
				
				facilityMap.put("opr_time_optn_guid_ko", facilityListVo.opr_time_optn_guid_ko);
				facilityMap.put("opr_time_optn_guid_en", facilityListVo.opr_time_optn_guid_en);
				facilityMap.put("opr_time_optn_guid_cn", facilityListVo.opr_time_optn_guid_cn);
				facilityMap.put("opr_time_optn_guid_jp", facilityListVo.opr_time_optn_guid_jp);
				
				facilityMap.put("irgu_open_hr_min", facilityListVo.irgu_open_hr_min);
				facilityMap.put("irgu_end_hr_min", facilityListVo.irgu_end_hr_min);
				
				facilityMap.put("sort_ord", facilityListVo.sort_ord);
				facilityMap.put("reps_open_hr_min", facilityListVo.reps_open_hr_min);
				facilityMap.put("reps_end_hr_min", facilityListVo.reps_end_hr_min);
				facilityMap.put("reps_tel_num1", facilityListVo.reps_tel_num1);
				facilityMap.put("reps_tel_num2", facilityListVo.reps_tel_num2);
				facilityMap.put("reps_tel_num3", facilityListVo.reps_tel_num3);
				facilityMap.put("facility_Details", facilityDetails);
				facilityMap.put("facility_desc_list_ko", facilityDescKoList);
				facilityMap.put("facility_desc_list_en", facilityDescEnList);
				facilityMap.put("facility_desc_list_cn", facilityDescCnList);
				facilityMap.put("facility_desc_list_jp", facilityDescJpList);
			
				resultList.add(facilityMap);
			}

			syncApply(resultList, keysName, "conv_faci_seq");
			FacilityRedisSyncVersion();
			
		} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.Facility.FACILITY_SYNC_DATA_PARSING_FAILED);
			throw be;
		}
	}

	/**
	 * facility Exp=N redis sync (노출목록 N, 층별리스트)
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional
	public void syncFacilityFloor(String bcn_cd) throws BaseException {
		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
		
		String keysNameFloor = "facilityFloorDetail";
		stringRedisTemplate.delete(stringRedisTemplate.keys(keysNameFloor + ":*"));
		
		try {

			SFCT001Vo sfct001vo = new SFCT001Vo();
			sfct001vo.bcn_cd = bcn_cd;
			sfct001vo.exp_yn = "N";
			sfct001vo.sts = StatusCode.Common.USE.getCode();
			
			List<Map<String, Object>> resultList = new ArrayList<>();
			List<Facility> facilityList = afct001Mapper.getFacilityList(sfct001vo);
			
			SFCT001_DVo vo = new SFCT001_DVo();
			vo.sts = StatusCode.Common.USE.getCode();
			//vo.zone_id = "null";
			
			String[] floorArr = {"B3", "B2", "B1", "1F", "2F", "3F", "4F"};
			
			for (int i = 0; i < floorArr.length; i++) {
				vo.fl = floorArr[i];
				
				Map<String, Object> facilityMap = new HashMap<>();
				List<Map<String, Object>> facilityFloorList = new ArrayList<>();
				List<SFCT001_DVo> facilityDetailList = afct001Mapper.getFacilityDetailList(vo);
				
				for(SFCT001_DVo facilityDtlVo : facilityDetailList) {
					Map<String, Object> facilityDtlMap = new HashMap<>();
					
					for(Facility facilityListVo : facilityList) {
					
						//마스터 테이블의 이미지 및 편의시설 설명을 포함하여 디테일목록 생성
						if(facilityListVo.conv_faci_seq.equals(facilityDtlVo.conv_faci_seq)) {
							
							facilityDtlMap.put("img_icon_uri", facilityListVo.img_icon_uri);
							facilityDtlMap.put("conv_faci_desc_ko", facilityListVo.conv_faci_desc_ko);
							facilityDtlMap.put("conv_faci_desc_en", facilityListVo.conv_faci_desc_ko);
							
							facilityDtlMap.put("conv_faci_dtl_seq", facilityDtlVo.conv_faci_dtl_seq);
							facilityDtlMap.put("conv_faci_seq", facilityDtlVo.conv_faci_seq);
							facilityDtlMap.put("conv_faci_nm_ko", facilityDtlVo.conv_faci_nm_ko);
							facilityDtlMap.put("conv_faci_nm_en", facilityDtlVo.conv_faci_nm_en);
							facilityDtlMap.put("fl", facilityDtlVo.fl);
							facilityDtlMap.put("room_num", facilityDtlVo.room_num);
							facilityDtlMap.put("plce_ko", facilityDtlVo.plce_ko);
							facilityDtlMap.put("plce_en", facilityDtlVo.plce_en);
							facilityDtlMap.put("tel_num1", facilityDtlVo.tel_num1);
							facilityDtlMap.put("tel_num2", facilityDtlVo.tel_num2);
							facilityDtlMap.put("tel_num3", facilityDtlVo.tel_num3);
							facilityDtlMap.put("open_hr_min", facilityDtlVo.open_hr_min);
							facilityDtlMap.put("end_hr_min", facilityDtlVo.end_hr_min);
							facilityDtlMap.put("opr_sts", facilityDtlVo.opr_sts);
							facilityDtlMap.put("zone_id", facilityDtlVo.zone_id);
							facilityDtlMap.put("map_id", facilityDtlVo.map_id);
							facilityDtlMap.put("poi_lev", facilityDtlVo.poi_lev);
							facilityDtlMap.put("x_ctn_cord", facilityDtlVo.x_ctn_cord);
							facilityDtlMap.put("y_ctn_cord", facilityDtlVo.y_ctn_cord);
							
							facilityFloorList.add(facilityDtlMap);
						}
					}
				}
				
				facilityMap.put("floor", floorArr[i]);
				facilityMap.put("list", facilityFloorList);
				
				resultList.add((Map<String, Object>) facilityMap);
				
			}
			
			for (int j = 0; j < resultList.size(); j++) {
				Map map = resultList.get(j);
				vOps.append("facilityFloorDetail:" + floorArr[j].toString(), new ObjectMapper().writeValueAsString(map));
				zOps.add("keys:" + keysNameFloor, keysNameFloor + ":" + floorArr[j].toString(),j);
			}
			
		} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.Facility.FACILITY_SYNC_FAIL_REDIS);
			throw be;
		}
	}

	/**
	 * Facility 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<FacilityForList> getFacilityGroupListForAdmin(FacilityGroupFilter filter) throws BaseException {

		ListResult<FacilityForList> result = new ListResult<>();
		
		List<FacilityForList> facilityGroupList =  afct001Mapper.getFacilityGroupListForAdmin(filter);

		result.list = facilityGroupList;
		
		int totCnt = facilityGroupList.size() > 0 ? facilityGroupList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(filter.offset, filter.limit, totCnt);

		result.paging = paging;
		
		return result;
	}
	
	/**
	 * Facility 목록 조회 (엑셀용)
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<FacilityForExcel> getFacilityGroupListForExcel(FacilityGroupFilter filter) throws BaseException {

		ListResult<FacilityForExcel> result = new ListResult<>();
		
		List<FacilityForExcel> facilityGroupList =  afct001Mapper.getFacilityGroupListForExcel(filter);

		result.list = facilityGroupList;
		
		int totCnt = facilityGroupList.size() > 0 ? facilityGroupList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(filter.offset, filter.limit, totCnt);

		result.paging = paging;
		
		return result;
	}
	
	public SimpleResult modifyFacilityGroup(FacilityGroup facilityGroup) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		SFCT001Vo vo = facilityGroup.toVo();
		
		int cnt = afct001Mapper.modifyFacilityGroup(vo);
		
		if(cnt < 1){
			BaseException be = new BaseException(ErrorCode.Facility.FACILITY_NOT_FOUND_DATA);
			throw be;
		}
		
		return result;
	}

	public FacilityGroup getFacilityGroup(String convFaciSeq) throws BaseException {
		FacilityGroup facilityGroup = afct001Mapper.getFacilityGroup(convFaciSeq);

		if(facilityGroup == null){
			BaseException be = new BaseException(ErrorCode.Facility.FACILITY_NOT_FOUND_DATA);
			throw be;
		}

		return facilityGroup;
	}

	public SimpleResult regFacilityGroup(FacilityGroup facilityGroup) {
		SimpleResult result = new SimpleResult();
		
		SFCT001Vo vo = facilityGroup.toVo();
		
		afct001Mapper.regFacilityGroup(vo);
		
		String convFaciSeq = vo.conv_faci_seq;
		facilityGroup.conv_faci_seq = convFaciSeq;
		result.extra = convFaciSeq;

		return result;
	}

	public SimpleResult deleteFacilityGroup(CommonDeleteModel faciDelete) {
		SimpleResult result = new SimpleResult();

		afct001Mapper.deleteFacilityGroup(faciDelete);

		return result;
	}
	
	/**
	 * Facility Detail 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<FacilityDetail> getFacilityDetailListForAdmin(FacilityFilter filter) throws BaseException {

		ListResult<FacilityDetail> result = new ListResult<>();
		List<FacilityDetail> facilityDetailList = afct001Mapper.getFacilityDetailListForAdmin(filter);

		result.list = facilityDetailList;
		
		int totCnt = facilityDetailList.size() > 0 ? facilityDetailList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(filter.offset, filter.limit, totCnt);

		result.paging = paging;
		
		return result;
	}
	
	public FacilityDetail getFacilityDetail(String convFaciDtlSeq) throws BaseException {
		FacilityDetail facility = afct001Mapper.getFacilityDetail(convFaciDtlSeq);

		if(facility == null){
			BaseException be = new BaseException(ErrorCode.Facility.FACILITY_NOT_FOUND_DATA);
			throw be;
		}

		return facility;
	}
	
	public SimpleResult regFacilityDetail(FacilityDetail facility) {
		SimpleResult result = new SimpleResult();
		
		SFCT001_DVo vo = facility.toVo();
		
		afct001Mapper.regFacilityDetail(vo);
		
		String convFaciDtlSeq = vo.conv_faci_dtl_seq;
		facility.conv_faci_dtl_seq = convFaciDtlSeq;
		result.extra = convFaciDtlSeq;

		return result;
	}
	
	public SimpleResult modifyFacilityDetail(FacilityDetail facility) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		SFCT001_DVo vo = facility.toVo();
		
		int cnt = afct001Mapper.modifyFacilityDetail(vo);
		
		if(cnt < 1){
			BaseException be = new BaseException(ErrorCode.Facility.FACILITY_NOT_FOUND_DATA);
			throw be;
		}
		
		return result;
	}

	public SimpleResult deleteFacilityDetail(CommonDeleteModel faciDelete) {
		SimpleResult result = new SimpleResult();

		afct001Mapper.deleteFacilityDetail(faciDelete);

		return result;
	}

	@Transactional
	public SimpleResult multiModifyFacilityGroup(List<FacilityGroup> facilityGroupList, String mod_user) {
		SimpleResult result = new SimpleResult();
		
		for(FacilityGroup fg : facilityGroupList) {
			fg.user = mod_user;
			afct001Mapper.modifyFacilityGroup(fg.toVo());
		}
		
		return result;
	}
}
