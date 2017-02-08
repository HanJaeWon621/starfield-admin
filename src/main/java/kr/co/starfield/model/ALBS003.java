/*
 * Emp.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.ALBS003Vo;
import kr.co.starfield.model.vo.SCPN001_DVo;

/**
 * ALBS003 model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class ALBS003 extends BaseModel {
	public String no;
	public String wel_msg_push_seq;//웰컴메시지푸시순번
	public String titl_img_seq;//타이틀이미지순번
	public String dtl_msg_seq;//상세이미지순번
	public String push_titl;//푸시타이틀
	public String push_cnt;//푸시카운트
	public String tot_push_cnt;//푸시카운트(누적)
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
	public String apply_yn;
	public String cp_seq;
	public String cp_titl;
	public String dtl_titl_img_seq;//수정자
	public String dtl_img_thmb_uri;//수정자
	public String search_strt_dt;
	public String search_end_dt;
	public String search_dt_type;
	public String cmp_id;
	public int tot_cnt;
	public int rnum;
	public String cp_act_strt_dt;//쿠폰유효시작일
	public String cp_act_end_dt;//쿠폰유효종료일
	
	public List<WelcomeZone> selTenantList = new ArrayList<>();
	@Override
	public ALBS003Vo toVo() {
		ALBS003Vo vo = new ALBS003Vo();
		
		vo.no  = this.no;                                                   
		vo.wel_msg_push_seq  = this.wel_msg_push_seq;//웰컴메시지푸시순번                                                   
		vo.titl_img_seq      = this.titl_img_seq;//타이틀이미지순번                                                         
		vo.dtl_msg_seq       = this.dtl_msg_seq;//상세이미지순번                                                            
		vo.push_titl         = this.push_titl;//푸시타이틀                                                                  
		vo.exp_strt_dt       = this.exp_strt_dt;//노출시작일                                                                
		vo.exp_end_dt        = this.exp_end_dt;//노출종료일                                                                 
		vo.sys_push_msg      = this.sys_push_msg;//시스템푸시메시지                                                         
		vo.dtl_msg           = this.dtl_msg;//상세메시지                                                                    
		vo.exp_yn            = this.exp_yn;//노출여부                                                                       
		vo.sts               = this.sts;//상태                                                                              
		vo.reg_dttm          = this.reg_dttm;//등록일시                                                                     
		vo.mod_dttm          = this.mod_dttm;//수정일시                                                                     
		vo.reg_usr           = this.reg_usr;//등록자                                                                        
		vo.mod_usr           = this.mod_usr;//수정자 
		vo.cp_act_strt_dt           = this.cp_act_strt_dt;//등록자                                                                        
		vo.cp_act_strt_dt           = this.cp_act_strt_dt;//수정자
				
		return vo;
	}
}
