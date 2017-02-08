package kr.co.starfield.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.CommonCode.Event.EventDvi;
import kr.co.starfield.common.CommonCode.Event.EventStatus;
import kr.co.starfield.common.CommonCode.Event.EventType;
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.vo.BaseVo;
import kr.co.starfield.model.vo.SEVT001Vo;

/**
 *  Event model
 *
 * @author yhkim
 * @version	1.0
 * @see
 * @date 2016.08.08
 */

public class Event extends BaseModel {
	
	public String no;
	public String bcn_cd;
	public String evt_seq;
	public String[] evt_seq_arr;
	public EventDvi evt_dvi;
	public String evt_titl;
	public String evt_cont;
	public String evt_img;
	public String evt_img_uri;
	public String evt_lnk_url;
	public String evt_lnk_btn;
	public EventType evt_type;
	public String evt_post_strt_dt;
	public String evt_post_end_dt;
	public String evt_post_strt_hour;
	public String evt_post_end_hour;
	public String evt_pick_dt;
	public String evt_pick_plan_dt;
	public String evt_strt_dt;
	public String evt_end_dt;
	public String evt_open_yn;
	public String evt_app_dt;
	
	public String reg_dttm;
	public String reg_usr;
	public String mod_usr;
	public String evt_app_id;
	public EventStatus evt_stat;

	public String evt_plce;
	public String prvc_agre_term;
	public String opr_info_agre_term;
	
	public String web_list_open_type;
	public String web_list_open_colr;
	public String web_list_open_img;
	public String web_list_open_img_uri;
	
	public String mobi_list_open_type;
	public String mobi_list_open_colr;
	public String mobi_list_open_img;
	public String mobi_list_open_img_uri;
	
	public int evt_hits;
	public int aply_cnt;
	public int pick_cnt;
	
	public String tnt_seq;
	public String tnt_nm_ko;
	public Integer sts;

	public SEVT001Vo toEventVo() throws ParseException {
		SEVT001Vo vo = new SEVT001Vo();
//		vo.evt_seq = this.evt_seq;
		vo.evt_seq_arr = this.evt_seq_arr;
		vo.bcn_cd = this.bcn_cd;
		vo.tnt_seq = this.tnt_seq;
		vo.evt_img_seq = this.evt_img;
		vo.web_list_open_img_seq = this.web_list_open_img;
		vo.mobi_list_open_img_seq = this.mobi_list_open_img;
		vo.evt_type = this.evt_type != null ? this.evt_type.getCodeCd() : null;
		vo.evt_dvi = this.evt_dvi != null ? this.evt_dvi.getCodeCd() : null;
		vo.evt_plce = this.evt_plce;
		vo.evt_titl = this.evt_titl;
		vo.evt_cont = this.evt_cont;
		vo.evt_lnk_url = this.evt_lnk_url;
		vo.evt_lnk_btn = this.evt_lnk_btn;
		vo.evt_strt_dt = !Utils.Str.isEmpty(this.evt_strt_dt) ? new SimpleDateFormat("yyyy.MM.dd").parse(this.evt_strt_dt) : null;
		vo.evt_end_dt = this.evt_end_dt != null ? new SimpleDateFormat("yyyy.MM.dd").parse(this.evt_end_dt) : null;
		vo.evt_pick_plan_dt = !Utils.Str.isEmpty(this.evt_pick_plan_dt)? new SimpleDateFormat("yyyy.MM.dd").parse(this.evt_pick_plan_dt) : null;
		vo.prvc_agre_term = this.prvc_agre_term;
		vo.opr_info_agre_term = this.opr_info_agre_term;
		vo.evt_open_yn = this.evt_open_yn;
		vo.evt_post_strt_dt = !Utils.Str.isEmpty(this.evt_post_strt_dt) ? new SimpleDateFormat("yyyy.MM.dd HH:mm").parse(this.evt_post_strt_dt +" "+ this.evt_post_strt_hour) : null;
		vo.evt_post_end_dt = !Utils.Str.isEmpty(this.evt_post_strt_dt) ? new SimpleDateFormat("yyyy.MM.dd HH:mm").parse(this.evt_post_end_dt +" "+ this.evt_post_end_hour) : null;
		vo.web_list_open_type = this.web_list_open_type;
		vo.web_list_open_colr = this.web_list_open_colr;
		vo.mobi_list_open_type = this.mobi_list_open_type;
		vo.mobi_list_open_colr = this.mobi_list_open_colr;
		vo.evt_app_dt = !Utils.Str.isEmpty(this.evt_app_dt) ? new SimpleDateFormat("yyyy.MM.dd").parse(this.evt_app_dt) : null;
		vo.evt_app_id = this.evt_app_id;
		vo.sts = this.sts;
		vo.reg_usr = this.user;
		vo.mod_usr = this.user;
		
		return vo;
	}

	@Override
	public BaseVo toVo() {
		throw new UnsupportedOperationException();
	}
}
