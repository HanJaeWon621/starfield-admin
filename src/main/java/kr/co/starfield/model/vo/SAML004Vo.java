package kr.co.starfield.model.vo;

import kr.co.starfield.model.SAML004;

public class SAML004Vo extends BaseVo{
	
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
	
	
	public SAML004 toModel() {
		SAML004 saml004 = new SAML004();
		
		saml004.read_seq            = this.read_seq;                    
		saml004.uuid                = this.uuid;                        
		saml004.cust_id             = this.cust_id;                     
		saml004.corp_cd             = this.corp_cd;                     
		saml004.prc_div             = this.prc_div;                     
		saml004.prc_info            = this.prc_info;                    
		saml004.sts                 = this.sts;                         
		saml004.reg_dttm            = this.reg_dttm;                    
		saml004.mod_dttm            = this.mod_dttm;                    
		saml004.reg_usr             = this.reg_usr;                     
		saml004.mod_usr             = this.mod_usr;                     
		
		
		return saml004;
	}


}
