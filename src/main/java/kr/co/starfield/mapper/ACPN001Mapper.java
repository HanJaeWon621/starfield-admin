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
import kr.co.starfield.model.ACPN005;
import kr.co.starfield.model.Category;
import kr.co.starfield.model.SCPN001;
import kr.co.starfield.model.SCPN001_D;
import kr.co.starfield.model.Tenant;
import kr.co.starfield.model.TenantMapping;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.ACPN002Vo;
import kr.co.starfield.model.vo.SCPN002Vo;
import kr.co.starfield.model.vo.SCPN002_D2Vo;
import kr.co.starfield.model.vo.SCPN006Vo;
import kr.co.starfield.model.vo.STNT001Vo;

/**
 * HomeMapper interface
 *
 * Copyright Copyright (c) 2016 Company Paprika
 *
 * @author xxx
 * @version 1.0,
 * @see
 * @date 2016.04.11
 */

public interface ACPN001Mapper {

	// 쿠폰 관리 리스트
	public List<ACPN001> getCoupons(ACPN001Vo vo);

	public List<ACPN003> getTenants(ACPN003Filter filter);

	public void reqCoupon(ACPN001 acpn001);

	public ACPN001 getCoupon(String cp_seq);

	public void modifyCoupon(ACPN001 acpn001);

	public List<Category> getCategorys();

	public void reqTenant(SCPN001_D scpn001_D);

	public List<SCPN001_D> cpTenant(String cp_seq);

	public List<ACPN005> getDownCoupons(ACPN005 acpn005);

	public void deleteTenant(String cp_seq);

	public SCPN001 getCouponDownDetail(SCPN001 scpn001);

	public void updateCouponUseSts(SCPN002_D2Vo vo);

	public TenantMapping getMappingTenantsCnt(TenantMapping tenantmapping);

	public List<TenantMapping> getMappingTenants(TenantMapping tenantmapping);

	public List<TenantMapping> getSalesTenants(TenantMapping tenantmapping);

	public void modifyTntMapping(TenantMapping tenantmapping);

	public void reqTntMapping(TenantMapping tenantmapping);

	public void deleteTntMapping(TenantMapping tenantmapping);

	public List<TenantMapping> getLbsZones(TenantMapping tenantmapping);

	public void modifyLbsZoneMapping(TenantMapping tenantmapping);

	public void allMapping();

	public List<TenantMapping> getFacis(TenantMapping tenantmapping);

	public List<TenantMapping> getLbsFacis(TenantMapping tenantmapping);

	public void modifyFaciZoneMapping(TenantMapping tenantmapping);

	public void unposted(String cp_seq);

	public ACPN001 getCouponImg();

	public ACPN005[] getExDownCoupons(ACPN005 acpn005);

	// 쿠폰 선택 팝업 쿠폰 리스트
	public List<ACPN001> getCouponSelectList();

	public List<ACPN005> getUseCoupons(ACPN005 acpn005);

	// 가승인 상태건 조회
	public List<ACPN005> getUseWaitCoupons(ACPN005 acpn005);

	// 영업정보 대사할 건 조회
	public List<ACPN005> getUseCompareCoupons(ACPN005 acpn005);

	public int modifyCpUseProcess(SCPN001 scpn001);

	public SCPN001 getCouponUseDetail(SCPN001 scpn001);

	public int updateCpCancel(SCPN001 scpn001);

	public int insertCpCancel(SCPN001 scpn001);

	public void insertExcelCoupon(ACPN001 acpn001);

	public void copyCoupon(String cp_seq);

	// 가승인 상태건 취소
	public void cnclCpUsePreAppr(ACPN001 acpn001);
	
	// 쿠폰 대사 처리
	public void compareEqaulCp(ACPN001 acpn001);
	
}
