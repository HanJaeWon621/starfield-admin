/*
 * Emp.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import java.util.List;

/**
 *  Emp model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class Block {
	public String no;//순번
	public int tot_cnt;
	public int offset;
	public int limit;
	public int all_tot_cnt;
	public String lang;
		
	public String fb_seq;
	public String spray;
	public String data;
	public String height;
	public String spray_oft_x;
	public String spray_oft_y;
	public String spray_sle;
	public String fl_seq;
	public String fl;
	public String point_cord;
	public String room_num;
	public String etc;
	public String insert_div;
	public String bcn_cd;
	public String dup_cnt;
	public String dup_disp_cnt;
	public String dup_yn;
	 
	
	public String sh_fl;
	public String sh_room_num;
	public List<BlockPoi> points;
}
