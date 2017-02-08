package kr.co.starfield.rest.controller;

import java.util.ArrayList;
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
import kr.co.starfield.model.Event;
import kr.co.starfield.model.EventApply;
import kr.co.starfield.model.EventFilter;
import kr.co.starfield.model.EventSelect;
import kr.co.starfield.model.EventSimpleFilter;
import kr.co.starfield.model.EventWinner;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.PickInfo;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.AEVT001Service;

/**
 *  AEVT001(event) REST 컨트롤러 클래스
 *
 * @author hhlee
 * @version 1.0,
 * @see
 * @date 2016.06.20
 */
@RestController
@RequestMapping("/admin/rest")
public class AEVT001RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(AEVT001RestController.class);
	
	@Autowired
	AEVT001Service aevt001Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * Event 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/events"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<Event> getEventList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset") int offset
 			, @RequestParam(value="limit") int limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		
		this.initHandler(req, res);
		
		EventFilter eventFilter = new EventFilter();
		
		Utils.Str.qParser(eventFilter, q);
		eventFilter.bcn_cd = bcn_cd;
		eventFilter.offset = offset;
		eventFilter.limit = limit;
		
		ll.info("getEventList EventFilter : {}", eventFilter);

		ListResult<Event> eventList = aevt001Service.getEventList(eventFilter);
		
		aact001service.regAdminAction(req, session, "이벤트 목록 조회", null);
		return eventList;
	}
	
	/**
	 * Event 상세
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/events/{evt_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public Event getEvent(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="evt_seq") String evt_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getEvent bcn_cd : {}, evt_seq : {}", new Object[] {bcn_cd, evt_seq});
		EventFilter eventFilter = new EventFilter();
		eventFilter.bcn_cd = bcn_cd;
		eventFilter.evt_seq = evt_seq;
		
		Event model = aevt001Service.getEvent(eventFilter);
	
		aact001service.regAdminAction(req, session, "이벤트 상세 조회", null);
		return model;
	}
	
	/**
	 * Event 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/events"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"	// 생산 가능한 미디어 타입으로 매핑 제한. 요청 헤더가 일치 할 때만 요청 매핑
			, headers = "content-type=application/json" // 요청 헤더의 파라미터 값 일치로 매핑 제한.
		)
	public SimpleResult regTenant(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestBody Event event ) throws Exception {
		
		this.initHandler(req, res);

		event.bcn_cd = bcnCd;
		event.sts = StatusCode.Common.WAITING.getCode();
		event.user = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = aevt001Service.regEvent(event);
		
		aact001service.regAdminAction(req, session, "이벤트 등록", null);
		return result;
	}

	/**
	 * 이벤트 수정
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/events/{evtSeqs}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult closedEvents(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @PathVariable(value="evtSeqs") String[] evtSeqs
			, @RequestBody Event event 
	) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info("moidfy target is {}", Arrays.asList(evtSeqs));
		
		if(evtSeqs.length < 1){
			BaseException be = new BaseException(ErrorCode.Event.EVENT_SELECTED_SIZE_ERROR);
			throw be;
		}
		
		event.bcn_cd = bcnCd;
		event.evt_seq_arr = evtSeqs;
		event.user = (String) session.getAttribute("adm_seq");
		
		if(event.sts != null && event.sts == StatusCode.Common.USE.getCode()) {
			//TODO 세션 처리
			event.evt_app_id = "super";
		}
		
		SimpleResult result = aevt001Service.updateEvents(event);
		
		aact001service.regAdminAction(req, session, "이벤트 수정", null);
		return result;
	}
	
	/**
	 * 이벤트 승인
	 * @return
	 */
	@AuthRequired(authArr={"starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/events/confirm/{evtSeqs}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult confirmEvents(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @PathVariable(value="evtSeqs") String[] evtSeqs
			, @RequestBody Event event 
	) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info("confirmEvents is {}", Arrays.asList(evtSeqs));
		
		if(evtSeqs.length < 1){
			BaseException be = new BaseException(ErrorCode.Event.EVENT_SELECTED_SIZE_ERROR);
			throw be;
		}
		
		event.bcn_cd = bcnCd;
		event.evt_seq_arr = evtSeqs;
		event.user = (String) session.getAttribute("adm_seq");
		
		if(event.sts != null && event.sts == StatusCode.Common.USE.getCode()) {
			//TODO 세션 처리
			event.evt_app_id = "super";
		}
		
		SimpleResult result = aevt001Service.updateEvents(event);
		
		aact001service.regAdminAction(req, session, "이벤트 승인", null);
		return result;
	}
	
	
	/**
	 * 이벤트 삭제
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcnCd}/events/{evtSeqs}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteEvents(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @PathVariable(value="evtSeqs") String[] evtSeqs
	) throws Exception {
		
		this.initHandler(req, res);
	
		ll.info("delete target is {}", Arrays.asList(evtSeqs));
		
		if(evtSeqs.length < 1){
			BaseException be = new BaseException(ErrorCode.Event.EVENT_SELECTED_SIZE_ERROR);
			throw be;
		}

		CommonDeleteModel eventDelete = new CommonDeleteModel();
		eventDelete.bcn_cd = bcnCd;
		eventDelete.seq_arr = evtSeqs;
		eventDelete.mod_usr = (String) session.getAttribute("adm_seq");
		
		SimpleResult result = aevt001Service.deleteEvents(eventDelete);
		
		aact001service.regAdminAction(req, session, "이벤트 삭제", null);
		return result;
	}
	
	/**
	 * Event 응모자 목록
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/events/{evtSeq}/apply/members"
		, method = RequestMethod.GET
		, produces = "application/json; charset=utf-8"
		, headers = "content-type=application/json"
	)
	public ListResult<EventApply> getEventApplyMemberList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcnCd
			, @PathVariable(value="evtSeq") String evtSeq
			, @RequestParam(value="codeCd") String codeCd
			, @RequestParam(value="offset") int offset
 			, @RequestParam(value="limit") int limit
 			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getEventApplyMemberList Event Seq : {}", evtSeq);

		EventSimpleFilter filter = new EventSimpleFilter();
		Utils.Str.qParser(filter, q);
		
		filter.offset  = offset;
		filter.limit   = limit;
		filter.evt_seq = evtSeq;
		filter.bcnCd   = bcnCd; // 지점코드
		
		boolean masking = true;
		
		ListResult<EventApply> eventApplyMemberList;
		
		if(Utils.Adm.authTypeCheck(session, "handle_personal_info")) {
			masking = false;
		}
		
		if( "9".equals(codeCd) ){
			// 이벤트유형 : 스탬프(9)
			eventApplyMemberList = aevt001Service.getStempApplyMemberList(filter);
		} else{
			eventApplyMemberList = aevt001Service.getEventApplyMemberList(filter, masking);
		}
		
		aact001service.regAdminAction(req, session, "이벤트 응모자 목록 조회", null);
		
		return eventApplyMemberList;
		
	}

	/**
	 * Event 당첨자 목록
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/events/{evtSeq}/winner/members"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<EventWinner> getEventWinnerList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="evtSeq") String evtSeq
			, @RequestParam(value="offset") int offset
 			, @RequestParam(value="limit") int limit
 			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		
		this.initHandler(req, res);

		ll.info("getEventWinnerList Event Seq : {}", evtSeq);

		EventSimpleFilter filter = new EventSimpleFilter();
		Utils.Str.qParser(filter, q);
		
		filter.offset = offset;
		filter.limit = limit;
		filter.evt_seq = evtSeq;
		
		boolean masking = true;
		
		if(Utils.Adm.authTypeCheck(session, "handle_personal_info")) {
			masking = false;
		}
		
		ListResult<EventWinner> eventApplyMemberList = aevt001Service.getEventWinnerList(filter, masking);
		
		aact001service.regAdminAction(req, session, "이벤트 당첨자 목록 조회", null);
		return eventApplyMemberList;
	}
	
	/**
	 * Event select 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/events/select"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ArrayList<EventSelect> getEventSelectList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="tnt_seq", required=false) String tnt_seq
	) throws Exception {
		
		this.initHandler(req, res);
		
		EventFilter eventFilter = new EventFilter();
		
		eventFilter.bcn_cd = bcn_cd;
		eventFilter.tnt_seq = tnt_seq;
		
		
		ll.info("getEventSelectList EventFilter : {}", eventFilter);
		
		ArrayList<EventSelect> list = aevt001Service.getEventSelectList(eventFilter);
		
		aact001service.regAdminAction(req, session, "이벤트 선택 조회", null);
		
		return list;
	}
	
	
	/**
	 * Event 추첨
	 * @return
	 */
	@AuthRequired(authArr={"starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/events/{evtSeq}/pick"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult pick(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="evtSeq") String evtSeq
			, @RequestBody PickInfo pickinfo
	) throws Exception {
		
		this.initHandler(req, res);
		
		pickinfo.evt_seq = evtSeq;
		pickinfo.user = (String) session.getAttribute("adm_seq");
		
		ll.info("pick PickInfo : {}", pickinfo);

		SimpleResult result = aevt001Service.pick(pickinfo);
		
		aact001service.regAdminAction(req, session, "이벤트 추첨", null);
		
		return result;
	}
}
