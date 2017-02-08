package kr.co.starfield.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.mapper.SCMS008Mapper;
import kr.co.starfield.model.QNAWeb;
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
public class SCMS008Service {

	@Autowired
	private SCMS008Mapper scms008Mapper;
	
	private static final Logger ll = LoggerFactory.getLogger(SCMS008Service.class);
	
	/**
	 * QNA 등록
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regQNA(QNAWeb model) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		scms008Mapper.regQNA(model);
		result.extra = model.qna_seq;
		
		return result;
	}
	
}
