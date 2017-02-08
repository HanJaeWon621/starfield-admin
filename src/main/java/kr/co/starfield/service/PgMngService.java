package kr.co.starfield.service;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.mapper.PgMngMapper;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.PgMng;
import kr.co.starfield.model.SimpleResult;



/**
 *  REST 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

@Service
public class PgMngService {

	@Autowired
	private PgMngMapper mapper;
	
	private static final Logger ll = LoggerFactory.getLogger(PgMngMapper.class);

	/**
	 * 스타일 세트 리스트
	 * @param ACPN001Vo
	 * @return 
	 */
	public ListResult<PgMng> getPgMngs(PgMng vo) {
		
		ListResult<PgMng> result = new ListResult<>();
		
		List<PgMng> cpList = mapper.getPgMngs(vo);
		result.list = cpList;

		int totCnt = cpList.size() > 0 ? cpList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;
		result.tot_cnt = totCnt;
	
		return result;
	}
	
	/**
	 * 스타일 세트 상세
	 * @param ACPN001Vo
	 * @return 
	 */
	public PgMng getPgMng(PgMng vo) {
		
		ListResult<PgMng> result = new ListResult<>();
		PgMng ss = mapper.getPgMng(vo);
		//result.list = cpList;
	
		return ss;
	}
	
	/**
	 * 프로그램 등록
	 * @param PgMng
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regPgMng(PgMng vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.regPgMng(vo);
	 	
	 	return result;
	}
	
	/**
	 * 프로그램 수정
	 * @param PgMng
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyPgMng(PgMng vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.modifyPgMng(vo);
	 	
	 	result.extra =vo.pg_info_seq;
	 	return result;
	}

	/**
	 * 프로그램 삭제
	 * @param PgMng
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deletePgMng(PgMng vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.deletePgMng(vo);
	 	
	 	result.extra =vo.pg_info_seq;
	 	return result;
	}
	
}
