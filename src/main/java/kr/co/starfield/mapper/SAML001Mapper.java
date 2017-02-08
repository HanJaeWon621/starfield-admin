/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.ArrayList;
import java.util.List;

import kr.co.starfield.model.ALBS001;
import kr.co.starfield.model.ALBS003;
import kr.co.starfield.model.SAML001;
import kr.co.starfield.model.SAML002;
import kr.co.starfield.model.SAML003;
import kr.co.starfield.model.SCPN003;
import kr.co.starfield.model.SCPN005;
import kr.co.starfield.model.SCPN006;
import kr.co.starfield.model.SMBR001;
import kr.co.starfield.model.ScenarioPush;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.Tenant;
import kr.co.starfield.model.vo.AITF009Vo;
import kr.co.starfield.model.vo.ALBS004_DVo;
import kr.co.starfield.model.vo.SAML002Vo;
import kr.co.starfield.model.vo.SAML003Vo;
import kr.co.starfield.model.vo.SAML004Vo;
import kr.co.starfield.model.vo.SMBR003Vo;

/**
 *  SOPR001Mapper interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author 
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

public interface SAML001Mapper {

	public int appActionLog(SAML003 saml003);

	public void appExecuteLog(SAML001 saml001);

	public void appMoveLog(SAML002 saml002);

	public List<SAML002Vo> getLocationLogs(SAML002Vo vo);

	public void reqLocationReadingLog(SAML004Vo saml004vo);

	public List<SAML004Vo> getLocationReadingLogs(SAML004Vo vo);

	public List<SAML003Vo> getActionLogs(SAML003Vo vo);

	public Tenant getTenant(String zone_id);

	public SCPN005 getExecutePush();

	public int getappExecuteLogCnt(SAML001 saml001);

	public List<SCPN006> getAppMoveLogPush(SAML002 saml002);

	public int appWelComeCnt(SAML003 saml003_1);

	public int appActionLogCnt(SAML003 saml003_2);

	public void updatePush(SCPN006 push);

	public String getMemCustid(String uuid);

	public void updateInboxCustid(SAML003 saml003_2);

	public void insertInbox(SAML002 saml002);

	public String getAppMketInfoRecepYn(String uuid);

	public String getUuid();

	public void appScenarioMoveLog(SAML002 saml002);

	public ALBS001 getScenarioCk(String campaign_id);

	public SMBR001 getMemb(String uuid);

	public ScenarioPush SP_ALBS001(SAML002 saml002);

	public ScenarioPush SP_ALBS001_2(SAML002 saml002);

	public void SP_ALBS001_4(SAML002 saml002);

	public void SP_ALBS001_3(SAML002 saml002);

	public void SP_ALBS001_5(SAML002 saml002);

	public SMBR003Vo getLikeTenantCnt(SAML002 saml002);

	public void appMoveLog2(SAML002 saml002);

	public String getAppScenarioMoveLog(SAML002 saml002);

	public int getInboxCpCnt(SCPN003 inbox);

	public void insertLikeInbox(ALBS004_DVo albs004_d);

	public void insertLikeInbox2(ALBS004_DVo albs004_d);

	public void SP_SAML013(SAML002 saml002);

	public int getActionCnt(SAML003 saml003);

	public AITF009Vo getZoneType(SAML002 saml002);
}
