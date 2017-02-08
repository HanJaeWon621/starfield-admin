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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.mapper.ACMS002Mapper;
import kr.co.starfield.model.Blog;
import kr.co.starfield.model.BlogFilter;
import kr.co.starfield.model.FAQ;
import kr.co.starfield.model.InstagramFilter;
import kr.co.starfield.model.InstagramImage;
import kr.co.starfield.model.InstagramImageWeb;
import kr.co.starfield.model.InstagramTag;
import kr.co.starfield.model.InstagramTagWeb;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;


/**
 * ACMS004(instagram) 서비스 클래스
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
public class ACMS002Service {

	@Autowired
	private ACMS002Mapper acms002Mapper;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	StringRedisTemplate stringSessionRedisTemplate;
	
	
	private static final Logger ll = LoggerFactory.getLogger(ACMS002Service.class);
		
	
	/**
	 * InstagramTag 등록
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regInstagramTag(InstagramTag[] instagramTagList) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		for(InstagramTag instagramTag : instagramTagList) {
			if(instagramTag.insta_tag_seq.equals("create")) {
				acms002Mapper.regInstagramTag(instagramTag);
			} else {
				acms002Mapper.modifyInstagramTag(instagramTag);
			}
			
		}
		
		SyncRun(instagramTagList[0].bcn_cd);
		
		return result;
	}
	
	/**
	 * InstagramTag 목록 조회
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	
	public ArrayList<InstagramTag> getInstagramTagList(InstagramFilter filter) throws BaseException {
		
		ArrayList<InstagramTag> instagramTagList = acms002Mapper.getInstagramTagList(filter);
		
		return instagramTagList;
	}
	
	

	/**
	 * InstagramTag 상세 조회
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	
	public InstagramTag getInstagramTag(InstagramFilter filter) throws BaseException {
		
		InstagramTag instagramTag = acms002Mapper.getInstagramTag(filter);
		
		if(instagramTag == null) {
			BaseException be = new BaseException(ErrorCode.SNS.INSTAGRAM_NOT_FOUND_DATA);
			throw be;
		} 
		
		instagramTag.instagramImageList = acms002Mapper.getInstagramImageList(filter);
		
		return instagramTag;
	}
	
	/**
	 * InstagramImage 등록
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regInstagramImage(InstagramImage[] instagramImageList, String bcn_cd) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		for(InstagramImage instagramImage : instagramImageList) {
			if(instagramImage.insta_seq.equals("create")) {
				acms002Mapper.regInstagramImage(instagramImage);
			} else {
				acms002Mapper.modifyInstagramImage(instagramImage);
			}
			
		}
		
		SyncRun(bcn_cd);
		
		return result;
	}
	
	
	/**
	 * Instagram redis 목록 불러오기
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	
	public ArrayList<InstagramTagWeb> getInstagramWebList(String bcn_cd) throws BaseException {
		
		ArrayList<InstagramTagWeb> instagramTagWebList = acms002Mapper.getInstagramTagWebList(bcn_cd);
		
		
		for(InstagramTagWeb instagramTagWeb : instagramTagWebList) {
			instagramTagWeb.insta_details = acms002Mapper.getInstagramImageWebList(instagramTagWeb);
		}
	
		return instagramTagWebList;
	}
	
	/**
	 * Instagram redis sync
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	
	public SimpleResult SyncRun(String bcn_cd) throws BaseException {
		
		SimpleResult result = new SimpleResult();
		
		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
		
		try {
			ArrayList<InstagramTagWeb> instagramTagWebList = getInstagramWebList(bcn_cd);
			vOps.set("instagram", new ObjectMapper().writeValueAsString(instagramTagWebList));
		} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.SNS.INSTAGRAM_SYNC_FAIL_REDIS);
			throw be;
			
		}
		
		return result;
	}
	
	
//	/**
//	 * Instagram redis sync
//	 * @param
//	 * @return 
//	 * @throws BaseException 
//	 */
//	
//	public SimpleResult SyncRun(String bcn_cd) throws BaseException {
//		
//		SimpleResult result = new SimpleResult();
//		
//		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
//		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
//		
//		try{
//			
//			
//			SCMS002Vo vo = new SCMS002Vo();
//			vo.bcn_cd = bcn_cd;
//			vo.sts = 1;
//
//			List<Map<String, Object>> resultList = new ArrayList<>();
//			List<SCMS002Vo> instaTagList = acms004Mapper.getInstaTagList(vo);
//
//			for(SCMS002Vo instaTagVo : instaTagList) {				
//				SCMS002_DVo instaDetailVo = new SCMS002_DVo();
//				
//				instaDetailVo.insta_tag_seq = instaTagVo.insta_tag_seq;
//				instaDetailVo.sts = 1; //1:사용, 9:사용안함
//				
//				Map<String, Object> instaMap = new LinkedHashMap<>();
//				List<Map<String, String>> instaDetails = new ArrayList<>();
//				List<SCMS002_DVo> instaDetailList = acms004Mapper.getInstaDetailList(instaDetailVo);
//				
//				for(SCMS002_DVo detailVo : instaDetailList) {
//					Map<String, String> instaDetailMap = new HashMap<>();
//					
//					instaDetailMap.put("insta_seq", detailVo.insta_seq);
//					instaDetailMap.put("insta_tag_seq", detailVo.insta_tag_seq);
//					instaDetailMap.put("cont_id", detailVo.cont_id);
//					instaDetailMap.put("url_lnk", detailVo.url_lnk);
//					instaDetailMap.put("url_thumb", detailVo.url_thumb);
//					instaDetailMap.put("url_low", detailVo.url_low);
//					instaDetailMap.put("url_std", detailVo.url_std);
//					instaDetailMap.put("user_name", detailVo.user_name);
//					instaDetailMap.put("created_time", detailVo.created_time);
//					instaDetailMap.put("like_count", detailVo.like_count);
//					instaDetailMap.put("tag", detailVo.tag);
//					
//					instaDetails.add(instaDetailMap);
//				}
//				
//				instaMap.put("insta_tag_seq", instaTagVo.insta_tag_seq);
//				instaMap.put("bcn_cd", instaTagVo.bcn_cd);
//				instaMap.put("insta_tag_nm", instaTagVo.insta_tag_nm);
//				instaMap.put("sort_ord", instaTagVo.sort_ord);
//				instaMap.put("insta_Details", instaDetails);
//			
//				resultList.add(instaMap);
//			}
//
//			SyncApply(resultList, "insta_tag_seq");
//			
//		} catch (Exception e) {
//			BaseException be = new BaseException(ErrorCode.SNS.INSTAGRAM_SYNC_DATA_PARSING_FAILED);
//			throw be;
//		}
//		
//		return result;
//	}
//	
//	public void SyncApply(List<Map<String, Object>> resultList, String keyValue) throws BaseException {
//		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
//		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
//		
//		try {
//			int count = 0;
//			
//			String keysName = "instagram";
//			stringRedisTemplate.delete(zOps.range("keys:" + keysName, 0, -1));
//			stringRedisTemplate.delete("keys:"+ keysName);
//			
//			for(Map<String, Object> resultMap : resultList){
//				vOps.set(keysName + ":" + resultMap.get(keyValue), new ObjectMapper().writeValueAsString(resultMap));
//				zOps.add("keys:" + keysName, keysName + ":" + resultMap.get(keyValue), count);
//				count++;
//			}
//			
//		} catch (Exception e) {
//			BaseException be = new BaseException(ErrorCode.SNS.INSTAGRAM_SYNC_FAIL_REDIS);
//			throw be;
//			
//		}
//	}
	
	public SimpleResult setToken(String token) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		ValueOperations<String, String> vOps = stringSessionRedisTemplate.opsForValue();
		
		vOps.set("token:instagram", token);
		
		return result;
	}
	
	public String getToken() throws BaseException {
		ValueOperations<String, String> vOps = stringSessionRedisTemplate.opsForValue();
		
		return vOps.get("token:instagram");
	}

	
	
	public String getClientId() throws BaseException {
		ValueOperations<String, String> vOps = stringSessionRedisTemplate.opsForValue();
		
		return vOps.get("token:instagram_client_id");
	}
}
