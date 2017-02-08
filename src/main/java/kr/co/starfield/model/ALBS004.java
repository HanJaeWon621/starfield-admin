/*
 * Emp.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import kr.co.starfield.model.vo.ALBS004Vo;

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

public class ALBS004 extends BaseModel {
	public String pull_req_seq;
	public String uuid;
	public String req_div_cd;
	public String sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public String cust_id;
	
	@Override
	public ALBS004Vo toVo() {
		ALBS004Vo vo = new ALBS004Vo();
		
		vo.pull_req_seq   		= this.pull_req_seq;   
		vo.uuid           		= this.uuid;           
		vo.req_div_cd     		= this.req_div_cd;     
		vo.sts            		= this.sts;            
		vo.reg_dttm       		= this.reg_dttm;       
		vo.mod_dttm       		= this.mod_dttm;       
		vo.reg_usr        		= this.reg_usr;        
		vo.mod_usr        		= this.mod_usr;        
				
		return vo;
	}
}

