package kr.co.starfield.model;

import kr.co.starfield.common.CommonCode.Facility.FacilityType;
import kr.co.starfield.model.vo.SFCT001Vo;

/**
 *  Facility Group model
 *
 * @author yhkim
 * @version	1.0
 * @see
 * @date 2016.10.04
 */

public class FacilityGroup extends BaseModel {
	
	public String conv_faci_seq;
	public String bcn_cd;
	
	public FacilityType faci_type;

	public String conv_faci_nm_ko;
	public String conv_faci_nm_en;
	public String conv_faci_nm_cn;
	public String conv_faci_nm_jp;
	public String conv_faci_desc_ko;
	public String conv_faci_desc_en;
	public String conv_faci_desc_cn;
	public String conv_faci_desc_jp;
	
	public String conv_faci_desc_dtl_ko1;
	public String conv_faci_desc_dtl_en1;
	public String conv_faci_desc_dtl_cn1;
	public String conv_faci_desc_dtl_jp1;
	public String conv_faci_desc_dtl_ko2;
	public String conv_faci_desc_dtl_en2;
	public String conv_faci_desc_dtl_cn2;
	public String conv_faci_desc_dtl_jp2;
	public String conv_faci_desc_dtl_ko3;
	public String conv_faci_desc_dtl_en3;
	public String conv_faci_desc_dtl_cn3;
	public String conv_faci_desc_dtl_jp3;
	public String conv_faci_desc_dtl_ko4;
	public String conv_faci_desc_dtl_en4;
	public String conv_faci_desc_dtl_cn4;
	public String conv_faci_desc_dtl_jp4;
	public String conv_faci_desc_dtl_ko5;
	public String conv_faci_desc_dtl_en5;
	public String conv_faci_desc_dtl_cn5;
	public String conv_faci_desc_dtl_jp5;

	public String reps_open_hr_min;
	public String reps_end_hr_min;
	public String irgu_open_hr_min;
	public String irgu_end_hr_min;

	public String reps_tel_num1;
	public String reps_tel_num2;
	public String reps_tel_num3;
	
	public int sort_ord;
	public Integer sts;
    public String img_seq_icon_uri;
    public String img_seq_icon;
    public String img_seq_facl_image_uri;
    public String img_seq_facl_image;

	@Override
	public SFCT001Vo toVo() {
		SFCT001Vo vo = new SFCT001Vo();
		
		vo.conv_faci_seq = this.conv_faci_seq;
		vo.bcn_cd = this.bcn_cd;
		vo.img_seq_icon = this.img_seq_icon;
		vo.img_seq_facl_image = this.img_seq_facl_image;
		vo.conv_faci_nm_ko = this.conv_faci_nm_ko;
		vo.conv_faci_nm_en = this.conv_faci_nm_en;
		vo.conv_faci_nm_cn = this.conv_faci_nm_cn;
		vo.conv_faci_nm_jp = this.conv_faci_nm_jp;
		vo.conv_faci_desc_ko = this.conv_faci_desc_ko;
		vo.conv_faci_desc_en = this.conv_faci_desc_en;
		vo.conv_faci_desc_cn = this.conv_faci_desc_cn;
		vo.conv_faci_desc_jp = this.conv_faci_desc_jp;
		
		vo.conv_faci_desc_dtl_ko1 = this.conv_faci_desc_dtl_ko1;
		vo.conv_faci_desc_dtl_en1 = this.conv_faci_desc_dtl_en1;
		vo.conv_faci_desc_dtl_cn1 = this.conv_faci_desc_dtl_cn1;
		vo.conv_faci_desc_dtl_jp1 = this.conv_faci_desc_dtl_jp1;
		vo.conv_faci_desc_dtl_ko2 = this.conv_faci_desc_dtl_ko2;
		vo.conv_faci_desc_dtl_en2 = this.conv_faci_desc_dtl_en2;
		vo.conv_faci_desc_dtl_cn2 = this.conv_faci_desc_dtl_cn2;
		vo.conv_faci_desc_dtl_jp2 = this.conv_faci_desc_dtl_jp2;
		vo.conv_faci_desc_dtl_ko3 = this.conv_faci_desc_dtl_ko3;
		vo.conv_faci_desc_dtl_en3 = this.conv_faci_desc_dtl_en3;
		vo.conv_faci_desc_dtl_cn3 = this.conv_faci_desc_dtl_cn3;
		vo.conv_faci_desc_dtl_jp3 = this.conv_faci_desc_dtl_jp3;
		vo.conv_faci_desc_dtl_ko4 = this.conv_faci_desc_dtl_ko4;
		vo.conv_faci_desc_dtl_en4 = this.conv_faci_desc_dtl_en4;
		vo.conv_faci_desc_dtl_cn4 = this.conv_faci_desc_dtl_cn4;
		vo.conv_faci_desc_dtl_jp4 = this.conv_faci_desc_dtl_jp4;
		vo.conv_faci_desc_dtl_ko5 = this.conv_faci_desc_dtl_ko5;
		vo.conv_faci_desc_dtl_en5 = this.conv_faci_desc_dtl_en5;
		vo.conv_faci_desc_dtl_cn5 = this.conv_faci_desc_dtl_cn5;
		vo.conv_faci_desc_dtl_jp5 = this.conv_faci_desc_dtl_jp5;
		vo.exp_yn = this.faci_type != null ? this.faci_type.getCodeCd() : null;
		vo.reps_open_hr_min = this.reps_open_hr_min;
		vo.reps_end_hr_min = this.reps_end_hr_min;
		vo.irgu_open_hr_min = this.irgu_open_hr_min;
		vo.irgu_end_hr_min = this.irgu_end_hr_min;
		
		vo.sort_ord = this.sort_ord;
		vo.reps_open_hr_min = this.reps_open_hr_min;
		vo.reps_end_hr_min = this.reps_end_hr_min;
		vo.reps_tel_num1 = this.reps_tel_num1;
		vo.reps_tel_num2 = this.reps_tel_num2;
		vo.reps_tel_num3 = this.reps_tel_num3;
		vo.sts = this.sts;
		vo.reg_usr = this.user;
		vo.mod_usr = this.user;		
		
		return vo;
	}

