package kr.co.starfield.model;

import kr.co.starfield.model.vo.SCMS008Vo;

/**
 *  FAQ model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.06.20
 */

public class QNAFilter {
	
	public String rnum;
	public String qna_seq; 
	public String bcn_cd;
	public String cust_nm;
	public String cust_email;
	public String qutn_titl;
	public String qutn_cont;
	public String qutn_dttm;
	public String reply_yn;
	public String reply_cont;
	public String reply_usr;
	public String reply_dttm;
	
	public int sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public String sort_name;
	public String sort_order;
	 
	public int searchType; //1 제목 + 내용, 2 제목, 3 내용
	public String searchKeyword;
	
	public int offset;
	public int limit;
}
