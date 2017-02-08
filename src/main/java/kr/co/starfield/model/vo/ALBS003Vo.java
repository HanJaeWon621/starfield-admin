/*
 * EmpVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.model.ALBS003;

/**
 * 
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class ALBS003Vo extends BaseVo {
	public String no;
	public String wel_msg_push_seq;//웰컴메시지푸시순번
	public String titl_img_seq;//타이틀이미지순번
	public String dtl_msg_seq;//상세이미지순번
	public String push_titl;//푸시타이틀
	public String exp_strt_dt;//노출시작일
	public String exp_end_dt;//노출종료일
	public String sys_push_msg;//시스템푸시메시지
	public String dtl_msg;//상세메시지
	public String exp_yn;//노출여부
	public int    sts;//상태
	public String   reg_dttm;//등록일시
	public String   mod_dttm;//수정일시
	public String reg_usr;//등록자
	public String mod_usr;//수정자
	public String cp_act_strt_dt;//쿠폰유효시작일
	public String cp_act_end_dt;//쿠폰유효종료일
	public String search_dt_type;
	public String search_strt_dt;
	public String search_end_dt;
	public String push_search_strt_dt;
	public String push_search_end_dt;
	public String sortColumn;
	public String sortColumnAscDesc;

	public ALBS003 toModel() {
		ALBS003 albs003 = new ALBS003();
		
		albs003.no  = this.no;                                                   
		albs003.wel_msg_push_seq  = this.wel_msg_push_seq;//웰컴메시지푸시순번                                                   
		albs003.titl_img_seq      = this.titl_img_seq;//타이틀이미지순번                                                         
		albs003.dtl_msg_seq       = this.dtl_msg_seq;//상세이미지순번                                                            
		albs003.push_titl         = this.push_titl;//푸시타이틀                                                                  
		albs003.exp_strt_dt       = this.exp_strt_dt;//노출시작일                                                                
		albs003.exp_end_dt        = this.exp_end_dt;//노출종료일                                                                 
		albs003.sys_push_msg      = this.sys_push_msg;//시스템푸시메시지                                                         
		albs003.dtl_msg           = this.dtl_msg;//상세메시지                                                                    
		albs003.exp_yn            = this.exp_yn;//노출여부                                                                       
		albs003.sts               = this.sts;//상태                                                                              
		albs003.reg_dttm          = this.reg_dttm;//등록일시                                                                     
		albs003.mod_dttm          = this.mod_dttm;//수정일시                                                                     
		albs003.reg_usr           = this.reg_usr;//등록자                                                                        
		albs003.mod_usr           = this.mod_usr;//수정자 
		albs003.cp_act_strt_dt    = this.cp_act_strt_dt;//등록자                                                                        
		albs003.cp_act_end_dt     = this.cp_act_end_dt;//수정자 
		
		return albs003;
	}
}
