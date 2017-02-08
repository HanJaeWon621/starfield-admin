/*
 * AuthorizationController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.ACPN005;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.PgMng;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.service.ACPN001Service;
import kr.co.starfield.service.PgMngService;

/**
 * 로그인 및 사용자인증 관련 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016 Company Paprika
 *
 * @author xxx
 * @version 1.0,
 * @see
 * @date 2016.04.11
 */

@Controller
@RequestMapping("/")
public class PgMngWebController {

	private Logger ll = LoggerFactory.getLogger(StyleSetWebController.class);

	@Autowired
	PgMngService service;

	// 스타일 세트 리스트
	@RequestMapping(value = "/pgMngs")
	public ModelAndView getUseWaitCoupons() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("pgmng/PgMng");
		return mv;
	}

	// 목록
	@RequestMapping(value = "/getPgMngs/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView getDownExcel(HttpServletRequest req, HttpServletResponse res
			, @RequestParam(value="sh_pg_nm" ) String sh_pg_nm) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "cp_down_list_" + strToday + ".xls\"");

		PgMng vo = new PgMng();
		//vo.offset = Integer.parseInt(offset);
		vo.limit = -1;
		vo.sh_pg_nm = sh_pg_nm;

		ListResult<PgMng> cplist  = service.getPgMngs(vo);


		String sheetName = "";

		sheetName = "목록";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();

		String[] arrTitle = { "프로그램명"};
		String[] colNameList = { "pg_nm" };

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", cplist.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		// aact001service.regAdminAction(req, session, "편의시설 엑셀 다운로드", null);
		return mav;
	}

}
