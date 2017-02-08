package kr.co.starfield.model.vo;

import kr.co.starfield.model.SAML001;

public class SAML001Vo extends BaseVo{
	public String app_seq;
	public String dvic_model;
	public String os_ver;
	public String app_ver;
	public String sts;
	public String uuid;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public SAML001 toModel() {
		SAML001 saml001 = new SAML001();
		
		saml001.app_seq       = this.app_seq;        
		saml001.dvic_model    = this.dvic_model;     
		saml001.os_ver        = this.os_ver;         
		saml001.app_ver       = this.app_ver;        
		saml001.sts           = this.sts;            
		saml001.uuid          = this.uuid;            
		saml001.reg_dttm      = this.reg_dttm;       
		saml001.mod_dttm      = this.mod_dttm;       
		saml001.reg_usr       = this.reg_usr;        
		saml001.mod_usr       = this.mod_usr;
		
		return saml001;
	}


}
