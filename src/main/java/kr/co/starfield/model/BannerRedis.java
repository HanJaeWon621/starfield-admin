package kr.co.starfield.model;

import java.util.Date;

import kr.co.starfield.model.vo.SCMS011Vo;

/**
 *  BannerRedis model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.09.07
 */

public class BannerRedis {
	public String bn_seq; 
	public String bn_img_uri;
	
	public String bn_titl;
	public String great_titl1;
	public String great_titl2;
	public String small_titl;
	public String div;
	public String any_seq;
	public String bg_img_type;
	public String bg_colr_cd;
	public String txt_colr_cd;
	public int sort_ord;

	public Date reg_dttm;
	public Date mod_dttm;
}
