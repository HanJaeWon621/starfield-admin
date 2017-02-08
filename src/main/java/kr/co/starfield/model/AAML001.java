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
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class AAML001 {
	public String no;//순번
	public String visit_dt;//방문일자
	public String bcn_cd;//점포코드
	
	public String pre_all_visit_cnt;//전체방문자수 전년도 							 		
	public String all_visit_cnt;//전체방문자수 현재년도
	public String pre_all_visit_mbr_cnt;//회원방문 전년도
	public String all_visit_mbr_cnt;//회원방문 현재년도 							 		
	public String pre_new_visit_cnt;//신규방문자수 전년도
	public String new_visit_cnt;//신규방문자수 현재년도
	public String pre_re_visit_cnt;//재방문자수 전년도 							 		
	public String re_visit_cnt;//재방문자수 현재년도
	
	public String sh_dt_dype;//조회기준
	public String sh_strt_dt;//조회일자 시작
	public String sh_end_dt;//조회일자 종료
	
	public String sortColumn;//정렬 컬럼
	public String sortColumnAscDesc;//정렬 기준
	
	public int offset;
	public int limit;
	public int tot_cnt;
	public String lang;

		
	
}
