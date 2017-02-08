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

public class APPLOG {
	public String evt_div_cd1;
	public String uuid;
	public String log_type;
	public String log_type_nm;
	public String grp_log_type_nm;
	public String log_nm;
	public String log_desc;
	public String mbr_div_nm;
	public String mbr_id;						 		
	public String mbr_div;
	public String reg_dttm;
	public String f_reg_dttm;
	public String mod_dttm;
	public String reg_usr;					 		
	public String mod_usr;
	
		
	public String sh_strt_dt;
	public String sh_end_dt;
	public String sh_text_type;
	public String sh_type1;
	public String sh_type2;
	public String sh_text;
	public String sortColumn;//정렬 컬럼
	public String sortColumnAscDesc;//정렬 기준
	public String no;
	public int offset;
	public int limit;
	public int tot_cnt;
	public String lang;

		
	
}
