package kr.co.starfield.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.mapper.AMBR001Mapper;
import kr.co.starfield.mapper.AMBR002Mapper;
import kr.co.starfield.model.Blog;
import kr.co.starfield.model.LikeTenant;
import kr.co.starfield.model.LikeEvent;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Member;
import kr.co.starfield.model.MemberDevice;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SMBR001Vo;
import kr.co.starfield.model.vo.SMBR002Vo;
import kr.co.starfield.model.vo.SMBR003Vo;
import kr.co.starfield.model.vo.SMBR005Vo;

@Service
public class AMBR002Service {
	
	private Logger ll = LoggerFactory.getLogger(AMBR002Service.class);
	
	@Autowired
	AMBR002Mapper ambr002Mapper;
	
	/**
	 * 관심 이벤트 등록
	 * @param vo
	 * @return 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regLikeEvent(SMBR002Vo vo) {
		SimpleResult result = new SimpleResult();
	
		
		if(ambr002Mapper.getLikeEventAllStatus(vo) == null) {
			ambr002Mapper.regLikeEvent(vo);
		} else {
			ambr002Mapper.modifyLikeEvent(vo);
		}

		return result;
	}
	
	/**
	 * 관심 이벤트 리스트 조회
	 * @param vo
	 * @return 
	 */
	public ListResult<LikeEvent> getLikeEventList(SMBR002Vo vo) {
		
		
		ListResult<LikeEvent> result = new ListResult<LikeEvent>();
		
		List<SMBR002Vo> likeEventList = ambr002Mapper.getLikeEventList(vo);
		
		for(SMBR002Vo likeEvent : likeEventList){
			result.list.add(likeEvent.toModel());
		}
			
		int tot_cnt = likeEventList.size() > 0 ? likeEventList.get(0).tot_cnt : 0;
		
		if(vo.limit > 0){
			Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
			
			result.paging = paging;
		}

		return result;
	}
	
	/**
	 * 관심 이벤트 상세 조회
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	public SimpleResult getLikeEvent(SMBR002Vo vo) throws BaseException {
		
		SimpleResult result = new SimpleResult();
		
		SMBR002Vo likeEventVo = ambr002Mapper.getLikeEvent(vo);
		
		result.code = 0;
		if(likeEventVo == null) {
			result.code = -1;
		}

		return result;
	}

	/**
	 * 관심 이벤트 삭제
	 * @param vo
	 * @return 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteLikeEvent(SMBR002Vo vo) {
		SimpleResult result = new SimpleResult();
	
		ambr002Mapper.deleteLikeEvent(vo);

		return result;
	}
	
	/**
	 * 관심 테넌트 등록
	 * @param vo
	 * @return 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regLikeTenant(SMBR003Vo vo) {
		SimpleResult result = new SimpleResult();
	
		if(ambr002Mapper.getLikeTenantAllStatus(vo) == null) {
			ambr002Mapper.regLikeTenant(vo);
		} else {
			ambr002Mapper.modifyLikeTenant(vo);
		}

		return result;
	}
	
	/**
	 * 관심 테넌트 리스트 등록
	 * @param vo
	 * @return 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regLikeTenantList(ArrayList<SMBR003Vo> voList) {
		SimpleResult result = new SimpleResult();
	
		for(SMBR003Vo vo : voList) {
			if(ambr002Mapper.getLikeTenantAllStatus(vo) == null) {
				ambr002Mapper.regLikeTenant(vo);
			} else {
				ambr002Mapper.modifyLikeTenant(vo);
			}
		}
		
		return result;
	}
	
	/**
	 * 관심 테넌트 리스트 조회
	 * @param vo
	 * @return 
	 */
	public ListResult<LikeTenant> getLikeTenantList(SMBR003Vo vo) {
		
		
		ListResult<LikeTenant> result = new ListResult<LikeTenant>();
		
		List<SMBR003Vo> likeTenantList = ambr002Mapper.getLikeTenantList(vo);
		
		for(SMBR003Vo likeEvent : likeTenantList){
			result.list.add(likeEvent.toModel());
		}
			
		int tot_cnt = likeTenantList.size() > 0 ? likeTenantList.get(0).tot_cnt : 0;
		
		if(vo.limit > 0){
			Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
			
			result.paging = paging;
		}
		
		

		return result;
	}
	
	/**
	 * 관심 테넌트 상세 조회
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	public SimpleResult getLikeTenant(SMBR003Vo vo) throws BaseException {
	
		SimpleResult result = new SimpleResult();
		
		
		
		SMBR003Vo likeTenantVo = ambr002Mapper.getLikeTenant(vo);
		
		result.code = 0;
		if(likeTenantVo == null) {
			result.code = -1;
		}

		return result;
	}

	/**
	 * 관심 테넌트 삭제
	 * @param vo
	 * @return 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteLikeTenant(SMBR003Vo vo) {
		SimpleResult result = new SimpleResult();
	
		ambr002Mapper.deleteLikeTenant(vo);

		return result;
	}
	
}
