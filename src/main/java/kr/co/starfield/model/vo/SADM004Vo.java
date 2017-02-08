package kr.co.starfield.model.vo;

import kr.co.starfield.model.SADM004;

public class SADM004Vo extends BaseVo {

	public String adm_acc_log_seq;
	public String acc_id;
	public String acc_dttm;
	public String acc_ip;
	public String info_usr_id;
	public String acc_pefm;
	public String acc_pefm_dtl;
	public String req_uri;
	
	public SADM004 toModel() {
		
		SADM004 sadm004 = new SADM004();
		
		sadm004.adm_acc_log_seq = this.adm_acc_log_seq;
		sadm004.acc_id = this.acc_id;
		sadm004.acc_dttm = this.acc_dttm;
		sadm004.acc_ip = this.acc_ip;
		sadm004.info_usr_id = this.info_usr_id;
		sadm004.acc_pefm = this.acc_pefm;
		sadm004.acc_pefm_dtl = this.acc_pefm_dtl;
		sadm004.req_uri = this.req_uri;

		return sadm004;
	}
}
