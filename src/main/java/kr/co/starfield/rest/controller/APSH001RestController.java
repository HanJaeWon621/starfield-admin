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
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Push;
import kr.co.starfield.model.PushFilter;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.APSH001Service;

/**
 *  APSH001(push) REST 컨트롤러 클래스
 *
 * @author yhkim
 * @version 1.0,
 * @see
 * @date 2016.10.10
 */
@RestController
@RequestMapping("/admin/rest")
public class APSH001RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(APSH001RestController.class);
	
	@Autowired
	APSH001Service apsh001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * Push 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/pushes"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<Push> getPushList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset") int offset
 			, @RequestParam(value="limit") int limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		
		this.initHandler(req, res);
		
		PushFilter filter = new PushFilter();
		
		Utils.Str.qParser(filter, q);
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		
		ll.info("getPushList PushFilter : {}", filter);

		ListResult<Push> eventList = apsh001Service.getPushList(filter);
		
		aact001service.regAdminAction(req, session, "푸시 목록 조회", null);
		return eventList;
	}
	
	/**
	 * Push 상세
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/pushes/{push_mng_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public Push getPush(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="push_mng_seq") String push_mng_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getPush bcn_cd : {}, push_mng_seq : {}", new Object[] {bcn_cd, push_mng_seq});
		PushFilter filter = new PushFilter();
		filter.bcn_cd = bcn_cd;
		filter.push_mng_seq = push_mng_seq;
		
		Push model = apsh001Service.getPush(filter);
	
		aact001service.regAdminAction(req, session, "푸시 상세 조회", null);
		return model;
	}
	
	/**
	 * Push 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/pushes"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"	// 생산 가능한 미디어 타입으로 매핑 제한. 요청 헤더가 일치 할 때만 요청 매핑
			, headers = "content-type=application/json" // 요청 헤더의 파라미터 값 일치로 매핑 제한.
		)
	public SimpleResult regPush(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestBody Push push ) throws Exception {
		
		this.initHandler(req, res);

		push.bcn_cd = bcnCd;
		push.sts = StatusCode.Common.USE.getCode();
		push.user = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = apsh001Service.regPush(push);
		
		aact001service.regAdminAction(req, session, "푸시 등록", null);
		return result;
	}
	
	/**
	 * Push 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/pushes/{pushMngSeq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult updatePush(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="pushMngSeq") String pushMngSeq
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestBody Push push ) throws Exception {
		
		this.initHandler(req, res);
		
		push.push_mng_seq = pushMngSeq;
		push.bcn_cd = bcnCd;
		push.user = (String) session.getAttribute("adm_seq");
		
		ll.info("Modify Target Push is {}", push);
		
		SimpleResult result = apsh001Service.updatePush(push);
		
		aact001service.regAdminAction(req, session, "푸시 수정", null);
		return result;
	}
	
	/**
	 * Push 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/pushes/{pushMngSeqs}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deletePushes(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @PathVariable(value="pushMngSeqs") String[] pushMngSeqs
	) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info("delete target is {}", Arrays.asList(pushMngSeqs));
		
		if(pushMngSeqs.length < 1){
			BaseException be = new BaseException(ErrorCode.Push.PUSH_SELECTED_SIZE_ERROR);
			throw be;
		}

		CommonDeleteModel delete = new CommonDeleteModel();
		delete.bcn_cd = bcnCd;
		delete.seq_arr = pushMngSeqs;
		delete.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = apsh001Service.deletePushes(delete);
		
		aact001service.regAdminAction(req, session, "푸시 삭제", null);
		return result;
	}
	
}
