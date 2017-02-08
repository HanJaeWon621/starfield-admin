package kr.co.starfield.model;

import org.springframework.web.bind.annotation.RequestParam;

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

public class LocInfoManageFilter {
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
	public int sts; //0:접수 1:확인중 8:삭제불가 9:삭제완료
	public String stat_dt;
	public String end_dt;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	public String sh_div;
	public String sh_text_type;
	public String sh_text;

	public String sort_name;
	public String sort_order;
	 
	
	public boolean date_search;
	public String date_type; //REQ_DEL_DTTM - 삭제 요청일, ACT_DTTM - 조치일
	public String stat_date;
	public String end_date;
	
	public int offset;
	public int limit;
}
