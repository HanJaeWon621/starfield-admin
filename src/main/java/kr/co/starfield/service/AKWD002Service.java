package kr.co.starfield.service;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.mapper.AKWD002Mapper;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.RecommendTag;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SKWD002Vo;


/**
 * AKWD002(RecommendTag) 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.06.21
 */

@Service
public class AKWD002Service {

	@Autowired
	private AKWD002Mapper akwd002Mapper;
	
	private static final Logger ll = LoggerFactory.getLogger(AKWD002Service.class);
		
	/**
	 * RecommendTag 등록
	 * @param vo
	 * @return 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regRecommendTag(SKWD002Vo vo) {
		SimpleResult result = new SimpleResult();
		
		akwd002Mapper.regRecommendTag(vo);
		
		result.extra = vo.recm_tag_seq;
		
		return result;
	}
	
	/**
	 * RecommendTag 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<RecommendTag> getRecommendTagList(SKWD002Vo vo) throws BaseException {
		
		ListResult<RecommendTag> result = new ListResult<RecommendTag>();
			
		List<SKWD002Vo> voList = akwd002Mapper.getRecommendTagList(vo);
			
		for(SKWD002Vo resultVo : voList){
			result.list.add(resultVo.toModel());
		}
			
		int tot_cnt = voList.size() > 0 ? voList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
			
		result.paging = paging;
	
		return result;
	}
	
	/**
	 * RecommendTag 상세
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	public RecommendTag getRecommendTag(SKWD002Vo vo) throws BaseException {
		
		SKWD002Vo resultVo = akwd002Mapper.getRecommendTag(vo);
		
		if(resultVo == null){
			BaseException be = new BaseException(ErrorCode.Contents.RECOMMEND_TAG_NOT_FOUND_DATA);
			throw be;
		} 
		
		return resultVo.toModel();
	}
	
	/**
	 * RecommendTag 수정
	 * @param vo
	 * @return 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyRecommendTag(SKWD002Vo vo) {
		SimpleResult result = new SimpleResult();
		
		akwd002Mapper.modifyRecommendTag(vo);

		return result;
	}
	
	/**
	 * RecommendTag 삭제 
	 * @param vo
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteRecommendTag(SKWD002Vo vo) {
		SimpleResult result = new SimpleResult();
		
		vo.sts = StatusCode.Common.DELETE.getCode();
				
		akwd002Mapper.modifyRecommendTag(vo);
		
		return result;
	}
}
