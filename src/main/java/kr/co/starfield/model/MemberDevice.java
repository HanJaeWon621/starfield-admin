package kr.co.starfield.model;

import kr.co.starfield.model.vo.SMBR005Vo;

public class MemberDevice extends BaseModel {
	public String mbr_seq;
	public String dvic_id;
	public String push_token;
	public Boolean alram_yn;
	public int pltf_type;
	public String end_arn;
	public String glob_arn;
	public String loc_info_coln_term_yn;
	public String app_mket_info_recep_yn;
	public Integer sts;
	
	
	@Override
	public SMBR005Vo toVo() {
		
		SMBR005Vo vo = new SMBR005Vo();
		vo.dvic_id = this.dvic_id;
		vo.push_token = this.push_token;
		vo.mbr_seq = this.mbr_seq;
		vo.alram_yn = this.alram_yn == null ? null : (this.alram_yn ? "Y" : "N");
		vo.pltf_type = this.pltf_type;
		vo.end_arn = this.end_arn;
		vo.glob_arn = this.glob_arn;
		vo.loc_info_coln_term_yn = this.loc_info_coln_term_yn;
		vo.app_mket_info_recep_yn = this.app_mket_info_recep_yn;
		vo.sts = this.sts;
		
		vo.reg_usr = this.user;
		vo.mod_usr = this.user;
		
		return vo;
	}

	@Override
	public String toString() {
		return "MemberDevice [mbr_seq=" + mbr_seq + ", dvic_id=" + dvic_id + ", push_token=" + push_token
				+ ", alram_yn=" + alram_yn + ", pltf_type=" + pltf_type + ", end_arn=" + end_arn + ", glob_arn="
				+ glob_arn + ", sts=" + sts + "]";
	}
	
}
