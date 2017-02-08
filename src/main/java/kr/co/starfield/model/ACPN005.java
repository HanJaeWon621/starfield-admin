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

public class ACPN005 {

	public String cp_iss_sub_seq; 			
	public String uuid; 			
	public String cp_iss_mst_seq; 			
	public String cp_iss_cd; 			//쿠폰발급코드
    public String cp_div_cd_nm;			//쿠폰구분(모바일,할인)
    public String cp_kind_cd_nm; 		//쿠폰종류(1:에누리 2:할인)//쿠폰종류
    public String cp_iss_type_cd_nm;	//발급타입
    public String cp_iss_bcd_id;		//쿠폰번호
    public String cust_id;				//사용자id
    public String cp_dn_dt;				//다운로드일시
    public String cp_use_dt;			//사용일시(사용일시:사용내역)
    public String cp_use_sts_cd;		//사용처리결과(사용내역에서만)
    public String cp_use_sts_cd_nm;
    public String busi_tnt_cd;			//사용처 사용다운
    public String cp_seq;
    public String use_tnt_nm;
    public String cp_titl;
    public String no;
    public String cp_prc_dt;
    public String user_nm;
    public String user_nm_f;
    public String phone_num_f;
    public String reg_usr;
    public String mod_dttm;
    public String mod_usr; 
    
	public String sh_strt_dt;
	public String sh_end_dt;
	public String sh_div_type;
	public String sh_text_type;
	public String sh_text;
	public String sortColumn;
	public String sortColumnAscDesc;
    
	
    public int rnum;
    public int offset;
	public int limit;
	public int tot_cnt;
	public String log_seq;

}
