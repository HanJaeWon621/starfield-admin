/*
 * Emp.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import kr.co.starfield.model.vo.SMBR002Vo;

/**
 *  likeEvent model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.07.20
 */

public class LikeEvent extends BaseModel {
	public String evt_seq;
	public String mbr_seq;
	
	@Override
	public SMBR002Vo toVo() {
		SMBR002Vo vo = new SMBR002Vo();
		
		vo.evt_seq = this.evt_seq;
		vo.mbr_seq = this.mbr_seq;
				
		return vo;
	}
}
