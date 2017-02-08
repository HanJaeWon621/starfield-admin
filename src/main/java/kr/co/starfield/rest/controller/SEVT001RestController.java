package kr.co.starfield.rest.controller;

import java.text.SimpleDateFormat;

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

import kr.co.starfield.common.StatusCode;
import kr.co.starfield.model.EventFilter;
import kr.co.starfield.model.EventWeb;
import kr.co.starfield.model.EventWinner;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SEVT003Vo;
import kr.co.starfield.service.SEVT001Service;

/**
 * SEVT001(event) REST 컨트롤러 클래스
 *
 * @author eezy
 * @version 1.0,
 * @see
 * @date 2016.06.20
 */
@RestController
@RequestMapping("/rest")
public class SEVT001RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(SEVT001RestController.class);
	
	@Autowired
	SEVT001Service sevt001Service;
	
	/**
	 * Event 목록 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/events"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<EventWeb> getEventList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="evt_type", required=false) String evt_type
			, @RequestParam(value="tnt_seq", required=false) String tnt_seq
			, @RequestParam(value="date", required=false) String date
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getEventList cn_cd : {}, offset : {}, limit : {}", new Object[] {bcn_cd, offset, limit});
		
		EventFilter filter = new EventFilter();
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		filter.evt_type = evt_type;
		filter.tnt_seq = tnt_seq;
		
		if(date != null) {
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			filter.evt_dt = transFormat.parse(date);
		}
		
		
		filter.sts = StatusCode.Common.USE.getCode();
		
		ListResult<EventWeb> eventList = sevt001Service.getEventWebList(filter);
		
		return eventList;
	}
	
	/**
	 * oldEvent 목록 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/oldEvents"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<EventWeb> getOldEventList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="offset", required=false, defaultValue = "0") int offset
			, @RequestParam(value="limit", required=false, defaultValue = "-1") int limit
			, @RequestParam(value="evt_type", required=false) String evt_type
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getOldEventList cn_cd : {}, offset : {}, limit : {}", new Object[] {bcn_cd, offset, limit});
		
		EventFilter filter = new EventFilter();
		filter.bcn_cd = bcn_cd;
		filter.offset = offset;
		filter.limit = limit;
		filter.evt_type = evt_type;
		
		filter.sts = StatusCode.Common.USE.getCode();
		
		ListResult<EventWeb> eventList = sevt001Service.getOldEventWebList(filter);
		
		return eventList;
	}
	
	/**
	 * Event 상세
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/events/{evt_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public EventWeb getEvent(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="evt_seq") String evt_seq
			, @RequestParam(value="mbr_seq", required=false) String mbr_seq) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getEvent bcn_cd : {}, evt_seq : {}", new Object[] {bcn_cd, evt_seq});
		EventFilter filter = new EventFilter();
		filter.bcn_cd = bcn_cd;
		filter.evt_seq = evt_seq;
		filter.mbr_seq = mbr_seq;
		
		filter.sts = StatusCode.Common.USE.getCode();
		
		EventWeb model = sevt001Service.getEventWeb(filter);
	
		return model;
	}
	
	/**
	 * 참여한 Event 목록 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/events/entry/{mbr_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<EventWeb> getEventEntryWebList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="mbr_seq") String mbr_seq
	) throws Exception {
		
		this.initHandler(req, res);
		
		EventFilter filter = new EventFilter();
		filter.mbr_seq = mbr_seq;

		filter.sts = StatusCode.Common.USE.getCode();
		
		ListResult<EventWeb> eventList = sevt001Service.getEventEntryWebList(filter);
		
		return eventList;
	}
	
	/**
	 * Event 응모
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/events/entry/{mbr_seq}/{evt_seq}"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regEventEntry(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="evt_seq") String evt_seq
			, @PathVariable(value="mbr_seq") String mbr_seq) throws Exception {
		
		this.initHandler(req, res);
		
		SEVT003Vo vo = new SEVT003Vo();
		vo.evt_seq = evt_seq;
		vo.mbr_seq = mbr_seq;
		
		vo.sts = StatusCode.Common.USE.getCode();
		
		return sevt001Service.regEventEntry(vo);
	}
	
	/**
	 * 참여한 이벤트 삭제 
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/events/entry/{mbr_seq}/{evt_seq}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult deleteEventEntry(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="evt_seq") String evt_seq
			, @PathVariable(value="mbr_seq") String mbr_seq) throws Exception {
		
		this.initHandler(req, res);
		
		SEVT003Vo vo = new SEVT003Vo();
		vo.evt_seq = evt_seq;
		vo.mbr_seq = mbr_seq;
		
		return sevt001Service.deleteEventEntry(vo);
	}
	
	/**
	 * 당첨자 목록 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/events/ann/{evt_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<EventWinner> getEventWinnerList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="evt_seq") String evt_seq
	) throws Exception {
		
		this.initHandler(req, res);
		
		EventFilter filter = new EventFilter();
		filter.evt_seq = evt_seq;

		filter.sts = StatusCode.Common.USE.getCode();
		
		ListResult<EventWinner> eventList = sevt001Service.getEventWinnerList(filter);
		
		return eventList;
	}
	
}
