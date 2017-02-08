package kr.co.starfield.model;

/**
 *  NewsFilter model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.06.20
 */

public class NewsFilter {
	public String rnum;
	public String news_seq; 
	public String cate_seq;
	public String cate_name;
	public String img_seq;
	public String img_name;
	public String bcn_cd;
	public String news_titl;
	public String news_cont;
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
