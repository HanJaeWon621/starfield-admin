package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.CommonCode;
import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.Event;

/**
 *  AEVT001(event) 테이블과 맵핑되는 클래스
 *
 * @author hhlee
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

public class SEVT001Vo extends BaseVo {
	public String evt_seq;
	public String[] evt_seq_arr;
	public String bcn_cd;
	public String tnt_seq;
	public String evt_img_seq;
	public String web_list_open_img_seq;
	public String mobi_list_open_img_seq;
	
	public String evt_type;
	public String evt_dvi;
	public String evt_plce;
	
	public String evt_titl;
	public String evt_cont;
	
	public Date evt_strt_dt;
	public Date evt_end_dt;
	
	public Date evt_pick_plan_dt;
	public Date evt_pick_dt;
	
	public String prvc_agre_term;
	public String opr_info_agre_term;
	
	public String evt_open_yn;
	
	public Date evt_app_dt;
	public String evt_app_id;
	
	public Date evt_post_strt_dt;
	public Date evt_post_end_dt;
	
	public int evt_hits;
	
	public String web_list_open_type;
	public String web_list_open_colr;
	
	public String mobi_list_open_type;
	public String mobi_list_open_colr;
	
	public Integer sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public String evt_lnk_url;
	public String evt_lnk_btn;
	public String evt_custom_yn;

}
