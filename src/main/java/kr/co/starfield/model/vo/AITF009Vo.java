package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.LbsZone;
import kr.co.starfield.model.MegaBoxP202;

/**
 *AITF003(megaBox-P202) 테이블과 맵핑되는 클래스
 *
 * @author hhLee
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

public class AITF009Vo extends BaseVo {
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
	public int    cmp_cnt;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	public String all_tot_cnt;
	
	public LbsZone toModel() {
		LbsZone model = new LbsZone();
		
		model.no = this.no;                     
		model.zone_seq = this.zone_seq;                     
		model.comp_id = this.comp_id;                      
		model.bcn_cd = this.bcn_cd;                       
		model.map_id = this.map_id;                       
		model.lbs_bcn_cd = this.lbs_bcn_cd;                   
		model.fl_id = this.fl_id;                        
		model.zone_id = this.zone_id;                      
		model.room_num = this.room_num;                     
		model.tnt_nm = this.tnt_nm;                       
		model.zone_type = this.zone_type;                    
		model.tnt_type = this.tnt_type;                     
		model.x_ctn_cord = this.x_ctn_cord;                   
		model.y_ctn_cord = this.y_ctn_cord;                   
		model.cmp_cnt = this.cmp_cnt;                      
		model.reg_dttm = Dt.toStringDateTime(this.reg_dttm);
		model.mod_dttm = Dt.toStringDateTime(this.mod_dttm);
		model.reg_usr = this.reg_usr;
		model.mod_usr = this.mod_usr;
		model.all_tot_cnt = this.all_tot_cnt;

		return model;
	}

}
