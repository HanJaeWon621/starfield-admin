/*
 * couponController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.ACPN003;
import kr.co.starfield.model.ACPN003Filter;
import kr.co.starfield.model.ALBS001;
import kr.co.starfield.model.ALBS002;
import kr.co.starfield.model.ALBS003;
import kr.co.starfield.model.ALBS004;
import kr.co.starfield.model.ALBS004_D;
import kr.co.starfield.model.ALBS005;
import kr.co.starfield.model.AbstMst;
import kr.co.starfield.model.AppListResult;
import kr.co.starfield.model.Event;
import kr.co.starfield.model.LbsZone;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Member;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.AITF009Vo;
import kr.co.starfield.model.vo.ALBS001Vo;
import kr.co.starfield.model.vo.ALBS002Vo;
import kr.co.starfield.model.vo.ALBS003Vo;
import kr.co.starfield.model.vo.ALBS004_DVo;
import kr.co.starfield.model.vo.SEVT001Vo;
import kr.co.starfield.model.vo.SMBR001Vo;
import kr.co.starfield.service.ALBS001Service;

/**
 *  app push 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

@RestController
@RequestMapping("/rest")  
public class ALBS001RestController extends BaseController {

	@Autowired
	ALBS001Service albs001Service ;
	
	private static final Logger ll = LoggerFactory.getLogger(ALBS001RestController.class);
	
	
	/**
	 * inbox 추가 (사용 안함)
	 * @return
	 */
	@RequestMapping(value = "/insertInbox"
		, method = RequestMethod.POST
		, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
	)
	public SimpleResult insertInbox(
			HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="req_div_cd") String req_div_cd,
			@RequestParam(value="uuid") String uuid,
			@RequestParam(value="cust_id") String cust_id
	) throws Exception {
		
		this.initHandler(req, res);
    	
		ALBS004 albs004 = new ALBS004();
		albs004.uuid = uuid;
		albs004.req_div_cd = req_div_cd;
		if(!StringUtils.isEmpty(cust_id)){
			albs004.cust_id = cust_id;
		}
    	SimpleResult result = albs001Service.insertInbox(albs004);
    	
		return result;
	}
	
	
	/**
	 * inbox 리스트 
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/inbox/{uuid}/{access_token}"
		, method = RequestMethod.GET
		, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
	)
	public List<ALBS004_D> getInboxList(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable(value="uuid") String uuid
			,@PathVariable(value="access_token") String access_token
	) throws Exception {
		
		this.initHandler(req, res);
		
		ALBS004_DVo vo = new ALBS004_DVo();
		vo.uuid = uuid;
		vo.access_token = access_token;
		
		List<ALBS004_D> inboxList  = albs001Service.getInboxList(vo);
    	
		return inboxList;
	}
	
	
	/**
	 * 웰컴 푸시 팝업 상세
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/inbox/welcome/{wc_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public ALBS005 getWelcomeDetail(
			HttpServletRequest req, HttpServletResponse res
			, @PathVariable String wc_seq
			) throws Exception {
		
		ALBS005 albs005 = albs001Service.getWelcomeDetail(wc_seq); 
		
		return albs005;
	}
	
	
	/**
	 * inbox 삭제 
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/deleteInbox/{inbox_seq}"
		, method = RequestMethod.DELETE
		, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json"
	)
	public SimpleResult deleteInbox(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable String[] inbox_seq
	) throws Exception {
		
		this.initHandler(req, res);
    	SimpleResult result = albs001Service.deleteInbox(inbox_seq);
    	
		return result;
	}
	
	
	/**
	 * inbox cust_id update (사용 안함)
	 * @return
	 */
	@RequestMapping(value = "/updateinboxUuid/{uuid}/{cust_id}"
		, method = RequestMethod.GET
		, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
	)
	public SimpleResult inboxUuidUpdate(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable(value="uuid") String uuid,
			@PathVariable(value="cust_id") String cust_id
	) throws Exception {
		
		this.initHandler(req, res);
		ALBS004_DVo vo = new ALBS004_DVo();
		vo.uuid = uuid;
		vo.cust_id = cust_id;
		
		SimpleResult result = albs001Service.updateinboxUuid(vo);
    	
		return result;
	}
	
	
	/**
	 * 웰컴 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getWcPushMsgs"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<ALBS003> getWcPushMsgs(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
			, @RequestParam(value="search_dt_type") String search_dt_type
			, @RequestParam(value="search_strt_dt") String search_strt_dt
			, @RequestParam(value="search_end_dt") String search_end_dt
			, @RequestParam(value="push_search_strt_dt") String push_search_strt_dt
			, @RequestParam(value="push_search_end_dt") String push_search_end_dt
			, @RequestParam(value="sortColumn", required=false, defaultValue = "") String sortColumn
			, @RequestParam(value="sortColumnAscDesc", required=false, defaultValue = "") String sortColumnAscDesc
	) throws Exception {
		
		this.initHandler(req, res);

		ALBS003Vo vo = new ALBS003Vo();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.search_dt_type = search_dt_type;
		vo.search_strt_dt = search_strt_dt;
		vo.search_end_dt = search_end_dt;
		vo.push_search_strt_dt = push_search_strt_dt;
		vo.push_search_end_dt = push_search_end_dt;
		vo.sortColumn = sortColumn;
		vo.sortColumnAscDesc = sortColumnAscDesc;
		
		ListResult<ALBS003> wcPushMsgList  = albs001Service.getWcPushMsgs(vo);
		
		return wcPushMsgList;
	}
	
	
	/**
	 * 웰컴 메시지 등록
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/reqWcPushMsg"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult reqWcPushMsg(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody ALBS003 albs003 ) throws Exception {
		//String login_id = (String) session.getAttribute("login_id");
		
		SimpleResult result = new SimpleResult();
		
		result = albs001Service.reqWcPushMsg(albs003);
		
		return result;
	}
	
	
	/**
	 * 웰컴 메시지 상세 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/appPush/welcome/{wel_msg_push_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public ALBS003 getWcPushMsg(
			HttpServletRequest req, HttpServletResponse res, HttpSession session, 
			@PathVariable(value="wel_msg_push_seq") String wel_msg_push_seq
	) throws Exception {
		
		ALBS003 getWcPushMsg = albs001Service.getWcPushMsg(wel_msg_push_seq);
		
		return getWcPushMsg;
	}
	
	
	/**
	 * 웰컴 메시지 수정
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/modifyWcPushMsg"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult modifyWcPushMsg(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody ALBS003 albs003 ) throws Exception {
		
 		SimpleResult result = new SimpleResult();
		
		result = albs001Service.modifyWcPushMsg(albs003);
		
		
 		return result;
	}
	
	
	/**
	 * 웰컴 게시
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/welcome/posting/{wel_msg_push_seq}"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult reqWcPosting(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody ALBS003 albs003 ) throws Exception {
		//String login_id = (String) session.getAttribute("login_id");
		
		SimpleResult result = new SimpleResult();
		
		result = albs001Service.reqWcPosting(albs003);
		
		return result;
	}
	
	
	/**
	 * 웰컴 미게시
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/welcome/unPosting/{wel_msg_push_seq}"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult reqWcUnPosting(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody ALBS003 albs003 ) throws Exception {
		//String login_id = (String) session.getAttribute("login_id");
		
		SimpleResult result = new SimpleResult();
		
		result = albs001Service.reqWcUnPosting(albs003);
		
		return result;
	}
	
	
	/**
	 * 푸시적용 대상 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getPushMembs"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<Member> getPushMembs(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
			, @RequestParam(value="app_tgt_mbr_div_cd", required=false, defaultValue = "") String app_tgt_mbr_div_cd
			, @RequestParam(value="app_tgt_sex", required=false, defaultValue = "") String app_tgt_sex
			, @RequestParam(value="tgt_age", required=false, defaultValue = "") String tgt_age
			, @RequestParam(value="abst_no", required=false, defaultValue = "") String abst_no
			, @RequestParam(value="memb_nm_ko", required=false, defaultValue = "") String memb_nm_ko
	) throws Exception {
		
		this.initHandler(req, res);
		System.out.println(app_tgt_sex +"," + tgt_age);
		SMBR001Vo vo = new SMBR001Vo();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.mbr_sex = app_tgt_sex;
		vo.mbr_age = tgt_age;
		vo.abst_no = abst_no;
		vo.memb_nm_ko = memb_nm_ko;
		
		ListResult<Member> pushMembList  = albs001Service.getPushMembs(vo, app_tgt_mbr_div_cd);
		return pushMembList;
	}

	
	/**
	 * 적용 쿠폰 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/{cp_iss_type_cd}/coupons"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	public ListResult<ACPN001> getApplyCoupons(
	//public ModelAndView getApplyCoupons(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
			, @RequestParam(value="cp_titl", required=false, defaultValue = "") String cp_titl
			, @PathVariable(value="cp_iss_type_cd") String cp_iss_type_cd
			) throws Exception {
		
		this.initHandler(req, res);
		
		ACPN001Vo vo = new ACPN001Vo();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.cp_titl = cp_titl;
		vo.cp_iss_type_cd = cp_iss_type_cd;
		
		ModelAndView mv = new ModelAndView();
		//mv.addObject("count", "100");
		
		ListResult<ACPN001> cplist  = albs001Service.getWelcomeCoupons(vo);
		
		
		return cplist;
	}
	
	
	
	
	/**
	 * 적용 존
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/welcome/tenants"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	public ListResult<LbsZone> getApplyTenants(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="tnt_nm", required=false, defaultValue = "") String tnt_nm
			) throws Exception {
		
		this.initHandler(req, res);
		
		AITF009Vo vo = new AITF009Vo();
		vo.offset = offset;
		vo.limit = limit;
		vo.tnt_nm = tnt_nm;
		ListResult<LbsZone> cplist  = albs001Service.getApplyTenants(vo);
		
		return cplist;
	}

	
	/**
	 * 고객 추출 마스터 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getAbstMsts"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	public List<AbstMst> getAbstMsts(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			) throws Exception {
		
		this.initHandler(req, res);
		
		List<AbstMst> abstMstList  = albs001Service.getAbstMsts();
		
		return abstMstList;
	}
	
	
	/**
	 * 시나리오 등록/수정
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/regScenario/{type}"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult regScenario(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="type") String type
			, @RequestBody ALBS001 albs001 ) throws Exception {
		
		SimpleResult result = new SimpleResult();
		if(type.equals("C")){
			result = albs001Service.regScenario(albs001);
			
		}else{
			result = albs001Service.modifyScenario(albs001);
		}
		
		return result;
	}
	
	/**
	 * 시나리오 기본 이미지 
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/getScnObImg/{scn_otb_div_cd}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public ALBS001 getScnObImg(
			HttpServletRequest req, HttpServletResponse res
			, @PathVariable(value="scn_otb_div_cd") String scn_otb_div_cd
	) throws Exception {
		ALBS001 vo = new ALBS001();
		vo.scn_otb_div_cd=scn_otb_div_cd;
		ALBS001 albs001 = albs001Service.getScnObImg(vo);
		 
 		return albs001;
	}
	
	/**
	 * 시나리오 상세
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/getScenario/{scn_otb_cp_push_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	/*@ResponseBody*/
	public ALBS001 getScenario(
			HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@PathVariable(value="scn_otb_cp_push_seq") String scn_otb_cp_push_seq 
		) throws Exception {
		
		ALBS001 albs001 = albs001Service.getScenario(scn_otb_cp_push_seq);
		
		return albs001;
	}
	
	
	/**
	 * 시나리오 미게시
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/regScenarioUnPosting/{scn_otb_cp_push_seq}"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	/*@ResponseBody*/
	public SimpleResult regScenarioUnPosting(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="scn_otb_cp_push_seq") String scn_otb_cp_push_seq 
			, @RequestBody ALBS001 albs001
		) throws Exception {
		
		SimpleResult result = new SimpleResult();
		result = albs001Service.regScenarioUnPosting(scn_otb_cp_push_seq, albs001);
		
		return result;
	}

	
	/**
	 * 시나리오 게시
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/regScenarioPosting/{scn_otb_cp_push_seq}"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	/*@ResponseBody*/
	public SimpleResult regScenarioPosting(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="scn_otb_cp_push_seq") String scn_otb_cp_push_seq 
			, @RequestBody ALBS001 albs001
			) throws Exception {
		
		SimpleResult result = new SimpleResult();
		result = albs001Service.regScenarioPosting(scn_otb_cp_push_seq, albs001);
		
		return result;
	}
	
	
	/**
	 * 시나리오 리스트
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/getScenarios/{type}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	/*@ResponseBody*/
	public ListResult<ALBS001> getScenarios(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="type") String type 
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
			, @RequestParam(value="sh_strt_dt") String sh_strt_dt
			, @RequestParam(value="sh_end_dt") String sh_end_dt
			, @RequestParam(value="sh_memb_type") String sh_memb_type
			, @RequestParam(value="sh_text_type") String sh_text_type
			, @RequestParam(value="sh_text") String sh_text
			, @RequestParam(value="sortColumn", required=false, defaultValue = "") String sortColumn
			, @RequestParam(value="sortColumnAscDesc", required=false, defaultValue = "") String sortColumnAscDesc
			) throws Exception {
		
		ALBS001Vo vo = new ALBS001Vo();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.sh_strt_dt = sh_strt_dt;
		vo.sh_end_dt = sh_end_dt;
		vo.sh_memb_type = sh_memb_type;
		vo.sh_text_type = sh_text_type;
		vo.sh_text = sh_text;
		vo.sortColumn = sortColumn;
		vo.sortColumnAscDesc = sortColumnAscDesc;
		if(type.equals("scenario")){
			vo.scn_otb_div_cd = "1";
		}else{
			vo.scn_otb_div_cd = "2";
		}
		
		ListResult<ALBS001> albs001 = albs001Service.getScenarios(vo);
		
		return albs001; 
	}
	
	
	/**
	 * 시나리오 리스트
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/getEvents"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	/*@ResponseBody*/
	public ListResult<Event> getEvents(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
			, @RequestParam(value="evt_titl", required=false, defaultValue = "") String evt_titl
			) throws Exception {
		
		SEVT001Vo vo = new SEVT001Vo();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.evt_titl = evt_titl;
		
		ListResult<Event> event = albs001Service.getEvents(vo);
		
		return event; 
	}
	
	
	/**
	 * 배너 등록/수정
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/regBanner/{type}"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult regBanner(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="type") String type
			, @RequestBody ALBS002 albs002 ) throws Exception {
		
		SimpleResult result = new SimpleResult();
		if(type.equals("C")){
			result = albs001Service.regBanner(albs002);
		}else{
			result = albs001Service.modifyBanner(albs002);
		}
		
		return result;
	}
	
	
	/**
	 * 배너 상세
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/banner/{bn_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public ALBS002 getBannerDetail(
			HttpServletRequest req, HttpServletResponse res
			, @PathVariable String bn_seq
			) throws Exception {
		
		ALBS002 albs002 = albs001Service.getBannerDetail(bn_seq); 
		
		return albs002;
	}
	
	
	/**
	 * 배너 api
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/random/banners/{map_id}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public AppListResult<ALBS002> randomBanners(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="map_id") String map_id
			) throws Exception {
		
		AppListResult<ALBS002> banner = albs001Service.randomBanners(map_id);
		
		return banner;
	}
	
	
	/**
	 * 배너 게시/미게시
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/regBannerPosting/{type}"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult regBannerPosting(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="type") String type
			, @RequestBody ALBS002 albs002
			) throws Exception {
		
		SimpleResult result = new SimpleResult();
		albs002.bn_exp_yn = type;
		result = albs001Service.regBannerPosting(albs002);
		
		return result;
	}
	
	
	/**
	 * 쿠폰 목록 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getBanners"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<ALBS002> getBanners(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
			, @RequestParam(value="search_strt_dt") String search_strt_dt
			, @RequestParam(value="search_end_dt") String search_end_dt
			, @RequestParam(value="search_dt_type") String search_dt_type
			, @RequestParam(value="sh_post_type") String sh_post_type
			, @RequestParam(value="sh_text_type") String sh_text_type
			, @RequestParam(value="sh_text") String sh_text
			, @RequestParam(value="sortColumn") String sortColumn
			, @RequestParam(value="sortColumnAscDesc") String sortColumnAscDesc
	) throws Exception {
		
		this.initHandler(req, res);

		ALBS002Vo vo = new ALBS002Vo();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.search_dt_type = search_dt_type;
		vo.search_strt_dt = search_strt_dt;
		vo.search_end_dt = search_end_dt;
		
		vo.sh_post_type = sh_post_type;
		vo.sh_text_type = sh_text_type;
		vo.sh_text = sh_text;
		
		vo.sortColumn = sortColumn;
		vo.sortColumnAscDesc = sortColumnAscDesc;
		
		ListResult<ALBS002> bannerList  = albs001Service.getBanners(vo);
		
		return bannerList;
	}
	
	
	/**
	 * 배너 상세
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/banner/ord"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public int getBannerOrdCnt(
			HttpServletRequest req, HttpServletResponse res
			, @RequestBody ALBS002 albs002 
			) throws Exception {
		
		int cnt = albs001Service.getBannerOrdCnt(albs002); 
		
		return cnt;
	}

	
	/**
	 * 푸시대상 cnt
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/pushMembCnt/{type}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	/*@ResponseBody*/
	public int pushMembCnt(
			HttpServletRequest req, HttpServletResponse res
			, @PathVariable(value="type") String type
			) throws Exception {
		
		int cnt = albs001Service.pushMembCnt(type); 
		
		return cnt;
	}
	
	
	/**
	 * 시나리오 존 불러오기
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getSnTenants"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<ACPN003> getTenants(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="cate_seq", required=false, defaultValue = "") String cate_seq
			, @RequestParam(value="tnt_nm_ko", required=false, defaultValue = "") String tnt_nm_ko
	) throws Exception {
		
		this.initHandler(req, res);
 
		ACPN003Filter filter = new ACPN003Filter();
		filter.offset = offset;
		filter.limit = limit;
		filter.cate_seq = cate_seq;
		filter.tnt_nm_ko = tnt_nm_ko;
		
		ListResult<ACPN003> list  = albs001Service.getSnTenants(filter);
		
		return list;
	}
}

