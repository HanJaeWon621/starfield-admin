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

import kr.co.starfield.mapper.TodoMngMapper;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.TodoMng;
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
public class TodoMngService {

	@Autowired
	private TodoMngMapper mapper;
	
	private static final Logger ll = LoggerFactory.getLogger(TodoMngMapper.class);

	/**
	 * 스타일 세트 리스트
	 * @param ACPN001Vo
	 * @return 
	 */
	public ListResult<TodoMng> getTodoMngs(TodoMng vo) {
		
		ListResult<TodoMng> result = new ListResult<>();
		
		List<TodoMng> cpList = mapper.getTodoMngs(vo);
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
	public TodoMng getTodoMng(TodoMng vo) {
		
		ListResult<TodoMng> result = new ListResult<>();
		TodoMng ss = mapper.getTodoMng(vo);
		//result.list = cpList;
	
		return ss;
	}
	
	/**
	 * TO_DO 등록
	 * @param TodoMng
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regTodoMng(TodoMng vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.regTodoMng(vo);
	 	
	 	return result;
	}
	
	/**
	 * TO_DO 수정
	 * @param TodoMng
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyTodoMng(TodoMng vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.modifyTodoMng(vo);
	 	
	 	result.extra =vo.to_do_seq;
	 	return result;
	}

	/**
	 * TO_DO 삭제
	 * @param TodoMng
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteTodoMng(TodoMng vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.deleteTodoMng(vo);
	 	
	 	result.extra =vo.to_do_seq;
	 	return result;
	}
	
}
