package kr.co.starfield.model;

import java.util.Date;

import kr.co.starfield.model.vo.SADM003Vo;

public class SADM003 extends BaseModel{

	public String auth_type_cd_seq;
	public String auth_nm;	
	public String auth_desc;	
	public int sts;	
	public Date reg_dttm;	
	public Date mod_dttm;	
	public String reg_usr;	
	public String mod_usr;
	
	public String getAuth_nm() {
		return auth_nm;
	}
	
	@Override
	public SADM003Vo toVo() {

		SADM003Vo vo = new SADM003Vo();
		
		vo.auth_type_cd_seq = this.auth_type_cd_seq;
		vo.auth_nm = this.auth_nm;
		vo.auth_desc = this.auth_desc;
		vo.sts = this.sts;
		vo.reg_dttm = this.reg_dttm;
		vo.mod_dttm = this.mod_dttm;
		vo.reg_usr = this.reg_usr;
		vo.mod_usr = this.mod_usr;
		
		return vo;
	}
}
