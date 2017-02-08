/*
 * EmpVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import java.util.List;

import kr.co.starfield.model.vo.SCPN001Vo;

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

public class SCPN001 extends BaseModel {
	public String cp_seq;
	public String bcn_cd;
	public String yymm;
	public String mst_seq;
	public String cp_div_cd;
	public String cp_kind_cd;
	public String cp_iss_type_cd;
	public String cp_use_sts_cd_nm;
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
	public String cp_prc_dt;
	public String mod_usr;

	public String sub_dir;
	public String cp_iss_bcd_id;
	public String cp_iss_sub_seq;
	public String cp_iss_mst_seq;
	public String img_thmb_uri;
	public String img_uri;
	public String uuid;
	public String cust_id;
	public List<SCPN001_D> selTenantList;
	public String cp_dn_dt;
	public String cp_div_cd_nm;
    public String cp_iss_cd;
    public String cp_kind_cd_nm;
    public String cp_iss_type_cd_nm;
    public String cp_sale_div_nm; 
    public String cp_use_sts_cd; 
    public String cp_use_dt; 
    public String use_flag; 
    public String use_tnt_nm; 
    public String log_seq; 
    public String bcd_img_pys_loc;
    public String bcd_img_uri;
    public String result;
	@Override
	public SCPN001Vo toVo() {
		SCPN001Vo scpn001Vo = new SCPN001Vo();
		
		scpn001Vo.cp_seq              = this.cp_seq;                                  
		scpn001Vo.bcn_cd              = this.bcn_cd;                                  
		scpn001Vo.yymm                = this.yymm;                                    
		scpn001Vo.mst_seq             = this.mst_seq;                                 
		scpn001Vo.cp_div_cd           = this.cp_div_cd;                               
		scpn001Vo.cp_kind_cd          = this.cp_kind_cd;                              
		scpn001Vo.cp_iss_type_cd      = this.cp_iss_type_cd;                          
		scpn001Vo.cp_titl             = this.cp_titl;                                 
		scpn001Vo.img_seq             = this.img_seq;                                 
		scpn001Vo.cp_iss_strt_dt      = this.cp_iss_strt_dt;                          
		scpn001Vo.cp_iss_end_dt       = this.cp_iss_end_dt;                           
		scpn001Vo.cp_act_strt_dt      = this.cp_act_strt_dt;                          
		scpn001Vo.cp_act_end_dt       = this.cp_act_end_dt;                           
		scpn001Vo.cp_sale_div_cd      = this.cp_sale_div_cd;                          
		scpn001Vo.cp_sum_sale_rt      = this.cp_sum_sale_rt;                          
		scpn001Vo.cp_ded_amt          = this.cp_ded_amt;                              
		scpn001Vo.cp_max_sale_amt     = this.cp_max_sale_amt;                         
		scpn001Vo.cp_iss_cnt          = this.cp_iss_cnt;                              
		scpn001Vo.cp_use_cond_amt     = this.cp_use_cond_amt;                         
		scpn001Vo.cp_use_plce_div_cd  = this.cp_use_plce_div_cd;                      
		scpn001Vo.cp_dtl_cont         = this.cp_dtl_cont;                             
		scpn001Vo.cp_att_part_cont    = this.cp_att_part_cont;                        
		scpn001Vo.if_yn               = this.if_yn;                                   
		scpn001Vo.if_dttm             = this.if_dttm;                                 
		scpn001Vo.sale_evt_cd         = this.sale_evt_cd;                             
		scpn001Vo.cp_exp_yn           = this.cp_exp_yn;                               
		scpn001Vo.sts                 = this.sts;                                     
		scpn001Vo.reg_dttm            = this.reg_dttm;                                
		scpn001Vo.mod_dttm            = this.mod_dttm;                                
		scpn001Vo.reg_usr             = this.reg_usr;                                 
		scpn001Vo.mod_usr             = this.mod_usr;                                 
		
		return scpn001Vo;
	}
}
