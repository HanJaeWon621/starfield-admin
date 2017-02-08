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

public class MovePathStats {
	
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
	public String sh_dt;
	public String sh_end_dt;
	public String sortColumn;
	public String sortColumnAscDesc;
	
	public String sts_dt; 
	public String move_dt; 
	public String move_tm; 
	public String tnt_nm;
	public String fl_nm;
	public String all_visit_cnt; 
	public String all_unk_visit_cnt; 
	public String all_tnt_rt; 
	public String input_top_tnt_nm; 
	public String exit_top_tnt_nm;
	
	public String input_tnt_nm;
	public String input_cnt;
	public String input_rt;
	public String exit_tnt_nm;
	public String exit_cnt;
	public String exit_rt;
	
	public String sh_tnt_seq;
	public String sh_grade;
	

	public String bcn_cd;//지점코드
	public String rnk;//순위
	public String tnt_seq;//테넌트순번
	//public String tnt_nm;//테넌트명
	public String zone_id;// --존id
	//public String fl_nm;// --층정보
	public String pre_top_tnt_seq;// --이전테넌트순번
	public String pre_top_tnt_nm;// --이전테넌트명
	public String pre_top_zone_id;// --이전존id
	public String post_top_tnt_seq;// --이후테넌트순번
	public String post_top_tnt_nm;// --이후테넌트명
	public String post_top_zone_id;// --이후존id
	
	public String c_zone_id;
	public String pre_zone_id; 
	public String pre_visit_cnt;   
	public String pre_visit_rt;
	public String post_zone_id; 
	public String post_visit_cnt; 
	public String post_visit_rt;
	public String pre_visit_tot_cnt;
	public String post_visit_tot_cnt;

   
}
