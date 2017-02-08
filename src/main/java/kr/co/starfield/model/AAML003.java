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
 * 회원기준 통계
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class AAML003 {
	public String no;//순번
	public String visit_dt;//방문일자
	public String bcn_cd;//점포코드
	
	public String tnt_nm;//테넌트명
	public String cate_nm;//케티고리명
	public String all_visit_mbr_cnt;
	public String today_visit_mbr_cnt;
	public String re_visit_mbr_cnt;
	
	public String sex_rt_m;
	public String sex_rt_f;
	
	public String sex_rt_20_m;
	public String sex_rt_30_m;
	public String sex_rt_40_m;
	public String sex_rt_50_m;
	
	public String sex_rt_20_f;
	public String sex_rt_30_f;
	public String sex_rt_40_f;
	public String sex_rt_50_f;
	
	public String sh_dt_dype;//조회기준
	public String sh_strt_dt;//조회일자 시작
	public String sh_end_dt;//조회일자 종료
	public String sh_tnt_nm;//조회일자 종료
	
	public String sortColumn;//정렬 컬럼
	public String sortColumnAscDesc;//정렬 기준
	
	public int offset;
	public int limit;
	public int tot_cnt;
	public String lang;

		
	
}
