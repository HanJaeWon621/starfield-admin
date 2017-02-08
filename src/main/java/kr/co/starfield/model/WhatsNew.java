package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.Date;

import kr.co.starfield.model.vo.BaseVo;
import kr.co.starfield.model.vo.SCMS010Vo;

/**
 *  WhatsNew model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.09.07
 */

public class WhatsNew {
	public int rnum;
	
	public String what_new_seq; 
	public String bcn_cd;
	public String what_group_seq; 
	
	public String any_seq;
	public String div;
	public String thumb_image_seq;
	public String thumb_image_uri;
	public String mobi_thumb_image_seq;
	public String mobi_thumb_image_uri;
	public String web_txt_colr;
	public String mobi_txt_colr;
	public String web_bg_colr;
	public String mobi_bg_colr;
	public String web_bg_img_type;
	public String mobi_bg_img_type;
	public String title_head_text;
	public String title_main_text;
	public String title_sub_text;
	public String cont_titl;
	public String cont_sts;
	public int sort_ord;
	
	public String start_dt;
	public String end_dt;
	public String etc_txt;
	
	
	
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
}
