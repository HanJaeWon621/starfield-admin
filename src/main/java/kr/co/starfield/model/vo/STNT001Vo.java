package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.CommonCode.Tenant.OperationStatus;
import kr.co.starfield.common.CommonCode.Tenant.TenantType;
import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.Tenant;

public class STNT001Vo extends BaseVo {
	
	public String tnt_seq;
	public String bcn_cd;
	public String tnt_cd;
	public String img_main_bg_web_uri;
	public String img_main_bg_mobi_uri;
	public String img_main_bg_logo_uri;
	public String img_logo_uri;
	public String img_mobi_logo_uri;
	public String img_thmb_uri;
	public String fl;
	public String room_num;
	public String tnt_nm_ko;
	public String tnt_nm_en;
	public String tnt_nm_cn;
	public String tnt_nm_jp;
	public TenantType tnt_type;
	public String tnt_tel_num1;
	public String tnt_tel_num2;
	public String tnt_tel_num3;
	public String tnt_tag;
	public String open_hr_min;
	public String end_hr_min;
	public String irgu_open_hr_min;
	public String irgu_end_hr_min;
	public String tnt_desc_ko;
	public String tnt_desc_en;
	public String tnt_desc_cn;
	public String tnt_desc_jp;
	public String reps_exp_poi_lev_yn;
	public Integer poi_lev;
	public String zone_id;
	public OperationStatus opr_sts;
	public Date	opr_reg_dttm;
	public Date opr_mod_dttm;
	public Integer sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	public String busi_tnt_cd;
	public Date coct_strt_prid;
	public Date coct_end_prid;

}
