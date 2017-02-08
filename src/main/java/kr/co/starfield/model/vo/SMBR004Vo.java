package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.LoginLog;

public class SMBR004Vo extends BaseVo{
	public String log_in_log_seq;
	public String mbr_seq;
	public String log_in_ip;
	public Date log_in_dttm;
	
	public LoginLog toModel(){
		LoginLog model = new LoginLog();
		
		model.log_in_log_seq = this.log_in_log_seq;
		model.mbr_seq = this.mbr_seq;
		model.log_in_ip = this.log_in_ip;
		model.log_in_dttm = Dt.toStringDateTime(this.log_in_dttm);
		
		return model; 
	}
}
