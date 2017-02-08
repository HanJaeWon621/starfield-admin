package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.Operation;
import kr.co.starfield.model.SAML003;

public class SAML003Vo extends BaseVo{
	
	public String log_seq;
	public String uuid;
	public String cust_id;
	public String mbr_div_cd;
	public String log_type;
	public String evt_div_cd1;
	public String evt_div_cd2;
	public String evt_val;
	public String sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	
	public SAML003 toModel() {
		SAML003 saml003 = new SAML003();
		
		saml003.log_seq     = this.log_seq;         
		saml003.uuid        = this.uuid;            
		saml003.cust_id     = this.cust_id;         
		saml003.mbr_div_cd  = this.mbr_div_cd;      
		saml003.log_type    = this.log_type;        
		saml003.evt_div_cd1 = this.evt_div_cd1;     
		saml003.evt_div_cd2 = this.evt_div_cd2;     
		saml003.evt_val     = this.evt_val;         
		saml003.sts         = this.sts;             
		saml003.reg_dttm    = this.reg_dttm;        
		saml003.mod_dttm    = this.mod_dttm;        
		saml003.reg_usr     = this.reg_usr;         
		saml003.mod_usr     = this.mod_usr;         
		
		
		return saml003;
	}


}
