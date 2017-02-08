package kr.co.starfield.model;

import kr.co.starfield.model.vo.SMBR003Vo;

/**
 *  Favorite Tenant model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author yhkim
 * @version	1.0,
 * @see
 * @date 2016.04.14
 */

public class LikeTenant extends BaseModel{
	public String mbr_seq;
	public String tnt_seq;
	public String bcn_cd;
	
	@Override
	public SMBR003Vo toVo() {
		SMBR003Vo vo = new SMBR003Vo();
		
		vo.mbr_seq = this.mbr_seq;
		vo.bcn_cd = this.bcn_cd;
		vo.tnt_seq = this.tnt_seq;
		
		return vo; 
	}
	
}
