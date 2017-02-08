/*
 * EmpVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import kr.co.starfield.model.ACPN002;

/**
 * EMP 테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class ACPN002Vo extends BaseVo {
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
	
	
	
	
	public ACPN002 toModel() {
		ACPN002 acpn002 = new ACPN002();
		
		acpn002.cp_seq          =   this.cp_seq;           
		acpn002.img_seq         =   this.img_seq;          
		acpn002.bcn_cd      	=   this.bcn_cd;           
		acpn002.yymm     	    =   this.yymm;           
		acpn002.cp_iss_sub_seq  =   this.cp_iss_sub_seq;   
		acpn002.sub_dir         =   this.sub_dir;          
		acpn002.cp_iss_bcd_id   =   this.cp_iss_bcd_id;    
		acpn002.cp_div_cd   	=   this.cp_div_cd;    
		acpn002.cp_kind_cd  	=   this.cp_kind_cd;    
		acpn002.cp_iss_type_cd  =   this.cp_iss_type_cd;    
		
		
		return acpn002;
	}
}
