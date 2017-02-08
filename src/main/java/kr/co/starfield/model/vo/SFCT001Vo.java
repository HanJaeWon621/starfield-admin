/*
 * SCMS002Vo.java	1.00 2016/06/016
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.Facility;

/**
 *  SFCT001Vo(facility master)
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author hhLee
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

public class SFCT001Vo extends BaseVo {
	public String conv_faci_seq;
	public String bcn_cd;
	public String img_seq_icon;
	public String img_seq_facl_image;
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
	public String exp_yn;
	public String use_amt_ko1;
	public String use_amt_ko2;
	public String use_amt_en1;
	public String use_amt_en2;
	public String reps_open_hr_min;
	public String reps_end_hr_min;
	public String opr_time_optn_guid_ko;
	public String opr_time_optn_guid_en;
	public String opr_time_optn_guid_cn;
	public String opr_time_optn_guid_jp;
	public String irgu_open_hr_min;
	public String irgu_end_hr_min;
	public int sort_ord;
	public String reps_tel_num1;
	public String reps_tel_num2;
	public String reps_tel_num3;
	public Integer sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public Facility toModel() {
		Facility model = new Facility();
		
		model.conv_faci_seq = this.conv_faci_seq;
		model.bcn_cd = this.bcn_cd;
		model.img_seq_icon = this.img_seq_icon;
		model.img_seq_facl_image = this.img_seq_facl_image;
		model.conv_faci_nm_ko = this.conv_faci_nm_ko;
		model.conv_faci_nm_en = this.conv_faci_nm_en;
		model.conv_faci_nm_cn = this.conv_faci_nm_cn;
		model.conv_faci_nm_jp = this.conv_faci_nm_jp;
		model.conv_faci_desc_ko = this.conv_faci_desc_ko;
		model.conv_faci_desc_en = this.conv_faci_desc_en;
		model.conv_faci_desc_cn = this.conv_faci_desc_cn;
		model.conv_faci_desc_jp = this.conv_faci_desc_jp;
		
		model.conv_faci_desc_dtl_ko1 = this.conv_faci_desc_dtl_ko1;
		model.conv_faci_desc_dtl_en1 = this.conv_faci_desc_dtl_en1;
		model.conv_faci_desc_dtl_cn1 = this.conv_faci_desc_dtl_cn1;
		model.conv_faci_desc_dtl_jp1 = this.conv_faci_desc_dtl_jp1;
		model.conv_faci_desc_dtl_ko2 = this.conv_faci_desc_dtl_ko2;
		model.conv_faci_desc_dtl_en2 = this.conv_faci_desc_dtl_en2;
		model.conv_faci_desc_dtl_cn2 = this.conv_faci_desc_dtl_cn2;
		model.conv_faci_desc_dtl_jp2 = this.conv_faci_desc_dtl_jp2;
		model.conv_faci_desc_dtl_ko3 = this.conv_faci_desc_dtl_ko3;
		model.conv_faci_desc_dtl_en3 = this.conv_faci_desc_dtl_en3;
		model.conv_faci_desc_dtl_cn3 = this.conv_faci_desc_dtl_cn3;
		model.conv_faci_desc_dtl_jp3 = this.conv_faci_desc_dtl_jp3;
		model.conv_faci_desc_dtl_ko4 = this.conv_faci_desc_dtl_ko4;
		model.conv_faci_desc_dtl_en4 = this.conv_faci_desc_dtl_en4;
		model.conv_faci_desc_dtl_cn4 = this.conv_faci_desc_dtl_cn4;
		model.conv_faci_desc_dtl_jp4 = this.conv_faci_desc_dtl_jp4;
		model.conv_faci_desc_dtl_ko5 = this.conv_faci_desc_dtl_ko5;
		model.conv_faci_desc_dtl_en5 = this.conv_faci_desc_dtl_en5;
		model.conv_faci_desc_dtl_cn5 = this.conv_faci_desc_dtl_cn5;
		model.conv_faci_desc_dtl_jp5 = this.conv_faci_desc_dtl_jp5;
		model.exp_yn = this.exp_yn;
		model.use_amt_ko1 = this.use_amt_ko1;
		model.use_amt_ko2 = this.use_amt_ko2;
		model.use_amt_en1 = this.use_amt_en1;
		model.use_amt_en2 = this.use_amt_en2;
		model.opr_time_optn_guid_ko = this.opr_time_optn_guid_ko;
		model.opr_time_optn_guid_en = this.opr_time_optn_guid_en;
		model.opr_time_optn_guid_cn = this.opr_time_optn_guid_cn;
		model.opr_time_optn_guid_jp = this.opr_time_optn_guid_jp;
		model.irgu_open_hr_min = this.irgu_open_hr_min;
		model.irgu_end_hr_min = this.irgu_end_hr_min;
		
		model.sort_ord = this.sort_ord;
		model.reps_open_hr_min = this.reps_open_hr_min;
		model.reps_end_hr_min = this.reps_end_hr_min;
		model.reps_tel_num1 = this.reps_tel_num1;
		model.reps_tel_num2 = this.reps_tel_num2;
		model.reps_tel_num3 = this.reps_tel_num3;
		model.sts = this.sts;
		model.reg_dttm = Dt.toStringDateTime(this.reg_dttm);
		model.mod_dttm = Dt.toStringDateTime(this.mod_dttm);
		model.reg_usr = this.reg_usr;
		model.mod_usr = this.mod_usr;
				
		return model;
	}

}
