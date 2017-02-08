package kr.co.starfield.model;

import java.util.Date;

import kr.co.starfield.model.vo.SCMS011Vo;

/**
 *  LanguageFilter model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.09.30
 */

public class LanguageFilter {
	public String rnum;
	public String pg_id; 
	public String count;
	public String bcn_cd;
	public String txt_symb;
	
	public int sts;
	
	public String reg_dttm;
	public String mod_dttm;

	
	public int offset = 0;
	public int limit = -1;
	public String sort_name = "rnum";
	public String sort_order = "desc";
	
	public int searchType = 0;
	public String searchKeyword;
}
