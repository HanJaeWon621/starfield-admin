package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.model.MemberDevice;

public class SMBR005Vo extends BaseVo {

	public String mbr_dvic_seq;
	public String mbr_seq;
	public String dvic_id;
	public String push_token;
	public String alram_yn;
	public int pltf_type;
	public String end_arn;
	public String glob_arn;
	public String loc_info_coln_term_yn;
	public String app_mket_info_recep_yn;
	
	public Integer sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;	
	public String mod_usr;

	public MemberDevice toModel() {
		MemberDevice m = new MemberDevice();
		m.dvic_id = this.dvic_id;
		m.push_token = this.push_token;
		m.mbr_seq = this.mbr_seq;
		m.alram_yn = "Y".equals(this.alram_yn) ? true : false;
		m.pltf_type = this.pltf_type;
		m.end_arn = this.end_arn;
		m.glob_arn = this.glob_arn;
		m.loc_info_coln_term_yn = this.loc_info_coln_term_yn;
		m.app_mket_info_recep_yn = this.app_mket_info_recep_yn;
		m.sts = this.sts;
		
		return m;
	}

	@Override
	public String toString() {
		return "SMBR005Vo [mbr_dvic_seq=" + mbr_dvic_seq + ", mbr_seq=" + mbr_seq + ", dvic_id=" + dvic_id
				+ ", push_token=" + push_token + ", alram_yn=" + alram_yn + ", pltf_type=" + pltf_type + ", end_arn="
				+ end_arn + ", glob_arn=" + glob_arn + ", loc_info_coln_term_yn=" + loc_info_coln_term_yn
				+ ", app_mket_info_recep_yn=" + app_mket_info_recep_yn + ", sts=" + sts + ", reg_dttm=" + reg_dttm
				+ ", mod_dttm=" + mod_dttm + ", reg_usr=" + reg_usr + ", mod_usr=" + mod_usr + "]";
	}
}
