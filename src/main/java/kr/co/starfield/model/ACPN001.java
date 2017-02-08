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

public class ACPN001 extends BaseModel {
	public String no;//순번
	public String cp_seq;//쿠폰순번
	public String bcn_cd;//점포코드
	public String yymm;//년월 							 		
	public String mst_seq;//마스터순번
	public String cp_div_cd;//쿠폰구분코드
	public String cp_kind_cd;//쿠폰종류코드
	public String cp_iss_type_cd;//쿠폰 발급 타입 코드 			
	public String cp_titl;//쿠폰타이틀
	public String img_seq;//쿠폰BI이미지순번
	public String cp_iss_strt_dt;//쿠폰발급시작일(발급일)
	public String cp_iss_end_dt;//쿠폰발급종료일
	public String cp_act_strt_dt;//쿠폰유효시작일
	public String cp_act_end_dt;//쿠폰유효종료일
	public String cp_sale_div_cd;//쿠폰소계할인구분코드
	public int    cp_sum_sale_rt;//쿠폰소계할인금액 ''
	public int    cp_ded_amt;//쿠폰금액차감금액
	
	public int 	  cp_iss_cnt;//쿠폰 발급 수량 	        							
	public String cp_use_cond_div_cd;//쿠폰사용조건구분코드   						
	public int cp_use_cond_amt;//쿠폰사용조건금액   								
	public String cp_dtl_cont;//쿠폰 상세내용									
	public String cp_att_part_cont;//쿠폰유의사항
	public String cp_exp_yn;//노출 여부										
	public String   reg_dttm;//등록일시
	public String   mod_dttm;//수정일시
	public String reg_usr;//등록자
	public String mod_usr;//수정자
	public String all_iss_cp_cnt;//전체발행쿠폰수
	public String dn_iss_cp_cnt;//다운로드쿠폰수
	public String rtn_prc_cp_cnt;//회수처리쿠폰수
	public String rtn_prc_cp_rt;//쿠폰회수율
	public String cp_use_plce_set_seq;//쿠폰 사용처 지정 순번  		d				
	public String cp_use_plce_set_cd;//쿠폰 사용처 지정 코드		d
	public String inf_sts_cd;
	public String sale_evt_cd;
	public String cp_max_sale_amt;
	public String cp_iss_bcd_id;
	public int tot_cnt;
	public String cp_iss_cd;
	public String use_cnt;
	public String tnt_nm_ko;
	public String push_cnt;//쿠폰 푸시 수량
	public String dn_cnt;
	public String use_rt;
	public String remain_cnt;//쿠폰 잔여 수량  발급수량 - 다운수량
	public String img_main_bg_web_uri;
	public String dtl_img_seq;
	public String web_bg_img_seq;
	public String web_bg_clr;
	public String app_bg_img_seq;
	public String app_bg_clr;
	public String img_main_bg_mobi_uri;
	public String web_bg_type;
	public String app_bg_type;
	public List<SCPN001_D> selTenantList;
	public List<Category> category;
	//public List<SCPN001_D> oldTenantList;
	//public List<SCPN001_D> chkTenantList;
	public String cp_sale_div_cd_nm;
	public String cp_kind_cd_nm;
	public String cp_div_cd_nm;
	public String cp_iss_type_cd_nm;
	public String img_uri;
	public String dtl_img_uri;
	public String web_bg_img_uri;
	public String app_bg_img_uri;
	public String dft_img_yn;
	public String all_tnt;
	public String post_cnt;//게시 여부 체크
	public String excel_err_yn;
	public String tnt_nm;
	public String log_seq;
	public String result;
	@Override
	public ACPN001Vo toVo() {
		ACPN001Vo vo = new ACPN001Vo();
		
		vo.no				= this.no;//쿠폰순번
		vo.cp_seq				= this.cp_seq;//쿠폰순번
		vo.bcn_cd				= this.bcn_cd;//점포코드
		vo.yymm					= this.yymm;//년월
		vo.mst_seq				= this.mst_seq;//마스터순번
		vo.cp_div_cd			= this.cp_div_cd;//쿠폰구분코드
		vo.cp_kind_cd			= this.cp_kind_cd;//쿠폰종류코드
		vo.cp_iss_type_cd		= this.cp_iss_type_cd;
		vo.cp_titl				= this.cp_titl;//쿠폰타이틀
		vo.img_seq				= this.img_seq;//쿠폰BI이미지순번
		vo.cp_iss_strt_dt		= this.cp_iss_strt_dt;//쿠폰발급시작일
		vo.cp_iss_end_dt		= this.cp_iss_end_dt;//쿠폰발급종료일
		vo.cp_act_strt_dt		= this.cp_act_strt_dt;//쿠폰유효시작일
		vo.cp_act_end_dt		= this.cp_act_end_dt;//쿠폰유효종료일
		vo.cp_act_end_dt		= this.cp_act_end_dt;//쿠폰유효종료일
		vo.cp_sale_div_cd		= this.cp_sale_div_cd;//쿠폰할인방식구분코드
		vo.cp_sum_sale_rt		= this.cp_sum_sale_rt;//쿠폰소계할인금액
		vo.cp_ded_amt			= this.cp_ded_amt;//쿠폰금액차감금액
		vo.cp_iss_cnt			= this.cp_iss_cnt;
		vo.cp_use_cond_div_cd	= this.cp_use_cond_div_cd; 
		vo.cp_use_cond_amt		= this.cp_use_cond_amt;
		vo.cp_att_part_cont		= this.cp_att_part_cont;//쿠폰유의사항
		vo.cp_dtl_cont			= this.cp_dtl_cont;//쿠폰유의사항
		vo.cp_exp_yn			= this.cp_exp_yn;//노출 여부4
		vo.cp_use_plce_set_seq	= this.cp_use_plce_set_seq;//쿠폰 사용처 지정 순번
		vo.cp_use_plce_set_cd	= this.cp_use_plce_set_cd;//쿠폰 사용처 지정 코드
		//coupon.reg_dttm				= Dt.toStringDateTime(this.reg_dttm);//등록일시
		//coupon.mod_dttm				= Dt.toStringDateTime(this.mod_dttm);//수정일시
		vo.reg_usr				= this.reg_usr;//등록자
		vo.mod_usr				= this.mod_usr;//수정자
		vo.dtl_img_seq 			= this.dtl_img_seq;               
		vo.web_bg_img_seq		= this.web_bg_img_seq;            
		vo.web_bg_clr			= this.web_bg_clr;                
		vo.app_bg_img_seq		= this.app_bg_img_seq;            
		vo.app_bg_clr			= this.app_bg_clr;
		vo.inf_sts_cd			= this.inf_sts_cd;
		vo.sale_evt_cd			= this.sale_evt_cd;
		vo.cp_max_sale_amt		= this.cp_max_sale_amt;
		vo.dft_img_yn			= this.dft_img_yn;
		vo.tnt_nm				= this.tnt_nm;
				
		return vo;
	}
}
