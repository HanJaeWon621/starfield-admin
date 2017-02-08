package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.MegaBoxP201;

/**
 *AITF002(megaBox-P201) 테이블과 맵핑되는 클래스
 *
 * @author hhLee
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

public class AITF002Vo extends BaseVo {
	public String reg_dt;
	public int reg_idx;
	public String bcn_cd;
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
	public String imageurl;
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public MegaBoxP201 toModel() {
		MegaBoxP201 model = new MegaBoxP201();
		
		model.reg_dt = this.reg_dt; 
		model.reg_idx = this.reg_idx;
		model.bcn_cd = this.bcn_cd;
		model.code = this.code; 
		model.kofcode = this.kofcode;
		model.koreantitle = this.koreantitle; 
		model.director = this.director;
		model.actors = this.actors; 
		model.genre = this.genre;
		model.runningtime = this.runningtime; 
		model.filmrating = this.filmrating;
		model.nationality = this.nationality; 
		model.releasedate = this.releasedate; 
		model.productionyear = this.productionyear;
		model.starscore = this.starscore;
		model.screenstatus = this.screenstatus;
		model.imageurl = this.imageurl;
		model.sts = this.sts;
		model.reg_dttm = Dt.toStringDateTime(this.reg_dttm);
		model.mod_dttm = Dt.toStringDateTime(this.mod_dttm);
		model.reg_usr = this.reg_usr;
		model.mod_usr = this.mod_usr;

		return model;
	}

}
