package kr.co.starfield.model;

import kr.co.starfield.model.vo.SADM004Vo;

public class SADM004 extends BaseModel {

	public String adm_acc_log_seq;
	public String acc_id;
	public String acc_dttm;
	public String acc_ip;
	public String info_usr_id;
	public String acc_pefm;
	public String acc_pefm_dtl;
	public String req_uri;

	@Override
	public SADM004Vo toVo() {
		
		SADM004Vo vo = new SADM004Vo();
		
		vo.adm_acc_log_seq = this.adm_acc_log_seq;
		vo.acc_id = this.acc_id;
		vo.acc_dttm = this.acc_dttm;
		vo.acc_ip = this.acc_ip;
		vo.info_usr_id = this.info_usr_id;
		vo.acc_pefm = this.acc_pefm;
		vo.acc_pefm_dtl = this.acc_pefm_dtl;
		vo.req_uri = this.req_uri;

		return vo;
	}
	
}
