/*
 * EmpVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import org.springframework.web.bind.annotation.RequestParam;

import kr.co.starfield.model.ALBS001;

/**
 * ALBS001 테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class ALBS001Vo extends BaseVo {
	public String scn_otb_cp_push_seq;		//시나리오아웃바운드쿠폰푸시순번
	public String bcn_cd;					//지점코드
	public String corp_cd;					//법인코드
	public String scn_cp_push_titl;			//시나리오쿠폰푸시타이틀
	public String scn_otb_div_cd;			//시나리오아웃바운드구분코드 1:시나리오 2:아웃바운드
	public String app_tgt_mbr_div_cd;		//적용대상회원구분코드
	public String app_tgt_sex;				//적용대상성별
	public String app_tgt_age_all;			//적용대상연령전체
	public String app_tgt_age_20;			//적용대상연령20대
	public String app_tgt_age_30;			//적용대상연령30대
	public String app_tgt_age_40;			//적용대상연령40대
	public String app_tgt_age_50;			//적용대상연령50대
	public String push_strt_dt;				//푸시시작일
	public String push_end_dt;				//푸시종료일
	public String push_time_div_cd;			//푸시시점구분코드
	public String stay_time_area_div_cd;	//체류시간지역구분코드
	public String stay_time_cd;				//체류시간코드
	public String stay_time;				//체류시간
	public String spc_tnt_seq_1;			//특정매장코드1
	public String spc_tnt_seq_2;			//특정매장코드2
	public String spc_tnt_seq_3;			//특정매장코드3
	public String spc_tnt_seq_4;			//특정매장코드4
	public String spc_tnt_seq_5;			//특정매장코드5
	public String spc_tnt_stay_time_div_cd;	//특정매장체류시간구분코드
	public String spc_tnt_stay_time;		//특정매장체류시간
	public String spc_zone_id;				//특정존ID
	public String exp_yn;					//노출여부
	public String push_sts_set_div_cd;		//푸시상황설정구분코드
	public String no_vst_div_cd;			//특정기간미방문구분코드
	public int    no_vst_cnt;				//미방문일수
	public String app_no_use_div_cd;		//특정기간앱미사용구분
	public int    no_use_cnt;				//미사용일수
	public String scn_push_msg;				//시나리오푸시메시지
	public int 	  sts;						//상태
	public String reg_dttm;					//등록일시
	public String mod_dttm;					//수정일시
	public String link_url;					
	public String reg_usr;					//등록자
	public String mod_usr;					//수정자
	public String cmp_id;		
	public String sh_strt_dt;		
	public String sh_end_dt;		
	public String sh_memb_type;		
	public String sh_text_type;		
	public String sh_text;		
	public String cp_act_strt_dt;//쿠폰유효시작일
	public String cp_act_end_dt;//쿠폰유효종료일
	public String sortColumn;
	public String sortColumnAscDesc;
	public String dft_img_yn;
	public String bi_img_seq;
	public String bi_img_seq_uri;
	public ALBS001 toModel() {
		ALBS001 albs001 = new ALBS001();
		
		albs001.scn_otb_cp_push_seq			=  this.scn_otb_cp_push_seq;		
		albs001.bcn_cd						=  this.bcn_cd;					
		albs001.corp_cd						=  this.corp_cd;					
		albs001.scn_cp_push_titl			=  this.scn_cp_push_titl;			
		albs001.scn_otb_div_cd				=  this.scn_otb_div_cd;			
		albs001.app_tgt_mbr_div_cd			=  this.app_tgt_mbr_div_cd;		
		albs001.app_tgt_sex					=  this.app_tgt_sex;				
		albs001.app_tgt_age_all				=  this.app_tgt_age_all;			
		albs001.app_tgt_age_20				=  this.app_tgt_age_20;			
		albs001.app_tgt_age_30				=  this.app_tgt_age_30;			
		albs001.app_tgt_age_40				=  this.app_tgt_age_40;			
		albs001.app_tgt_age_50				=  this.app_tgt_age_50;			
		albs001.push_strt_dt				=  this.push_strt_dt;				
		albs001.push_end_dt					=  this.push_end_dt;				
		albs001.push_time_div_cd			=  this.push_time_div_cd;			
		albs001.stay_time_area_div_cd		=  this.stay_time_area_div_cd;	
		albs001.stay_time_cd				=  this.stay_time_cd;				
		albs001.stay_time					=  this.stay_time;				
		albs001.spc_tnt_seq_1				=  this.spc_tnt_seq_1;			
		albs001.spc_tnt_seq_2				=  this.spc_tnt_seq_2;			
		albs001.spc_tnt_seq_3				=  this.spc_tnt_seq_3;			
		albs001.spc_tnt_seq_4				=  this.spc_tnt_seq_4;			
		albs001.spc_tnt_seq_5				=  this.spc_tnt_seq_5;			
		albs001.spc_tnt_stay_time_div_cd	=  this.spc_tnt_stay_time_div_cd;	
		albs001.spc_tnt_stay_time			=  this.spc_tnt_stay_time;		
		albs001.spc_zone_id					=  this.spc_zone_id;				
		albs001.exp_yn						=  this.exp_yn;					
		albs001.push_sts_set_div_cd			=  this.push_sts_set_div_cd;		
		albs001.no_vst_div_cd				=  this.no_vst_div_cd;			
		albs001.no_vst_cnt					=  this.no_vst_cnt;				
		albs001.app_no_use_div_cd			=  this.app_no_use_div_cd;		
		albs001.no_use_cnt					=  this.no_use_cnt;				
		albs001.scn_push_msg				=  this.scn_push_msg;				
		albs001.sts							=  this.sts;						
		albs001.reg_dttm					=  this.reg_dttm;					
		albs001.mod_dttm					=  this.mod_dttm;					
		albs001.reg_usr						=  this.reg_usr;					
		albs001.mod_usr						=  this.mod_usr;					
		albs001.cmp_id						=  this.cmp_id;					
		albs001.link_url					=  this.link_url;
		albs001.cp_act_strt_dt				=  this.cp_act_strt_dt;
		albs001.cp_act_end_dt				=  this.cp_act_end_dt;
		albs001.bi_img_seq					=  this.bi_img_seq;
		albs001.bi_img_seq_uri				=  this.bi_img_seq_uri;
		
		
		return albs001;
	}
}
