package kr.co.starfield.model;

import java.util.ArrayList;

/**
 *  InstagramTag model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.10.14
 */

public class InstagramTag {
	public String insta_tag_seq;
	public String bcn_cd;
	public String insta_tag_nm;
	public int sort_ord;
	public int img_count;
	
	public ArrayList<InstagramImage> instagramImageList;
	
	public int sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
}
