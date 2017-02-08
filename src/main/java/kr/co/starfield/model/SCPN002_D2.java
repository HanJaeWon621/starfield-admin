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
import kr.co.starfield.model.vo.SCPN001_DVo;
import kr.co.starfield.model.vo.SCPN002_D2Vo;

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

public class SCPN002_D2 extends BaseModel {
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

	@Override
	public SCPN002_D2Vo toVo() {
		SCPN002_D2Vo vo = new SCPN002_D2Vo();
		
		vo.cp_iss_mst_seq   = this.cp_iss_mst_seq;              
		vo.cp_seq           = this.cp_seq;                      
		vo.cp_iss_sub_seq   = this.cp_iss_sub_seq;              
		vo.cust_id          = this.cust_id;                     
		vo.uuid             = this.uuid;                        
		vo.cp_dn_dt         = this.cp_dn_dt;                    
		vo.cp_use_dt        = this.cp_use_dt;                   
		vo.cp_use_sts_cd    = this.cp_use_sts_cd;               
		vo.cp_use_res_cd    = this.cp_use_res_cd;               
		vo.cp_use_res_msg   = this.cp_use_res_msg;              
		vo.busi_tnt_cd      = this.busi_tnt_cd;                 
		vo.tnt_seq          = this.tnt_seq;                     
		vo.sts              = this.sts;                         
		vo.reg_dttm         = this.reg_dttm;                    
		vo.mod_dttm         = this.mod_dttm;                    
		vo.reg_usr          = this.reg_usr;                     
		vo.mod_usr          = this.mod_usr;                     
		vo.cp_cofm_dt       = this.cp_cofm_dt;     
				
		return vo;
	}
}
