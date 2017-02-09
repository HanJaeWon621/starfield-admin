/*
 * couponController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.model.Coffee;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.TodoMng;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.TodoMngService;

/**
 * 쿠폰 관리
 * 
 * @author dhlee
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

@RestController
@RequestMapping("/rest")  
public class TodoMngRestController extends BaseController {

	@Autowired
	TodoMngService service;

	private static final Logger ll = LoggerFactory.getLogger(TodoMngRestController.class);
	
	/**
	 * TO_DO 리스트
	 * @return
	 */
	@RequestMapping(value = "/getTodoMngsAPI"
			, method = RequestMethod.GET
		)
	@ResponseBody
	public List<Coffee> getTodoMngs(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			
	) throws Exception {
		
		
		this.initHandler(req, res);

		Coffee coffee1 = new Coffee("Latte", 100);
		Coffee coffee2 = new Coffee("Americano", 100);
		List<Coffee> list = new ArrayList();
		list.add(coffee1);
		list.add(coffee2);
		return list;
		
	}
	
	/**
	 * TO_DO 리스트
	 * @return
	 */
	@RequestMapping(value = "/getTodoMngs"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<TodoMng> getTodoMngs(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_work_nm" ) String sh_work_nm
			
	) throws Exception {
		
		
		this.initHandler(req, res);

		TodoMng vo = new TodoMng();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.sh_work_nm = sh_work_nm;

		ListResult<TodoMng> cplist  = service.getTodoMngs(vo);
		
		return cplist;
	}
	
	/**
	 * TO_DO 상세
	 * @return
	 */
	@RequestMapping(value = "/getTodoMng"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	@ResponseBody
	public TodoMng getTodoMng(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="sh_to_do_seq") String sh_to_do_seq
			
	) throws Exception {
		
		System.out.println("1style_set_seq>>>"+sh_to_do_seq);
		this.initHandler(req, res);
		
		TodoMng vo = new TodoMng();
		vo.sh_to_do_seq = sh_to_do_seq;
		
		
		TodoMng style  = service.getTodoMng(vo);
		
		return style;
	}
	/**
	 * TO_DO 등록
	 * @return
	 */
	@RequestMapping(value = "/regTodoMng"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult regTodoMng(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody TodoMng vo ) throws Exception {
		//String login_id = (String) session.getAttribute("login_id");

 		SimpleResult result = new SimpleResult();
		result = service.regTodoMng(vo);
		
		
 		return result;
	}
	
	/**
	 * TO_DO 수정
	 * @return
	 */
	@RequestMapping(value = "/modifyTodoMng"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult modifyTodoMng(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody TodoMng vo ) throws Exception {
		//String login_id = (String) session.getAttribute("login_id");

 		SimpleResult result = new SimpleResult();
		result = service.modifyTodoMng(vo);
		
 		return result;
	}
	
	/**
	 * TO_DO 삭제
	 * @return
	 */
	@RequestMapping(value = "/deleteTodoMng"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult deleteTodoMng(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="sh_to_do_seq") String sh_to_do_seq
			) throws Exception {
		//String login_id = (String) session.getAttribute("login_id");

 		SimpleResult result = new SimpleResult();
 		TodoMng vo = new TodoMng();
 		vo.sh_to_do_seq = sh_to_do_seq;
		result = service.deleteTodoMng(vo);
		
		
 		return result;
	}
	

}
