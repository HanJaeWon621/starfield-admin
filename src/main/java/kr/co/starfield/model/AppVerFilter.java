package kr.co.starfield.model;

/**
 *  AppVer model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.10.11
 */

public class AppVerFilter {
	public String rnum;
	public String amav_seq;
	public String bcn_cd;
	public int pltf_type;
	public String version;
	public String apply_dttm;
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
