package kr.co.starfield.model;

import java.util.Date;

import kr.co.starfield.model.vo.SADM002Vo;

public class SADM002 extends BaseModel {

	public String role_seq;
	public String role_nm;
	public String role_desc;
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	@Override
	public SADM002Vo toVo() {

		SADM002Vo vo = new SADM002Vo();
		
		vo.role_seq = this.role_seq;
		vo.role_nm = this.role_nm;
		vo.role_desc = this.role_desc;
		vo.sts = this.sts;
		vo.reg_dttm = this.reg_dttm;
		vo.mod_dttm = this.mod_dttm;
		vo.reg_usr = this.reg_usr;
		vo.mod_usr = this.mod_usr;
		
		return vo;
	}
}
