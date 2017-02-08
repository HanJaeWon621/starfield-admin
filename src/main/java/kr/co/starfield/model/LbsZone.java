/*
 * Emp.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.AITF009Vo;
import kr.co.starfield.model.vo.SCPN001_DVo;

/**
 *  Emp model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class LbsZone extends BaseModel {
	public String no;
	public String zone_seq;
	public String comp_id;
	public String bcn_cd;
	public String map_id;
	public String lbs_bcn_cd;
	public String fl_id;
	public String zone_id;
	public String room_num;
	public String tnt_nm;
	public String zone_type;
	public String tnt_type;
	public Double x_ctn_cord;
	public Double y_ctn_cord;
	public int cmp_cnt;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	public String all_tot_cnt;
	
	@Override
	public AITF009Vo toVo() {
		AITF009Vo vo = new AITF009Vo();
		
		vo.no = this.no;                     
		vo.zone_seq = this.zone_seq;                     
		vo.comp_id = this.comp_id;                      
		vo.bcn_cd = this.bcn_cd;                       
		vo.map_id = this.map_id;                       
		vo.lbs_bcn_cd = this.lbs_bcn_cd;                   
		vo.fl_id = this.fl_id;                        
		vo.zone_id = this.zone_id;                      
		vo.room_num = this.room_num;                     
		vo.tnt_nm = this.tnt_nm;                       
		vo.zone_type = this.zone_type;                    
		vo.tnt_type = this.tnt_type;                     
		vo.x_ctn_cord = this.x_ctn_cord;                   
		vo.y_ctn_cord = this.y_ctn_cord;                   
		vo.cmp_cnt = this.cmp_cnt;                      
		vo.reg_usr = this.reg_usr;
		vo.mod_usr = this.mod_usr;
		vo.all_tot_cnt = this.all_tot_cnt;

		return vo;
	}
}
