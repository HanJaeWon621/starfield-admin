/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.List;

import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.ACPN004;
import kr.co.starfield.model.ACPN006;
import kr.co.starfield.model.CouponTenant;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SCPN001;
import kr.co.starfield.model.SCPN002;
import kr.co.starfield.model.SCPN003;
import kr.co.starfield.model.SCPN004;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.ACPN002Vo;
import kr.co.starfield.model.vo.SCPN001Vo;
import kr.co.starfield.model.vo.SCPN002Vo;
import kr.co.starfield.model.vo.SCPN002_D2Vo;
import kr.co.starfield.model.vo.SCPN006Vo;
import kr.co.starfield.model.vo.SMBR003Vo;
import kr.co.starfield.model.vo.STNT001Vo;
/**
 *  ACPN002Mapper interface
 *
 * @author djlee
 * @version	1.0,
 * @see
 * @date 2016.08.01
 */

public interface ACPN002Mapper {

	public SCPN001 getIssuCoupon(SCPN001 scpn001);

	public void reqIssuCoupon(SCPN002Vo vo);

	public void regBcImg(SCPN006Vo scpn006Vo);

	public int useCoupon(SCPN001 scpn001);

	public int getUseCouponCnt(String cp_seq);

	public List<ACPN004> getCoupons(String tntSeq);

	public void updatePosting(String cp_seq);

	public SCPN002 getNmCpBcImg(String cp_seq);

	public int cpUseProcess(SCPN002_D2Vo vo);

	public List<SCPN003> getBrandCpuons(SCPN001Vo vo);

	public List<SCPN003> getNmCpuons(SCPN003 param);

	public List<SCPN003> getMyCpuons(SCPN002_D2Vo vo);

	public SCPN004 getCouponDetail(SCPN002_D2Vo vo);

	public List<ACPN006> getTenantUseCp();

	public int myCpUseProcess(SCPN002_D2Vo vo);

	public void myCpDelete(SCPN002_D2Vo vo);

	//public List<ACPN006> cpTenants(SCPN001Vo vo);

	public List<SCPN003> getCpTenants(STNT001Vo vo);

	public int useCouponCnt(SCPN001 scpn001);

	public int reCoupon(SCPN001 scpn001);

	public List<SCPN003> usedMyCpuons(SCPN002_D2Vo vo);

	public SCPN002_D2Vo getCpUseCk(SCPN002_D2Vo vo);

	public List<CouponTenant> cpTenants(SCPN002_D2Vo vo);

	public void useMbCoupon(SCPN001 scpn001);

	public List<SCPN003> getCpZones(STNT001Vo vo);

	public int getIssCntCk(String cp_seq);

	public int getUseDnCntCk(SCPN001 cpck);

	public void SP_SCPN002_PRE(SCPN001 cpck);
	
	public void SP_SCPN002(SCPN001 cpck);

}
