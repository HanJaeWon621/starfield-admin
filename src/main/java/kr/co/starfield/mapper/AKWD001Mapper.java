package kr.co.starfield.mapper;

import java.util.List;

import kr.co.starfield.model.vo.SKWD001Vo;

/**
 *  RecommendKeywordMapper interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author yhkim
 * @version	1.0,
 * @see
 * @date 2016.04.14
 */

public interface AKWD001Mapper {

	void regRecommendKeyword(SKWD001Vo vo);

	void deleteRecommendKeyword(String recmKeywordSeqr);

	void modifyRecommendKeyword(SKWD001Vo vo);

	List<SKWD001Vo> getRecommendKeywords(SKWD001Vo vo);
	
	SKWD001Vo getRecommendKeyword(String recmKeywordSeq);
}
