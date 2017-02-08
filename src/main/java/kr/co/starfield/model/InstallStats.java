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
 *  Emp model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author ldh
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class InstallStats {
	
	public String visit_dt;
	public String reg_dttm;
	public String i_cnt;
	public String a_cnt;
	public String sum_cnt;
	public String stats_strt_dt;
	public String stats_end_dt;
	
	public String no;//순번
	public int offset;
	public int limit;
	public int tot_cnt;
	public String lang;
	public String sh_dt_type;
	public String sh_strt_dt;
	public String sh_end_dt;
	public String sortColumn;
	public String sortColumnAscDesc;
	
}
