/*
 * EmpVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import kr.co.starfield.model.ALBS004;
import kr.co.starfield.model.ALBS004_D;
import kr.co.starfield.model.SCPN001_D;

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

public class SCPN001_DVo extends BaseVo {
	public String cp_seq;
	public String tnt_seq;
	public String busi_tnt_cd;
	public String sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	public String fl;
	
	public SCPN001_D toModel() {
		SCPN001_D scpn001_D = new SCPN001_D();
		
		scpn001_D.cp_seq       = this.cp_seq;          
        scpn001_D.tnt_seq      = this.tnt_seq;         
        scpn001_D.busi_tnt_cd  = this.busi_tnt_cd;     
        scpn001_D.sts          = this.sts;             
        scpn001_D.reg_dttm     = this.reg_dttm;        
        scpn001_D.mod_dttm     = this.mod_dttm;        
        scpn001_D.reg_usr      = this.reg_usr;         
        scpn001_D.mod_usr      = this.mod_usr;        
        scpn001_D.fl      	   = this.fl;        
		
		return scpn001_D;
	}
}
