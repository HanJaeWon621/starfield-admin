/*
 * EmpVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import java.util.Arrays;
import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.SCPN002_D2;

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

public class SCPN002_D2Vo extends BaseVo {
	public String cp_iss_mst_seq;
	public String cp_seq;
	public String cp_iss_sub_seq;
	public String cust_id;
	public String uuid;
	public String cp_dn_dt;
	public String cp_use_dt;
	public String cp_use_sts_cd;
	public String cp_use_res_cd;
	public String cp_use_res_msg;
	public String busi_tnt_cd;
	public String tnt_seq;
	public String sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	public String cp_cofm_dt;
	
	public SCPN002_D2 toModel() {
		SCPN002_D2 scpn002_d2 = new SCPN002_D2();
		
		scpn002_d2.cp_iss_mst_seq   = this.cp_iss_mst_seq;              
		scpn002_d2.cp_seq           = this.cp_seq;                      
		scpn002_d2.cp_iss_sub_seq   = this.cp_iss_sub_seq;              
		scpn002_d2.cust_id          = this.cust_id;                     
		scpn002_d2.uuid             = this.uuid;                        
		scpn002_d2.cp_dn_dt         = this.cp_dn_dt;                    
		scpn002_d2.cp_use_dt        = this.cp_use_dt;                   
		scpn002_d2.cp_use_sts_cd    = this.cp_use_sts_cd;               
		scpn002_d2.cp_use_res_cd    = this.cp_use_res_cd;               
		scpn002_d2.cp_use_res_msg   = this.cp_use_res_msg;              
		scpn002_d2.busi_tnt_cd      = this.busi_tnt_cd;                 
		scpn002_d2.tnt_seq          = this.tnt_seq;                     
		scpn002_d2.sts              = this.sts;                         
		scpn002_d2.reg_dttm         = this.reg_dttm;                    
		scpn002_d2.mod_dttm         = this.mod_dttm;                    
		scpn002_d2.reg_usr          = this.reg_usr;                     
		scpn002_d2.mod_usr          = this.mod_usr;                     
		scpn002_d2.cp_cofm_dt       = this.cp_cofm_dt;                  
		
		return scpn002_d2;
	}
}
