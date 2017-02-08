package kr.co.starfield.rest.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.model.SEVT004ListResult;
import kr.co.starfield.model.SEVT004Vo;
import kr.co.starfield.model.SEVT004VoResult;
import kr.co.starfield.service.SEVT004Service;
	
/**
 * SEVT004(stamp_event) REST 컨트롤러 클래스
 *
 * @author  
 * @version 1.0,
 * @see
 * @date 2016.11.21
 */

@RestController
@RequestMapping("/rest")
public class SEVT004RestController extends BaseController {
	
	@Autowired
	SEVT004Service sevt004Service;
	
	/**
	 * Stamp Event 조회
	 * 
	 * @param  bcn_cd, evt_seq, cust_id, uuid
	 * @return code, message, giftCount, list[inbox_cont]
	 */
	@RequestMapping(value = "/{bcn_cd}/stampEvent"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SEVT004ListResult<SEVT004VoResult> stampEvent(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="evt_seq", required=true) String evt_seq
			, @RequestParam(value="uuid", required=true) String uuid
	) throws Exception {
		
		this.initHandler(req, res);
		
		SEVT004Vo vo = new SEVT004Vo();

		vo.bcn_cd  = bcn_cd;  // 지점코드
		vo.evt_seq = evt_seq; // 이벤트 순번
		vo.uuid    = uuid;    // 디바이스 ID
		
		return sevt004Service.stampEvent(vo);
		
	}
	
	/**
	 * Stamp 교환
	 * 
	 * @param  bcn_cd, evt_seq, mbr_seq, cust_id, uuid
	 * @return code
	 */
	@RequestMapping(value = "/{bcn_cd}/stampEventConfirm"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public HashMap<String, String> stampEventConfirm(
				HttpServletRequest req, HttpServletResponse res, HttpSession session
			  , @RequestBody SEVT004Vo vo
			  , @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		res.setHeader("Cache-Control", "no-cache");
		
		vo.bcn_cd = bcn_cd;
		
		return sevt004Service.stampEventConfirm(vo);

	}
	
}
