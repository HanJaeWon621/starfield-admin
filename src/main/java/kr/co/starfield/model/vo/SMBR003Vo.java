package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.LikeTenant;

public class SMBR003Vo extends BaseVo{
	public String mbr_seq;
	public String tnt_seq;
	public String bcn_cd;
	public Integer sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public LikeTenant toModel(){
		LikeTenant likeTenant = new LikeTenant();
		
		likeTenant.mbr_seq = this.mbr_seq;
		likeTenant.bcn_cd = this.bcn_cd;
		likeTenant.tnt_seq = this.tnt_seq;
		
		return likeTenant; 
	}
	
	
	
}
