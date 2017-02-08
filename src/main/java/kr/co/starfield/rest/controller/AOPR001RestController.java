package kr.co.starfield.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.model.Operation;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.StarFieldHoliday;
import kr.co.starfield.model.StarFieldOperation;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.AOPR001Service;

/**
 *  AOPR001(운영정보) 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author hhLee
 * @version 1.0,
 * @see
 * @date 2016.06.14
 */

@RestController
@RequestMapping("/admin/rest")
public class AOPR001RestController extends BaseController {

private Logger ll = LoggerFactory.getLogger(AOPR001RestController.class);
	
	@Autowired
	AOPR001Service aopr001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * 운영정보 조회
	 * @return Operation info
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/stores/operations"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public Operation getOperation(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		
		this.initHandler(req, res);
		
		Operation operation = aopr001Service.getOperation(bcn_cd);
		
		aact001service.regAdminAction(req, session, "운영정보 조회", null);
		
		return operation;
	}
	
	/**
	 * 운영정보 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/operations"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyOperation(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody StarFieldOperation starFieldOperation 
			, @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		this.initHandler(req, res);


		starFieldOperation.operation.mod_usr = (String) session.getAttribute("adm_seq");
		
		for(StarFieldHoliday starFieldHoliday : starFieldOperation.starFieldHolidayList) {
			starFieldHoliday.mod_usr = (String) session.getAttribute("adm_seq");
		}
		
		SimpleResult result = aopr001Service.modifyOperation(starFieldOperation);
				
		aact001service.regAdminAction(req, session, "운영정보/비정기 운영시간 수정", null);
		
		return result;
	}

}

