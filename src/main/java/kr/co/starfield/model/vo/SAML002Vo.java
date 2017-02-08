package kr.co.starfield.model.vo;

import kr.co.starfield.model.SAML001;
import kr.co.starfield.model.SAML002;

public class SAML002Vo extends BaseVo{
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
	
	public String sh_text;
	public String sh_text_type;
	public String sh_strt_dt;
	public String sh_end_dt;
	public String sortColumn;
	public String sortColumnAscDesc;

	
	public SAML002 toModel() {
		SAML002 saml002 = new SAML002();
		
		saml002.log_seq     = this.log_seq;               
		saml002.uuid        = this.uuid;                  
		saml002.cust_id     = this.cust_id;               
		saml002.corp_cd     = this.corp_cd;               
		saml002.brnd_cd     = this.brnd_cd;               
		saml002.bcn_cd      = this.bcn_cd;                
		saml002.fl          = this.fl;                    
		saml002.zone_id     = this.zone_id;               
		saml002.x_ctn_cord  = this.x_ctn_cord;            
		saml002.y_ctn_cord  = this.y_ctn_cord;            
		saml002.sts         = this.sts;                   
		saml002.stay_time   = this.stay_time;                   
		saml002.reg_dttm    = this.reg_dttm;              
		saml002.mod_dttm    = this.mod_dttm;              
		saml002.reg_usr     = this.reg_usr;               
		saml002.mod_usr     = this.mod_usr;               
		
		return saml002;
	}


}
