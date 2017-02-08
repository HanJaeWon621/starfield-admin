package kr.co.starfield.service;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.mapper.ACMS010Mapper;
import kr.co.starfield.mapper.ACMS011Mapper;
import kr.co.starfield.model.OldAppMainInit;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.WhatsNew;
import kr.co.starfield.model.WhatsNewDelete;
import kr.co.starfield.model.WhatsNewFilter;
import kr.co.starfield.model.WhatsNewGroup;
import kr.co.starfield.model.WhatsNewGroupDelete;


/**
 * ACMS011(WhatsNew) 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.09.07
 */

@Service
public class ACMS011Service {

	@Autowired
	private ACMS011Mapper acms011Mapper;
	
	private static final Logger ll = LoggerFactory.getLogger(ACMS011Service.class);
	
	/**
	 * whatsNewGroup 등록
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regWhatsNewGroup(WhatsNewGroup whatsNewGroup) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms011Mapper.regWhatsNewGroup(whatsNewGroup);
		
		for(WhatsNew whatsNew : whatsNewGroup.whatsNewList) {
			whatsNew.what_group_seq = whatsNewGroup.what_group_seq;
			acms011Mapper.regWhatsNew(whatsNew);
		}
		
		result.extra = whatsNewGroup.what_group_seq;
		
		return result;
	}
	
	/**
	 * whatsNewGroup 수정
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyWhatsNewGroup(WhatsNewGroup whatsNewGroup) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms011Mapper.modifyWhatsNewGroup(whatsNewGroup);
		
		for(WhatsNew whatsNew : whatsNewGroup.whatsNewList) {
			if(whatsNew.what_new_seq.equals("create")) {
				whatsNew.sts = StatusCode.Common.USE.getCode();
				acms011Mapper.regWhatsNew(whatsNew);
			} else {
				acms011Mapper.modifyWhatsNew(whatsNew);
			}
		}

		return result;
	}
	
	/**
	 * whatsNewGroup 삭제
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteWhatsNewGroup(WhatsNewGroupDelete whatsNewGroupDelete) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms011Mapper.deleteWhatsNewGroup(whatsNewGroupDelete);

		return result;
	}
	
	/**
	 * whatsNewGroup 목록 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<WhatsNewGroup> getWhatsNewGroupList(WhatsNewFilter filter) throws BaseException {

		ListResult<WhatsNewGroup> result = new ListResult<WhatsNewGroup>();
		List<WhatsNewGroup> whatsNewGroupList = acms011Mapper.getWhatsNewGroupList(filter);
		

		result.list.addAll(whatsNewGroupList);

		if(filter.limit > 0){
			int tot_cnt = acms011Mapper.getTotalCount(filter);
	
			Paging paging = new Paging(filter.offset, filter.limit, tot_cnt);
				
			result.paging = paging;
		}		
		return result;
	}
	
	/**
	 * whatsNewGroup 상세 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public WhatsNewGroup getWhatsNewGroup(WhatsNewFilter filter) throws BaseException {

		WhatsNewGroup whatsNewGroup = acms011Mapper.getWhatsNewGroup(filter);

		if(whatsNewGroup == null){
			BaseException be = new BaseException(ErrorCode.Contents.WHATNEW_GROUP_NOT_FOUND_DATA);
			throw be;
		} 
		
		whatsNewGroup.whatsNewList.addAll(acms011Mapper.getWhatsNewList(filter));
		return whatsNewGroup;
	}

	
	
	
	
	
	
	
	
	
	/**
	 * WhatsNew 등록
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regWhatsNew(WhatsNew whatsNew) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms011Mapper.regWhatsNew(whatsNew);
		
		result.extra = whatsNew.what_new_seq;

		return result;
	}
	
	/**
	 * WhatsNew 수정
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyWhatsNew(WhatsNew whatsNew) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms011Mapper.modifyWhatsNew(whatsNew);

		return result;
	}
	
	/**
	 * WhatsNew 삭제
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteWhatsNew(WhatsNewDelete whatsNewDelete) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms011Mapper.deleteWhatsNew(whatsNewDelete);

		return result;
	}
	
	/**
	 * WhatsNew 상세 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public WhatsNew getWhatsNew(WhatsNewFilter filter) throws BaseException {
		WhatsNew whatsNew = acms011Mapper.getWhatsNew(filter);

		if(whatsNew == null){
			BaseException be = new BaseException(ErrorCode.Contents.WHATNEW_NOT_FOUND_DATA);
			throw be;
		}
		return whatsNew;
	}
}
