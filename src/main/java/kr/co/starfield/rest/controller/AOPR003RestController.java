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

import kr.co.starfield.common.StatusCode;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.StarFieldHoliday;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.AOPR003Service;

/**
 *  AOPR003(StarFieldHoliday) REST 컨트롤러 클래스
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
public class AOPR003RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(AOPR003RestController.class);
	
	@Autowired
	AOPR003Service aopr003Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * StarFieldHoliday 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcn_cd}/holidays/ir/bcn"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regStarFieldHolidays(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody StarFieldHoliday[] models ) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("regStarFieldHolidays bcn_cd : {}, StarFieldHoliday : {}", new Object[] {bcn_cd, models});
		

		for(StarFieldHoliday model : models){
			model.bcn_cd = bcn_cd;
			
			model.reg_usr = (String) session.getAttribute("adm_seq");
			model.mod_usr = (String) session.getAttribute("adm_seq");
		}
		
		SimpleResult result = aopr003Service.regStarFieldHolidays(models);
		
		aact001service.regAdminAction(req, session, "스타필드 비정기운영일 등록", null);
		
		return result;
	}
	
	/**
	 * StarFieldHoliday 목록 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/holidays/ir/bcn"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<StarFieldHoliday> getStarFieldHolidayList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="q", required=false) String q) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getStarFieldHolidayList bcn_cd : {}, q : {}", new Object[] {bcn_cd, q});
		
		StarFieldHoliday model = new StarFieldHoliday();
		model.bcn_cd = bcn_cd;
		model.sts = StatusCode.Common.USE.getCode();
		
		ListResult<StarFieldHoliday> modelList = aopr003Service.getStarFieldHolidayList(model);
		
		aact001service.regAdminAction(req, session, "스타필드 비정기운영일 목록 조회", null);
		
		return modelList;
	}
	
	/**
	 * StarFieldHoliday 상세
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/holidays/ir/bcn/{stf_irgu_opr_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public StarFieldHoliday getStarFieldHoliday(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="stf_irgu_opr_seq") String stf_irgu_opr_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getStarFieldHoliday bcn_cd : {}, stf_irgu_opr_seq : {}", new Object[] {bcn_cd, stf_irgu_opr_seq});
		
		StarFieldHoliday model = new StarFieldHoliday();
		model.bcn_cd = bcn_cd;
		model.stf_irgu_opr_seq = stf_irgu_opr_seq;
		model.sts = StatusCode.Common.USE.getCode();
		
		StarFieldHoliday starFieldHoliday = aopr003Service.getStarFieldHoliday(model);
	
		aact001service.regAdminAction(req, session, "스타필드 비정기운영일 상세 조회", null);
		
		return starFieldHoliday;
	}
	
	/**
	 * StarFieldHoliday 수정
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/holidays/ir/bcn/{stf_irgu_opr_seq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyStarFieldHoliday(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody StarFieldHoliday model
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="stf_irgu_opr_seq") String stf_irgu_opr_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("modifyHoliday stf_irgu_opr_seq : {}, bcn_cd : {}, stf_irgu_opr_seq : {}", new Object[] {model, bcn_cd, stf_irgu_opr_seq});
		
		model.bcn_cd = bcn_cd;
		model.stf_irgu_opr_seq = stf_irgu_opr_seq;
		
		model.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = aopr003Service.modifyStarFieldHoliday(model);
				
		aact001service.regAdminAction(req, session, "스타필드 비정기운영일 수정", null);
		
		return result;
	}
	
	/**
	 * StarFieldHoliday 삭제
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/holidays/ir/bcn/{stf_irgu_opr_seq}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteStarFieldHoliday(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="stf_irgu_opr_seq") String stf_irgu_opr_seq) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info("deleteStarFieldHoliday bcn_cd : {}, stf_irgu_opr_seq : {}", new Object[] {bcn_cd, stf_irgu_opr_seq});
		
		StarFieldHoliday model = new StarFieldHoliday();
		model.bcn_cd = bcn_cd;
		model.stf_irgu_opr_seq = stf_irgu_opr_seq;
		
		model.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = aopr003Service.deleteStarFieldHoliday(model);
		
		aact001service.regAdminAction(req, session, "스타필드 비정기운영일 삭제", null);
		
		return result;
	}
}
