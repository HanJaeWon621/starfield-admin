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
import kr.co.starfield.model.vo.SAML004Vo;
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

public class SAML004 extends BaseModel {
	public String read_seq;
	public String uuid;
	public String cust_id;
	public String corp_cd;
	public String prc_div;
	public String prc_info;
	public String sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	@Override
	public SAML004Vo toVo() {
		SAML004Vo vo = new SAML004Vo();
		
		vo.read_seq            = this.read_seq;                    
		vo.uuid                = this.uuid;                        
		vo.cust_id             = this.cust_id;                     
		vo.corp_cd             = this.corp_cd;                     
		vo.prc_div             = this.prc_div;                     
		vo.prc_info            = this.prc_info;                    
		vo.sts                 = this.sts;                         
		vo.reg_dttm            = this.reg_dttm;                    
		vo.mod_dttm            = this.mod_dttm;                    
		vo.reg_usr             = this.reg_usr;                     
		vo.mod_usr             = this.mod_usr;             
				
		return vo;
	}
}
