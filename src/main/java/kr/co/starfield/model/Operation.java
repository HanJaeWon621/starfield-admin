package kr.co.starfield.model;


import kr.co.starfield.model.vo.SOPR001Vo;

public class Operation extends BaseModel {

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
	public int sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	@Override
	public SOPR001Vo toVo(){
		SOPR001Vo operationVo = new SOPR001Vo();
		
		operationVo.opr_info_seq = this.opr_info_seq;
		operationVo.bcn_cd = this.bcn_cd;
		operationVo.norm_day_open_hr_min = this.norm_day_open_hr_min;
		operationVo.norm_day_end_hr_min = this.norm_day_end_hr_min;    
		operationVo.weekend_open_hr_min = this.weekend_open_hr_min;  
		operationVo.weekend_end_hr_min = this.weekend_end_hr_min;        
		operationVo.reps_num1 = this.reps_num1;
		operationVo.reps_num2 = this.reps_num2;
		operationVo.reps_num3 = this.reps_num3;
		operationVo.srvc_cntr_num1 = this.srvc_cntr_num1;
		operationVo.srvc_cntr_num2 = this.srvc_cntr_num2;
		operationVo.srvc_cntr_num3 = this.srvc_cntr_num3;
		operationVo.reps_fax_num1 = this.reps_fax_num1;
		operationVo.reps_fax_num2 = this.reps_fax_num2;
		operationVo.reps_fax_num3 = this.reps_fax_num3;
		operationVo.reps_email = this.reps_email;
		operationVo.reps_nm = this.reps_nm;
		operationVo.reps_addr1 = this.reps_addr1;
		operationVo.reps_addr2 = this.reps_addr2;
		operationVo.sts = this.sts;
		operationVo.reg_usr = this.reg_usr;
		operationVo.mod_usr = this.mod_usr;
		
		return operationVo; 
	}


}
