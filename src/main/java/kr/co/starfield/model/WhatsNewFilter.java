package kr.co.starfield.model;

import java.util.Date;

import kr.co.starfield.model.vo.SCMS011Vo;

/**
 *  WhatsNewFilter model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.09.07
 */

public class WhatsNewFilter {
	public int rnum;
	public String what_group_seq; 
	public String bcn_cd;
	public int what_sts;
	public String what_group_titl;
	public Date what_post_strt;
	public Date what_post_end;
	public String what_titl;

	public String what_new_seq; 
	
	public int any_seq;
	public String div;
	public String thumb_image_seq;
	public String thumb_image_uri;
	public String mobi_thumb_image_seq;
	public String mobi_thumb_image_uri;
	public String web_bg_colr;
	public String mobi_bg_colr;
	public String web_bg_img_type;
	public String mobi_bg_img_type;
	public String title_head_text;
	public String title_main_text;
	public String title_sub_text;
	public String cont_titl;
	public int sort_ord;
	
	public String start_dt;
	public String end_dt;
	
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
