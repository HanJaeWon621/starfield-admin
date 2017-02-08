package kr.co.starfield.model;


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

public class FAQ {
	public String faq_seq; 
	public String cate_seq;
	public String bcn_cd;

	public String faq_titl_ko;
	public String faq_titl_en;
	public String faq_titl_jp;
	public String faq_titl_cn;
	
	public String faq_cont_ko;
	public String faq_cont_en;
	public String faq_cont_jp;
	public String faq_cont_cn;
	
	public int sort_ord;
	
	public int sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
}
