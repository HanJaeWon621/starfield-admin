/*
 * ACMS008Mapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.List;

import kr.co.starfield.model.QNA;
import kr.co.starfield.model.QNADelete;
import kr.co.starfield.model.QNAFilter;
import kr.co.starfield.model.vo.SCMS008Vo;

/**
 *  ACMS008Mapper(qna) interface
 *  
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author hhLee
 * @version	1.0,
 * @see
 * @date 2016.08.11
 */

public interface ACMS008Mapper {	
	// QNA 목록 조회
	public List<QNA> getQNAList(QNAFilter filter);
	
	// QNA 카운트
	public int getTotalCount(QNAFilter filter);
	
	// QNA 조회
	public QNA getQNA(QNAFilter filter);
	
	// QNA 수정
	public void modifyQNA(QNA model);
	
	// QNA 삭제
	public void deleteQNA(QNADelete qnaDelete);
}
