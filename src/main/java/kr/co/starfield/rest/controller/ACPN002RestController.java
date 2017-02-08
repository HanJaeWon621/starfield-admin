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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.ErrorLogger;
import kr.co.starfield.model.ACPN004;
import kr.co.starfield.model.ACPN006;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SCPN003;
import kr.co.starfield.model.SCPN004;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SCPN001Vo;
import kr.co.starfield.model.vo.SCPN002_D2Vo;
import kr.co.starfield.model.vo.STNT001Vo;
import kr.co.starfield.service.ACPN002Service;

/**
 * 쿠폰 rest api
 *
 *
 * @author dhlee
 * @version	1.0,
 * @see
 * @date 2016.08.01
 */

@RestController
@RequestMapping("/rest")  
public class ACPN002RestController extends BaseController {

	@Autowired
	ACPN002Service acpn002Service;
	@Autowired
	ErrorLogger errorLogger;
	
	/*@Value("${file.img.host}")
	String imgHost;
	@Value("${file.barcodeimg.path}")
	String imgPath;
	@Value("${cdn.barcodeimg.path}")
	String cndImgPath;*/
	
	String imgHost;
	String imgPath;
	String cndImgPath;
	
	private static final Logger ll = LoggerFactory.getLogger(ACPN002RestController.class);
	
