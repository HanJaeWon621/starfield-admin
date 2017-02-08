/*
 * NoticeVo.java	1.00 2016/06/016
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.QNA;

/**
 *  SCMS008Vo(1:1 QnA) 테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.08.11
 */

public class SCMS008Vo extends BaseVo {
	public String qna_seq; 
	public String bcn_cd;
	public String cust_nm;
	public String cust_email;
	public String qutn_titl;
	public String qutn_cont;
	public String reply_yn;
	public String reply_cont;
	public Date qutn_dttm;
	public Date reply_dttm;
	
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;

}
