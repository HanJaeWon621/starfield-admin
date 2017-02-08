/*
 * NoticeRestController.java	1.00 2016/06/16
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  AOPR004(TenantHoliday) REST 컨트롤러 클래스
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
public class AOPR004RestController extends BaseController {
//	
//	private Logger ll = LoggerFactory.getLogger(AOPR004RestController.class);
//	
//	@Autowired
//	AOPR004Service aopr004Service;
//	
//	/**
//	 * TenantHoliday 등록
//	 * @return 
//	 */
//	@RequestMapping(value = "/{bcn_cd}/holidays/ir/tnt"
//			, method = RequestMethod.POST
//			, produces = "application/json; charset=utf-8"
//			, headers = "content-type=application/json"
//		)
//	public SimpleResult regTenantHolidays(
//			HttpServletRequest req, HttpServletResponse res, HttpSession session
//			, @PathVariable(value="bcn_cd") String bcn_cd
//			, @RequestBody TenantHoliday[] models ) throws Exception {
//		
//		this.initHandler(req, res);
//		
//		ll.info("regTenantHolidays bcn_cd : {}, TenantHoliday : {}", new Object[] {bcn_cd, models});
//		
//		
//		ArrayList<SOPR004Vo> voList = new ArrayList<>();
//		for(TenantHoliday model : models){
//			model.bcn_cd = bcn_cd;
//			
//			//TODO reg_usr 로그인 완료시 세션 연동
//			model.reg_usr = "eezy";
//			
//			voList.add(model.toVo());
//		}
//		
//		SimpleResult result = aopr004Service.regTenantHolidays(voList);
//		return result;
//	}
//	
//	/**
//	 * TenantHoliday 목록 조회
//	 * @return
//	 */
//	@RequestMapping(value = "/{bcn_cd}/holidays/ir/tnt"
//			, method = RequestMethod.GET
//			, produces = "application/json; charset=utf-8"
//			, headers = "content-type=application/json"
//		)
//	public ListResult<TenantHoliday> getTenantHolidayList(
//			HttpServletRequest req, HttpServletResponse res, HttpSession session
//			, @PathVariable(value="bcn_cd") String bcn_cd
//			, @RequestParam(value="q", required=false) String q) throws Exception {
//		
//		this.initHandler(req, res);
//		
//		ll.info("getTenantHolidayList bcn_cd : {}, q : {}", new Object[] {bcn_cd, q});
//		
//		SOPR004Vo vo = new SOPR004Vo();
//		vo.bcn_cd = bcn_cd;
//		vo.sts = StatusCode.Common.USE.getCode();
//		Utils.Str.qParser(vo, q);
//		
//		ListResult<TenantHoliday> modelList = aopr004Service.getTenantHolidayList(vo);
//		
//		return modelList;
//	}
//	
//	/**
//	 * TenantHoliday 상세
//	 * @return
//	 */
//	@RequestMapping(value = "/{bcn_cd}/holidays/ir/tnt/{tnt_irgu_opr_seq}"
//			, method = RequestMethod.GET
//			, produces = "application/json; charset=utf-8"
//			, headers = "content-type=application/json"
//		)
//	public TenantHoliday getTenantHoliday(
//			HttpServletRequest req, HttpServletResponse res, HttpSession session
//			, @PathVariable(value="bcn_cd") String bcn_cd
//			, @PathVariable(value="tnt_irgu_opr_seq") String tnt_irgu_opr_seq) throws Exception {
//		
//		this.initHandler(req, res);
//		
//		ll.info("getTenantHoliday bcn_cd : {}, tnt_irgu_opr_seq : {}", new Object[] {bcn_cd, tnt_irgu_opr_seq});
//		SOPR004Vo vo = new SOPR004Vo();
//		vo.bcn_cd = bcn_cd;
//		vo.tnt_irgu_opr_seq = tnt_irgu_opr_seq;
//		vo.sts = StatusCode.Common.USE.getCode();
//		
//		TenantHoliday model = aopr004Service.getTenantHoliday(vo);
//	
//		return model;
//	}
//	
//	/**
//	 * TenantHoliday 수정
//	 * @return
//	 */
//	@RequestMapping(value = "/{bcn_cd}/holidays/ir/tnt/{tnt_irgu_opr_seq}"
//			, method = RequestMethod.PUT
//			, produces = "application/json; charset=utf-8"
//			, headers = "content-type=application/json"
//		)
//	public SimpleResult modifyTenantHoliday(
//			HttpServletRequest req, HttpServletResponse res, HttpSession session
//			, @RequestBody TenantHoliday model
//			, @PathVariable(value="bcn_cd") String bcn_cd
//			, @PathVariable(value="tnt_irgu_opr_seq") String tnt_irgu_opr_seq) throws Exception {
//		
//		this.initHandler(req, res);
//		
//		ll.info("modifyTenantHoliday tnt_irgu_opr_seq : {}, bcn_cd : {}, tnt_irgu_opr_seq : {}", new Object[] {model, bcn_cd, tnt_irgu_opr_seq});
//		
//		model.bcn_cd = bcn_cd;
//		model.tnt_irgu_opr_seq = tnt_irgu_opr_seq;
//		
//		//TODO mod_usr 로그인 완료시 세션 연동
//		model.mod_usr = "eezy";
//		
//		SimpleResult result = aopr004Service.modifyTenantHoliday(model.toVo());
//				
//		return result;
//	}
//	
//	/**
//	 * TenantHoliday 삭제
//	 * @return
//	 */
//	@RequestMapping(value = "/{bcn_cd}/holidays/ir/tnt/{tnt_irgu_opr_seq}"
//			, method = RequestMethod.DELETE
//			, produces = "application/json; charset=utf-8"
//			, headers = "content-type=application/json"
//		)
//	public SimpleResult deleteTenantHoliday(
//			HttpServletRequest req, HttpServletResponse res, HttpSession session
//			, @PathVariable(value="bcn_cd") String bcn_cd
//			, @PathVariable(value="tnt_irgu_opr_seq") String tnt_irgu_opr_seq) throws Exception {
//		
//		this.initHandler(req, res);
//	
//		ll.info("deleteTenantHoliday bcn_cd : {}, tnt_irgu_opr_seq : {}", new Object[] {bcn_cd, tnt_irgu_opr_seq});
//		
//		SOPR004Vo vo = new SOPR004Vo();
//		vo.bcn_cd = bcn_cd;
//		vo.tnt_irgu_opr_seq = tnt_irgu_opr_seq;
//		
//		//TODO mod_usr 로그인 완료시 세션 연동
//		vo.mod_usr = "eezy";
//		
//		SimpleResult result = aopr004Service.deleteTenantHoliday(vo);
//		
//		return result;
//	}
}
