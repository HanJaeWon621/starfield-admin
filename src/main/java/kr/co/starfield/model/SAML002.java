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
import kr.co.starfield.model.vo.SAML001Vo;
import kr.co.starfield.model.vo.SAML002Vo;
import kr.co.starfield.model.vo.SAML003Vo;
import kr.co.starfield.model.vo.SCPN001_DVo;

/**
 *  SAML001 model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class SAML002 extends BaseModel {
	public String log_seq;
	public String uuid;
	public String cust_id;
	public String corp_cd;
	public String brnd_cd;
	public String bcn_cd;
	public String fl;
	public String zone_id;
	public String x_ctn_cord;
	public String y_ctn_cord;
	public String sts;
	public String stay_time;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public String campaign_id;
	
	
	public String cp_seq;
	public String scn_cp_push_titl;
	public String scn_push_msg;
	public String img_titl_uri;
	public String img_dtl_uri;
	public String sys_push_msg;
	public String cp_act_strt_dt;
	public String cp_act_end_dt;
	public String scn_otb_cp_push_seq;
	public String push_time_div_cd;
	public String div_cd;
	public String titl_type;
	public String dtl_type;
	
	
	
	public String tgt;
	public String act_path;
	public String svc;
	public String rcv_nm;
	public String f_reg_dttm;
	public String no;
	public int tot_cnt;
	@Override
	public SAML002Vo toVo() {
		SAML002Vo vo = new SAML002Vo();
		
		vo.log_seq     = this.log_seq;    
		vo.uuid        = this.uuid;       
		vo.cust_id     = this.cust_id;    
		vo.corp_cd     = this.corp_cd;   
		vo.brnd_cd     = this.brnd_cd;  
		vo.bcn_cd      = this.bcn_cd;     
		vo.fl          = this.fl;         
		vo.zone_id     = this.zone_id;    
		vo.x_ctn_cord  = this.x_ctn_cord; 
		vo.y_ctn_cord  = this.y_ctn_cord;
		vo.sts         = this.sts;        
		vo.stay_time   = this.stay_time;        
		vo.reg_dttm    = this.reg_dttm;   
		vo.mod_dttm    = this.mod_dttm;   
		vo.reg_usr     = this.reg_usr;   
		vo.mod_usr     = this.mod_usr;     
		
		return vo;
	}
}
