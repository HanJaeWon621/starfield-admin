/*
 * EmpVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import java.util.Arrays;
import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.ALBS002;

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

public class ALBS002Vo extends BaseVo {
	public String no;//순번
	public String bn_seq;
	public String bn_bi_img_seq;
	public String bn_all_img_seq;
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
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public String search_strt_dt;		
	public String search_end_dt;		
	public String search_dt_type;
	
	public String sh_post_type;		
	public String sh_text_type;		
	public String sh_text;
	
	
	public String sortColumn;
	public String sortColumnAscDesc;
	
	public ALBS002 toModel() {
		ALBS002 banner = new ALBS002();
		
		banner.no					= this.no;//쿠폰순번
		banner.bn_seq               = this.bn_seq;                 
		banner.bn_bi_img_seq        = this.bn_bi_img_seq;          
		banner.bn_all_img_seq       = this.bn_all_img_seq;         
		banner.cp_seq               = this.cp_seq;                 
		banner.evt_seq              = this.evt_seq;                
		banner.bn_titl              = this.bn_titl;                
		banner.bn_post_strt_dt      = this.bn_post_strt_dt;        
		banner.bn_post_end_dt       = this.bn_post_end_dt;         
		banner.bn_post_type         = this.bn_post_type;           
		banner.bn_exp_yn            = this.bn_exp_yn;              
		banner.bn_dtl_cts           = this.bn_dtl_cts;             
		banner.ord_seq           = this.ord_seq;             
		banner.sts                  = this.sts;                    
		banner.reg_dttm             = this.reg_dttm;               
		banner.mod_dttm             = this.mod_dttm;               
		banner.reg_usr              = this.reg_usr;                
		banner.mod_usr              = this.mod_usr;
		
		banner.sh_post_type         = this.sh_post_type;
		banner.sh_text_type         = this.sh_text_type;
		banner.sh_text              = this.sh_text;
		
		
		
		return banner;
	}
}
