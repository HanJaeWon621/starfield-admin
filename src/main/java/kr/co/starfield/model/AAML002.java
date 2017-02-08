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

public class AAML002 {
	public String no;//순번
	public String visit_dt;//방문일자
	public String bcn_cd;//점포코드
	
	//TNT_NM, CATE_NM,  SUM(ALL_VISIT_CNT) ALL_VISIT_CNT, SUM(ALL_VISIT_MBR_CNT) ALL_VISIT_MBR_CNT, SUM(RE_VISIT_CNT) RE_VISIT_CNT
	
	public String tnt_nm;//테넌트명
	public String cate_nm;//케티고리명
	public String all_visit_cnt;//전체방문자수
	public String all_visit_mbr_cnt;//회원방문 현재년도
	public String new_visit_cnt;//금일방문자수
	public String re_visit_cnt;//재방문자수
	
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
