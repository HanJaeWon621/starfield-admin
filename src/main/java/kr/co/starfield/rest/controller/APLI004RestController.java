package kr.co.starfield.rest.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.model.APPLOG;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SAML002;
import kr.co.starfield.model.SAML007;
import kr.co.starfield.model.SMBR001;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SAML002Vo;
import kr.co.starfield.model.vo.SMBR001Vo;
import kr.co.starfield.service.APLI004Service;

/**
 *  위치정보 열람 요청 및 내역
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author ldh
 * @version 1.0,
 * @see
 * @date 2016.10.10
 */
@RestController
@RequestMapping("/rest")
public class APLI004RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(APLI004RestController.class);
	
	@Autowired
	APLI004Service apli004Service;
	
	
	/**
	 * 위치정보 열람 요청 및 내역 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getLocationReqViewList"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<SAML007> getLocationReqViewList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_strt_dt" ) String sh_strt_dt
			, @RequestParam(value="sh_end_dt" ) String sh_end_dt
			, @RequestParam(value="sh_text_type" ) String sh_text_type
			, @RequestParam(value="sh_text" ) String sh_text
			, @RequestParam(value = "sortColumn") String sortColumn
			, @RequestParam(value = "sortColumnAscDesc") String sortColumnAscDesc
			
	) throws Exception {
		
		
		this.initHandler(req, res);

		SAML007 saml007 = new SAML007();
		saml007.offset = Integer.parseInt(offset);
		saml007.limit = Integer.parseInt(limit);
		saml007.sh_end_dt = sh_end_dt;
		saml007.sh_strt_dt = sh_strt_dt;
		saml007.sh_text = sh_text;
		saml007.sh_text_type = sh_text_type;
		saml007.sortColumn = sortColumn;
		saml007.sortColumnAscDesc = sortColumnAscDesc;
		
		ListResult<SAML007> locationReqView  = apli004Service.getLocationReqViewList(saml007);
		
		return locationReqView;
	}

	
	/**
	 * 사용자(요청자) 리스트
 	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getRequester"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	public ListResult<SMBR001> getRequester(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_text_type" ) String sh_text_type
			, @RequestParam(value="sh_text" ) String sh_text
			
			) throws Exception {
		
		
		this.initHandler(req, res);
		
		SMBR001Vo vo = new SMBR001Vo();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.sh_text = sh_text;
		vo.sh_text_type = sh_text_type;
		
		ListResult<SMBR001> requester  = apli004Service.getRequester(vo);
		
		return requester;
	}
	
	
	/**
	 * 위치정보 열람 요청 등록
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/regLocationReqView"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult regLocationReqView(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody SAML007 saml007 ) throws Exception {
		
		SimpleResult result = new SimpleResult();
		String adm_seq = (String) session.getAttribute("adm_seq");
		String adm_id = (String) session.getAttribute("adm_id");
		saml007.dealadm_nm = adm_id;
		saml007.dealadm_seq = adm_seq;
		
		result = apli004Service.regLocationReqView(saml007);
		
		return result;
	}

	
	/**
	 * 처리현황 업데이트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/regLocationDnYn/{read_req_seq}"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	/*@ResponseBody*/
	public SimpleResult regLocationDnYn(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="read_req_seq") String read_req_seq
			) throws Exception {
		
		SimpleResult result = new SimpleResult();
		String adm_seq = (String) session.getAttribute("adm_seq");
		SAML007 saml007 = new SAML007();
		saml007.dealadm_seq = adm_seq;
		saml007.read_req_seq = read_req_seq;
		
		result = apli004Service.regLocationDnYn(saml007);
		
		return result;
	}
	
	
	@RequestMapping(value = "/{bcn_cd}/locationExcel/{mem_seq}", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView locationExcel(HttpServletRequest req, HttpServletResponse res
			, @PathVariable(value="mem_seq") String mem_seq
			) throws Exception {

		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

		res.setHeader("Content-Disposition", "attachment; filename=\" " + "location_list_" + strToday + ".xls\"");


		/*
		SAML002 saml002 = apli004Service.getLocationReqViewExcel(mem_seq);
		ListResult<SAML002> resultlist = new ListResult<SAML002>();
		if(!StringUtils.isEmpty(saml002)){
			resultlist.list.add(saml002);
		}
		*/
		ListResult<SAML002> resultlist  = apli004Service.getLocationReqViewExcel(mem_seq);
		
		String sheetName = "";

		sheetName = "위치로그";

		Map<String, Object> setting = new LinkedHashMap<String, Object>();
					
		String[] arrTitle = { "X좌표", "Y좌표", "체류시간"};
		String[] colNameList = { "x_ctn_cord", "y_ctn_cord", "stay_time"};

		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", resultlist.list);

		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);

		return mav;
	}

	
	@RequestMapping(value = "/{bcn_cd}/locationReqViewListExcel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView locationReqViewListExcel(HttpServletRequest req, HttpServletResponse res
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_strt_dt" ) String sh_strt_dt
			, @RequestParam(value="sh_end_dt" ) String sh_end_dt
			, @RequestParam(value="sh_text_type" ) String sh_text_type
			, @RequestParam(value="sh_text" ) String sh_text
			) throws Exception {
		
		String strToday = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "location_list_" + strToday + ".xls\"");
		
		
		
		SAML007 saml007 = new SAML007();
		saml007.offset = Integer.parseInt(offset);
		saml007.limit = Integer.parseInt(limit);
		saml007.sh_end_dt = sh_end_dt;
		saml007.sh_strt_dt = sh_strt_dt;
		saml007.sh_text = sh_text;
		saml007.sh_text_type = sh_text_type;
		
		ListResult<SAML007> locationReqView  = apli004Service.getLocationReqViewList(saml007);
		
		String sheetName = "";
		
		sheetName = "위치정보이용,제공사실 열람,고지 확인 자료";
		
		Map<String, Object> setting = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = { "취급자", "요청자", "목적", "처리현황", "이용일시"};
		String[] colNameList = { "dealadm_nm", "req_adm_nm", "read_obj","dn_yn","use_dttm"};
		
		setting.put("sheetName", sheetName);
		setting.put("titleList", arrTitle);
		setting.put("colNameList", colNameList);
		setting.put("dataList", locationReqView.list);
		
		ModelAndView mav = new ModelAndView("listExcelBuilder", "setting", setting);
		
		return mav;
	}
	
	
	/**
	 * 위치정보 열람 요청 및 내역 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getAppLogList"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<APPLOG> getAppLogList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_strt_dt" ) String sh_strt_dt
			, @RequestParam(value="sh_end_dt" ) String sh_end_dt
			, @RequestParam(value="sh_text_type" ) String sh_text_type
			, @RequestParam(value="sh_type1" ) String sh_type1
			, @RequestParam(value="sh_type2" ) String sh_type2
			, @RequestParam(value="sh_text", required=false, defaultValue = "") String sh_text
			, @RequestParam(value = "sortColumn") String sortColumn
			, @RequestParam(value = "sortColumnAscDesc") String sortColumnAscDesc
	) throws Exception {
		
		this.initHandler(req, res);

		APPLOG applog = new APPLOG();
		applog.offset = Integer.parseInt(offset);
		applog.limit = Integer.parseInt(limit);
		applog.sh_end_dt = sh_end_dt;
		applog.sh_strt_dt = sh_strt_dt;
		applog.sh_text = sh_text;
		applog.sh_text_type = sh_text_type;
		applog.sh_type1 = sh_type1;
		applog.sh_type2 = sh_type2;
		applog.sortColumn = sortColumn;
		applog.sortColumnAscDesc = sortColumnAscDesc;
		
		ListResult<APPLOG> getAppLogList  = apli004Service.getAppLogList(applog);
		
		return getAppLogList;
	}
	
	
	/**
	 * 사용자(요청자) 리스트
 	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getLocationUseList"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	public ListResult<SAML002> getLocationUseList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_strt_dt" ) String sh_strt_dt
			, @RequestParam(value="sh_end_dt" ) String sh_end_dt
			, @RequestParam(value="sh_text_type" ) String sh_text_type
			, @RequestParam(value="sh_text", required=false, defaultValue = "") String sh_text
			, @RequestParam(value = "sortColumn") String sortColumn
			, @RequestParam(value = "sortColumnAscDesc") String sortColumnAscDesc
			
			) throws Exception {
		
		
		this.initHandler(req, res);
		
		SAML002Vo vo = new SAML002Vo();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.sh_text = sh_text;
		vo.sh_text_type = sh_text_type;
		vo.sh_strt_dt = sh_strt_dt;
		vo.sh_end_dt = sh_end_dt;
		vo.sortColumn = sortColumn;
		vo.sortColumnAscDesc = sortColumnAscDesc;
		
		ListResult<SAML002> getLocationUseList  = apli004Service.getLocationUseList(vo);
		
		return getLocationUseList;
	}
}
