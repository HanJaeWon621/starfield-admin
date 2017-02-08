package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.Operation;

public class SOPR001Vo extends BaseVo{

	public String opr_info_seq;
	public String bcn_cd;
	public String norm_day_open_hr_min;
	public String norm_day_end_hr_min;
	public String weekend_open_hr_min;
	public String weekend_end_hr_min;
	public String reps_num1;
	public String reps_num2;
	public String reps_num3;
	public String srvc_cntr_num1;
	public String srvc_cntr_num2;
	public String srvc_cntr_num3;
	public String reps_fax_num1;
	public String reps_fax_num2;
	public String reps_fax_num3;
	public String reps_email;
	public String reps_nm;
	public String reps_addr1;
	public String reps_addr2;
	public String insta_token;
	public int  sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public Operation toModel(){
		Operation operation = new Operation();
		
		operation.opr_info_seq = this.opr_info_seq;
		operation.bcn_cd = this.bcn_cd;
		operation.norm_day_open_hr_min = this.norm_day_open_hr_min;
		operation.norm_day_end_hr_min = this.norm_day_end_hr_min;
		operation.weekend_open_hr_min = this.weekend_open_hr_min;
		operation.weekend_end_hr_min = this.weekend_end_hr_min;
		operation.reps_num1 = this.reps_num1;
		operation.reps_num2 = this.reps_num2;
		operation.reps_num3 = this.reps_num3;
		operation.srvc_cntr_num1 = this.srvc_cntr_num1;
		operation.srvc_cntr_num2 = this.srvc_cntr_num2;
		operation.srvc_cntr_num3 = this.srvc_cntr_num3;
		operation.reps_fax_num1 = this.reps_fax_num1;
		operation.reps_fax_num2 = this.reps_fax_num2;
		operation.reps_fax_num3 = this.reps_fax_num3;
		operation.reps_email = this.reps_email;
		operation.reps_nm = this.reps_nm;
		operation.reps_addr1 = this.reps_addr1;
		operation.reps_addr2 = this.reps_addr2;
		operation.sts = this.sts;
		operation.reg_dttm = Dt.toStringDateTime(this.reg_dttm);
		operation.mod_dttm = Dt.toStringDateTime(this.mod_dttm);
		operation.reg_usr = this.reg_usr;
		operation.mod_usr = this.mod_usr;
 
		return operation;
	}


}
