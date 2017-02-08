/*
 * HomeRestController.java	1.00 2016/04/04
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.model.BannerFilter;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACMS001Service;
import kr.co.starfield.service.ACMS002Service;
import kr.co.starfield.service.ACMS003Service;
import kr.co.starfield.service.ACMS006Service;
import kr.co.starfield.service.ACMS010Service;
import kr.co.starfield.service.AFCT001Service;
import kr.co.starfield.service.AOPR001Service;
import kr.co.starfield.service.AOPR002Service;
import kr.co.starfield.service.AOPR003Service;
import kr.co.starfield.service.AOPR004Service;
import kr.co.starfield.service.ATNT001Service;

/**
 *  레디스 동기화 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author yhkim
 * @version 1.0,
 * @see
 * @date 2016.06.10
 */

@RestController
@RequestMapping("/sync/redis")
public class RSYNC001RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(RSYNC001RestController.class);
	
	@Autowired
	ATNT001Service atnt001Service;
	
	@Autowired
	AFCT001Service afct001Service;
	
	@Autowired
	ACMS001Service acms001Service;
	
	@Autowired
	ACMS003Service acms003Service;
	
	@Autowired
	ACMS002Service acms004Service;
	
	@Autowired
	ACMS006Service acms006Service;
	
	@Autowired
	AOPR001Service aopr001Service;
	
	@Autowired
	AOPR002Service aopr002Service;
	
	@Autowired
	AOPR003Service aopr003Service;
	
	@Autowired
	AOPR004Service aopr004Service;
	
	@Autowired
	ACMS010Service acms010Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * 테넌트 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/tenants"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"	// 생산 가능한 미디어 타입으로 매핑 제한. 요청 헤더가 일치 할 때만 요청 매
			, headers = "content-type=application/json" // 요청 헤더의 파라미터 값 일치로 매핑 제한.
		)
	public SimpleResult syncTenant(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd) throws Exception {

		return atnt001Service.syncRedisTenantSuite();
	}
	
	/**
	 * 편의시설 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/facility"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"	// 생산 가능한 미디어 타입으로 매핑 제한. 요청 헤더가 일치 할 때만 요청 매
			, headers = "content-type=application/json" // 요청 헤더의 파라미터 값 일치로 매핑 제한.
		)
	public SimpleResult syncFacility(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd) throws Exception {

		return afct001Service.syncRedisFacilitySuite();
	}
	
	/**
	 * 블로그 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/blogs"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult syncBlog(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd) throws Exception {

		return acms001Service.syncBlog(bcnCd);
	}
	
	/**
	 * 추천테마 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/themes"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult syncTheme(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd) throws Exception {

		return acms001Service.syncTheme(bcnCd);
	}
	
	/**
	 * 뉴스 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/news"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult syncNews(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd) throws Exception {

		return acms003Service.syncNews(bcnCd);
	}
	
	
	/**
	 * FAQ 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/faqs"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult syncFaq(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd) throws Exception {

		return acms006Service.syncFAQ(bcnCd);
	}
	
	/**
	 * 인스타그램 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/instagrams"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult syncInstagrams(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd) throws Exception {

		return acms004Service.SyncRun(bcnCd);
	}
	
	/**
	 * 운영정보 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/operations"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult syncOperation(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd) throws Exception {

		return aopr001Service.syncOperation(bcnCd);
	}
	
	/**
	 * 스타필드 휴일정보 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/holidays/bcn"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult syncStarFieldHoliday(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd) throws Exception {

		return aopr003Service.syncStarFieldHoliday(bcnCd);
	}
	
	/**
	 * 테넌트 휴일정보 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/holidays/tnt"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult syncTenantHoliday(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd) throws Exception {

		return aopr004Service.syncTenantHoliday(bcnCd);
	}
	
	
	/**
	 * 메인 정보 redis
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/main"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult redisMain(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("redisBannerGroup bcn_cd : {}", new Object[] {bcn_cd});
		
		BannerFilter filter = new BannerFilter();
		
		filter.bcn_cd = bcn_cd;
		
		return acms010Service.redisBanner(filter);
	}
}