	@Override
	public String toString() {
		return "FacilityGroup [conv_faci_seq=" + conv_faci_seq + ", bcn_cd=" + bcn_cd + ", faci_type=" + faci_type
				+ ", conv_faci_nm_ko=" + conv_faci_nm_ko + ", conv_faci_nm_en=" + conv_faci_nm_en + ", conv_faci_nm_cn="
				+ conv_faci_nm_cn + ", conv_faci_nm_jp=" + conv_faci_nm_jp + ", conv_faci_desc_ko=" + conv_faci_desc_ko
				+ ", conv_faci_desc_en=" + conv_faci_desc_en + ", conv_faci_desc_cn=" + conv_faci_desc_cn
				+ ", conv_faci_desc_jp=" + conv_faci_desc_jp + ", conv_faci_desc_dtl_ko1=" + conv_faci_desc_dtl_ko1
				+ ", conv_faci_desc_dtl_en1=" + conv_faci_desc_dtl_en1 + ", conv_faci_desc_dtl_cn1="
				+ conv_faci_desc_dtl_cn1 + ", conv_faci_desc_dtl_jp1=" + conv_faci_desc_dtl_jp1
				+ ", conv_faci_desc_dtl_ko2=" + conv_faci_desc_dtl_ko2 + ", conv_faci_desc_dtl_en2="
				+ conv_faci_desc_dtl_en2 + ", conv_faci_desc_dtl_cn2=" + conv_faci_desc_dtl_cn2
				+ ", conv_faci_desc_dtl_jp2=" + conv_faci_desc_dtl_jp2 + ", conv_faci_desc_dtl_ko3="
				+ conv_faci_desc_dtl_ko3 + ", conv_faci_desc_dtl_en3=" + conv_faci_desc_dtl_en3
				+ ", conv_faci_desc_dtl_cn3=" + conv_faci_desc_dtl_cn3 + ", conv_faci_desc_dtl_jp3="
				+ conv_faci_desc_dtl_jp3 + ", conv_faci_desc_dtl_ko4=" + conv_faci_desc_dtl_ko4
				+ ", conv_faci_desc_dtl_en4=" + conv_faci_desc_dtl_en4 + ", conv_faci_desc_dtl_cn4="
				+ conv_faci_desc_dtl_cn4 + ", conv_faci_desc_dtl_jp4=" + conv_faci_desc_dtl_jp4
				+ ", conv_faci_desc_dtl_ko5=" + conv_faci_desc_dtl_ko5 + ", conv_faci_desc_dtl_en5="
				+ conv_faci_desc_dtl_en5 + ", conv_faci_desc_dtl_cn5=" + conv_faci_desc_dtl_cn5
				+ ", conv_faci_desc_dtl_jp5=" + conv_faci_desc_dtl_jp5 + ", reps_open_hr_min=" + reps_open_hr_min
				+ ", reps_end_hr_min=" + reps_end_hr_min + ", irgu_open_hr_min=" + irgu_open_hr_min
				+ ", irgu_end_hr_min=" + irgu_end_hr_min + ", reps_tel_num1=" + reps_tel_num1 + ", reps_tel_num2="
				+ reps_tel_num2 + ", reps_tel_num3=" + reps_tel_num3 + ", sort_ord=" + sort_ord + ", sts=" + sts
				+ ", img_seq_icon_uri=" + img_seq_icon_uri + ", img_seq_facl_image_uri=" + img_seq_facl_image_uri + "]";
	}
}
