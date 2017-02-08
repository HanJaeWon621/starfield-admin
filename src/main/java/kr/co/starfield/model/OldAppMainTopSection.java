package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.Date;

import kr.co.starfield.model.vo.SCMS011Vo;

/**
 *  AppMainInit model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.09.20
 */

public class OldAppMainTopSection {
	public String any_seq;
	public String top_bg_img_uri;
	public String top_logo_img_uri; 
	public String div;
	public String title_head_text; 
	public String title_main_text; 
	public String title_sub_text; 
	public String text_class; 
	public String desc_text; 
	public String start_dt; 
	public String end_dt;
	public Date mod_dttm;
	
	public void setAny_seq(String any_seq) {
		this.any_seq = any_seq == null ? "" : any_seq;
	}
	public void setTop_bg_img_uri(String top_bg_img_uri) {
		this.top_bg_img_uri = top_bg_img_uri == null ? "" : top_bg_img_uri;
	}
	public void setTop_logo_img_uri(String top_logo_img_uri) {
		this.top_logo_img_uri = top_logo_img_uri == null ? "" : top_logo_img_uri;
	}
	public void setDiv(String div) {
		this.div = div == null ? "" : div;
	}
	public void setTitle_head_text(String title_head_text) {
		this.title_head_text = title_head_text == null ? "" : title_head_text;
	}
	public void setTitle_main_text(String title_main_text) {
		this.title_main_text = title_main_text == null ? "" : title_main_text;
	}
	public void setTitle_sub_text(String title_sub_text) {
		this.title_sub_text = title_sub_text == null ? "" : title_sub_text;
	}
	public void setText_class(String text_class) {
		this.text_class = text_class == null ? "" : text_class;
	}
	public void setDesc_text(String desc_text) {
		this.desc_text = desc_text == null ? "" : desc_text;
	}
	public void setStart_dt(String start_dt) {
		this.start_dt = start_dt == null ? "" : start_dt;
	}
	public void setEnd_dt(String end_dt) {
		this.end_dt = end_dt == null ? "" : end_dt;
	}
	
}
