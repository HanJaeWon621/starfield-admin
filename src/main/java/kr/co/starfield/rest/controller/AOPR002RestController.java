/*
 * NoticeRestController.java	1.00 2016/06/16
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.model.Holiday;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.AOPR002Service;

/**
 *  AOPR002(Holiday) REST 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author eezy
 * @version 1.0,
 * @see
 * @date 2016.06.21
 */
@RestController
@RequestMapping("/admin/rest")
public class AOPR002RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(AOPR002RestController.class);
	
	@Autowired
	AOPR002Service aopr002Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * Holiday 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/holidays"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regHolidays(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody Holiday[] modelList ) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("regHoliday bcn_cd : {}, Holidays : {}", new Object[] {bcn_cd, modelList});
		
		
		for(Holiday model : modelList){
			model.bcn_cd = bcn_cd;
		
			model.reg_usr = (String) session.getAttribute("adm_seq");
		}
		
		SimpleResult result = aopr002Service.regHolidays(modelList);
		
		aact001service.regAdminAction(req, session, "비정기운영일 등록/수정", null);
		
		return result;
	}
	
	/**
	 * Holiday 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/holidays"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<Holiday> getHolidayList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="q", required=false) String q) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getHolidayList bcn_cd : {}, q : {}", new Object[] {bcn_cd, q});
		
		Holiday model = new Holiday();
		model.bcn_cd = bcn_cd;
		model.sts = StatusCode.Common.USE.getCode();
		
		ListResult<Holiday> modelList = aopr002Service.getHolidayList(model);
		
		aact001service.regAdminAction(req, session, "비정기운영일 목록 조회", null);
		
		return modelList;
	}
	
	/**
	 * Holiday 상세
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/holidays/{holiday_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public Holiday getHoliday(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="holiday_seq") String holiday_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getHoliday bcn_cd : {}, holiday_seq : {}", new Object[] {bcn_cd, holiday_seq});
		Holiday model = new Holiday();
		model.bcn_cd = bcn_cd;
		model.holiday_seq = holiday_seq;
		model.sts = StatusCode.Common.USE.getCode();

		Holiday holiday = aopr002Service.getHoliday(model);
		
		aact001service.regAdminAction(req, session, "비정기운영일 상제 조회", null);
		
		return holiday;
	}
	
	/**
	 * Holiday 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/holidays/{holiday_seq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyHoliday(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody Holiday model
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="holiday_seq") String holiday_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("modifyHoliday holiday_seq : {}, bcn_cd : {}, holiday_seq : {}", new Object[] {model, bcn_cd, holiday_seq});
		
		model.bcn_cd = bcn_cd;
		model.holiday_seq = holiday_seq;
		
		model.mod_usr = (String) session.getAttribute("adm_seq");;
		
		SimpleResult result = aopr002Service.modifyHoliday(model);
				
		aact001service.regAdminAction(req, session, "비정기운영일 수정", null);
		
		return result;
	}
	
	/**
	 * Holiday 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/holidays"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteHoliday(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody Holiday[] modelList ) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info("deleteHoliday bcn_cd : {}, modelList : {}", new Object[] {bcn_cd, modelList});

		for(Holiday model : modelList){
			model.bcn_cd = bcn_cd;
			
			model.mod_usr = (String) session.getAttribute("adm_seq");
		}
		
		
		SimpleResult result = aopr002Service.deleteHoliday(modelList);
		
		aact001service.regAdminAction(req, session, "비정기운영일 삭제", null);
		
		return result;
	}
}
