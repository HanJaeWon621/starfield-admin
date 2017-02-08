/*
 * couponController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.rest.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.ACPN003;
import kr.co.starfield.model.ACPN003Filter;
import kr.co.starfield.model.ACPN005;
import kr.co.starfield.model.Category;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SCPN001;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.TenantMapping;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.SCPN002_D2Vo;
import kr.co.starfield.service.ACPN001Service;
import kr.co.starfield.service.ACPN002Service;

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
public class ACPN001RestController extends BaseController {

	@Autowired
	ACPN001Service acpn001Service;
	@Autowired
	ACPN002Service acpn002Service;
	
	private static final Logger ll = LoggerFactory.getLogger(ACPN001RestController.class);
	
	/**
	 * 쿠폰 관리 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getCoupons"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<ACPN001> getCoupons(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_dt_type") String sh_dt_type
			, @RequestParam(value="sh_strt_dt") String sh_strt_dt
			, @RequestParam(value="sh_end_dt") String sh_end_dt
			, @RequestParam(value="sh_div_type") String sh_div_type
			, @RequestParam(value="sh_kind_type") String sh_kind_type
			, @RequestParam(value="sh_text_type") String sh_text_type
			, @RequestParam(value="sh_text") String sh_text
			, @RequestParam(value="sortColumn") String sortColumn
			, @RequestParam(value="sortColumnAscDesc") String sortColumnAscDesc
			
	) throws Exception {
		
		
		this.initHandler(req, res);

		ACPN001Vo vo = new ACPN001Vo();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.sh_dt_type = sh_dt_type;
		vo.sh_strt_dt = sh_strt_dt;
		vo.sh_end_dt = sh_end_dt;
		vo.sh_div_type = sh_div_type;
		vo.sh_kind_type = sh_kind_type;
		vo.sh_text_type = sh_text_type;
		vo.sh_text = sh_text;
		vo.sortColumn = sortColumn;
		vo.sortColumnAscDesc = sortColumnAscDesc;
			
		
		ListResult<ACPN001> cplist  = acpn001Service.getCoupons(vo);
		
		return cplist;
	}
	
	
	/**
	 * 쿠폰 테넌트 팝업 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getTenants"
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
			, @RequestParam(value="busi_tnt_cd", required=false, defaultValue = "") String busi_tnt_cd
	) throws Exception {
		
		this.initHandler(req, res);
		
 
		ACPN003Filter filter = new ACPN003Filter();
		filter.offset = offset;
		filter.limit = limit;
		filter.cate_seq = cate_seq;
		filter.tnt_nm_ko = tnt_nm_ko;
		filter.busi_tnt_cd = busi_tnt_cd;
		
		ListResult<ACPN003> list  = acpn001Service.getTenants(filter);
		
		return list;
	}
	
	
	/**
	 * 쿠폰 관리 등록
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/reqCoupon"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult reqCoupon(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody ACPN001 acpn001 ) throws Exception {
		//String login_id = (String) session.getAttribute("login_id");
		
		SimpleResult result = new SimpleResult();
		
		
		result = acpn001Service.reqCoupon(acpn001);
		
		return result;
	}
	
	
	/**
	 * 쿠폰 상세 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getCoupon/{cp_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public ACPN001 getCoupon(
			HttpServletRequest req, HttpServletResponse res, HttpSession session, 
			@PathVariable(value="cp_seq") String cp_seq
	) throws Exception {
		
		ACPN001 getcoupon = acpn001Service.getCoupon(cp_seq);
		
		return getcoupon;
	}
	
	
	/**
	 * 쿠폰 관리 수정
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/modifyCoupon"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult modifyCoupon(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody ACPN001 acpn001 ) throws Exception {
		//String login_id = (String) session.getAttribute("login_id");
		
 		SimpleResult result = new SimpleResult();
		
		//acpn001.reg_usr = "ldh";
		result = acpn001Service.modifyCoupon(acpn001);
		
		
 		return result;
	}
	
	
	/**
	 * 카테고리 리스트 (사용안함)
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getCategorys"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<Category> getCategorys(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
	) throws Exception {
		
		this.initHandler(req, res);
		ListResult<Category> list  = acpn001Service.getCategorys();
		
		return list;
	}
	
	
	/**
	 * 직접회수형 쿠폰 노출
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/nmCoupon/posting/{cp_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json" 
		)
	public SimpleResult nmCouponPosting(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable(value="cp_seq") String cp_seq
	) throws Exception {
		
		this.initHandler(req, res);
    	
    	SimpleResult result = acpn002Service.nmCouponPosting(cp_seq);
    	
		return result;
	}
	
	/**
	 * 쿠폰 복사
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/copyCoupon/{cp_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json" 
		)
	public SimpleResult copyCoupon(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable(value="cp_seq") String cp_seq
	) throws Exception {
		
		this.initHandler(req, res);
    	SimpleResult result = acpn001Service.copyCoupon(cp_seq);
    	
		return result;
	}
	
	/**
	 * 가승인 취소 처리
	 * SCPN002_D3 INSERT SCPN002_D2 UPDATE
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/cnclCpUsePreAppr/{log_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json" 
		)
	public SimpleResult cnclCpUsePreAppr(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable(value="log_seq") String log_seq
	) throws Exception {
		
		this.initHandler(req, res);
    	SimpleResult result = acpn001Service.cnclCpUsePreAppr(log_seq);
    	
		return result;
	}
	
	/**
	 * 대상 조정 처리
	 * SCPN002_D3 INSERT SCPN002_D2 UPDATE
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/compareEqaulCp/{log_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json" 
		)
	public SimpleResult compareEqaulCp(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable(value="log_seq") String log_seq
	) throws Exception {
		
		this.initHandler(req, res);
    	SimpleResult result = acpn001Service.compareEqaulCp(log_seq);
    	
		return result;
	}
	/**
	 * 자동회수형 쿠폰 쿠폰 노출
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/mbCoupon/posting/{cp_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json" 
		)
	public SimpleResult mbCouponPosting(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable(value="cp_seq") String cp_seq
	) throws Exception {
		
		this.initHandler(req, res);
    	SimpleResult result = acpn002Service.updatePosting(cp_seq);
    	
		return result;
	}
	
	
	/**
	 * 자동회수형 쿠폰 미노출
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/nmCoupon/unposted/{cp_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json" 
		)
	public SimpleResult unPosted(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable(value="cp_seq") String cp_seq
	) throws Exception {
		
		this.initHandler(req, res);
    	
    	SimpleResult result = acpn001Service.unposted(cp_seq);
    	
		return result;
	}
	
	
	/**
	 * 쿠폰다운로드 내역조회
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getDownCoupons"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<ACPN005> getDownCoupons(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
			, @RequestParam(value="sh_strt_dt") String sh_strt_dt
			, @RequestParam(value="sh_end_dt") String sh_end_dt
			, @RequestParam(value="sh_div_type") String sh_div_type
			, @RequestParam(value="sh_text_type") String sh_text_type
			, @RequestParam(value="sh_text") String sh_text
			, @RequestParam(value="sortColumn") String sortColumn
			, @RequestParam(value="sortColumnAscDesc") String sortColumnAscDesc
	) throws Exception {
		
		this.initHandler(req, res);

		ACPN005 acpn005 = new ACPN005();
		acpn005.offset = Integer.parseInt(offset);
		acpn005.limit = Integer.parseInt(limit);
		acpn005.sh_strt_dt = sh_strt_dt;
		acpn005.sh_end_dt = sh_end_dt;
		acpn005.sh_div_type = sh_div_type;
		acpn005.sh_text_type = sh_text_type;
		acpn005.sh_text = sh_text;
		acpn005.sortColumn = sortColumn;
		acpn005.sortColumnAscDesc = sortColumnAscDesc;
		
		ListResult<ACPN005> dwonCplist  = acpn001Service.getDownCoupons(acpn005);
		
		return dwonCplist;
	}

	
	
	
	/**
	 * 쿠폰 사용 내역조회
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getUseCoupons"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<ACPN005> getUseCoupons(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
			, @RequestParam(value="sh_strt_dt") String sh_strt_dt
			, @RequestParam(value="sh_end_dt") String sh_end_dt
			, @RequestParam(value="sh_div_type") String sh_div_type
			, @RequestParam(value="sh_text_type") String sh_text_type
			, @RequestParam(value="sh_text") String sh_text
			, @RequestParam(value="sortColumn") String sortColumn
			, @RequestParam(value="sortColumnAscDesc") String sortColumnAscDesc
	) throws Exception {
		
		this.initHandler(req, res);

		ACPN005 acpn005 = new ACPN005();
		acpn005.offset = Integer.parseInt(offset);
		acpn005.limit = Integer.parseInt(limit);
		acpn005.sh_strt_dt = sh_strt_dt;
		acpn005.sh_end_dt = sh_end_dt;
		acpn005.sh_div_type = sh_div_type;
		acpn005.sh_text_type = sh_text_type;
		acpn005.sh_text = sh_text;	
		acpn005.sortColumn = sortColumn;
		acpn005.sortColumnAscDesc = sortColumnAscDesc;
		
		ll.info("offset is {}", offset);
		ll.info("limit is {}", limit);
		
		ListResult<ACPN005> dwonCplist  = acpn001Service.getUseCoupons(acpn005);
		
		return dwonCplist;
	}

	/**
	 * 쿠폰 가승인 상태 목록 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getUseWaitCoupons"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<ACPN005> getUseWaitCoupons(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
			, @RequestParam(value="sh_strt_dt") String sh_strt_dt
			, @RequestParam(value="sh_end_dt") String sh_end_dt
			, @RequestParam(value="sh_text_type") String sh_text_type
			, @RequestParam(value="sh_text") String sh_text
			, @RequestParam(value="sortColumn") String sortColumn
			, @RequestParam(value="sortColumnAscDesc") String sortColumnAscDesc
	) throws Exception {
		
		this.initHandler(req, res);

		ACPN005 acpn005 = new ACPN005();
		acpn005.offset = Integer.parseInt(offset);
		acpn005.limit = Integer.parseInt(limit);
		acpn005.sh_strt_dt = sh_strt_dt;
		acpn005.sh_end_dt = sh_end_dt;
		acpn005.sh_text_type = sh_text_type;
		acpn005.sh_text = sh_text;	
		acpn005.sortColumn = sortColumn;
		acpn005.sortColumnAscDesc = sortColumnAscDesc;
		
		ll.info("offset is {}", offset);
		ll.info("limit is {}", limit);
		
		ListResult<ACPN005> dwonCplist  = acpn001Service.getUseWaitCoupons(acpn005);
		
		return dwonCplist;
	}
	
	/**
	 * 영업정보랑 상태가 틀린건 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getUseCompareCoupons"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<ACPN005> getUseCompareCoupons(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
			, @RequestParam(value="sh_strt_dt") String sh_strt_dt
			, @RequestParam(value="sh_end_dt") String sh_end_dt
			, @RequestParam(value="sh_text_type") String sh_text_type
			, @RequestParam(value="sh_text") String sh_text
			, @RequestParam(value="sortColumn") String sortColumn
			, @RequestParam(value="sortColumnAscDesc") String sortColumnAscDesc
	) throws Exception {
		
		this.initHandler(req, res);

		ACPN005 acpn005 = new ACPN005();
		acpn005.offset = Integer.parseInt(offset);
		acpn005.limit = Integer.parseInt(limit);
		acpn005.sh_strt_dt = sh_strt_dt;
		acpn005.sh_end_dt = sh_end_dt;
		acpn005.sh_text_type = sh_text_type;
		acpn005.sh_text = sh_text;	
		acpn005.sortColumn = sortColumn;
		acpn005.sortColumnAscDesc = sortColumnAscDesc;
		
		ll.info("offset is {}", offset);
		ll.info("limit is {}", limit);
		
		ListResult<ACPN005> dwonCplist  = acpn001Service.getUseCompareCoupons(acpn005);
		
		return dwonCplist;
	}
	/**
	 * 쿠폰 다운로드 내역 상세
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getDownCoupon"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SCPN001 getCouponDownDetail(
			HttpServletRequest req, HttpServletResponse res, HttpSession session, 
			@RequestBody SCPN001 scpn001
	) throws Exception {
		
		
		SCPN001 getCouponDownDetail = acpn001Service.getCouponDownDetail(scpn001);
		
		return getCouponDownDetail;
	}

	
	/**
	 * 쿠폰 사용 내역 상세
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getUseCoupon"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	/*@ResponseBody*/
	public SCPN001 getCouponUseDetail(
			HttpServletRequest req, HttpServletResponse res, HttpSession session, 
			@RequestBody SCPN001 scpn001
			) throws Exception {
		
		
		SCPN001 getCouponUseDetail = acpn001Service.getCouponUseDetail(scpn001);
		
		return getCouponUseDetail;
	}
	
	
	/**
	 * 쿠폰 사용처리 변경 (사용 안함)
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/modifyCpUseProcess"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult modifyCpUseProcess(
			HttpServletRequest req, HttpServletResponse res, HttpSession session, 
			@RequestBody SCPN001 scpn001
	) throws Exception {
		
		
		SimpleResult result = acpn001Service.modifyCpUseProcess(scpn001);
		
		return result;
	}

	
	/**
	 * 쿠폰 사용처리 변경
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/modifyCpUseProcess2"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	/*@ResponseBody*/
	public SimpleResult modifyCpUseProcess2(
			HttpServletRequest req, HttpServletResponse res, HttpSession session, 
			@RequestBody SCPN001 scpn001
			) throws Exception {
		
		scpn001.reg_usr = (String) session.getAttribute("adm_id");
		SimpleResult result = acpn001Service.modifyCpUseProcess2(scpn001);
		
		return result;
	}
	
	
	/**
	 * 쿠폰 사용처리 (사용 안함)
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/coupon/use"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult updateCouponUseSts(
			HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value="cp_iss_mst_seq") String cp_iss_mst_seq,
			@RequestParam(value="cp_seq") String cp_seq,
			@RequestParam(value="cp_iss_sub_seq") String cp_iss_sub_seq,
			@RequestParam(value="uuid") String uuid, 
			@RequestParam(value="cp_use_sts_cd") String cp_use_sts_cd 
		) throws Exception {
		
		SimpleResult result = new SimpleResult();
		SCPN002_D2Vo vo = new SCPN002_D2Vo();
		vo.cp_iss_mst_seq = cp_iss_mst_seq;
		vo.cp_seq = cp_seq;
		vo.cp_iss_sub_seq = cp_iss_sub_seq;
		vo.uuid = uuid;
		vo.cp_use_sts_cd = cp_use_sts_cd;
		
		result = acpn001Service.updateCouponUseSts(vo);
		
		return result;
	}
	
	
	/**
	 * 모바일 쿠폰 사용처리 (사용 안함)
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/coupon/posting/{cp_seq}"
		, method = RequestMethod.GET
		, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
	)
	public SimpleResult updatePosting(
			HttpServletRequest req, HttpServletResponse res, 
			@PathVariable(value="cp_seq") String cp_seq
	) throws Exception {
		
		this.initHandler(req, res); 
    	
    	SimpleResult result = acpn002Service.updatePosting(cp_seq);
    	
		return result;
	}
	
	
	/**
	 * 테넌트 매핑 리스트
	 * @return Map<Object, Object>
	 */
	@RequestMapping(value = "/{bcn_cd}/tenant/mappings"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
		)
	public ModelAndView  tntMappings( 
			HttpServletRequest req, HttpServletResponse res
			, @RequestParam(value="bcn_cd" ) String bcn_cd
			, @RequestParam(value="tnt_nm_ko") String tnt_nm_ko
			, @RequestParam(value="tenant_type") String tenant_type
			, @RequestParam(value="mapping_type") String mapping_type
		) throws Exception {
		 
		this.initHandler(req, res);
		TenantMapping tenantmapping = new TenantMapping();
		tenantmapping.bcn_cd = bcn_cd;
		tenantmapping.tnt_nm_ko = tnt_nm_ko;
		tenantmapping.tenant_type = tenant_type;
		tenantmapping.mapping_type = mapping_type;
		tenantmapping.sh_text_type = "1";
		tenantmapping.scr_type = "1";//화면타입 //테넌트매핑
    	Map<Object, Object> tenantlist  = acpn001Service.tntMappings(tenantmapping);
    	
    	ModelAndView mv = new ModelAndView();
    	mv.addObject("data", tenantlist);
		return mv;
	}
	
	
	/**
	 * 에누리 쿠폰 기본 이미지 
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/getCouponImg"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public ACPN001 getCouponImg(
			HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
		ACPN001 acpn001 = acpn001Service.getCouponImg();
		 
 		return acpn001;
	}
	
	
	/**
	 * 테넌트 매핑  수정
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/tenant/mapping"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult modifyTntMapping(
			HttpServletRequest req, HttpServletResponse res
			, @RequestBody TenantMapping tenantmapping 
	) throws Exception {
		 
 		SimpleResult result = new SimpleResult();
		
		result = acpn001Service.modifyTntMapping(tenantmapping);
		
		 
 		return result;
	}
	
	
	/**
	 * lbs 존 매핑 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/lbsZone/mappings"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
		)
	public ModelAndView tntZoneMappings( 
			HttpServletRequest req, HttpServletResponse res
			, @RequestParam(value="bcn_cd" ) String bcn_cd
			, @RequestParam(value="tnt_nm_ko") String tnt_nm_ko
			, @RequestParam(value="tenant_type") String tenant_type
			, @RequestParam(value="sh_text_type") String sh_text_type
			, @RequestParam(value="mapping_type") String mapping_type
		) throws Exception {
		 
		this.initHandler(req, res);
		TenantMapping tenantmapping = new TenantMapping();
		tenantmapping.bcn_cd = bcn_cd;
		tenantmapping.tnt_nm_ko = tnt_nm_ko;
		tenantmapping.tenant_type = tenant_type;
		tenantmapping.sh_text_type = sh_text_type;
		tenantmapping.mapping_type = mapping_type;
		tenantmapping.scr_type = "2";//화면타입 //lbs존매핑
    	Map<Object, Object> tenantlist  = acpn001Service.tntZoneMappings(tenantmapping);
    	ModelAndView mv = new ModelAndView();
    	mv.addObject("data", tenantlist);
		return mv;
	}
	
	
	/**
	 * lbs 존 매핑  수정
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/lbsZone/mapping"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult modifyLbsZoneMapping(
			HttpServletRequest req, HttpServletResponse res
			, @RequestBody TenantMapping tenantmapping 
	) throws Exception {
		
 		SimpleResult result = new SimpleResult();
		
		result = acpn001Service.modifyLbsZoneMapping(tenantmapping);
		
		 
 		return result;
	}
	
	
	/**
	 * lbs 존 전체 매핑
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/lbsZone/allMapping"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult allMapping(
			HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		
 		SimpleResult result = new SimpleResult();
		
		result = acpn001Service.allMapping();
		
		 
 		return result;
	}
	
	
	/**
	 * lbs 편의시설 존 매핑 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/faci/mappings"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
		)
	public Map<Object, Object> faciZoneMappings( 
			HttpServletRequest req, HttpServletResponse res
			, @RequestParam(value="bcn_cd" ) String bcn_cd
			, @RequestParam(value="tnt_nm_ko") String tnt_nm_ko
			, @RequestParam(value="tenant_type") String tenant_type
			, @RequestParam(value="mapping_type") String mapping_type
		) throws Exception {
		 
		this.initHandler(req, res);
		TenantMapping tenantmapping = new TenantMapping();
		tenantmapping.bcn_cd = bcn_cd;
		tenantmapping.tnt_nm_ko = tnt_nm_ko;
		tenantmapping.tenant_type = tenant_type;
		tenantmapping.mapping_type = mapping_type;
    	Map<Object, Object> tenantlist  = acpn001Service.faciZoneMappings(tenantmapping);
    	
		return tenantlist;
	}
	
	
	/**
	 * lbs 편의시설 존 매핑  수정
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/faci/mapping"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult modifyFaciZoneMapping(
			HttpServletRequest req, HttpServletResponse res
			, @RequestBody TenantMapping tenantmapping 
	) throws Exception {
		
 		SimpleResult result = new SimpleResult();
		
		result = acpn001Service.modifyFaciZoneMapping(tenantmapping);
		
		 
 		return result;
	}
	
	/**
	 * 쿠폰 다운로드 내역 엑셀 다운
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/cpDownsExcel", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/vnd.ms-excel") 
	public ModelAndView  empListPage(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		
		this.initHandler(req, res);
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "test" + ".xls\"");
		
		ACPN005 acpn005 = new ACPN005();
		acpn005.limit = -1;
		ACPN005[] dwonCplist  = acpn001Service.getExDownCoupons(acpn005);
		
		Map<String, Object> dataList = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = {"no"};
		String[] colNameList = {"no"};
		
		dataList.put("sheetName", "샘플엑셀시트");
		dataList.put("titleList", arrTitle);
		dataList.put("colNameList", colNameList);
		dataList.put("dataList", dwonCplist);
		
		ModelAndView mav = new ModelAndView("testExcelView", "data", dataList);
		
		return mav;
	}
	
	/**
	 * 쿠폰 엑셀 업로드
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/excel/coupon"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8")
	@ResponseBody
    public SimpleResult excelUploadCoupon(
    		HttpServletRequest req, HttpServletResponse res, HttpSession session
    		, @RequestParam("file") MultipartFile file) throws BaseException, Exception {
		
		SimpleResult result = acpn001Service.excelUploadCoupon(file);
			
		return result;
	}
}
