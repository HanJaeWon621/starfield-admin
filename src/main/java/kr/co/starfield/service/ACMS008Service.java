package kr.co.starfield.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.Utils;
import kr.co.starfield.mapper.ACMS008Mapper;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.LocInfoManage;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.QNA;
import kr.co.starfield.model.QNADelete;
import kr.co.starfield.model.QNAFilter;
import kr.co.starfield.model.SimpleResult;


/**
 * SCMS008(QNA) 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.08.11
 */

@Service
public class ACMS008Service {

	@Autowired
	private ACMS008Mapper acms008Mapper;
	
	private static final Logger ll = LoggerFactory.getLogger(ACMS008Service.class);
	
	
	/**
	 * QNA 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<QNA> getQNAList(QNAFilter filter, boolean masking) throws BaseException {

		ListResult<QNA> result = new ListResult<QNA>();
		result.list.addAll(acms008Mapper.getQNAList(filter));
		
		if(masking) {
			for(QNA qna : result.list) {
				qna.cust_nm = Utils.Str.maskingName(qna.cust_nm);
				
				int lastIndex = qna.cust_email.indexOf("@");
				qna.cust_email = Utils.Str.masking(qna.cust_email, 1, lastIndex - 1);
			}
		}
		
		if(filter.limit > 0){
			int tot_cnt = acms008Mapper.getTotalCount(filter);
	
			Paging paging = new Paging(filter.offset, filter.limit, tot_cnt);
				
			result.paging = paging;
		}	
	
		return result;
	}
	
	/**
	 * QNA 상세
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	public QNA getQNA(QNAFilter filter, boolean masking) throws BaseException {
		
		QNA qna = acms008Mapper.getQNA(filter);
		
		if(qna == null) {
			BaseException be = new BaseException(ErrorCode.Contents.QNA_NOT_FOUND_DATA);
			throw be;
		} 
		
		if(masking) {
			qna.cust_nm = Utils.Str.maskingName(qna.cust_nm);
			
			int lastIndex = qna.cust_email.indexOf("@");
			qna.cust_email = Utils.Str.masking(qna.cust_email, 1, lastIndex - 1);
		}
		
		
		return qna;
	}
	
	/**
	 * QNA 수정
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyQNA(QNA model) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms008Mapper.modifyQNA(model);
		
		return result;
	}
	
	/**
	 * QNA 삭제
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteQNA(QNADelete qnaDelete) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms008Mapper.deleteQNA(qnaDelete);
		
		return result;
	}
	
}
