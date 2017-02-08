package kr.co.starfield.model;


/**
 *  AdminActionLog model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.10.13
 */

public class AdminActionLogFilter {
	public String rnum;
	public String adm_acc_log_seq;
	public String cd_nm;
	public String adm_id; 
	public String adm_nm;
	public String role_nm;
	public String acc_pefm_dtl;
	public String acc_dttm;
	
	public int offset = 0;
	public int limit = -1;
	public String sort_name = "rnum";
	public String sort_order = "desc";
	public String start_date;
	public String end_date;
	public String searchKeyword;
}
