/*
 * Paging.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

/**
 *  Paging model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author xxx
 * @version 1.0,
 * @see
 * @date 2016.04.21
 */

public class Paging {
	
	public final static int DEFAULT_OFFSET = 1;
	public final static int DEFAULT_LIST_LIMIT = 10;	
	public final static int DEFAULT_PAGE_LIMIT = 10;
	
	public int total_cnt;
	public int total_page_cnt;
	public int page_start;
	public int page_end;
	public int cur_page;
	public int page_limit;
	public int list_limit;
	
	public Paging(int totCnt) {
		
		this.setPaging(DEFAULT_OFFSET, DEFAULT_LIST_LIMIT, totCnt, DEFAULT_PAGE_LIMIT);
	}

	public Paging(int offset, int limit, int totCnt) {
		// TODO yhkim. 5 -> 10으로 변경 영향도 체크 필요 
		this.setPaging(offset, limit, totCnt, DEFAULT_PAGE_LIMIT);
	}
	
	public Paging(int offset, int limit, int totCnt, int pageLimit) {
		
		this.setPaging(offset, limit, totCnt, pageLimit);
	}
	
	public void setPaging(int offset, int limit, int totCnt, int pageLimit) {
		offset = (offset == 0) ? DEFAULT_OFFSET : offset;
		limit = (limit == 0) ? DEFAULT_LIST_LIMIT : limit;
		pageLimit = (offset == 0) ? DEFAULT_PAGE_LIMIT : pageLimit;
		
		this.total_cnt = totCnt;
		this.list_limit = limit == -1 ? total_cnt : limit;
		this.page_limit = pageLimit;
		this.cur_page = totCnt == 0 ? 1 : (offset / this.list_limit) + 1;
		this.total_page_cnt = totCnt == 0 ? 1 : (totCnt / this.list_limit) + ((totCnt % this.list_limit) >= 1 ? 1 : 0);
		this.page_start = (this.cur_page - 1) / this.page_limit * this.page_limit + 1;
		this.page_end = this.page_start + this.page_limit - 1;
		this.page_end = this.page_end > this.total_page_cnt ? this.total_page_cnt : this.page_end;
	}
	
}
