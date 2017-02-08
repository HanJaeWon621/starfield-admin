/*
 * Emp.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import kr.co.starfield.model.vo.ALBS004Vo;
import kr.co.starfield.model.vo.ALBS004_DVo;
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

public class SCPN001_D extends BaseModel {
	public String scn_otb_cp_push_seq;
	public String cp_seq;
	public String tnt_seq;
	public String zone_id;
	public String busi_tnt_cd;
	public String sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	public String fl;
	public String cate_nm_ko;
	public String tnt_nm_ko;
	public String room_num;
	public String img_logo_uri;
	public String img_thmb_uri;
	
	@Override
	public SCPN001_DVo toVo() {
		SCPN001_DVo vo = new SCPN001_DVo();

		vo.cp_seq       = this.cp_seq;          
        vo.tnt_seq      = this.tnt_seq;         
        vo.busi_tnt_cd  = this.busi_tnt_cd;     
        vo.sts          = this.sts;             
        vo.reg_dttm     = this.reg_dttm;        
        vo.mod_dttm     = this.mod_dttm;        
        vo.reg_usr      = this.reg_usr;         
        vo.mod_usr      = this.mod_usr;         
        vo.fl	        = this.fl;         
		
        
        return vo;
	}
}
