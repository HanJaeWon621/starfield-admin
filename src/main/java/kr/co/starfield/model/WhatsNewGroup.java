package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.Date;

import kr.co.starfield.model.vo.BaseVo;
import kr.co.starfield.model.vo.SCMS010Vo;

/**
 *  WhatsNewGroup model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.09.07
 */

public class WhatsNewGroup {
	public int rnum;
	public String what_group_seq; 
	public String bcn_cd;
	public int what_sts;
	public String what_group_titl;
	public Date what_post_strt;
	public Date what_post_end;
	public String what_titl;
	public ArrayList<WhatsNew> whatsNewList = new ArrayList<>();
	
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public String what_post_strt_date;
	public String what_post_strt_time;
	public String what_post_end_date;
	public String what_post_end_time;
}
