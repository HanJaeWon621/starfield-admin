package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.TenantHoliday;
/**
 *  SOPR004 테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.07.11
 */

public class SOPR004Vo extends BaseVo {
	public String tnt_irgu_opr_seq;
	public String tnt_seq;
	public String bcn_cd;
	public String year;
	public String mont;
	public String day;
	public String open_hr_min;
	public String end_hr_min;
	
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public TenantHoliday toModel() {
		TenantHoliday model = new TenantHoliday();
		
		model.tnt_irgu_opr_seq = this.tnt_irgu_opr_seq;
		model.tnt_seq = this.tnt_seq;
		model.bcn_cd = this.bcn_cd;
		model.year = this.year;
		model.mont = this.mont;
		model.day = this.day;
		model.open_hr_min = this.open_hr_min;
		model.end_hr_min = this.end_hr_min;
		
		model.sts = this.sts;
		model.reg_dttm = Dt.toStringDateTime(this.reg_dttm);
		model.mod_dttm = Dt.toStringDateTime(this.mod_dttm);
		model.reg_usr = this.reg_usr;
		model.mod_usr = this.mod_usr;
				
		return model;
	}

}
