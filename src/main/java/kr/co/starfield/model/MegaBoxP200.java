/*
 * MegaBoxP200.java	1.00 2016/06/16
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import kr.co.starfield.model.vo.AITF001Vo;

/**
 *  MegaBoxP200 model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.06.20
 */

public class MegaBoxP200 extends BaseModel {
	public String reg_dt;
	public int reg_idx;
	public String bcn_cd;
	public int rank;
	public String percentage;
	public String code;
	public String kofcode;
	public String koreantitle;
	public String director;
	public String actors;
	public String genre;
	public String runningtime;
	public String filmrating;
	public String nationality;
	public String releasedate;
	public String productionyear;
	public String starscore;
	public String screenstatus;
	public String imageurl;;
	public int sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	@Override
	public AITF001Vo toVo() {
		AITF001Vo vo = new AITF001Vo();
			
			vo.reg_dt = this.reg_dt; 
			vo.reg_idx = this.reg_idx;
			vo.bcn_cd = this.bcn_cd;
			vo.rank = this.rank; 
			vo.percentage = this.percentage;
			vo.code = this.code; 
			vo.kofcode = this.kofcode;
			vo.koreantitle = this.koreantitle; 
			vo.director = this.director;
			vo.actors = this.actors; 
			vo.genre = this.genre;
			vo.runningtime = this.runningtime; 
			vo.filmrating = this.filmrating;
			vo.nationality = this.nationality; 
			vo.releasedate = this.releasedate; 
			vo.productionyear = this.productionyear;
			vo.starscore = this.starscore;
			vo.screenstatus = this.screenstatus;
			vo.imageurl = this.imageurl;
			vo.sts = this.sts;
			vo.reg_usr = this.reg_usr;
			vo.mod_usr = this.mod_usr;

			return vo;
	}
	
}
