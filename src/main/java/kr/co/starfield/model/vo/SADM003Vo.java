package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.model.SADM003;

public class SADM003Vo extends BaseVo {

	public String auth_type_cd_seq;
	public String auth_nm;	
	public String auth_desc;	
	public int sts;	
	public Date reg_dttm;	
	public Date mod_dttm;	
	public String reg_usr;	
	public String mod_usr;
	
	public SADM003 toModel() {

		SADM003 model = new SADM003();
		
		model.auth_type_cd_seq = this.auth_type_cd_seq;
		model.auth_nm = this.auth_nm;
		model.auth_desc = this.auth_desc;
		model.sts = this.sts;
		model.reg_dttm = this.reg_dttm;
		model.mod_dttm = this.mod_dttm;
		model.reg_usr = this.reg_usr;
		model.mod_usr = this.mod_usr;
		
		return model;
	}
	
	
	
}
