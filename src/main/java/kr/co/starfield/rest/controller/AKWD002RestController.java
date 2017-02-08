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
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.RecommendTag;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SKWD002Vo;
import kr.co.starfield.service.AKWD002Service;

/**
 *  AKWD002(RecommendTag) REST 컨트롤러 클래스
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
public class AKWD002RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(AKWD002RestController.class);
	
	@Autowired
	AKWD002Service akwd002Service;
	
	/**
	 * RecommendTag 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcn_cd}/recommend/tags"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regRecommendTag(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody RecommendTag model ) throws Exception {
		
		this.initHandler(req, res);
		
		model.bcn_cd = bcn_cd;
		
		//TODO reg_usr 로그인 완료시 세션 연동
		model.reg_usr = "eezy";
		
		ll.info("regRecommendTags bcn_cd : {}, recommendTag : {}", new Object[] {bcn_cd, model});

		SimpleResult result = akwd002Service.regRecommendTag(model.toVo());
		
		return result;
	}
	
	/**
	 * RecommendTag 목록 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/recommend/tags"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<RecommendTag> getRecommendTagList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset") int offset
			, @RequestParam(value="limit") int limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getRecommendTagList q : {},  bcn_cd : {}, offset : {}, limit : {}", new Object[] {q, bcn_cd, offset, limit});
		
		SKWD002Vo vo = new SKWD002Vo();
		vo.bcn_cd = bcn_cd;
		vo.offset = offset;
		vo.limit = limit;
		vo.sts = StatusCode.Common.USE.getCode();
		
		Utils.Str.qParser(vo, q);
		
		ListResult<RecommendTag> modelList = akwd002Service.getRecommendTagList(vo);
		
		return modelList;
	}
	
	/**
	 * RecommendTag 상세
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/recommend/tags/{recm_tag_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public RecommendTag getRecommendTag(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="recm_tag_seq") String recm_tag_seq) throws Exception {
		
		ll.info("getRecommendTag bcn_cd : {}, recm_tag_seq : {}", new Object[] {bcn_cd, recm_tag_seq});
		SKWD002Vo vo = new SKWD002Vo();
		vo.bcn_cd = bcn_cd;
		vo.recm_tag_seq = recm_tag_seq;
		vo.sts = StatusCode.Common.USE.getCode();
		
		RecommendTag model = akwd002Service.getRecommendTag(vo);
	
		return model;
	}
	
	/**
	 * RecommendTag 수정
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/recommend/tags/{recm_tag_seq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyRecommendTag(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestBody RecommendTag model
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="recm_tag_seq") String recm_tag_seq) throws Exception {
		
		ll.info("modifyRecommendTag recommendTag : {}, bcn_cd : {}, recm_tag_seq : {}", new Object[] {model, bcn_cd, recm_tag_seq});
		
		model.bcn_cd = bcn_cd;
		model.recm_tag_seq = recm_tag_seq;
		
		//TODO mod_usr 로그인 완료시 세션 연동
		model.mod_usr = "eezy";
		
		SimpleResult result = akwd002Service.modifyRecommendTag(model.toVo());
				
		return result;
	}
	
	/**
	 * RecommendTag 삭제
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/recommend/tags/{recm_tag_seq}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteRecommendTag(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="recm_tag_seq") String recm_tag_seq) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info("deleteRecommendTag bcn_cd : {}, recm_tag_seq : {}", new Object[] {bcn_cd, recm_tag_seq});
		
		SKWD002Vo vo = new SKWD002Vo();
		vo.bcn_cd = bcn_cd;
		vo.recm_tag_seq = recm_tag_seq;
		
		//TODO mod_usr 로그인 완료시 세션 연동
		vo.mod_usr = "eezy";
		
		SimpleResult result = akwd002Service.deleteRecommendTag(vo);
		
		return result;
	}
}
