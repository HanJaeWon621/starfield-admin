/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.List;

import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.ACPN003;
import kr.co.starfield.model.ACPN003Filter;
import kr.co.starfield.model.ALBS001;
import kr.co.starfield.model.ALBS002;
import kr.co.starfield.model.ALBS003;
import kr.co.starfield.model.ALBS004;
import kr.co.starfield.model.ALBS004_D;
import kr.co.starfield.model.ALBS005;
import kr.co.starfield.model.AbstMst;
import kr.co.starfield.model.Event;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SCPN001_D;
import kr.co.starfield.model.SMBR001;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.Tenant;
import kr.co.starfield.model.WelcomeZone;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.ACPN002Vo;
import kr.co.starfield.model.vo.AITF009Vo;
import kr.co.starfield.model.vo.AITF012Vo;
import kr.co.starfield.model.vo.ALBS001Vo;
import kr.co.starfield.model.vo.ALBS002Vo;
import kr.co.starfield.model.vo.ALBS003Vo;
import kr.co.starfield.model.vo.ALBS004Vo;
import kr.co.starfield.model.vo.ALBS004_DVo;
import kr.co.starfield.model.vo.SCPN002Vo;
import kr.co.starfield.model.vo.SCPN006Vo;
import kr.co.starfield.model.vo.SEVT001Vo;
import kr.co.starfield.model.vo.SMBR001Vo;
/**
 *  HomeMapper interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public interface ALBS001Mapper {

	public void insertPull(ALBS004 albs004);

	public int insertInbox(ALBS004_DVo albs004_DVo);

	public List<ALBS004_D> getInboxList(ALBS004_DVo vo);

	public void deleteInbox(String[] inbox_seq);

	public void updateinboxUuid(ALBS004_DVo vo);

	public void deletePull(ALBS004 albs004);

	public List<ALBS003> getWcPushMsgs(ALBS003Vo vo);

	public void regWcPushMsg(ALBS003 albs003);

	public ALBS003 getWcPushMsg(String wel_msg_push_seq);

	public void modifyWcPushMsg(ALBS003 albs003);

	public List<SMBR001Vo> getMembPushApplies(ALBS001Vo vo);
	
	public List<ACPN001Vo> getWelcomeCouponsAllCnt(ACPN001Vo vo);
	
	public List<ACPN001Vo> getWelcomeCoupons(ACPN001Vo vo);

	public void regWcPushMsgCp(ALBS003 albs003);

	public ALBS005 getWelcomeDetail(String wc_seq);
	
	public List<AITF009Vo> getApplyTenantsAllCnt(AITF009Vo vo);
	
	public List<AITF009Vo> getApplyTenants(AITF009Vo vo);

	public void regWcPushMsgZoneId(WelcomeZone welcomeZone);

	public void deleteWcPushMsgZoneId(ALBS003 albs003);

	public List<WelcomeZone> getWcPushMsgZone(String wel_msg_push_seq);

	public List<SMBR001Vo> getPushMembs(SMBR001Vo vo);

	public List<SMBR001Vo> getAttentionMembs(SMBR001Vo vo);

	public List<SMBR001Vo> getVipMembs(SMBR001Vo vo);

	public List<AbstMst> getAbstMsts();

	public void deleteInbox(String inbox_seq);

	public void regScenario(ALBS001 albs001);
	
	public ALBS001 getScnObImg(ALBS001 albs001);
	
	public void regScenarioMem(ALBS001 albs001);

	public void regScenarioCp(ALBS001 albs001);

	public void regScenarioZoneId(SCPN001_D scpn001_d);

	public ALBS001 getScenario(String scn_otb_cp_push_seq);

	public List<SCPN001_D> getScenarioZoneId(String scn_otb_cp_push_seq);

	public void modifyScenario(ALBS001 albs001);

	public void modifyScenarioCp(ALBS001 albs001);

	public void deleteScenarioZone(ALBS001 albs001);

	public void regScenarioUnPosting(String scn_otb_cp_push_seq);

	public void regScenarioPosting(String scn_otb_cp_push_seq);

	public List<ALBS001> getScenarios(ALBS001Vo vo);

	public List<Event> getEvents(SEVT001Vo vo);

	public int regBanner(ALBS002 albs002);

	public List<ALBS002> randomBanners(String map_id);

	public ALBS002 getBannerDetail(String bn_seq);

	public int regBannerPosting(ALBS002 albs002);

	public int modifyBanner(ALBS002 albs002);

	public List<ALBS002> getBanners(ALBS002Vo vo);

	public int getBannerOrdCnt(ALBS002 albs002);

	public void modifyBannerOrd(ALBS002 albs002);

	public AITF012Vo getComp();

	public void updateCmpId(ALBS001 albs001);

	public void reqWcPosting(ALBS003 albs003);

	public void reqWcUnPosting(ALBS003 albs003);

	public String[] getScenarioCustId(String scn_otb_cp_push_seq);

	public void deleteScenarioMemb(ALBS001 albs001);

	public void updateWcCmpId(ALBS003 albs003);

	public ALBS005 getScenarioDetail(String wc_seq);

	public void deleteWcPushMsgCp(ALBS003 albs003);

	public int getMembCnt();

	public int getAttentionMembCnt();

	public int getVipMembCnt();

	public List<ACPN003> getSnTenants(ACPN003Filter filter);

	public int bannerCnt();



}
