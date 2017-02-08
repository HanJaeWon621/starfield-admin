/*
 * EmpVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import kr.co.starfield.model.vo.ACPN002Vo;

/**
 * 
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class ACPN002 extends BaseModel {
	public String cp_seq;
	public String img_seq;
	public String bcn_cd;
	public String yymm;
	public String cp_iss_sub_seq;
	public String sub_dir;
	public String cp_iss_bcd_id;
	public String cp_div_cd;
	public String cp_kind_cd;
	public String cp_iss_type_cd;
	
	@Override
	public ACPN002Vo toVo() {
		ACPN002Vo acpn002Vo = new ACPN002Vo();
		
		acpn002Vo.cp_seq          =   this.cp_seq;           
		acpn002Vo.img_seq         =   this.img_seq;          
		acpn002Vo.bcn_cd      	  =   this.bcn_cd;           
		acpn002Vo.yymm     	  	  =   this.yymm;           
		acpn002Vo.cp_iss_sub_seq  =   this.cp_iss_sub_seq;   
		acpn002Vo.sub_dir         =   this.sub_dir;          
		acpn002Vo.cp_iss_bcd_id   =   this.cp_iss_bcd_id;
		acpn002Vo.cp_div_cd   	  =   this.cp_div_cd;    
		acpn002Vo.cp_kind_cd  	  =   this.cp_kind_cd;    
		acpn002Vo.cp_iss_type_cd  =   this.cp_iss_type_cd;
		
		return acpn002Vo;
	}
}
