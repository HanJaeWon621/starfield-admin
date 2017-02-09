/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.List;

import kr.co.starfield.model.TodoMng;

/**
 * HomeMapper interface
 *
 * Copyright Copyright (c) 2016 Company Paprika
 *
 * @author xxx
 * @version 1.0,
 * @see
 * @date 2016.04.11
 */

public interface TodoMngMapper {

	// TO_DO 리스트
	public List<TodoMng> getTodoMngs(TodoMng vo);
	
	// TO_DO 상세
	public TodoMng getTodoMng(TodoMng vo);
	
	// TO_DO 등록
	public void regTodoMng(TodoMng vo);
		
	// TO_DO 수정
	public void modifyTodoMng(TodoMng vo);
	
	// TO_DO 삭제
	public void deleteTodoMng(TodoMng vo);
}
