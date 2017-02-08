package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.Banner;
import kr.co.starfield.model.BannerGroup;
import kr.co.starfield.model.QNA;

/**
 *  SCMS011Vo(Banne) 테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.09.07
 */

public class SCMS011Vo extends BaseVo {	
	public String bn_seq; 
	public String bn_group_seq;
	public String img_seq;
	public String bn_titl;
	public String great_titl1;
	public String great_titl2;
	public String small_titl;
	public String link_url;
	public int bg_img_type;
	public String bg_colr_cd;
	public String txt_colr_cd;
	public int sort_ord;

	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public Banner toModel() {
		Banner model = new Banner();
		
		model.bn_seq = this.bn_seq;
		model.bn_group_seq = this.bn_group_seq;

		model.bn_titl = this.bn_titl;
		model.great_titl1 = this.great_titl1;
		model.great_titl2 = this.great_titl2;
		model.small_titl = this.small_titl;
		model.link_url = this.link_url;
		model.bg_img_type = this.bg_img_type;
		model.bg_colr_cd = this.bg_colr_cd;
		model.txt_colr_cd = this.txt_colr_cd;
		model.sort_ord = this.sort_ord;
		
		model.sts = this.sts;
		model.reg_dttm = this.reg_dttm;
		model.mod_dttm = this.mod_dttm;
				
		return model;
	}

}
