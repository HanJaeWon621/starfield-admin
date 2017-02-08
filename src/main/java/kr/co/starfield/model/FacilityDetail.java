/*
 * InstaDetail.java	1.00 2016/06/16
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import kr.co.starfield.model.vo.SFCT001_DVo;


/**
 *  FacilityDatail model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eeLee
 * @version	1.0
 * @see
 * @date 2016.06.20
 */

public class FacilityDetail extends BaseModel {
	
	public int no;
	public String bcn_cd;
	public String conv_faci_seq;
	public String conv_faci_dtl_seq;
	public String conv_faci_group_nm_ko;
	public String conv_faci_nm_ko;
	public String fl;
	public String room_num;
	public String zone_id;
	public String map_id;
	public Double x_ctn_cord;
	public Double y_ctn_cord;
	public String poi_lev;
	public int sts;
	public int data_sts;
	
	@Override
	public SFCT001_DVo toVo() {
		SFCT001_DVo vo = new SFCT001_DVo();
		
		vo.conv_faci_seq = this.conv_faci_seq;
		vo.conv_faci_dtl_seq = this.conv_faci_dtl_seq;
		vo.conv_faci_nm_ko = this.conv_faci_nm_ko;
		vo.fl = this.fl;
		vo.room_num = this.room_num;
		vo.poi_lev = this.poi_lev;
		vo.reg_usr = this.user;
		vo.mod_usr = this.user;
		vo.sts = this.sts;

		return vo;
	}
}
