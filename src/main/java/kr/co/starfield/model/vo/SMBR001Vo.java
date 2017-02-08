/**
 * SMBR001테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author hhLee
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.Member;

public class SMBR001Vo extends BaseVo{

	public String no;
	public String mbr_seq;
	public String bcn_cd;
	public String cust_id;
	public String mbr_nm;
	public String mbr_sex;
	public String mbr_age;
	public String mbr_cel_num1;
	public String mbr_cel_num2;
	public String mbr_cel_num3;
	public String quie_acut_yn;
	public String stf_off_join_yn;
	public String stf_point_card_num;
	public String uuid;
	public String mbr_div;
	
	public String stf_mbr_ship_agre_yn;
	public String prvc_coln_use_agre_yn;
	public String stf_mbr_third_party_agre_yn;
	public String mket_info_sms_agre_yn;
	public String mket_info_email_agre_yn;
	public String mket_info_dm_agre_yn;
	public String mket_info_tm_agre_yn;
	public String third_party_info_agre_yn;
	
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	public String point_card_bcd;
	public String abst_no;
	public String memb_nm_ko;
	public String sh_text_type;
	public String sh_text;
	
	public Member toModel() {
		
		Member model = new Member();
		
		model.no = this.no;
		model.mbr_seq = this.mbr_seq;
		model.bcn_cd = this.bcn_cd;
		model.cust_id = this.cust_id;
		model.mbr_nm = this.mbr_nm;
		model.mbr_sex = this.mbr_sex;
		model.mbr_age = this.mbr_age;
		model.mbr_cel_num1 = this.mbr_cel_num1;
		model.mbr_cel_num2 = this.mbr_cel_num2;
		model.mbr_cel_num3 = this.mbr_cel_num3;
		model.quie_acut_yn = this.quie_acut_yn;
		model.stf_off_join_yn = this.stf_off_join_yn;
		model.stf_point_card_num = this.stf_point_card_num;
		
		model.stf_mbr_ship_agre_yn = this.stf_mbr_ship_agre_yn;
		model.prvc_coln_use_agre_yn = this.prvc_coln_use_agre_yn;
		model.stf_mbr_third_party_agre_yn = this.stf_mbr_third_party_agre_yn;
		model.mket_info_sms_agre_yn = this.mket_info_sms_agre_yn;
		model.mket_info_email_agre_yn = this.mket_info_email_agre_yn;
		model.mket_info_dm_agre_yn = this.mket_info_dm_agre_yn;
		model.mket_info_tm_agre_yn = this.mket_info_tm_agre_yn;
		model.third_party_info_agre_yn = this.third_party_info_agre_yn;
		
		model.uuid = this.uuid;
		model.mbr_div = this.mbr_div;
		
		model.point_card_bcd = this.point_card_bcd;
		
		model.sts = this.sts;
		model.reg_dttm = Dt.toStringDateTime(this.reg_dttm);
		model.mod_dttm = Dt.toStringDateTime(this.mod_dttm);
		model.reg_usr = this.reg_usr;
		model.mod_usr = this.mod_usr;

		model.tot_cnt = this.tot_cnt;
		
		return model; 
	}
}
