package kr.co.starfield.model;

import java.util.Date;

import kr.co.starfield.model.vo.SCMS011Vo;

/**
 *  Banner model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.09.07
 */

public class BannerFilter {
	public int rnum;
	public String bn_seq; 
	public String bn_group_seq;
	public String img_uri;
	public String bn_titl;
	public String great_titl1;
	public String great_titl2;
	public String small_titl;
	public String link_url;
	public String div;
	public String any_seq;
	public int bg_img_type;
	public String colr_cd;
	public int sort_ord;

	public String bcn_cd;
	public int bn_div;
	public int bn_sts;
	public String bn_group_titl;
	public String bn_post_strt;
	public String bn_post_end;
	
	public int sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public int offset = 0;
	public int limit = -1;
	public String sort_name = "rnum";
	public String sort_order = "desc";
}
