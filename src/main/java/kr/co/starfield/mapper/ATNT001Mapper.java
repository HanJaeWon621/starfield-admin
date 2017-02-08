/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.List;
import java.util.Set;

import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Tenant;
import kr.co.starfield.model.TenantCategory;
import kr.co.starfield.model.SimpleTenantForList;
import kr.co.starfield.model.TenantFilter;
import kr.co.starfield.model.TenantSimpleCategory;
import kr.co.starfield.model.TenantWeb;
import kr.co.starfield.model.vo.STNT002Vo;
import kr.co.starfield.model.vo.STNT001Vo;

/**
 *  TenantMapper interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author yhkim
 * @version	1.0,
 * @see
 * @date 2016.04.13
 */

public interface ATNT001Mapper {
	
	// 테넌트 상세 조회
	public Tenant getTenant(String tnt_seq);

	// 테넌트 등록
	public void regTenant(STNT001Vo vo);

	// 테넌트 수정
	public int modifyTenant(STNT001Vo vo);
		
	// 테넌트 삭제
	public void deleteTenants(CommonDeleteModel tenantDelete);

	// 테넌트 카테고리 등록 
	public void regTenantCategory(STNT002Vo vo);

	// 테넌트 카테고리 목록 조회 ( mama_cate_seq, cate_seq 만 필요 )
	public List<TenantSimpleCategory> getSimpleCategoriesRelatedTenant(String tntSeq);
		
	// 테넌트 카테고리 목록 조회 (redis 에서 사용)
	public List<TenantCategory> getCategoriesRelatedTenant(String tntSeq);

	// 테넌트 카테고리 수정 
	public void modifyCategoryTenant(STNT002Vo tntSeq);
	
	// 테넌트 카테고리 삭제
	public void deleteCategoryTenants(CommonDeleteModel tenantDelete);
		
	// 카테고리별 테넌트 조회
	public List<String> getTenantsRelatedCategory(String cate_seq);
	
	// 카테고리별 테넌트 조회
//	public List<TenantWeb> getTenantsDetailRelatedCategory(String cate_seq);

	public List<String> getCategorySeqsRelatedTenant(String tntSeq);

	// 테넌트 상세 조회 (WEB용)
	public List<TenantWeb> getTenantForWebList();

	public List<SimpleTenantForList> getTenantsInCategory(TenantFilter tenantInCategoryFilter);

}
