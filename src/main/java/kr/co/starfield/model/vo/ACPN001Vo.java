/*
 * EmpVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import java.util.Arrays;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestParam;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.ACPN001;

/**
 * EMP 테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class ACPN001Vo extends BaseVo {
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
	public int cp_sum_sale_rt;//쿠폰소계할인금액 ''
	public int cp_ded_amt;//쿠폰금액차감금액
	public int cp_iss_cnt;    //쿠폰 발급 수량 	        						
	public String cp_use_cond_div_cd;//쿠폰사용조건구분코드   						
	public int cp_use_cond_amt;//쿠폰사용조건금액   								
	public String cp_att_part_cont;//쿠폰유의사항
	public String cp_dtl_cont;//쿠폰 상세내용
	public String cp_exp_yn;//노출 여부
	public String all_iss_cp_cnt;//전체발행쿠폰수
	public String dn_iss_cp_cnt;//다운로드쿠폰수
	public String rtn_prc_cp_cnt;//회수처리쿠폰수
	public String rtn_prc_cp_rt;//쿠폰회수율
	public String cp_use_plce_set_seq;//쿠폰 사용처 지정 순번
	public String cp_use_plce_set_cd;//쿠폰 사용처 지정 코드
	public String reg_dttm;//등록일시
	public String mod_dttm;//수정일시
	public String reg_usr;//등록자
	public String mod_usr;//수정자
	public String inf_sts_cd;
	public String sale_evt_cd;
	public String cp_max_sale_amt;
	public String dn_cnt;
	public String remain_cnt;
	public String dtl_img_seq;
	public String web_bg_img_seq;
	public String web_bg_clr;
	public String app_bg_img_seq;
	public String app_bg_clr;
	public String dft_img_yn;
	public String tnt_nm;
	
	public String sh_dt_type;
	public String sh_strt_dt;
	public String sh_end_dt;
	public String sh_div_type;
	public String sh_kind_type;
	public String sh_text_type;
	public String sh_text;
	public String sortColumn;//정렬 컬럼
	public String sortColumnAscDesc;//정렬 기준
	
	public String sh_down_strt_dt;
	public String sh_down_end_dt;
	public String sh_use_strt_dt;
	public String sh_use_end_dt;
	public String sh_down_dt_app_yn;
	public String sh_use_dt_app_yn;

	public ACPN001 toModel() {
		ACPN001 coupon = new ACPN001();
		
		coupon.no				= this.no;//쿠폰순번
		coupon.cp_seq				= this.cp_seq;//쿠폰순번
		coupon.bcn_cd				= this.bcn_cd;//점포코드
		coupon.yymm					= this.yymm;//년월
		coupon.mst_seq				= this.mst_seq;//마스터순번
		coupon.cp_div_cd			= this.cp_div_cd;//쿠폰구분코드
		coupon.cp_kind_cd			= this.cp_kind_cd;//쿠폰종류코드
		coupon.cp_iss_type_cd		= this.cp_iss_type_cd;
		coupon.cp_titl				= this.cp_titl;//쿠폰타이틀
		coupon.img_seq				= this.img_seq;//쿠폰BI이미지순번
		coupon.cp_iss_strt_dt		= this.cp_iss_strt_dt;//쿠폰발급시작일
		coupon.cp_iss_end_dt		= this.cp_iss_end_dt;//쿠폰발급종료일
		coupon.cp_act_strt_dt		= this.cp_act_strt_dt;//쿠폰유효시작일
		coupon.cp_act_end_dt		= this.cp_act_end_dt;//쿠폰유효종료일
		coupon.cp_sale_div_cd	    = this.cp_sale_div_cd;//쿠폰할인방식구분코드
		coupon.cp_sum_sale_rt		= this.cp_sum_sale_rt;//쿠폰소계할인금액
		coupon.cp_ded_amt			= this.cp_ded_amt;//쿠폰금액차감금액
		coupon.cp_iss_cnt			= this.cp_iss_cnt;
		coupon.cp_use_cond_div_cd	= this.cp_use_cond_div_cd;
		coupon.cp_use_cond_amt		= this.cp_use_cond_amt;
		coupon.cp_att_part_cont		= this.cp_att_part_cont;//쿠폰유의사항
		coupon.cp_dtl_cont			= this.cp_dtl_cont;//쿠폰유의사항
		coupon.cp_exp_yn			= this.cp_exp_yn;//노출 여부4
		coupon.cp_use_plce_set_seq	= this.cp_use_plce_set_seq;//쿠폰 사용처 지정 순번
		coupon.cp_use_plce_set_cd	= this.cp_use_plce_set_cd;//쿠폰 사용처 지정 코드
		coupon.reg_dttm				= this.reg_dttm;//등록일시
		coupon.mod_dttm				= this.mod_dttm;//수정일시
		coupon.reg_usr				= this.reg_usr;//등록자
		coupon.mod_usr				= this.mod_usr;//수정자
		coupon.dtl_img_seq 			= this.dtl_img_seq;               
		coupon.web_bg_img_seq		= this.web_bg_img_seq;            
		coupon.web_bg_clr			= this.web_bg_clr;                
		coupon.app_bg_img_seq		= this.app_bg_img_seq;            
		coupon.app_bg_clr			= this.app_bg_clr;                
		coupon.inf_sts_cd			= this.inf_sts_cd;
		coupon.sale_evt_cd			= this.sale_evt_cd;
		coupon.cp_max_sale_amt		= this.cp_max_sale_amt;
		coupon.dft_img_yn			= this.dft_img_yn;
		coupon.dn_cnt				= this.dn_cnt;
		coupon.remain_cnt			= this.remain_cnt;
		coupon.tnt_nm				= this.tnt_nm;
		
		return coupon;
	}
}
