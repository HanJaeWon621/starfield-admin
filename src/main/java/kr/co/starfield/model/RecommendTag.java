package kr.co.starfield.model;

import kr.co.starfield.model.vo.SKWD002Vo;

/**
 *  RecommendTag model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.06.21
 */

public class RecommendTag extends BaseModel {
	public String recm_tag_seq;
	public String bcn_cd;
	public String tag_div;
	public String tag_nm;
	public int sort_ord;
	public int sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	@Override
	public SKWD002Vo toVo() {
		SKWD002Vo vo = new SKWD002Vo();
		
		vo.recm_tag_seq = this.recm_tag_seq;
		vo.bcn_cd = this.bcn_cd;
		vo.tag_div = this.tag_div;
		vo.tag_nm = this.tag_nm;
		vo.sort_ord = this.sort_ord;
		vo.sts = this.sts;
		vo.reg_usr = this.reg_usr;
		vo.mod_usr = this.mod_usr;
		
		return vo;
	}
	
	@Override
	public String toString() {
		return "RecommendTag [recm_tag_seq=" + recm_tag_seq + ", bnc_cd=" + bcn_cd + ", tag_div=" + tag_div
				+ ", tag_nm=" + tag_nm + ", sort_ord=" + sort_ord + ", sts=" + sts
				+ ", reg_dttm=" + reg_dttm + ", mod_dttm=" + mod_dttm + ", reg_usr=" + reg_usr
				+ ", mod_usr=" + mod_usr + "]";
	}
}
