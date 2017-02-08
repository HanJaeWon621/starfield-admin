/*
 * EmpVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.List;

import kr.co.starfield.model.vo.BaseVo;


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

public class ACPN003 extends BaseVo{
	public String cate_nm_ko;
	public String tnt_nm_ko;
	public String room_num;
	public String fl;
	public String tnt_seq;
	public String cate_seq;
	public String busi_tnt_cd;
	public String img_logo_uri;
	public String img_thmb_uri;
	public String zone_id;
	public List<Category> cate_list = new ArrayList<>();
	
}
