/*
 * NoticeVo.java	1.00 2016/06/016
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.FAQ;

/**
 *  SCMS006(FAQ) 테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

public class SCMS006Vo extends BaseVo {
	public String faq_seq; 
	public String cate_seq;
	public String bcn_cd;
	
	public String faq_titl_ko;
	public String faq_titl_en;
	public String faq_titl_jp;
	public String faq_titl_cn;
	
	public String faq_cont_ko;
	public String faq_cont_en;
	public String faq_cont_jp;
	public String faq_cont_cn;
	
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public FAQ toModel() {
		FAQ model = new FAQ();
		
		model.faq_seq = this.faq_seq;
		model.cate_seq = this.cate_seq;
		model.bcn_cd = this.bcn_cd;
		
		model.faq_titl_ko = this.faq_titl_ko;
		model.faq_titl_en = this.faq_titl_en;
		model.faq_titl_jp = this.faq_titl_jp;
		model.faq_titl_cn = this.faq_titl_cn;
		
		model.faq_cont_ko = this.faq_cont_ko;
		model.faq_cont_en = this.faq_cont_en;
		model.faq_cont_jp = this.faq_cont_jp;
		model.faq_cont_cn = this.faq_cont_cn;
		
		model.sts = this.sts;
		model.reg_dttm = Dt.toStringDateTime(this.reg_dttm);
		model.mod_dttm = Dt.toStringDateTime(this.mod_dttm);
		model.reg_usr = this.reg_usr;
		model.mod_usr = this.mod_usr;
				
		return model;
	}

}