	/**
	 * 테넌트 쿠폰 api 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/tenants/{tntSeq}/coupons"
		, method = RequestMethod.GET
		, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
	)
	public List<ACPN004> getCoupons(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable(value="tntSeq") String tntSeq
	) throws Exception {
		
		this.initHandler(req, res);
    
		List<ACPN004> cplist = new ArrayList<ACPN004>(); 	
		try {
			cplist = acpn002Service.getCoupons(tntSeq);

		} catch(Exception e) {
			errorLogger.log(ErrorCode.Severity.LEVEL_1, ErrorCode.Coupon.COUPON_DATA_FAILED.getCode(), 
					this.getClass().getName(), "getCoupons",  
					"coupon list", e);
		
		}
    	
		return cplist;
	}
	
	
	/**
	 * 쿠폰 1차 사용처리 (사용안함)
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/coupon/use/{use_type}/{cp_seq}"
		, method = RequestMethod.PUT
		, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
	)
	public SimpleResult cpUseProcess(
			HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="cust_id", required=false, defaultValue = "null") String cust_id,
			@PathVariable(value="cp_seq") String cp_seq,
			@PathVariable(value="use_type") String use_type
	) throws Exception {
		
		this.initHandler(req, res); 
    	
		SCPN002_D2Vo vo = new SCPN002_D2Vo();
		if(!cust_id.equals("null")){
			vo.cust_id = cust_id;
		}
		vo.cp_seq = cp_seq;
		
		SimpleResult result = new SimpleResult();
		try {
			result = acpn002Service.cpUseProcess(vo,use_type);
		
		} catch(Exception e) {
		/*	errorLogger.log(ErrorCode.Severity.LEVEL_1, ErrorCode.Coupon.COUPON_USE_FAILED.getCode(), 
					this.getClass().getName(), "cpUseProcess",  
					"coupon use", e);*/
			
		}
    	
		return result;
	}
	
	/**
	 * 쿠폰 리스트(브랜드) 조회 (사용안함)
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/coupon/brand"
		, method = RequestMethod.GET
		, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
	)
	public ListResult<SCPN003> getBrandCoupons(
			HttpServletRequest req, HttpServletResponse res
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit") String limit
	) throws Exception {
		
		this.initHandler(req, res);

		SCPN001Vo vo = new SCPN001Vo();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
    
    	
		ListResult<SCPN003> cplist  = acpn002Service.getBrandCpuons(vo);
		return cplist;
	}
	
	/**
	 * 일반 쿠폰 리스트 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/coupon/nomal"
		, method = RequestMethod.GET
		, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
	)
	public ListResult<SCPN003> getNmCoupons(
			HttpServletRequest req, HttpServletResponse res
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="cp_kind_cd" , required=false, defaultValue = "") String cp_kind_cd
			/*, @RequestParam(value="cust_id" , required=false, defaultValue = "") String cust_id*/
	) throws Exception {
		
		this.initHandler(req, res);

		SCPN003 param = new SCPN003();
		param.offset = offset;
		param.limit = limit;
		param.cp_kind_cd = cp_kind_cd;
		/*if(StringUtils.isEmpty(cust_id)){
			param.cust_id = cust_id;
		}*/
    	
		ListResult<SCPN003> cplist = new ListResult<SCPN003>();
		try {
			cplist = acpn002Service.getNmCpuons(param);
		
		} catch(Exception e) {
			
		}
		
		return cplist;
	}
	
	
	/**
	 * 보유 쿠폰 리스트 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/coupon/my/{cust_id}"
		, method = RequestMethod.GET
		, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
	)
	public ListResult<SCPN003> getMyCpuons(
			HttpServletRequest req, HttpServletResponse res
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @PathVariable(value="cust_id") String cust_id
	) throws Exception {
		
		this.initHandler(req, res);

		SCPN002_D2Vo vo = new SCPN002_D2Vo();
		vo.offset = offset;
		vo.limit = limit;
		vo.cust_id = cust_id;
		
		ListResult<SCPN003> cplist = new ListResult<SCPN003>();
		try {
			cplist = acpn002Service.getMyCpuons(vo);
		
		} catch(Exception e) {
			/*errorLogger.log(ErrorCode.Severity.LEVEL_1, ErrorCode.Coupon.MY_COUPON_DATA_FAILED.getCode(), 
					this.getClass().getName(), "getMyCpuons",  
					"my coupon list", e);*/
			
		}
		
		return cplist;
	}
	
	/**
	 * 쿠폰 발급 //담기
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/coupon/my/{cust_id}/{cp_seq}"
		, method = RequestMethod.POST
		, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
	)
	public SimpleResult issuMbCoupon(
			HttpServletRequest req, HttpServletResponse res, 
			@PathVariable(value="cust_id") String cust_id,
			@PathVariable(value="cp_seq") String cp_seq,
			@PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		
		this.initHandler(req, res); 
		SimpleResult result =  new SimpleResult();
		try {
			//System.out.println("쿠폰발급>>"+ bcn_cd +"," + cust_id +","+ cp_seq);
			result = acpn002Service.issuCouponNew(bcn_cd,cust_id, cp_seq);
		
		} catch(Exception e) {
			//System.out.println("쿠폰발급에러>>");
			/*errorLogger.log(ErrorCode.Severity.LEVEL_1, ErrorCode.Coupon.COUPON_ISSU_FAILED.getCode(), 
					this.getClass().getName(), "issuMbCoupon",  
					"coupon issu error", e);*/
			
		}
    	
		return result;
	}
	
	
	/**
	 * 보유쿠폰 삭제 
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/coupon/my/{cust_id}/{cp_seq}"
		, method = RequestMethod.DELETE
		, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
	)
	public SimpleResult myCpDelete(
			HttpServletRequest req, HttpServletResponse res, 
			@PathVariable(value="cust_id") String cust_id,
			@PathVariable(value="cp_seq") String cp_seq
	) throws Exception {
		
		this.initHandler(req, res); 
    	
		SCPN002_D2Vo vo = new SCPN002_D2Vo();
		vo.cp_seq = cp_seq;
		vo.cust_id = cust_id;
		
		SimpleResult result =  new SimpleResult();
		try {
			result = acpn002Service.myCpDelete(vo);
		
		} catch(Exception e) {
			errorLogger.log(ErrorCode.Severity.LEVEL_1, ErrorCode.Coupon.COUPON_DATA_DELETE_FAILED.getCode(), 
					this.getClass().getName(), "myCpDelete",  
					"coupon issu error", e);
			
		}
		
    	
		return result;
	}
	
	/**
	 * 쿠폰 상세 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/coupon/{cust_id}/{cp_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SCPN004 getCouponDetail(
			HttpServletRequest req, HttpServletResponse res, HttpSession session, 
			@PathVariable(value="cust_id") String cust_id,
			@PathVariable(value="cp_seq") String cp_seq
	) throws Exception {
		
		SCPN002_D2Vo vo = new SCPN002_D2Vo();
		vo.cp_seq = cp_seq;
		vo.cust_id = cust_id;
		
		SCPN004 getcoupon = new SCPN004();
		//try {
		getcoupon = acpn002Service.getCouponDetail(vo);
		
	/*	} catch(Exception e) {
			errorLogger.log(ErrorCode.Severity.LEVEL_1, ErrorCode.Coupon.COUPON_DETAIL_DATA_FAILED.getCode(), 
					this.getClass().getName(), "getCouponDetail",  
					"coupon detail error", e);
			
		}*/
		
		return getcoupon;
	}
	
	/**
	 * 쿠폰이 있는 테넌트  
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/tenants/useCoupons"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
		)
	public List<ACPN006> getTenantUseCp( 
			HttpServletRequest req, HttpServletResponse res 
		) throws Exception {
		
		this.initHandler(req, res);
		List<ACPN006> cplist = new ArrayList<ACPN006>();
		
		try {
			cplist = acpn002Service.getTenantUseCp();
		
		} catch(Exception e) {
			errorLogger.log(ErrorCode.Severity.LEVEL_1, ErrorCode.Coupon.COUPON_TENANT_DATA_FAILED.getCode(), 
					this.getClass().getName(), "getTenantUseCp",  
					"tenant coupons error", e);
			
		}
    	
		return cplist;
	}
	
	/**
	 * 테넌트 쿠폰 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/coupon/tenants/{tnt_seq}"
		, method = RequestMethod.GET
		, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
	)
	public ListResult<SCPN003> getCpTenants(
			HttpServletRequest req, HttpServletResponse res
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @PathVariable(value="tnt_seq") String tnt_seq
	) throws Exception {
		
		this.initHandler(req, res);
		STNT001Vo vo = new STNT001Vo();
		vo.offset = offset;
		vo.limit = limit;
		vo.tnt_cd = tnt_seq;

		ListResult<SCPN003> cplist = new ListResult<SCPN003>();
		try {
			cplist = acpn002Service.getCpTenants(vo);
		
		} catch(Exception e) {
			errorLogger.log(ErrorCode.Severity.LEVEL_1, ErrorCode.Coupon.TENANT_COUPON_DATA_FAILED.getCode(), 
					this.getClass().getName(), "getCpTenants",  
					"coupon tenants error", e);
			
		}
		
		return cplist;
	}
	
	
	/**
	 * 사용한 쿠폰 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/coupon/used/{cust_id}"
		, method = RequestMethod.GET
		, produces = "application/json; charset=utf-8" 
		, headers = "content-type=application/json" 
	)
	public ListResult<SCPN003> usedMyCpuons(
			HttpServletRequest req, HttpServletResponse res
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @PathVariable(value="cust_id") String cust_id
	) throws Exception {
		
		this.initHandler(req, res);

		SCPN002_D2Vo vo = new SCPN002_D2Vo();
		vo.offset = offset;
		vo.limit = limit;
		vo.cust_id = cust_id;
		
		ListResult<SCPN003> cplist = new ListResult<SCPN003>();
		try {
			cplist = acpn002Service.usedMyCpuons(vo);
		
		} catch(Exception e) {
			errorLogger.log(ErrorCode.Severity.LEVEL_1, ErrorCode.Coupon.COUPON_USED_DATA_FAILED.getCode(), 
					this.getClass().getName(), "usedMyCpuons",  
					"coupon used", e);
			
		}
		
		return cplist;
	}
	
	/**
	 * 쿠폰 테넌트  
	 * @return
	 *//*
	@RequestMapping(value = "/{bcn_cd}/coupon/tenants/{cp_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json" 
		)
	public ListResult<ACPN006> cpTenants( 
			HttpServletRequest req, HttpServletResponse res
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @PathVariable(value="cp_seq") String cp_seq
		) throws Exception {
		
		this.initHandler(req, res);
		SCPN001Vo vo = new SCPN001Vo();
		vo.offset = offset;
		vo.limit = limit;
		vo.cp_seq = cp_seq;
		ListResult<ACPN006> cplist  = acpn002Service.cpTenants(vo);
    	
		return cplist;
	}*/
	
	/**
	 * 쿠폰 사용 여부
	 * @return
	 */ 
	@RequestMapping(value = "/{bcn_cd}/coupon/useCk/{cust_id}/{cp_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	/*@ResponseBody*/
	public SimpleResult getUpUseCk(
			HttpServletRequest req, HttpServletResponse res, HttpSession session, 
			@PathVariable(value="cust_id") String cust_id,
			@PathVariable(value="cp_seq") String cp_seq
	) throws Exception {
		
		SCPN002_D2Vo vo = new SCPN002_D2Vo();
		vo.cp_seq = cp_seq;
		vo.cust_id = cust_id;
		
		SimpleResult result = new SimpleResult();
		try {
			result = acpn002Service.getCpUseCk(vo);
		
		} catch(Exception e) {
			errorLogger.log(ErrorCode.Severity.LEVEL_1, ErrorCode.Coupon.COUPON_USED_CK_DATA_FAILED.getCode(), 
					this.getClass().getName(), "getUpUseCk",  
					"coupon used check", e);
			
		}
		
		return result;
	}
}
