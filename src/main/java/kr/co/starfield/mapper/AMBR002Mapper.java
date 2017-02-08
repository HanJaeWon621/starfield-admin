package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.vo.SMBR002Vo;
import kr.co.starfield.model.vo.SMBR003Vo;

/**
 *  AMBR002Mapper interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.07.20
 */

public interface AMBR002Mapper {
	ArrayList<SMBR002Vo> getLikeEventList(SMBR002Vo vo);
	
	SMBR002Vo getLikeEvent(SMBR002Vo vo);
	
	SMBR002Vo getLikeEventAllStatus(SMBR002Vo vo);
	
	void regLikeEvent(SMBR002Vo vo);
	
	void modifyLikeEvent(SMBR002Vo vo);

	void deleteLikeEvent(SMBR002Vo vo);
	
	ArrayList<SMBR003Vo> getLikeTenantList(SMBR003Vo vo);
	
	SMBR003Vo getLikeTenant(SMBR003Vo vo);
	
	SMBR003Vo getLikeTenantAllStatus(SMBR003Vo vo);
	
	void regLikeTenant(SMBR003Vo vo);
	
	void modifyLikeTenant(SMBR003Vo vo);

	void deleteLikeTenant(SMBR003Vo vo);

}
