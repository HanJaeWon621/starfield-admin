package kr.co.starfield.model;

import kr.co.starfield.model.vo.SOPR004Vo;

/**
 *  starFieldHoliday model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.07.11
 */

public class TenantHoliday extends BaseModel {
	public String tnt_irgu_opr_seq;
	public String tnt_seq;
	public String bcn_cd;
	public String year;
	public String mont;
	public String day;
	public String open_hr_min;
	public String end_hr_min;
	
	public int sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	@Override
	public SOPR004Vo toVo() {
		SOPR004Vo vo = new SOPR004Vo();
		vo.tnt_irgu_opr_seq = this.tnt_irgu_opr_seq;
		vo.tnt_seq = this.tnt_seq;
		vo.bcn_cd = this.bcn_cd;
		vo.year = this.year;
		vo.mont = this.mont;
		vo.day = this.day;
		vo.open_hr_min = this.open_hr_min;
		vo.end_hr_min = this.end_hr_min;
		
		vo.sts = this.sts;
		vo.reg_usr = this.reg_usr;
		vo.mod_usr = this.mod_usr;
		
		return vo;
	}
}
