package kr.co.starfield.model;

import java.util.Date;

/**
 *  WhatsNewRedis model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.09.07
 */

public class WhatsNewRedis {
	public String what_new_seq; 
	public String bcn_cd;
	public String what_group_seq; 
	
	public String any_seq;
	public String div; //ET, CP, TT
	public String thumb_image_uri;
	public String txt_colr;
	public String bg_colr;
	public String bg_img_type;
	public String title_head_text;
	public String title_main_text;
	public String title_sub_text;
	public String start_dt;
	public String end_dt;
	public int sort_ord;

	
	public Date reg_dttm;
	public Date mod_dttm;
}
