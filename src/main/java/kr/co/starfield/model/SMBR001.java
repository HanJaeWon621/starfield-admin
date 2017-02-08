/*
 * Emp.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.SCPN001_DVo;
import kr.co.starfield.model.vo.SMBR001Vo;

/**
 *  SMBR001 model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class SMBR001 extends BaseModel {
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
	public String mket_info_sms_agre_yn;
	public String mket_info_email_agre_yn;
	public String mket_info_dm_agre_yn;
	public String mket_info_tm_agre_yn;
	public String third_party_info_agre_yn;
	public String uuid;
	public String mbr_div;
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	public String app_tgt_mbr_div_cd;
	public String tot_cnt;
	public String list_tot_cnt;
	public String point_card_bcd;
	public String fnum;
	public String point_card_bcd_uri;
	public String mbr_div_cd1;
	public String mbr_div_cd2;
	
	public String app_tgt_age_all;
	public String app_tgt_age_20;
	public String app_tgt_age_30;
	public String app_tgt_age_40;
	public String app_tgt_age_50;
	
	@Override
	public SMBR001Vo toVo() {
		SMBR001Vo vo = new SMBR001Vo();
		
		vo.mbr_seq             =   this.mbr_seq;                                  
		vo.bcn_cd              =   this.bcn_cd;                                   
		vo.cust_id             =   this.cust_id;                                  
		vo.mbr_nm              =   this.mbr_nm;                                   
		vo.mbr_sex             =   this.mbr_sex;                                  
		vo.mbr_age             =   this.mbr_age;                                  
		vo.mbr_cel_num1        =   this.mbr_cel_num1;                             
		vo.mbr_cel_num2        =   this.mbr_cel_num2;                             
		vo.mbr_cel_num3        =   this.mbr_cel_num3;                             
		vo.quie_acut_yn        =   this.quie_acut_yn;                             
		vo.stf_off_join_yn     =   this.stf_off_join_yn;                          
		vo.stf_point_card_num  =   this.stf_point_card_num;                       
		vo.stf_mbr_ship_agre_yn = this.stf_mbr_ship_agre_yn;
		vo.prvc_coln_use_agre_yn = this.prvc_coln_use_agre_yn;
		vo.mket_info_sms_agre_yn = this.mket_info_sms_agre_yn;
		vo.mket_info_email_agre_yn = this.mket_info_email_agre_yn;
		vo.mket_info_dm_agre_yn = this.mket_info_dm_agre_yn;
		vo.mket_info_tm_agre_yn = this.mket_info_tm_agre_yn;
		vo.third_party_info_agre_yn = this.third_party_info_agre_yn;                    
		vo.uuid                =   this.uuid;                                     
		vo.mbr_div             =   this.mbr_div;                                  
		vo.sts                 =   this.sts;                                      
		vo.reg_dttm            =   this.reg_dttm;                                 
		vo.mod_dttm            =   this.mod_dttm;                                 
		vo.reg_usr             =   this.reg_usr;                                  
		vo.mod_usr             =   this.mod_usr;
		vo.point_card_bcd      =   this.point_card_bcd;
				
		return vo;
	}
}
