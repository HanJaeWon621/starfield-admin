package kr.co.starfield.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.mapper.AKWD001Mapper;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.RecommendKeyword;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SKWD001Vo;

@Service
public class AKWD001Service {
	
	private Logger ll = LoggerFactory.getLogger(ACTG001Service.class);
	
	@Autowired
	AKWD001Mapper akwd001Mapper;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	/**
	 * 추천 키워드 등록
	 * @param vo
	 * @return 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regRecommendKeyword(SKWD001Vo vo) {
		SimpleResult result = new SimpleResult();
			
		akwd001Mapper.regRecommendKeyword(vo);
		
		result.extra = vo.recm_keyword_seq;
		
		return result;
	}

	/**
	 * 추천 키워드 조회 
	 * @param brndSeq
	 * @return MainBrand
	 * @throws BaseException 
	 */
	public RecommendKeyword getRecommendKeyword(String brndSeq) throws BaseException {

		SKWD001Vo recommendKeywordVo = akwd001Mapper.getRecommendKeyword(brndSeq);
		
		if(recommendKeywordVo == null){
			BaseException be = new BaseException(ErrorCode.Contents.RECOMMEND_KEYWORD_NOT_FOUND_DATA);
			throw be;
		} 
		
		return recommendKeywordVo.toModel();
	}

	/**
	 * 추천 키워드 삭제 
	 * @param brndSeq
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteRecommendKeyword(String brndSeq) {
		SimpleResult result = new SimpleResult();

		akwd001Mapper.deleteRecommendKeyword(brndSeq);
		
		return result;
	}

	/**
	 * 추천 키워드 수정
	 * @param vo
	 * @return 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyRecommendKeyword(SKWD001Vo vo) throws BaseException {
		SimpleResult result = new SimpleResult();

		akwd001Mapper.modifyRecommendKeyword(vo);
			
		return result;	
	}

	/**
	 * 추천 키워드 목록 
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<RecommendKeyword> getRecommendKeywords(SKWD001Vo vo) throws BaseException {
		
		ListResult<RecommendKeyword> result = new ListResult<>();
	
		List<SKWD001Vo> recommendKeywords = akwd001Mapper.getRecommendKeywords(vo);
			
		for(SKWD001Vo recommendKeyword : recommendKeywords){
			result.list.add(recommendKeyword.toModel());
		}
			
		int totCnt = recommendKeywords.size() > 0 ? recommendKeywords.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
			
		result.paging = paging;
	
		return result;
	}
	
	private void syncRedisRecommendKeywords(){
		// recommends/keywords
	}
}
