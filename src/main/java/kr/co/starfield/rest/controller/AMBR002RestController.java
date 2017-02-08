package kr.co.starfield.rest.controller;

import java.util.ArrayList;

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
import kr.co.starfield.model.LikeEvent;
import kr.co.starfield.model.LikeTenant;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SMBR002Vo;
import kr.co.starfield.model.vo.SMBR003Vo;
import kr.co.starfield.service.AMBR002Service;

/**
 *  관심 이벤트 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author eezy
 * @version 1.0,
 * @see
 * @date 2016.07.20
 */

@RestController
@RequestMapping("/rest")
public class AMBR002RestController extends BaseController {

private Logger ll = LoggerFactory.getLogger(ACTG001RestController.class);
	
	@Autowired
	AMBR002Service ambr002Service;
	
	
	/**
	 * 관심 이벤트 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcn_cd}/members/{mbr_seq}/likes/events"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"	// 생산 가능한 미디어 타입으로 매핑 제한. 요청 헤더가 일치 할 때만 요청 매
			, headers = "content-type=application/json" // 요청 헤더의 파라미터 값 일치로 매핑 제한.
		)
	public SimpleResult regLikeEvent(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbr_seq") String mbr_seq
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody LikeEvent likeEvent ) throws Exception {
		
		this.initHandler(req, res);
		
		likeEvent.mbr_seq = mbr_seq;
		
		SMBR002Vo vo = likeEvent.toVo();
		
		vo.sts = StatusCode.Common.USE.getCode();
		
		SimpleResult result = ambr002Service.regLikeEvent(vo);
		
		return result;
	}
	
	
	/**
	 * 관심 이벤트 목록 조회
	 * @return FavoriteTenant List 
	 */
	@RequestMapping(value = "/{bcn_cd}/members/{mbr_seq}/likes/events"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<LikeEvent> getLikeEventList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbr_seq") String mbr_seq
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
	) throws Exception {
		
		this.initHandler(req, res);
			
		SMBR002Vo vo = new SMBR002Vo();
		vo.offset = offset;
		vo.limit = limit;
		
		vo.mbr_seq = mbr_seq;
		vo.sts = StatusCode.Common.USE.getCode();

		
		ListResult<LikeEvent> likeEventList = ambr002Service.getLikeEventList(vo);
		
		return likeEventList;
	}

	/**
	 * 관심 이벤트 상세 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/members/{mbr_seq}/likes/events/{evt_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult getLikeEvent(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbr_seq") String mbr_seq
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="evt_seq") String evt_seq
	) throws Exception {
		
		this.initHandler(req, res);
	
		
		SMBR002Vo vo = new SMBR002Vo();
		vo.evt_seq = evt_seq;
		vo.mbr_seq = mbr_seq;
		vo.sts = StatusCode.Common.USE.getCode();
		
		return ambr002Service.getLikeEvent(vo);
	}
	
	/**
	 * 관심 이벤 삭제
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/members/{mbr_seq}/likes/events/{evt_seq}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteLikeEvent(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbr_seq") String mbr_seq
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="evt_seq") String evt_seq
	) throws Exception {
		
		this.initHandler(req, res);
	
		
		SMBR002Vo vo = new SMBR002Vo();
		
		vo.evt_seq = evt_seq;
		vo.mbr_seq = mbr_seq;
		SimpleResult result = ambr002Service.deleteLikeEvent(vo);
		
		return result;
	}
	
	
	
	
	
	
	/**
	 * 관심 테넌트 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcn_cd}/members/{mbr_seq}/likes/tenants"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"	// 생산 가능한 미디어 타입으로 매핑 제한. 요청 헤더가 일치 할 때만 요청 매
			, headers = "content-type=application/json" // 요청 헤더의 파라미터 값 일치로 매핑 제한.
		)
	public SimpleResult regLikeTenant(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbr_seq") String mbr_seq
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody LikeTenant likeTenant ) throws Exception {
		
		this.initHandler(req, res);
		
		likeTenant.mbr_seq = mbr_seq;
		likeTenant.bcn_cd = bcn_cd;
		
		SMBR003Vo vo = likeTenant.toVo();
		
		vo.sts = StatusCode.Common.USE.getCode();
		
		SimpleResult result = ambr002Service.regLikeTenant(vo);
		
		return result;
	}
	
	/**
	 * 관심 테넌트 리스트 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcn_cd}/members/{mbr_seq}/likes/tenants/list"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"	// 생산 가능한 미디어 타입으로 매핑 제한. 요청 헤더가 일치 할 때만 요청 매
			, headers = "content-type=application/json" // 요청 헤더의 파라미터 값 일치로 매핑 제한.
		)
	public SimpleResult regLikeTenantList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbr_seq") String mbr_seq
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody ArrayList<LikeTenant> likeTenantList ) throws Exception {
		
		this.initHandler(req, res);
		
		ArrayList<SMBR003Vo> voList = new ArrayList<>();
		
		for(LikeTenant likeTenant : likeTenantList) {
			likeTenant.mbr_seq = mbr_seq;
			likeTenant.bcn_cd = bcn_cd;
			SMBR003Vo vo = likeTenant.toVo();
			vo.sts = StatusCode.Common.USE.getCode();
			voList.add(vo);
		}
		
		SimpleResult result = ambr002Service.regLikeTenantList(voList);
		
		return result;
	}
	
	
	/**
	 * 관심 테넌트 목록 조회
	 * @return FavoriteTenant List 
	 */
	@RequestMapping(value = "/{bcn_cd}/members/{mbr_seq}/likes/tenants"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<LikeTenant> getLikeTenantList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbr_seq") String mbr_seq
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
	) throws Exception {
		
		this.initHandler(req, res);
			
		SMBR003Vo vo = new SMBR003Vo();
		vo.offset = offset;
		vo.limit = limit;
		vo.bcn_cd = bcn_cd;
		vo.mbr_seq = mbr_seq;
		vo.sts = StatusCode.Common.USE.getCode();

		
		ListResult<LikeTenant> likeTenantList = ambr002Service.getLikeTenantList(vo);
		
		return likeTenantList;
	}

	/**
	 * 관심 테넌트 상세 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/members/{mbr_seq}/likes/tenants/{tnt_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult getLikeTenant(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbr_seq") String mbr_seq
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="tnt_seq") String tnt_seq
	) throws Exception {
		
		this.initHandler(req, res);
	
		
		SMBR003Vo vo = new SMBR003Vo();
		vo.tnt_seq = tnt_seq;
		vo.mbr_seq = mbr_seq;
		vo.bcn_cd = bcn_cd;
		vo.sts = StatusCode.Common.USE.getCode();
		
		return ambr002Service.getLikeTenant(vo);
	}
	
	/**
	 * 관심 테넌트 삭제
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/members/{mbr_seq}/likes/tenants/{tnt_seq}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteLikeTenant(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbr_seq") String mbr_seq
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="tnt_seq") String tnt_seq
	) throws Exception {
		
		this.initHandler(req, res);
	
		
		SMBR003Vo vo = new SMBR003Vo();
		
		vo.tnt_seq = tnt_seq;
		vo.mbr_seq = mbr_seq;
		vo.bcn_cd = bcn_cd;
		SimpleResult result = ambr002Service.deleteLikeTenant(vo);
		
		return result;
	}
	
	
}

