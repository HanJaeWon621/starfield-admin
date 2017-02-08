package kr.co.starfield.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kr.co.starfield.model.vo.STNT002Vo;
import kr.co.starfield.common.Utils;
import kr.co.starfield.common.CommonCode.Tenant.OperationStatus;
import kr.co.starfield.common.CommonCode.Tenant.TenantType;
import kr.co.starfield.model.vo.BaseVo;
import kr.co.starfield.model.vo.STNT001Vo;

public class Tenant extends BaseModel{
	public String tnt_seq;
	public String bcn_cd;
	public String tnt_cd;
	public TenantType tnt_type;
	
	public String tnt_nm_ko;
	public String tnt_nm_en;
	
	public String tnt_desc_ko;
	public String tnt_desc_en;
	
	public String fl;
	public String room_num;
	public OperationStatus opr_sts;
	public Integer sts;
	
	public String busi_tnt_cd;
	public String coct_strt_prid;
	public String coct_end_prid;
	
	public String zone_id;
	public String map_id;
	public double x_ctn_cord;
	public double y_ctn_cord;
	public Integer poi_lev;
	public String reps_exp_poi_lev_yn;
	
	public String tnt_tel_num1;
	public String tnt_tel_num2;
	public String tnt_tel_num3;
	
	public String open_hr_min;
	public String end_hr_min;
	public String irgu_open_hr_min;
	public String irgu_end_hr_min;
	
	public List<TenantSimpleCategory> cate_list = new ArrayList<>();
	public String tnt_tag;
	public Set<String> tnt_tag_list = new HashSet<>();
	
	public String img_main_bg_web_uri;
	public String img_main_bg_mobi_uri;
	public String img_main_bg_logo_uri;
	public String img_logo_uri;
	public String img_mobi_logo_uri;
	public String img_thmb_uri;
	
	public boolean modify_cate_yn = false;
	
	public STNT001Vo toTenantVo() throws ParseException {
		STNT001Vo tenantVo = new STNT001Vo();
		
		tenantVo.tnt_seq = this.tnt_seq;
		tenantVo.bcn_cd = this.bcn_cd;
		tenantVo.tnt_cd = this.tnt_cd;
		tenantVo.img_main_bg_web_uri = this.img_main_bg_web_uri;
		tenantVo.img_main_bg_mobi_uri = this.img_main_bg_mobi_uri;
		tenantVo.img_main_bg_logo_uri = this.img_main_bg_logo_uri;
		tenantVo.img_logo_uri = this.img_logo_uri;
		tenantVo.img_mobi_logo_uri = this.img_mobi_logo_uri;
		tenantVo.img_thmb_uri = this.img_thmb_uri;
		tenantVo.fl = this.fl;
		tenantVo.room_num = this.room_num;
		tenantVo.tnt_nm_ko = this.tnt_nm_ko;
		tenantVo.tnt_nm_en = this.tnt_nm_en;
		tenantVo.tnt_nm_cn = this.tnt_nm_en;
		tenantVo.tnt_nm_jp = this.tnt_nm_en;
		tenantVo.tnt_type = this.tnt_type;
		tenantVo.tnt_tel_num1 = this.tnt_tel_num1;
		tenantVo.tnt_tel_num2 = this.tnt_tel_num2;
		tenantVo.tnt_tel_num3 = this.tnt_tel_num3;
		tenantVo.tnt_tag = this.tnt_tag;
		tenantVo.open_hr_min = this.open_hr_min;
		tenantVo.end_hr_min = this.end_hr_min;
		tenantVo.irgu_open_hr_min = this.irgu_open_hr_min;
		tenantVo.irgu_end_hr_min = this.irgu_end_hr_min;
		tenantVo.tnt_desc_ko = this.tnt_desc_ko;
		tenantVo.tnt_desc_en = this.tnt_desc_en;
		tenantVo.tnt_desc_cn = this.tnt_desc_en;
		tenantVo.tnt_desc_jp = this.tnt_desc_en;
		tenantVo.reps_exp_poi_lev_yn = this.reps_exp_poi_lev_yn;
		tenantVo.opr_sts = this. opr_sts;
		tenantVo.sts = this.sts;	
//		tenantVo.coct_strt_prid =!Utils.Str.isEmpty(this.coct_strt_prid) ? new SimpleDateFormat("yyyy.MM.dd").parse(this.coct_strt_prid) : null;
//		tenantVo.coct_end_prid =!Utils.Str.isEmpty(this.coct_end_prid) ? new SimpleDateFormat("yyyy.MM.dd").parse(this.coct_end_prid) : null;
		tenantVo.reg_usr = this.user;
		tenantVo.mod_usr = this.user;
		return tenantVo;		
	}

	public List<STNT002Vo> toCategoriesVo() {
		List<STNT002Vo> result = new ArrayList<>();
		
		for(TenantSimpleCategory cateSeq : cate_list){
			STNT002Vo vo = new STNT002Vo();
			vo.bcn_cd = this.bcn_cd;
			vo.tnt_seq = this.tnt_seq;
			vo.cate_seq = cateSeq.cate_seq;
			vo.reg_usr = this.user;
			vo.mod_usr = this.user;
			result.add(vo);
		}

		return result;
	}
	
	public List<String> toCategories() {
		List<String> result = new ArrayList<>();
		
		for(TenantSimpleCategory category : cate_list){
			result.add(category.cate_seq);
		}

		return result;
	}

	@Override
	public BaseVo toVo() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString() {
		return "Tenant [tnt_seq=" + tnt_seq + ", bcn_cd=" + bcn_cd + ", tnt_cd=" + tnt_cd + ", tnt_type=" + tnt_type
				+ ", tnt_nm_ko=" + tnt_nm_ko + ", tnt_nm_en=" + tnt_nm_en + ", tnt_desc_ko=" + tnt_desc_ko
				+ ", tnt_desc_en=" + tnt_desc_en + ", fl=" + fl + ", room_num=" + room_num + ", opr_sts=" + opr_sts
				+ ", sts=" + sts + ", busi_tnt_cd=" + busi_tnt_cd + ", coct_strt_prid=" + coct_strt_prid
				+ ", coct_end_prid=" + coct_end_prid + ", zone_id=" + zone_id + ", x_ctn_cord=" + x_ctn_cord
				+ ", y_ctn_cord=" + y_ctn_cord + ", poi_lev=" + poi_lev + ", reps_exp_poi_lev_yn=" + reps_exp_poi_lev_yn
				+ ", tnt_tel_num1=" + tnt_tel_num1 + ", tnt_tel_num2=" + tnt_tel_num2 + ", tnt_tel_num3=" + tnt_tel_num3
				+ ", open_hr_min=" + open_hr_min + ", end_hr_min=" + end_hr_min + ", irgu_open_hr_min="
				+ irgu_open_hr_min + ", irgu_end_hr_min=" + irgu_end_hr_min + ", cate_list=" + cate_list + ", tnt_tag="
				+ tnt_tag + ", tnt_tag_list=" + tnt_tag_list + ", img_main_bg_web_uri=" + img_main_bg_web_uri
				+ ", img_main_bg_mobi_uri=" + img_main_bg_mobi_uri + ", img_main_bg_logo_uri=" + img_main_bg_logo_uri
				+ ", img_logo_uri=" + img_logo_uri + ", img_mobi_logo_uri=" + img_mobi_logo_uri + ", img_thmb_uri="
				+ img_thmb_uri + "]";
	}

}
