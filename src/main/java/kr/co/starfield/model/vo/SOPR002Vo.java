/*
 * NoticeVo.java	1.00 2016/06/016
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.Holiday;
/**
 *  SOPR002(HOLIDAY) 테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.06.23
 */

public class SOPR002Vo extends BaseVo {
	public String holiday_seq;
	public String bcn_cd;
	public String year;
	public String mont;
	public String day;
	
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public Holiday toModel() {
		Holiday model = new Holiday();
		
		model.holiday_seq = this.holiday_seq;
		model.bcn_cd = this.bcn_cd;
		model.year = this.year;
		model.mont = this.mont;
		model.day = this.day;
		
		model.sts = this.sts;
		model.reg_dttm = Dt.toStringDateTime(this.reg_dttm);
		model.mod_dttm = Dt.toStringDateTime(this.mod_dttm);
		model.reg_usr = this.reg_usr;
		model.mod_usr = this.mod_usr;
				
		return model;
	}

}
