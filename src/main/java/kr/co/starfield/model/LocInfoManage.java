package kr.co.starfield.model;

/**
 *  LocInfoManage model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.10.10
 */

public class LocInfoManage {
	public int rnum;
	public String plid_mng_seq;
	public String bcn_cd;
	public String name;
	public String phone_num;
	public String phone_num_nf;
	public String req_del_dttm;
	public String act_dttm;
	public String act_usr;
	public String act_yn;
	public String del_div;//1 고객요청 2 보유기간 만료
	public String card_no;
	public int sts; //0:접수 1:확인중 8:삭제불가 9:삭제완료
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public String sort_name;
	public String sort_order;
	public String sortColumn;
	public String sortColumnAscDesc;
	 
	
	public boolean date_search;
	public String date_type; //REQ_DEL_DTTM - 삭제 요청일, ACT_DTTM - 조치일
	public String stat_date;
	public String end_date;
	public String sh_div;
	public String sh_text_type;
	public String sh_text;
	public int offset;
	public int limit;
	
	public int tot_cnt;
}
