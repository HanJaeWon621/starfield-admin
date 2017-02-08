/*
 * Emp.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.SCPN001_DVo;

/**
 *  위치정보 열람
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author ldh
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class SAML007 {
	public String read_req_seq;
	public String req_dttm;
	public String dealadm_seq;
	public String req_adm_seq;
	public String read_obj;						 		
	public String dealadm_nm;
	public String req_adm_nm;
	public String use_dttm;						 		
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;					 		
	public String mod_usr;
	public String cust_id;
	public String dn_yn;
	
		
	public String sh_strt_dt;
	public String sh_end_dt;
	public String sh_text_type;
	public String sh_text;
	public String sortColumn;//정렬 컬럼
	public String sortColumnAscDesc;//정렬 기준
	public String no;
	public int offset;
	public int limit;
	public int tot_cnt;
	public String lang;

		
	
}
