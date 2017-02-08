/*
 * Emp.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.ALBS002Vo;
import kr.co.starfield.model.vo.SCPN001_DVo;

/**
 *  ALBS002 model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author dhlee
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class ALBS002 extends BaseModel {
	public String no;//순번
	public String bn_seq;
	public String bn_bi_img_seq;
	public String bn_all_img_seq;
	public String bn_bi_img;
	public String bn_all_img;
	public String cp_seq;
	public String evt_seq;
	public String bn_titl;
	public String bn_post_strt_dt;
	public String bn_post_end_dt;
	public String bn_post_type;
	public String bn_exp_yn;
	public String bn_dtl_cts;
	public String ord_seq;
	public String sts;
	public String f_reg_dttm;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public String img_type;
	public String img_uri;
	public String bn_post_seq;
	public String link_pg_titl;
	public String click_cnt;
	public String bn_post_dt;
	
	public String sh_post_type;		
	public String sh_text_type;		
	public String sh_text;
	
	@Override
	public ALBS002Vo toVo() {
		ALBS002Vo vo = new ALBS002Vo();
		
		vo.no  = this.no;
		vo.bn_seq               = this.bn_seq;                 
		vo.bn_bi_img_seq        = this.bn_bi_img_seq;          
		vo.bn_all_img_seq       = this.bn_all_img_seq;         
		vo.cp_seq               = this.cp_seq;                 
		vo.evt_seq              = this.evt_seq;                
		vo.bn_titl              = this.bn_titl;                
		vo.bn_post_strt_dt      = this.bn_post_strt_dt;        
		vo.bn_post_end_dt       = this.bn_post_end_dt;         
		vo.bn_post_type         = this.bn_post_type;           
		vo.bn_exp_yn            = this.bn_exp_yn;              
		vo.bn_dtl_cts           = this.bn_dtl_cts;             
		vo.ord_seq           	= this.ord_seq;             
		vo.sts                  = this.sts;                    
		vo.reg_dttm             = this.reg_dttm;               
		vo.mod_dttm             = this.mod_dttm;               
		vo.reg_usr              = this.reg_usr;                
		vo.mod_usr              = this.mod_usr;                	
		
		vo.sh_post_type         = this.sh_post_type;
		vo.sh_text_type         = this.sh_text_type;
		vo.sh_text              = this.sh_text;
		return vo;
	}
}
