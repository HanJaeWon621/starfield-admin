package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class TenantWeb {
	public String bcn_cd;
	public String tnt_seq;
	public List<TenantCategory> cate_detail = new ArrayList<>();
	public List<String> cate_list_ko = new ArrayList<>();
	public List<String> cate_list_en = new ArrayList<>();
	public List<String> cate_list_cn = new ArrayList<>();
	public List<String> cate_list_jp = new ArrayList<>();
	public String tnt_nm_ko;
	public String tnt_nm_en;
	public String tnt_nm_cn;
	public String tnt_nm_jp;
	public String img_main_bg_web_uri;
	public String img_main_bg_mobi_uri;
	public String img_main_bg_logo_uri;
	public String img_logo_uri;
	public String img_mobi_logo_uri;
	public String img_thmb_uri;
	public int tnt_type;
	public String fl;
	public List<String> fl_list = new ArrayList<>();
	public String open_hr_min;
	public String end_hr_min;
	public String irgu_open_hr_min;
	public String irgu_end_hr_min;
	public String tnt_tel;
	public String zone_id;
	public Integer poi_lev;
	public double x_ctn_cord;
	public double y_ctn_cord;
	public String map_id; 
	public String tnt_tag;
	public Set<String> tnt_tag_list = new HashSet<>();
	public String tnt_desc_ko;
	public String tnt_desc_en;
	public String tnt_desc_cn;
	public String tnt_desc_jp;
	public String mobi_url;
	public int sort_ord;
	@Override
	public String toString() {
		return "TenantWeb [bcn_cd=" + bcn_cd + ", tnt_seq=" + tnt_seq + ", cate_detail=" + cate_detail
				+ ", cate_list_ko=" + cate_list_ko + ", cate_list_en=" + cate_list_en + ", cate_list_cn=" + cate_list_cn
				+ ", cate_list_jp=" + cate_list_jp + ", tnt_nm_ko=" + tnt_nm_ko + ", tnt_nm_en=" + tnt_nm_en
				+ ", tnt_nm_cn=" + tnt_nm_cn + ", tnt_nm_jp=" + tnt_nm_jp + ", img_main_bg_web_uri="
				+ img_main_bg_web_uri + ", img_main_bg_mobi_uri=" + img_main_bg_mobi_uri + ", img_main_bg_logo_uri="
				+ img_main_bg_logo_uri + ", img_logo_uri=" + img_logo_uri + ", img_thmb_uri=" + img_thmb_uri
				+ ", tnt_type=" + tnt_type + ", fl=" + fl + ", fl_list=" + fl_list + ", open_hr_min=" + open_hr_min
				+ ", end_hr_min=" + end_hr_min + ", irgu_open_hr_min=" + irgu_open_hr_min + ", irgu_end_hr_min="
				+ irgu_end_hr_min + ", tnt_tel=" + tnt_tel + ", zone_id=" + zone_id + ", poi_lev=" + poi_lev
				+ ", x_ctn_cord=" + x_ctn_cord + ", y_ctn_cord=" + y_ctn_cord + ", map_id=" + map_id + ", tnt_tag="
				+ tnt_tag + ", tnt_tag_list=" + tnt_tag_list + ", tnt_desc_ko=" + tnt_desc_ko + ", tnt_desc_en="
				+ tnt_desc_en + ", tnt_desc_cn=" + tnt_desc_cn + ", tnt_desc_jp=" + tnt_desc_jp + ", mobi_url="
				+ mobi_url + ", sort_ord=" + sort_ord + "]";
	}
	
	
}
