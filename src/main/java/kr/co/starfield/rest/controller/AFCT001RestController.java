/*
 * NoticeRestController.java	1.00 2016/06/16
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.rest.controller;

import java.util.Arrays;
import java.util.List;

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
import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.FacilityDetail;
import kr.co.starfield.model.FacilityFilter;
import kr.co.starfield.model.FacilityForList;
import kr.co.starfield.model.FacilityGroup;
import kr.co.starfield.model.FacilityGroupFilter;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.AFCT001Service;

/**
 *  AFCT001(facility) REST 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author hhlee
 * @version 1.0,
 * @see
 * @date 2016.06.20
 */
@RestController
@RequestMapping("/admin/rest")
public class AFCT001RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(AFCT001RestController.class);
	
	@Autowired
	AFCT001Service afct001Service;

	@Autowired
    AACT001Service aact001service;
	/**
	 * Facility Group 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/facilities"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<FacilityForList> getFacilityList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset") int offset
			, @RequestParam(value="limit") int limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getFacilityList q : {},  bcn_cd : {}, offset : {}, limit : {}", new Object[] {q, bcn_cd, offset, limit});
		
		FacilityGroupFilter filter = new FacilityGroupFilter();
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		Utils.Str.qParser(filter, q);
		
		ListResult<FacilityForList> facilityList = afct001Service.getFacilityGroupListForAdmin(filter);
		
		aact001service.regAdminAction(req, session, "편의시설 그룹 목록 조회", null);
		return facilityList;
	}
	
	/**
	 * Facility Group 상세 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/facilities/{convFaciSeq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public FacilityGroup getFacilityGroup(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="convFaciSeq") String convFaciSeq
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info(String.format("Request target convFaciSeq is %s", convFaciSeq));
		
		FacilityGroup facliityGroup = afct001Service.getFacilityGroup(convFaciSeq);
		
		aact001service.regAdminAction(req, session, "편의시설 그룹 상세 조회", null);
		return facliityGroup;
	}

	/**
	 * Facility Group 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/facilities"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regFacilityGroup(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestBody FacilityGroup facilityGroup ) throws Exception {
		
		this.initHandler(req, res);

		facilityGroup.bcn_cd = bcnCd;
		facilityGroup.user = (String) session.getAttribute("adm_seq");
		
		ll.info("facilityGroup : {}", facilityGroup);
		
		SimpleResult result = afct001Service.regFacilityGroup(facilityGroup);
		
		aact001service.regAdminAction(req, session, "편의시설 그룹 등록", null);
		return result;
		
	}

	/**
	 * Facility Group 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/facilities/{convFaciSeq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyFacilityGroup(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="convFaciSeq") String convFaciSeq
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestBody FacilityGroup facilityGroup ) throws Exception {
		
		this.initHandler(req, res);
		
		facilityGroup.conv_faci_seq = convFaciSeq;
		facilityGroup.bcn_cd = bcnCd;
		facilityGroup.user = (String) session.getAttribute("adm_seq");
		
		ll.info("Modify Target facilityGroup is {}", facilityGroup);
		
		SimpleResult result = afct001Service.modifyFacilityGroup(facilityGroup);
		
		aact001service.regAdminAction(req, session, "편의시설 그룹 수정", null);
		return result;
	}
	
	/**
	 * Facility Group 많이 수정....
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/facilities"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult multiModifyFacilityGroup(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestBody List<FacilityGroup> facilityGroupList ) throws Exception {
		
		this.initHandler(req, res);

		ll.info("Modify Target facilityGroup is {}", facilityGroupList);
		
		String mod_user = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = afct001Service.multiModifyFacilityGroup(facilityGroupList, mod_user);
		
		aact001service.regAdminAction(req, session, "편의시설 그룹 다수 수정", null);
		return result;
	}

	/**
	 * Facility Group 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/facilities/{convFaciSeqs}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteFacilityGroup(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @PathVariable(value="convFaciSeqs") String[] convFaciSeqs
	) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info("delete target is {}", Arrays.asList(convFaciSeqs));
		
		if(convFaciSeqs.length < 1){
			BaseException be = new BaseException(ErrorCode.Facility.FACILITY_SELECTED_SIZE_ERROR);
			throw be;
		}
		
		CommonDeleteModel faciDelete = new CommonDeleteModel();
		faciDelete.bcn_cd = bcnCd;
		faciDelete.seq_arr = convFaciSeqs;
		faciDelete.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = afct001Service.deleteFacilityGroup(faciDelete);
		
		aact001service.regAdminAction(req, session, "편의시설 그룹 삭제", null);
		return result;
	}
	
	/**
	 * Facility Detail 목록 조회
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/facilities/{conv_faci_seq}/all"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<FacilityDetail> getFacilityDetailList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="conv_faci_seq") String conv_faci_seq
			, @RequestParam(value="offset") int offset
			, @RequestParam(value="limit") int limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getFacilityDetailList q : {},  bcn_cd : {}, conv_faci_seq : {}, offset : {}, limit : {}", new Object[] {q, bcn_cd, conv_faci_seq, offset, limit});
		
		FacilityFilter filter = new FacilityFilter();
		
		filter.conv_faci_seq = conv_faci_seq;
		filter.offset = offset;
		filter.limit = limit;
		Utils.Str.qParser(filter, q);
		
		ListResult<FacilityDetail> facilityDetailList = afct001Service.getFacilityDetailListForAdmin(filter);
		
		aact001service.regAdminAction(req, session, "편의시설 디테일 목록 조회", null);
		return facilityDetailList;
	}
	
	/**
	 * Facility Detail 상세 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/facilities/{convFaciSeq}/{convFaciDtlSeq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public FacilityDetail getFacilityDetail(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="convFaciSeq") String convFaciSeq
			, @PathVariable(value="convFaciDtlSeq") String convFaciDtlSeq
	) throws Exception {
		

		this.initHandler(req, res);
		
		ll.info(String.format("Request target convFaciDtlSeq is %s", convFaciDtlSeq));
		
		FacilityDetail facility = afct001Service.getFacilityDetail(convFaciDtlSeq);
		
		aact001service.regAdminAction(req, session, "편의시설 디테일 상세 조회", null);
		return facility;
	}

	/**
	 * Facility Detail 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/facilities/{convFaciSeq}"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regFacilityDetail(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @PathVariable(value="convFaciSeq") String convFaciSeq
			, @RequestBody FacilityDetail facility ) throws Exception {
		
		this.initHandler(req, res);

		facility.bcn_cd = bcnCd;
		facility.conv_faci_seq = convFaciSeq;
		facility.user = (String) session.getAttribute("adm_seq");
		
		ll.info("facility : {}", facility);
		
		SimpleResult result = afct001Service.regFacilityDetail(facility);
		
		aact001service.regAdminAction(req, session, "편의시설 디테일 등록", null);
		return result;
		
	}

	/**
	 * Facility Detail 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/facilities/{convFaciSeq}/{convFaciDtlSeq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyFacilityDetail(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="convFaciSeq") String convFaciSeq
			, @PathVariable(value="convFaciDtlSeq") String convFaciDtlSeq
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestBody FacilityDetail facilityDetail ) throws Exception {
		
		this.initHandler(req, res);
		
		facilityDetail.bcn_cd = bcnCd;
		facilityDetail.conv_faci_seq = convFaciSeq;
		facilityDetail.conv_faci_dtl_seq = convFaciDtlSeq;
		facilityDetail.user = (String) session.getAttribute("adm_seq");
		
		ll.info("Modify Target FacilityDetail is {}", facilityDetail);
		
		SimpleResult result = afct001Service.modifyFacilityDetail(facilityDetail);
				
		aact001service.regAdminAction(req, session, "편의시설 디테일 수정", null);
		return result;
	}

	/**
	 * Facility Detail 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/facilities/{convFaciSeqs}/{convFaciDtlSeqs}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteFacilityDetail(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @PathVariable(value="convFaciDtlSeqs") String[] convFaciDtlSeqs
	) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info("delete target is {}", Arrays.asList(convFaciDtlSeqs));
		
		if(convFaciDtlSeqs.length < 1){
			BaseException be = new BaseException(ErrorCode.Facility.FACILITY_SELECTED_SIZE_ERROR);
			throw be;
		}
		
		CommonDeleteModel faciDelete = new CommonDeleteModel();
		faciDelete.bcn_cd = bcnCd;
		faciDelete.seq_arr = convFaciDtlSeqs;
		faciDelete.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = afct001Service.deleteFacilityDetail(faciDelete);
		
		aact001service.regAdminAction(req, session, "편의시설 디테일 삭제", null);
		return result;
	}
	
	/**
	 * 편의시설 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/facility/redis"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"	// 생산 가능한 미디어 타입으로 매핑 제한. 요청 헤더가 일치 할 때만 요청 매
			, headers = "content-type=application/json" // 요청 헤더의 파라미터 값 일치로 매핑 제한.
		)
	public SimpleResult syncFacility(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd) throws Exception {

		SimpleResult result = afct001Service.syncRedisFacilitySuite();
		aact001service.regAdminAction(req, session,  "편의시설 레디스 동기화", null);
		return result;
	}
}
