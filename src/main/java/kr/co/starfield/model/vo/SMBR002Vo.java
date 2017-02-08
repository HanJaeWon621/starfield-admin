/**
 * SMBR002테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.07.20
 */

package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.model.LikeEvent;

public class SMBR002Vo extends BaseVo{

	public String evt_seq;
	public String mbr_seq;
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public LikeEvent toModel() {
		
		LikeEvent model = new LikeEvent();
		model.evt_seq = this.evt_seq;
		model.mbr_seq = this.mbr_seq;
				
		return model; 
	}
}
