package kr.co.starfield.model.vo;

import java.sql.Date;

import kr.co.starfield.model.AERL001;

public class AERL001VO extends BaseVo{
	public String LOG_SEQ; 
	public int STS;
	public Date REG_DTTM;
	public Date MOD_DTTM;
	public String REG_USR;
	public String MOD_USR;
	public String DATETIME;
	public int SEVERITY;
	public int ERR_CD;
	public String SERVER_NM;
	public String MODULE_NM;
	public String METHOD_NM;
	public String ERR_MSG;
	public String STACK_TRACE;
	public String rnum;
	public String order_key;
	public String order_type;
	public String sDate;
	public String eDate;
	
	public AERL001 toModel() {
		AERL001 model = new AERL001();
		
		model.LOG_SEQ = this.LOG_SEQ;
		model.DATETIME = this.DATETIME;
		model.ERR_CD = this.ERR_CD;
		model.ERR_MSG = this.ERR_MSG;
		model.METHOD_NM = this.METHOD_NM;
		model.MOD_DTTM = this.MOD_DTTM;
		model.MOD_USR = this.MOD_USR;
		model.REG_DTTM = this.REG_DTTM;
		model.REG_USR = this.REG_USR;
		model.SERVER_NM = this.SERVER_NM;
		model.SEVERITY = this.SEVERITY;
		model.STACK_TRACE = this.STACK_TRACE;
		model.STS = this.STS;
		model.MODULE_NM = this.MODULE_NM;
		model.rnum = this.rnum;
		
		return model;
		
	}
	
	
}
