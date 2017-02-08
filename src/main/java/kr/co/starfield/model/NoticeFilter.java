package kr.co.starfield.model;

import kr.co.starfield.model.vo.SCMS008Vo;

/**
 *  NoticeFilter
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.06.20
 */

public class NoticeFilter {
	public String noti_seq; 
	public String bcn_cd;
	public String noti_titl;
	public String noti_cont;
	
	public int sts;
	
	public String sort_name;
	public String sort_order;
	 
	public int searchType; //1 제목 + 내용, 2 제목, 3 내용
	public String searchKeyword;
	
	public int offset;
	public int limit;
}
