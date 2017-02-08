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

public class PgMng {
	public String no;//순번
	public int tot_cnt;
	public int offset;
	public int limit;
	public int all_tot_cnt;
	public String lang;
	
	public String pg_info_seq;
	public String pg_nm;
	public String disp_sn;
	public String disp_depth;
	public String func_desc;
	public String rel_src_items;
	public String func_list_desc;
	public String use_db_object;
	public String etc_desc;
	public String pg_url;
	public String pg_div;
	public String menu_yn;
	public String expl;
	public String sh_pg_nm;
	public String sh_pg_info_seq;
}
