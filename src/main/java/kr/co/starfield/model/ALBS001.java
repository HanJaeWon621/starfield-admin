/*
 * Emp.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import java.util.List;

import kr.co.starfield.model.vo.ALBS001Vo;

/**
 *  ALBS001 model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class ALBS001 extends BaseModel {
	public String no;
	public String scn_otb_cp_push_seq;		//시나리오아웃바운드쿠폰푸시순번
	public String bcn_cd;					//지점코드
	public String corp_cd;					//법인코드
	public String scn_cp_push_titl;			//시나리오쿠폰푸시타이틀
	public String scn_otb_div_cd;			//시나리오아웃바운드구분코드 1:시나리오 2:아웃바운드
	public String app_tgt_mbr_div_cd;		//적용대상회원구분코드
	public String app_tgt_sex;				//적용대상성별
	public String app_tgt_age;				//적용대상연령
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
	public String cmp_id;				
	public String bi_img;				
	public int 	  sts;						//상태
	public int 	  day_cnt;			
	public int 	  day_cnt2;			
	public String reg_dttm;					//등록일시
	public String mod_dttm;					//수정일시
	public String reg_usr;					//등록자
	public String mod_usr;					//수정자
	public String link_url;					
	public String exit_cmp_id;					
	public String bi_img_seq;
	public String dft_img_yn;
	public String cp_act_strt_dt;//쿠폰유효시작일
	public String cp_act_end_dt;//쿠폰유효종료일
	
	
	public String[] delMemSeq;				//푸시대상
	public String delMemYn;				//푸시대상 flag
	public String cp_seq;					//적용 쿠폰 순번
	public String cp_titl;					//적용 쿠폰 명
	public List<SCPN001_D> selTenantList;   //적용 존	
	public String stay_time_cd2;			
	public String stay_time2;				
	public String[] tgt_age;				
	public String acua_wait_cond;				
	public String push_cnt;
	public String tot_push_cnt;
	public String dn_cnt;
	public String memb_cnt;				
	public String app_tgt_mbr_div_nm;				
	public String push_time_div_nm;				
	public String img_uri;
	public String bi_img_seq_uri;

	
	
	
	@Override
	public ALBS001Vo toVo() {
		ALBS001Vo vo = new ALBS001Vo();
		
		vo.scn_otb_cp_push_seq			=  this.scn_otb_cp_push_seq;		
		vo.bcn_cd						=  this.bcn_cd;					
		vo.corp_cd						=  this.corp_cd;					
		vo.scn_cp_push_titl				=  this.scn_cp_push_titl;			
		vo.scn_otb_div_cd				=  this.scn_otb_div_cd;			
		vo.app_tgt_mbr_div_cd			=  this.app_tgt_mbr_div_cd;		
		vo.app_tgt_sex					=  this.app_tgt_sex;				
		vo.app_tgt_age_all				=  this.app_tgt_age_all;			
		vo.app_tgt_age_20				=  this.app_tgt_age_20;			
		vo.app_tgt_age_30				=  this.app_tgt_age_30;			
		vo.app_tgt_age_40				=  this.app_tgt_age_40;			
		vo.app_tgt_age_50				=  this.app_tgt_age_50;			
		vo.push_strt_dt					=  this.push_strt_dt;				
		vo.push_end_dt					=  this.push_end_dt;				
		vo.push_time_div_cd				=  this.push_time_div_cd;			
		vo.stay_time_area_div_cd		=  this.stay_time_area_div_cd;	
		vo.stay_time_cd					=  this.stay_time_cd;				
		vo.stay_time					=  this.stay_time;				
		vo.spc_tnt_seq_1				=  this.spc_tnt_seq_1;			
		vo.spc_tnt_seq_2				=  this.spc_tnt_seq_2;			
		vo.spc_tnt_seq_3				=  this.spc_tnt_seq_3;			
		vo.spc_tnt_seq_4				=  this.spc_tnt_seq_4;			
		vo.spc_tnt_seq_5				=  this.spc_tnt_seq_5;			
		vo.spc_tnt_stay_time_div_cd		=  this.spc_tnt_stay_time_div_cd;	
		vo.spc_tnt_stay_time			=  this.spc_tnt_stay_time;		
		vo.spc_zone_id					=  this.spc_zone_id;				
		vo.exp_yn						=  this.exp_yn;					
		vo.push_sts_set_div_cd			=  this.push_sts_set_div_cd;		
		vo.no_vst_div_cd				=  this.no_vst_div_cd;			
		vo.no_vst_cnt					=  this.no_vst_cnt;				
		vo.app_no_use_div_cd			=  this.app_no_use_div_cd;		
		vo.no_use_cnt					=  this.no_use_cnt;				
		vo.scn_push_msg					=  this.scn_push_msg;				
		vo.sts							=  this.sts;						
		vo.reg_dttm						=  this.reg_dttm;					
		vo.mod_dttm						=  this.mod_dttm;					
		vo.reg_usr						=  this.reg_usr;					
		vo.mod_usr						=  this.mod_usr;	
		vo.cmp_id						=  this.cmp_id;	
		vo.link_url						=  this.link_url;
		vo.cp_act_strt_dt				=  this.cp_act_strt_dt;	
		vo.cp_act_end_dt				=  this.cp_act_end_dt;
		vo.dft_img_yn					=  this.dft_img_yn;
		vo.bi_img_seq					=  this.bi_img_seq;
		vo.bi_img_seq_uri				=  this.bi_img_seq_uri;

				
		return vo;
	}
}
