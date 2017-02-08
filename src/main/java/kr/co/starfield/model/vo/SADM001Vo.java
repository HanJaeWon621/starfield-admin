package kr.co.starfield.model.vo;

import java.util.Date;

import javax.management.modelmbean.ModelMBeanOperationInfo;

import kr.co.starfield.model.SADM001;

/**
 * 관리자
 * @author jojong-gyun
 *
 */
public class SADM001Vo extends BaseVo {
	
	public int rnum;
	public String adm_seq;
	public String bcn_cd;
	public String role_seq;
	public String role_nm;
	public String role_desc;
	public String adm_id;
	public String adm_pw;
	public String adm_dept;
	public String adm_rank;
	public String adm_nm;
	public String adm_tel_num1;
	public String adm_tel_num2;
	public String adm_tel_num3;
	public String adm_cel_num1;
	public String adm_cel_num2;
	public String adm_cel_num3;
	public String adm_email;
	public String term_agre_yn;
	public Date term_agre_date;
	public int adm_log_in_fail_cnt;
	public String adm_acut_act_yn;
	public Date adm_pw_mod_day;
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	public int adm_pw_seq;
	
	public SADM001 toModel() {
		
		SADM001 model = new SADM001();
		
		model.rnum				  = this.rnum;
		model.adm_seq             = this.adm_seq;
		model.bcn_cd              = this.bcn_cd;
		model.role_seq            = this.role_seq;
		model.role_nm			  = this.role_nm;
		model.role_desc		      = this.role_desc;
		model.adm_id              = this.adm_id;
		model.adm_pw              = this.adm_pw;
		model.adm_dept            = this.adm_dept;
		model.adm_rank            = this.adm_rank;
		model.adm_nm              = this.adm_nm;
		model.adm_tel_num1        = this.adm_tel_num1;
		model.adm_tel_num2        = this.adm_tel_num2;
		model.adm_tel_num3        = this.adm_tel_num3;
		model.adm_cel_num1        = this.adm_cel_num1;
		model.adm_cel_num2        = this.adm_cel_num2;
		model.adm_cel_num3        = this.adm_cel_num3;
		model.adm_email			  = this.adm_email;
		model.term_agre_yn        = this.term_agre_yn;
		model.term_agre_date      = this.term_agre_date;
		model.adm_log_in_fail_cnt = this.adm_log_in_fail_cnt;
		model.adm_acut_act_yn     = this.adm_acut_act_yn;
		model.adm_pw_mod_day      = this.adm_pw_mod_day;
		model.sts                 = this.sts;
		model.reg_dttm            = this.reg_dttm;
		model.mod_dttm            = this.mod_dttm;
		model.reg_usr             = this.reg_usr;
		model.mod_usr             = this.mod_usr;
		model.adm_pw_seq          = this.adm_pw_seq;
		
		return model;
	}
	
}
