package kr.co.starfield.rest.controller;

import java.util.Arrays;

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
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.model.Banner;
import kr.co.starfield.model.BannerDelete;
import kr.co.starfield.model.BannerFilter;
import kr.co.starfield.model.BannerGroup;
import kr.co.starfield.model.BannerGroupDelete;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACMS010Service;

/**
 *  ACMS010(Banner) REST 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author eezy
 * @version 1.0,
 * @see
 * @date 2016.09.07
 */
@RestController
@RequestMapping("/admin/rest")
public class ACMS010RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(ACMS010RestController.class);
	
	@Autowired
	ACMS010Service acms010Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * 배너 그룹 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/bannerGroups"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<BannerGroup> getBannerGroupList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="sort_name", required=false, defaultValue = "rnum") String sort_name
			, @RequestParam(value="sort_order", required=false, defaultValue = "desc") String sort_order
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getBannerGroupList bcn_cd : {}", new Object[] {bcn_cd});
		
		BannerFilter filter = new BannerFilter();
		
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		filter.sort_name = sort_name;
		filter.sort_order = sort_order;
		
		ListResult<BannerGroup> bannerGroupList = acms010Service.getBannerGroupList(filter);
		
		aact001service.regAdminAction(req, session, "상단 히어로 배너 그룹 조회", null);
		
		return bannerGroupList;
	}
	
	/**
	 * 배너 그룹 등록
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/bannerGroups"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regBannerGroup(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody BannerGroup bannerGroup
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("creageBannerGroup bcn_cd : {}", new Object[] {bcn_cd});
		
		bannerGroup.bcn_cd = bcn_cd;
		bannerGroup.sts = StatusCode.Common.USE.getCode();
		
		bannerGroup.reg_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult simpleResult = acms010Service.regBannerGroup(bannerGroup);
		
		aact001service.regAdminAction(req, session, "상단 히어로 배너 그룹 등록", null);
		
		return simpleResult;
	}
	
	/**
	 * 배너 그룹 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/bannerGroups/{bn_group_seq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyBannerGroup(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="bn_group_seq") String bn_group_seq
			, @RequestBody BannerGroup bannerGroup
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("modifyBannerGroup bcn_cd : {}", new Object[] {bcn_cd});
		
		bannerGroup.bn_group_seq = bn_group_seq;
		bannerGroup.bcn_cd = bcn_cd;
		bannerGroup.sts = StatusCode.Common.USE.getCode();
		
		bannerGroup.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult simpleResult = acms010Service.modifyBannerGroup(bannerGroup);
		
		aact001service.regAdminAction(req, session, "상단 히어로 배너 그룹 수정", null);
		
		return simpleResult;
	}
	
	/**
	 * 배너 그룹 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/bannerGroups/{bn_group_seq}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteBannerGroup(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="bn_group_seq") String[] bn_group_seq_list
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("deleteBannerGroup bn_group_seq_list {}", Arrays.asList(bn_group_seq_list));
		
		if(bn_group_seq_list.length < 1){
			BaseException be = new BaseException(ErrorCode.Contents.BANNER_GROUP_SELECTED_SIZE_ERROR);
			throw be;
		}
		
		BannerGroupDelete bannerGroupDelete = new BannerGroupDelete();
		bannerGroupDelete.bcn_cd = bcn_cd;
		bannerGroupDelete.bn_group_seq_list = bn_group_seq_list;
		bannerGroupDelete.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult simpleResult = acms010Service.deleteBannerGroup(bannerGroupDelete);
		
		aact001service.regAdminAction(req, session, "상단 히어로 배너 그룹 삭제", null);
		
		return simpleResult;
	}
	
	/**
	 * 배너 그룹 상세 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/bannerGroups/{bn_group_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public BannerGroup getBannerGroupList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="bn_group_seq") String bn_group_seq
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getBannerGroupList bcn_cd : {}, bn_group_seq : {}", new Object[] {bcn_cd, bn_group_seq});
		
		BannerFilter filter = new BannerFilter();
		
		filter.bcn_cd = bcn_cd;
		filter.bn_group_seq = bn_group_seq;
		
		BannerGroup bannerGroup = acms010Service.getBannerGroup(filter);
		
		aact001service.regAdminAction(req, session, "상단 히어로 배너 목록 조회", null);
		
		return bannerGroup;
	}
	
	/**
	 * 배너 등록
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/bannerGroups/{bn_group_seq}"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regBanner(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="bn_group_seq") String bn_group_seq
			, @RequestBody Banner banner
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("creageBanner bcn_cd : {}, bn_group_seq : {}", new Object[] {bcn_cd, bn_group_seq});
		
		banner.bn_group_seq = bn_group_seq;
		banner.sts = StatusCode.Common.USE.getCode();
		banner.reg_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult simpleResult = acms010Service.regBanner(banner);
		
		aact001service.regAdminAction(req, session, "상단 히어로 배너 등록", null);
		
		return simpleResult;
	}
	
	/**
	 * 배너 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/bannerGroups/{bn_group_seq}/{bn_seq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyBanner(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="bn_group_seq") String bn_group_seq
			, @PathVariable(value="bn_seq") String bn_seq
			, @RequestBody Banner banner
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("modifyBanner bcn_cd : {}, bn_group_seq : {}, bn_seq : {}", new Object[] {bcn_cd, bn_group_seq, bn_seq});
		
		banner.bn_seq = bn_seq;
		banner.bn_group_seq = bn_group_seq;
		banner.sts = StatusCode.Common.USE.getCode();
		banner.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult simpleResult = acms010Service.modifyBanner(banner);
		
		aact001service.regAdminAction(req, session, "상단 히어로 배너 수정", null);
		
		return simpleResult;
	}
	
	/**
	 * 배너 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/bannerGroups/{bn_group_seq}/{bn_seq}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteBanner(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="bn_group_seq") String bn_group_seq
			, @PathVariable(value="bn_seq") String[] bn_seq_list
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("deleteBanner bcn_cd : {}, bn_group_seq : {}, bn_seq : {}", new Object[] {bcn_cd, bn_group_seq, bn_seq_list});
		
		if(bn_seq_list.length < 1){
			BaseException be = new BaseException(ErrorCode.Contents.BANNER_SELECTED_SIZE_ERROR);
			throw be;
		}
		
		BannerDelete bannerDelete = new BannerDelete();
		
		bannerDelete.bn_group_seq = bn_group_seq;
		bannerDelete.bn_seq_list = bn_seq_list;
		bannerDelete.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult simpleResult = acms010Service.deleteBanner(bannerDelete);
		
		aact001service.regAdminAction(req, session, "상단 히어로 배너 삭제", null);
		
		return simpleResult;
	}
	
	
	/**
	 * 배너그룹 redis
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield"})
	@RequestMapping(value = "/{bcn_cd}/bannerGroups/redis"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult redisBannerGroup(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("redisBannerGroup bcn_cd : {}", new Object[] {bcn_cd});
		
		BannerFilter filter = new BannerFilter();
		
		filter.bcn_cd = bcn_cd;
		
		SimpleResult simpleResult = acms010Service.redisBanner(filter);
		
		aact001service.regAdminAction(req, session, "배너 동기화", null);
		
		return simpleResult;
	}
}
