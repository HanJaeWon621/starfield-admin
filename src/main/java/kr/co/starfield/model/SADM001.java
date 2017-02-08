package kr.co.starfield.model;

import java.util.Date;
import kr.co.starfield.model.vo.SADM001Vo;

public class SADM001 extends BaseModel {
	
	public int rnum;
	public String adm_seq;
	public String bcn_cd;
	public String role_seq;
	public String role_nm;
	public String role_desc;
	public String adm_id;
	public String adm_pw;
	public String adm_new_pw;
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
	public String sts_desc;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	public int adm_pw_seq;
	
	@Override
	public SADM001Vo toVo() {
		
		SADM001Vo vo = new SADM001Vo();
		
		vo.rnum				   = this.rnum; 
		vo.adm_seq             = this.adm_seq;
		vo.bcn_cd              = this.bcn_cd;
		vo.role_seq            = this.role_seq;
		vo.role_nm			   = this.role_nm;
		vo.role_desc		   = this.role_desc;
		vo.adm_id              = this.adm_id;
		vo.adm_pw              = this.adm_pw;
		vo.adm_dept            = this.adm_dept;
		vo.adm_rank            = this.adm_rank;
		vo.adm_nm              = this.adm_nm;
		vo.adm_tel_num1        = this.adm_tel_num1;
		vo.adm_tel_num2        = this.adm_tel_num2;
		vo.adm_tel_num3        = this.adm_tel_num3;
		vo.adm_cel_num1        = this.adm_cel_num1;
		vo.adm_cel_num2        = this.adm_cel_num2;
		vo.adm_cel_num3        = this.adm_cel_num3;
		vo.adm_email		   = this.adm_email;
		vo.term_agre_yn        = this.term_agre_yn;
		vo.term_agre_date      = this.term_agre_date;
		vo.adm_log_in_fail_cnt = this.adm_log_in_fail_cnt;
		vo.adm_acut_act_yn     = this.adm_acut_act_yn;
		vo.adm_pw_mod_day      = this.adm_pw_mod_day;
		vo.sts                 = this.sts;
		vo.reg_dttm            = this.reg_dttm;
		vo.mod_dttm            = this.mod_dttm;
		vo.reg_usr             = this.reg_usr;
		vo.mod_usr             = this.mod_usr;
		vo.adm_pw_seq          = this.adm_pw_seq;
		
		return vo;
	}

}
