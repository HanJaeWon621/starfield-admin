/*
 * EmpVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import kr.co.starfield.model.ALBS004;

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

public class ALBS004Vo extends BaseVo {
	public String pull_req_seq;
	public String uuid;
	public String req_div_cd; //1몰진입 2웹실행 3특종존방문 4 고객동선별푸쉬 5 웰컴
	public String sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public ALBS004 toModel() {
		ALBS004 albs004 = new ALBS004();
		
		albs004.pull_req_seq   		= this.pull_req_seq;   
		albs004.uuid           		= this.uuid;           
		albs004.req_div_cd     		= this.req_div_cd;     
		albs004.sts            		= this.sts;            
		albs004.reg_dttm       		= this.reg_dttm;       
		albs004.mod_dttm       		= this.mod_dttm;       
		albs004.reg_usr        		= this.reg_usr;        
		albs004.mod_usr        		= this.mod_usr;     
		
		return albs004;
	}
}
