/*
 * Emp.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import kr.co.starfield.model.vo.ALBS004Vo;
import kr.co.starfield.model.vo.ALBS004_DVo;

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

public class ALBS004_D extends BaseModel {
	public String inbox_seq;    //인박스 순번
	public String fav_reg_yn;   //즐겨찾기 여부(Y/N)
	public String link_uri;     
	public String link_type;	//D/U/P
	public String push_div_cd;
	public String inbox_titl;
	public String inbox_cont;
	public String reg_dttm;
	public String log_cd;

	@Override
	public ALBS004_DVo toVo() {
		ALBS004_DVo vo = new ALBS004_DVo();

		vo.inbox_seq            = this.inbox_seq;              
        vo.push_div_cd          = this.push_div_cd;              
        vo.inbox_titl           = this.inbox_titl;               
        vo.inbox_cont           = this.inbox_cont;               
		vo.fav_reg_yn           = this.fav_reg_yn;               
		vo.reg_dttm             = this.reg_dttm;                 
		
		return vo;
	}
}
