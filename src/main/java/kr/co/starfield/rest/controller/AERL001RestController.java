package kr.co.starfield.rest.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.common.Utils;
import kr.co.starfield.model.AERL001;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.vo.AERL001VO;
import kr.co.starfield.service.AERL001Service;

@RestController
@RequestMapping("/admin/rest")
public class AERL001RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(AERL001RestController.class);
	
	@Autowired
	AERL001Service aerl001Service;
	
	
	/**
	 * error 목록 조회
	 * @return
	 */
	@RequestMapping(value = "/error"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<AERL001> getErrorList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") int offset
			, @RequestParam(value="limit") int limit
			, @RequestParam(value="q", required=false) String q
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getErrorList q : {}, offset : {}, limit : {}", new Object[] {q, offset, limit});
		
		AERL001VO vo = new AERL001VO();
		vo.offset = offset;
		vo.limit = limit;
		Utils.Str.qParser(vo, q);
		
		ListResult<AERL001> errorList = aerl001Service.getErrorLogList(vo);
		
		return errorList;
	}
	
}
