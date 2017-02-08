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

public class SAML003 extends BaseModel {
	public String log_seq;
	public String uuid;
	public String cust_id;
	public String mbr_div_cd;
	public String log_type;
	public String evt_div_cd1;
	public String evt_div_cd2;
	public String key;
	public String val;
	public String evt_val;
	public String sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	@Override
	public SAML003Vo toVo() {
		SAML003Vo vo = new SAML003Vo();
		
		vo.log_seq     = this.log_seq;         
		vo.uuid        = this.uuid;            
		vo.cust_id     = this.cust_id;         
		vo.mbr_div_cd  = this.mbr_div_cd;      
		vo.log_type    = this.log_type;        
		vo.evt_div_cd1 = this.evt_div_cd1;     
		vo.evt_div_cd2 = this.evt_div_cd2;     
		vo.evt_val     = this.evt_val;         
		vo.sts         = this.sts;             
		vo.reg_dttm    = this.reg_dttm;        
		vo.mod_dttm    = this.mod_dttm;        
		vo.reg_usr     = this.reg_usr;         
		vo.mod_usr     = this.mod_usr;  
				
		return vo;
	}
}
