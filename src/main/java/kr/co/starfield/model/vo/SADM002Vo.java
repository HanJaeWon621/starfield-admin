package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.model.SADM002;

public class SADM002Vo extends BaseVo {
	
	public String role_seq;
	public String role_nm;
	public String role_desc;
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public SADM002 toModel() {
		
		SADM002 model = new SADM002();
		
		model.role_seq = this.role_seq;
		model.role_nm = this.role_nm;
		model.role_desc = this.role_desc;
		model.sts = this.sts;
		model.reg_dttm = this.reg_dttm;
		model.mod_dttm = this.mod_dttm;
		model.reg_usr = this.reg_usr;
		model.mod_usr = this.mod_usr;

		return model;
	}

}
