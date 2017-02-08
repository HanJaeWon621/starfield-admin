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

import kr.co.starfield.common.Utils;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.RecommendKeyword;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SKWD001Vo;
import kr.co.starfield.service.AKWD001Service;

/**
 * 추천 키워드 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author yhkim
 * @version 1.0,
 * @see
 * @date 2016.06.22
 */

@RestController
@RequestMapping("/admin/rest")
public class AKWD001RestController extends BaseController {

	private Logger ll = LoggerFactory.getLogger(AKWD001RestController.class);
	
	@Autowired
	AKWD001Service akwd001Service;
	
	
	/**
	 * 추천 키워드 등록
	 * @return 
	 */
	@RequestMapping(value = "/{bcnCd}/recommends/keywords"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"	// 생산 가능한 미디어 타입으로 매핑 제한. 요청 헤더가 일치 할 때만 요청 매
			, headers = "content-type=application/json" // 요청 헤더의 파라미터 값 일치로 매핑 제한.
		)
	public SimpleResult regRecommendKeyword(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestBody RecommendKeyword recommendKeyword ) throws Exception {
		
		this.initHandler(req, res);
		
		recommendKeyword.bcn_cd = bcnCd;
		
		ll.info("RecommendKeyword : {}", recommendKeyword);
		
		SimpleResult result = akwd001Service.regRecommendKeyword(recommendKeyword.toVo());
		
		return result;
	}
	
	/**
	 * 추천 키워드 조회
	 * @return Category
	 */
	@RequestMapping(value = "/{bcnCd}/recommends/keywords/{recmKeywordSeq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public RecommendKeyword getRecommendKeyword(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="recmKeywordSeq") String recmKeywordSeq
	) throws Exception {
		
		ll.info("RecommendKeyword seq is {}", recmKeywordSeq);
		
		RecommendKeyword recommendKeyword = akwd001Service.getRecommendKeyword(recmKeywordSeq);
		
		return recommendKeyword;
	}
	
	/**
	 * 추천 키워드 목록 조회
	 * @return Category List
	 */
	@RequestMapping(value = "/{bcnCd}/recommends/keywords"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<RecommendKeyword> getRecommendKeywords(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestParam(value="offset") String _offset
			, @RequestParam(value="limit") String _limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("offset is {}", _offset);
		ll.info("limit is {}", _limit);
		ll.info("q is {}", q);
	
		SKWD001Vo vo = new SKWD001Vo();
		vo.offset = Integer.parseInt(_offset);
		vo.limit = Integer.parseInt(_limit);
		vo.bcn_cd = bcnCd;
		
		Utils.Str.qParser(vo, q);
		
		ListResult<RecommendKeyword> recommendKeywords = akwd001Service.getRecommendKeywords(vo);
		
		return recommendKeywords;
	}

	/**
	 * 추천 키워드 수정
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/recommends/keywords/{recmKeywordSeq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult modifyRecommendKeyword(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="recmKeywordSeq") String recmKeywordSeq
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestBody RecommendKeyword recommendKeyword ) throws Exception {
		
		this.initHandler(req, res);
		recommendKeyword.bcn_cd = bcnCd;
		recommendKeyword.recm_keyword_seq = recmKeywordSeq;
		
		ll.info("RecommendKeyword is {}", recommendKeyword);
		
		SimpleResult result = akwd001Service.modifyRecommendKeyword(recommendKeyword.toVo());
				
		return result;
	}

	/**
	 * 추천 키워드 삭제
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/recommends/keywords/{recmKeywordSeq}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteRecommendKeyword(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @PathVariable(value="recmKeywordSeq") String recmKeywordSeq
	) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info("delete target RecommendKeyword seq is {}", recmKeywordSeq);
		
		SimpleResult result = akwd001Service.deleteRecommendKeyword(recmKeywordSeq);
		
		return result;
	}

}
