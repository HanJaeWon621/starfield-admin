/*
 * ImgFile.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import java.util.Date;

import kr.co.starfield.model.vo.ImgFileVo;

/**
 *  ImgFile model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class ImgFile extends BaseModel {
	
	public String img_seq;
	public String bcn_cd;
	public String img_pys_loc;
	public String img_uri;
	public String img_thmb;
	public String img_thmb_uri;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	@Override
	public ImgFileVo toVo() {
		
		ImgFileVo vo = new ImgFileVo();
		
		vo.img_seq = img_seq;
		vo.bcn_cd = bcn_cd;
		vo.img_pys_loc = img_pys_loc;
		vo.img_uri = img_uri;
		vo.img_thmb = img_thmb;
		vo.img_thmb_uri = img_thmb_uri;
		vo.reg_dttm = reg_dttm;
		vo.mod_dttm = mod_dttm;
		vo.reg_usr = reg_usr;
		vo.mod_usr = mod_usr;
		
		return vo;
	}
	
}
