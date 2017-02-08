/*
 * Emp.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.SCPN001_DVo;

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

public class StyleSet {
	public String no;//순번
	public int tot_cnt;
	public int offset;
	public int limit;
	public int all_tot_cnt;
	public String lang;
	
	public String style_set_seq;
	public String style_nm;
	public String mouse_state;
	public String style_comment;
	public String bg_color;
	public String bd_size;
	public String bd_color;
	public String ext_style;
	public String important_cd;
	
	public String bcn_cd;
	
	public String sh_bcn_cd;
	public String sh_style_nm;
	public String sh_style_set_seq;
}
