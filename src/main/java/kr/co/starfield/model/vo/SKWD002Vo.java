/*
 * NoticeVo.java	1.00 2016/06/016
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.RecommendTag;
/**
 *  SKWD002(recommend tag) 테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.06.21
 */

public class SKWD002Vo extends BaseVo {
	public String recm_tag_seq;
	public String bcn_cd;
	public String tag_div;
	public String tag_nm;
	public int sort_ord;
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public RecommendTag toModel() {
		RecommendTag model = new RecommendTag();
		
		model.recm_tag_seq = this.recm_tag_seq;
		model.bcn_cd = this.bcn_cd;
		model.tag_div = this.tag_div;
		model.tag_nm = this.tag_nm;
		model.sort_ord = this.sort_ord;
		model.sts = this.sts;
		model.reg_dttm = Dt.toStringDateTime(this.reg_dttm);
		model.mod_dttm = Dt.toStringDateTime(this.mod_dttm);
		model.reg_usr = this.reg_usr;
		model.mod_usr = this.mod_usr;
				
		return model;
	}

}
