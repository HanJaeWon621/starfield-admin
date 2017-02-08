package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.Date;

import kr.co.starfield.model.vo.BaseVo;
import kr.co.starfield.model.vo.SCMS010Vo;

/**
 *  BannerGroup model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.09.07
 */

public class BannerGroup {
	public int rnum;
	public String bn_group_seq; 
	public String bcn_cd;
	public int bn_div;
	public int bn_sts;
	public String bn_group_titl;
	public Date bn_post_strt;
	public Date bn_post_end;
	public String bn_titl;
	public ArrayList<Banner> bannerList = new ArrayList<>();
	
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public String bn_post_strt_date;
	public String bn_post_strt_time;
	public String bn_post_end_date;
	public String bn_post_end_time;

}
