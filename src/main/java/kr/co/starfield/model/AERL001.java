package kr.co.starfield.model;

import java.sql.Date;

import kr.co.starfield.model.vo.AERL001VO;

public class AERL001 extends BaseModel {
	
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
	
	
	@Override
	public AERL001VO toVo() {
		
		AERL001VO vo = new AERL001VO();
		
		vo.LOG_SEQ = this.LOG_SEQ;
		vo.STS = this.STS;
		vo.REG_DTTM = this.REG_DTTM;
		vo.MOD_DTTM = this.MOD_DTTM;
		vo.REG_USR = this.REG_USR;
		vo.MOD_USR = this.MOD_USR;
		vo.DATETIME = this.DATETIME;
		vo.SEVERITY = this.SEVERITY;
		vo.ERR_CD = this.ERR_CD;
		vo.SERVER_NM = this.SERVER_NM;
		vo.MODULE_NM = this.MODULE_NM;
		vo.METHOD_NM = this.METHOD_NM;
		vo.ERR_MSG = this.ERR_MSG;
		vo.STACK_TRACE = this.STACK_TRACE;
		
		
		return vo;
	}


	@Override
	public String toString() {
		return "AERL001 [LOG_SEQ=" + LOG_SEQ + ", STS=" + STS + ", REG_DTTM=" + REG_DTTM + ", MOD_DTTM=" + MOD_DTTM
				+ ", REG_USR=" + REG_USR + ", MOD_USR=" + MOD_USR + ", DATETIME=" + DATETIME + ", SEVERITY=" + SEVERITY
				+ ", ERR_CD=" + ERR_CD + ", SERVER_NM=" + SERVER_NM + ", MODULE_NM=" + MODULE_NM + ", METHOD_NM="
				+ METHOD_NM + ", ERR_MSG=" + ERR_MSG + ", STACK_TRACE=" + STACK_TRACE + "]";
	}
	
}
