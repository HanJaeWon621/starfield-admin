
package kr.co.starfield.model;

import kr.co.starfield.model.vo.SMBR004Vo;

public class LoginLog extends BaseModel {
	public String log_in_log_seq;
	public String mbr_seq;
	public String log_in_ip;
	public String log_in_dttm;
	@Override
	public SMBR004Vo toVo() {
		SMBR004Vo vo = new SMBR004Vo();
		vo.log_in_log_seq = this .log_in_log_seq;
		vo.mbr_seq = this .mbr_seq;
		vo.log_in_ip = this .log_in_ip;
		
		return vo;
	}
	
}
