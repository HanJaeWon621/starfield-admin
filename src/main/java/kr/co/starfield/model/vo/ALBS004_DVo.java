/*
 * EmpVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import kr.co.starfield.model.ALBS004;
import kr.co.starfield.model.ALBS004_D;

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

public class ALBS004_DVo extends BaseVo {
	public String inbox_seq;
	public String pull_req_seq;
	public String uuid;
	public String cp_seq;
	public String cust_id;
	public String push_seq;
	public String push_div_cd;
	public String send_usr;
	public String inbox_titl;
	public String inbox_cont;
	public String fav_reg_yn;
	public String sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	public String pull_div_cd;
	public String inbox_cont2;
	public String inbox_cont3;
	public String inbox_cont4;
	public String link_seq;
	public String zone_id;
	public String access_token;
	
	public ALBS004_D toModel() {
		ALBS004_D albs004_D = new ALBS004_D();
		
        albs004_D.push_div_cd          = this.push_div_cd;              
        albs004_D.inbox_titl           = this.inbox_titl;               
        albs004_D.inbox_cont           = this.inbox_cont;               
		albs004_D.fav_reg_yn           = this.fav_reg_yn;               
		albs004_D.reg_dttm             = this.reg_dttm;                 
		
		return albs004_D;
	}
}
