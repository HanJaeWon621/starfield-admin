package kr.co.starfield.model;

import kr.co.starfield.common.CommonCode.Facility.FacilityType;
import kr.co.starfield.model.vo.BaseVo;

/**
 *  Facility Group model
 *
 * @author yhkim
 * @version	1.0
 * @see
 * @date 2016.10.04
 */

public class FacilityForExcel extends BaseModel {
	
	public int no;
	
	public FacilityType faci_type;

	public String conv_faci_group_nm_ko;
	public String conv_faci_group_nm_en;
	public String conv_faci_group_nm_cn;
	public String conv_faci_group_nm_jp;
	public String conv_faci_desc_ko;
	public String conv_faci_desc_en;
	public String conv_faci_desc_cn;
	public String conv_faci_desc_jp;
	
	public String conv_faci_desc_dtl_ko1;
	public String conv_faci_desc_dtl_en1;
	public String conv_faci_desc_dtl_cn1;
	public String conv_faci_desc_dtl_jp1;
	public String conv_faci_desc_dtl_ko2;
	public String conv_faci_desc_dtl_en2;
	public String conv_faci_desc_dtl_cn2;
	public String conv_faci_desc_dtl_jp2;
	public String conv_faci_desc_dtl_ko3;
	public String conv_faci_desc_dtl_en3;
	public String conv_faci_desc_dtl_cn3;
	public String conv_faci_desc_dtl_jp3;
	public String conv_faci_desc_dtl_ko4;
	public String conv_faci_desc_dtl_en4;
	public String conv_faci_desc_dtl_cn4;
	public String conv_faci_desc_dtl_jp4;
	public String conv_faci_desc_dtl_ko5;
	public String conv_faci_desc_dtl_en5;
	public String conv_faci_desc_dtl_cn5;
	public String conv_faci_desc_dtl_jp5;

	public String reps_open_hr_min;
	public String reps_end_hr_min;
	public String irgu_open_hr_min;
	public String irgu_end_hr_min;

	public String reps_tel_num1;
	public String reps_tel_num2;
	public String reps_tel_num3;
	
	public String sort_ord;
	public String group_open_yn;
	
	public String conv_faci_nm_ko;
	public String fl;
	public String room_num;
	public String zone_id;
	public String map_id;
	public Double x_ctn_cord;
	public Double y_ctn_cord;
	public String poi_lev;
	public String open_yn;
    
	
    @Override
	public BaseVo toVo() {
		throw new UnsupportedOperationException();
	}
}
