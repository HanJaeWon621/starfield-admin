/*
 * EmpVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import kr.co.starfield.model.ACPN002;
import kr.co.starfield.model.SCPN001;
import kr.co.starfield.model.SCPN003;

/**
 * SCPM001 테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class SCPN001Vo extends BaseVo {
	public String cp_seq;
	public String bcn_cd;
	public String yymm;
	public String mst_seq;
	public String cp_div_cd;
	public String cp_kind_cd;
	public String cp_iss_type_cd;
	public String cp_titl;
	public String img_seq;
	public String cp_iss_strt_dt;
	public String cp_iss_end_dt;
	public String cp_act_strt_dt;
	public String cp_act_end_dt;
	public String cp_sale_div_cd;
	public String cp_sum_sale_rt;
	public String cp_ded_amt;
	public String cp_max_sale_amt;
	public String cp_iss_cnt;
	public String cp_use_cond_amt;
	public String cp_use_plce_div_cd;
	public String cp_dtl_cont;
	public String cp_att_part_cont;
	public String if_yn;
	public String if_dttm;
	public String sale_evt_cd;
	public String cp_exp_yn;
	public String sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	
	public SCPN001 toModel() {
		SCPN001 scpm001 = new SCPN001();
		
		scpm001.cp_seq              = this.cp_seq;                                  
		scpm001.bcn_cd              = this.bcn_cd;                                  
		scpm001.yymm                = this.yymm;                                    
		scpm001.mst_seq             = this.mst_seq;                                 
		scpm001.cp_div_cd           = this.cp_div_cd;                               
		scpm001.cp_kind_cd          = this.cp_kind_cd;                              
		scpm001.cp_iss_type_cd      = this.cp_iss_type_cd;                          
		scpm001.cp_titl             = this.cp_titl;                                 
		scpm001.img_seq             = this.img_seq;                                 
		scpm001.cp_iss_strt_dt      = this.cp_iss_strt_dt;                          
		scpm001.cp_iss_end_dt       = this.cp_iss_end_dt;                           
		scpm001.cp_act_strt_dt      = this.cp_act_strt_dt;                          
		scpm001.cp_act_end_dt       = this.cp_act_end_dt;                           
		scpm001.cp_sale_div_cd      = this.cp_sale_div_cd;                          
		scpm001.cp_sum_sale_rt      = this.cp_sum_sale_rt;                          
		scpm001.cp_ded_amt          = this.cp_ded_amt;                              
		scpm001.cp_max_sale_amt     = this.cp_max_sale_amt;                         
		scpm001.cp_iss_cnt          = this.cp_iss_cnt;                              
		scpm001.cp_use_cond_amt     = this.cp_use_cond_amt;                         
		scpm001.cp_use_plce_div_cd  = this.cp_use_plce_div_cd;                      
		scpm001.cp_dtl_cont         = this.cp_dtl_cont;                             
		scpm001.cp_att_part_cont    = this.cp_att_part_cont;                        
		scpm001.if_yn               = this.if_yn;                                   
		scpm001.if_dttm             = this.if_dttm;                                 
		scpm001.sale_evt_cd         = this.sale_evt_cd;                             
		scpm001.cp_exp_yn           = this.cp_exp_yn;                               
		scpm001.sts                 = this.sts;                                     
		scpm001.reg_dttm            = this.reg_dttm;                                
		scpm001.mod_dttm            = this.mod_dttm;                                
		scpm001.reg_usr             = this.reg_usr;                                 
		scpm001.mod_usr             = this.mod_usr;    
		
		return scpm001;
	}
	
}
