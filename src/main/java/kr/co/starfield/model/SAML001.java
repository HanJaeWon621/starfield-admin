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

public class SAML001 extends BaseModel {
	public String app_seq;
	public String dvic_model;
	public String os_ver;
	public String app_ver;
	public String sts;
	public String uuid;
	public String execute_yn;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	@Override
	public SAML001Vo toVo() {
		SAML001Vo vo = new SAML001Vo();
		
		vo.app_seq       = this.app_seq;        
		vo.dvic_model    = this.dvic_model;     
		vo.os_ver        = this.os_ver;         
		vo.app_ver       = this.app_ver;        
		vo.sts           = this.sts;            
		vo.uuid          = this.uuid;            
		vo.reg_dttm      = this.reg_dttm;       
		vo.mod_dttm      = this.mod_dttm;       
		vo.reg_usr       = this.reg_usr;        
		vo.mod_usr       = this.mod_usr;        
		
		return vo;
	}
}
