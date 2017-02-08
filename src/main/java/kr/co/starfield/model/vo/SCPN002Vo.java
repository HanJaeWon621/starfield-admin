/*
 * EmpVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import kr.co.starfield.model.SCPN002;

/**
 * SCPN002 테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class SCPN002Vo extends BaseVo {
	public String cp_iss_mst_seq;//쿠폰발급마스터순번
	public String cp_iss_sub_seq;//발급부순번
	public String cp_seq;//쿠폰순번
	public String tnt_seq;//테넌트순번
	public String img_seq;//쿠폰발급바코드이미지순번
	public String cp_iss_bcd_id;//쿠콘발급바코드ID
	public String reg_dttm;//등록일시
	public String mod_dttm;//수정일시
	public String reg_usr;//등록자
	public String mod_usr;//수정자
	
	
	public SCPN002 toModel() {
		SCPN002 scpn0002 = new SCPN002();
		
		scpn0002.cp_iss_mst_seq    =   this.cp_iss_mst_seq;                       
		scpn0002.cp_iss_sub_seq    =   this.cp_iss_sub_seq;                       
		scpn0002.cp_seq            =   this.cp_seq;                           
		scpn0002.img_seq           =   this.img_seq;                          
		scpn0002.tnt_seq           =   this.tnt_seq;                          
		scpn0002.cp_iss_bcd_id     =   this.cp_iss_bcd_id;                    
		scpn0002.reg_dttm          =   this.reg_dttm;                         
		scpn0002.mod_dttm          =   this.mod_dttm;                         
		scpn0002.reg_usr           =   this.reg_usr;                          
		scpn0002.mod_usr           =   this.mod_usr;                          
		
		return scpn0002;
	}
}
