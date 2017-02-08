package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kr.co.starfield.model.vo.STNT002Vo;
import kr.co.starfield.model.vo.SMBR001Vo;
import kr.co.starfield.model.vo.STNT001Vo;

public class Member {
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
	
	public String stf_mbr_ship_agre_yn;
	public String prvc_coln_use_agre_yn;
	public String stf_mbr_third_party_agre_yn;
	public String mket_info_sms_agre_yn;
	public String mket_info_email_agre_yn;
	public String mket_info_dm_agre_yn;
	public String mket_info_tm_agre_yn;
	public String third_party_info_agre_yn;
	
	public String uuid;
	public String mbr_div;
	
	public Integer sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr; 
	
	public String point_card_bcd;
	public int tot_cnt;
	
	public SMBR001Vo toVo() {
		SMBR001Vo vo = new SMBR001Vo();
		
		vo.no = this.no;
		vo.mbr_seq = this.mbr_seq;
		vo.bcn_cd = this.bcn_cd;
		vo.cust_id = this.cust_id;
		vo.mbr_nm = this.mbr_nm;
		vo.mbr_sex = this.mbr_sex;
		vo.mbr_age = this.mbr_age;
		vo.mbr_cel_num1 = this.mbr_cel_num1;
		vo.mbr_cel_num2 = this.mbr_cel_num2;
		vo.mbr_cel_num3 = this.mbr_cel_num3;
		vo.quie_acut_yn = this.quie_acut_yn;
		vo.stf_off_join_yn = this.stf_off_join_yn;
		vo.stf_point_card_num = this.stf_point_card_num;
		
		
		vo.stf_mbr_ship_agre_yn = this.stf_mbr_ship_agre_yn;
		vo.prvc_coln_use_agre_yn = this.prvc_coln_use_agre_yn;
		vo.stf_mbr_third_party_agre_yn = this.stf_mbr_third_party_agre_yn;
		vo.mket_info_sms_agre_yn = this.mket_info_sms_agre_yn;
		vo.mket_info_email_agre_yn = this.mket_info_email_agre_yn;
		vo.mket_info_dm_agre_yn = this.mket_info_dm_agre_yn;
		vo.mket_info_tm_agre_yn = this.mket_info_tm_agre_yn;
		vo.third_party_info_agre_yn = this.third_party_info_agre_yn;
		
		
		vo.uuid = this.uuid;
		vo.mbr_div = this.mbr_div;
		     
		vo.point_card_bcd = this.point_card_bcd;
		
		return vo;		
	}
	
	@Override
	public String toString() {
		return "Member [mbr_seq=" + mbr_seq + ", bcn_cd=" + bcn_cd + ", cust_id=" + cust_id
				+ ", mbr_nm=" + mbr_nm + ", mbr_sex=" + mbr_sex + ", mbr_age=" + mbr_age
				+ ", mbr_cel_num1=" + mbr_cel_num1 + ", mbr_cel_num2=" + mbr_cel_num2 + ", mbr_cel_num3=" + mbr_cel_num3
				+ ", quie_acut_yn=" + quie_acut_yn + ", stf_off_join_yn=" + stf_off_join_yn 
				+ ", stf_point_card_num=" + stf_point_card_num + ", stf_mbr_ship_agre_yn=" + stf_mbr_ship_agre_yn 
				+ ", prvc_coln_use_agre_yn=" + prvc_coln_use_agre_yn + ", uuid=" + uuid + ", mbr_div=" + mbr_div + "]";
	}
}
