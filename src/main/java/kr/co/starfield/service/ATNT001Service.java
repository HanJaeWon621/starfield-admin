package kr.co.starfield.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.CommonCode;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.mapper.ATNT001Mapper;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.SimpleTenantForList;
import kr.co.starfield.model.Tenant;
import kr.co.starfield.model.TenantCategory;
import kr.co.starfield.model.TenantFilter;
import kr.co.starfield.model.TenantSimpleCategory;
import kr.co.starfield.model.TenantWeb;
import kr.co.starfield.model.vo.SCTG001Vo;
import kr.co.starfield.model.vo.STNT001Vo;
import kr.co.starfield.model.vo.STNT002Vo;

@Service
public class ATNT001Service {
	
	private Logger ll = LoggerFactory.getLogger(ATNT001Service.class);
	
	@Autowired
	ATNT001Mapper atnt001Mapper;
	
	@Autowired
	ACTG001Service actg001Service;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@Value("${web.rest.url}")
	String webRestUrl;
	
	/**
	 * 테넌트 카테고리 목록 조회
	 * @param tenantInCategoryFilter 
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<SimpleTenantForList> getTenantsInCategory(TenantFilter tenantInCategoryFilter) {

		ListResult<SimpleTenantForList> result = new ListResult<>();
		
		List<SimpleTenantForList> tenantInCategory = atnt001Mapper.getTenantsInCategory(tenantInCategoryFilter);
		
		result.list = tenantInCategory;
		
		int totCnt = tenantInCategory.size() > 0 ? tenantInCategory.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(tenantInCategoryFilter.offset, tenantInCategoryFilter.limit, totCnt);

		result.paging = paging;
		
		return result;
	}
		
	/**
	 * 테넌트 등록
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional
	public SimpleResult regTenant(Tenant tenant) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		STNT001Vo tenantVo;
		
		try {
			tenantVo = tenant.toTenantVo();
		} catch (ParseException e) {
			BaseException be = new BaseException(ErrorCode.Tenant.TENANT_DATE_PARSE_ERROR);
			throw be;
		}
		
		atnt001Mapper.regTenant(tenantVo);
		
		String tntSeq = tenantVo.tnt_seq;
		tenant.tnt_seq = tntSeq;
		result.extra = tntSeq;
		
		regTenantCategories(tenant.toCategoriesVo());
		
//		syncRedisTenantSuite();

		return result;
	}
	
	/**
	 * 테넌트 수정
	 * @param vo
	 * @return 
	 */
	@Transactional
	public SimpleResult modifyTenant(Tenant tenant) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		STNT001Vo tenantVo;
		
		try {
			tenantVo = tenant.toTenantVo();
		} catch (ParseException e) {
			BaseException be = new BaseException(ErrorCode.Tenant.TENANT_DATE_PARSE_ERROR);
			throw be;
		}
		
		int cnt = atnt001Mapper.modifyTenant(tenantVo);
		
		if(cnt < 1){
			BaseException be = new BaseException(ErrorCode.Tenant.TENANT_NOT_FOUND_DATA);
			throw be;
		}
		
		if(tenant.modify_cate_yn) {
			// 카테고리 삭제
			STNT002Vo vo = new STNT002Vo();
			vo.tnt_seq = tenant.tnt_seq;
			vo.sts = StatusCode.Common.DELETE.getCode();
			vo.mod_usr = tenant.user;
			atnt001Mapper.modifyCategoryTenant(vo);
			
			// 카테고리 추가
			regTenantCategories(tenant.toCategoriesVo());
		}
		
//		syncRedisTenantSuite();
		
		return result;	
	}
	
	/**
	 * 테넌트 카테고리 등록
	 * @param 테넌트 카테고리 vo List
	 * @return 
	 */
	private void regTenantCategories(List<STNT002Vo> tenantCategoryVoList){
		for(STNT002Vo vo : tenantCategoryVoList){
			vo.sts = StatusCode.Common.USE.getCode();
			atnt001Mapper.regTenantCategory(vo);
		}
	}
	
	/**
	 * 테넌트 조회 
	 * @param tntSeq
	 * @return Tenant
	 * @throws BaseException 
	 * @throws UnsupportedEncodingException 
	 */
	public Tenant getTenant(String tntSeq) throws BaseException {
		
		Tenant tenant = atnt001Mapper.getTenant(tntSeq);

		if(tenant == null){
			BaseException be = new BaseException(ErrorCode.Tenant.TENANT_NOT_FOUND_DATA);
			throw be;
		}
		
		// set Tag
		if(tenant.tnt_tag != null) {
			StringTokenizer tagSt = new StringTokenizer(tenant.tnt_tag, ";"); 
			while(tagSt.hasMoreTokens()) {
				tenant.tnt_tag_list.add(tagSt.nextToken());
			}
		}
		
		tenant.tnt_tag = StringUtils.join(tenant.tnt_tag_list, ";");
		
		tenant.cate_list = atnt001Mapper.getSimpleCategoriesRelatedTenant(tntSeq);
		
		return tenant;
	}
	
	/**
	 * 테넌트 삭제 
	 * @param tntSeq
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteTenants(CommonDeleteModel tenantDelete) throws BaseException {
		SimpleResult result = new SimpleResult();

		atnt001Mapper.deleteCategoryTenants(tenantDelete);
		atnt001Mapper.deleteTenants(tenantDelete);
	
//		syncRedisTenantSuite();
		
		return result;
	}


	/**
	 * 테넌트 목록 
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 * @throws UnsupportedEncodingException 
	 */
//	public ListResult<Tenant> getTenants(STNT001Vo vo) {
//		
//		ListResult<Tenant> result = new ListResult<>();
//			
//		List<STNT001Vo> tenants = atnt001Mapper.getTenants(vo);
//			
//		for(STNT001Vo tenantVo : tenants){
//			Tenant tenant = tenantVo.toModel();
//			tenant.cate_list = atnt001Mapper.getCategoriesRelatedTenant(tenant.tnt_seq);
//			result.list.add(tenant);
//		}
//			
//		int totCnt = tenants.size() > 0 ? tenants.get(0).tot_cnt : 0;
//		
//		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
//
//		result.paging = paging;
//	
//		return result;
//	}

	/**
	 * 테넌트 상세 조회 (REDIS-WEB용)
	 */
	public List<TenantWeb> getTenantForWebList(){
		return atnt001Mapper.getTenantForWebList();
	}
	
	/**
	 * 레디스 동기화 세트
	 * @throws BaseException 
	 */
	public SimpleResult syncRedisTenantSuite() throws BaseException{
		
		SimpleResult result = new SimpleResult();
		
		List<TenantWeb> transTenants = transTenantForWeb(getTenantForWebList());
		ll.info("====================== syncRedisTenantSuite start ====================== ");
		ll.info("list lenght {}", transTenants.size());
		syncRedisTenant(transTenants);
		syncRedisCategoryTenant();
		syncRedisFloorTenant(transTenants);
		syncRedisTagTenant(transTenants);
		syncRedisTenantVersion();
		actg001Service.syncRedisCategories("TENANT");
		ll.info("======================  syncRedisTenantSuite end ====================== ");
		
		return result;
	}
	
	
	/**
	 * 레디스 테넌트 동기화
	 * @param tenants 
	 * @throws BaseException 
	 */
	@Transactional
	public void syncRedisTenant(List<TenantWeb> tenants) throws BaseException{
		ll.info("1. syncRedisTenant start");
		String prefixKey = "tnt";
		String prefixZoneKey = "tnt:zone";
		
		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
		
		// TNT SEQ
		stringRedisTemplate.delete(zOps.range("keys:"+prefixKey, 0, -1));
		stringRedisTemplate.delete("keys:"+prefixKey);
		
		// TNT ZONE
		stringRedisTemplate.delete(zOps.range("keys:"+prefixZoneKey, 0, -1));
		stringRedisTemplate.delete("keys:"+prefixZoneKey);
		
		// TNT ALL
		stringRedisTemplate.delete("tnt:all");
		
		try {
			
			for(TenantWeb tenantWeb : tenants){
				String tenantJson = new ObjectMapper().writeValueAsString(tenantWeb);
				// TNT SEQ
				vOps.set(prefixKey+":"+tenantWeb.tnt_seq, tenantJson);
				zOps.add("keys:"+prefixKey, prefixKey+":"+tenantWeb.tnt_seq, tenantWeb.sort_ord);
				
				boolean isDept = CommonCode.Tenant.TenantType.getEnum(Integer.toString(tenantWeb.tnt_type)) == CommonCode.Tenant.TenantType.DEPT;
				// TNT ZONE -- 백화점 제외
				if(tenantWeb.zone_id != null && !"".equals(tenantWeb.zone_id) && !isDept) {
					vOps.set(prefixZoneKey+":"+tenantWeb.zone_id, tenantWeb.tnt_seq);
					zOps.add("keys:"+prefixZoneKey, prefixZoneKey+":"+tenantWeb.zone_id, tenantWeb.sort_ord);
				}
			}

			String tenantAllJson = new ObjectMapper().writeValueAsString(tenants);
			vOps.set(prefixKey+":all", tenantAllJson);
			
		} catch (IOException e) {
			ll.info("1. syncRedisTenant faild");
			ll.error(e.getMessage());
			BaseException be = new BaseException(ErrorCode.Tenant.TENANT_TO_JSON_FOR_SET_REDIS_ERROR);
			throw be;
		}
		ll.info("1. syncRedisTenant end");
	}
	
	/**
	 * 테넌트 상세 조회 (REDIS-WEB용)
	 * @throws BaseException 
	 */
	protected List<TenantWeb> transTenantForWeb(List<TenantWeb> tenants) throws BaseException{
		List<TenantWeb> resultTenants = new ArrayList<TenantWeb>();
		
		for(TenantWeb tenant : tenants){

			// set Category
			List<TenantCategory> tenantCategories = atnt001Mapper.getCategoriesRelatedTenant(tenant.tnt_seq);
			
			if(tenantCategories.size() == 0) {
				continue;
			}
			
			tenant.cate_detail = tenantCategories;
			
			for(TenantCategory category : tenantCategories) {
				if(category.lvl == category.max_level)
					continue;
				
				tenant.cate_list_ko.add(category.cate_nm_ko);
				tenant.cate_list_en.add(category.cate_nm_en);
				tenant.cate_list_cn.add(category.cate_nm_cn);
				tenant.cate_list_jp.add(category.cate_nm_jp);
			}
			
			// set FL 
			if(tenant.fl != null) {
				StringTokenizer flSt = new StringTokenizer(tenant.fl, "~"); 
				while(flSt.hasMoreTokens()){
					tenant.fl_list.add(flSt.nextToken());
				}
			}
			
			if(CommonCode.Tenant.TenantType.getEnum(Integer.toString(tenant.tnt_type)) == CommonCode.Tenant.TenantType.DEPT) {
				tenant.fl = "신세계백화점 " + tenant.fl;
			}
			
			tenant.tnt_tag_list.add(tenant.tnt_nm_ko.replaceAll(" ", "").toUpperCase());
			tenant.tnt_tag_list.add(tenant.tnt_nm_en.replaceAll(" ", "").toUpperCase());
			
			// set Tag
			if(tenant.tnt_tag != null) {
				StringTokenizer tagSt = new StringTokenizer(tenant.tnt_tag, ";"); 
				while(tagSt.hasMoreTokens()) {
					tenant.tnt_tag_list.add(tagSt.nextToken().replaceAll(" ", "").toUpperCase());
				}
			}
			
			tenant.tnt_tag = StringUtils.join(tenant.tnt_tag_list, ";");
			
			List<String> tnt_tel_list = new ArrayList<>();
			
			if(tenant.tnt_tel != null) {
				StringTokenizer telSt = new StringTokenizer(tenant.tnt_tel, "-"); 
				while(telSt.hasMoreTokens()) {
					tnt_tel_list.add(telSt.nextToken());
				}
			}
			tenant.tnt_tel = StringUtils.join(tnt_tel_list, '-');
			
			tenant.mobi_url = webRestUrl + "/tenant/tenantDetail/" + tenant.tnt_seq;
			
			resultTenants.add(tenant);
		}
		
		return resultTenants;
	}
	
	/**
	 * 레디스 카테고리-테넌트 동기화
	 */
	@Transactional
	private void syncRedisCategoryTenant() throws BaseException{
		ll.info("2. syncRedisCategoryTenant start");
		String cateCd = "TENANT";
		String prefixKey = "tnt:cate";
		
		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
		ListOperations<String, String> lOps = stringRedisTemplate.opsForList();

		stringRedisTemplate.delete(zOps.range("keys:"+prefixKey, 0, -1));
		stringRedisTemplate.delete("keys:"+prefixKey);
		
		SCTG001Vo vo = new SCTG001Vo();
		vo.cate_cd = cateCd;
		List<SCTG001Vo> categories = actg001Service.getHierachyCategories(vo);

		for(SCTG001Vo category : categories) {
			String cateSeq = category.cate_seq;
			List<String> tenants = atnt001Mapper.getTenantsRelatedCategory(cateSeq);
			if(tenants.size() > 0){
				lOps.rightPushAll(prefixKey+":"+cateSeq, tenants);
				zOps.add("keys:"+prefixKey, prefixKey+":"+cateSeq, 0);
			}
		}
		
		ll.info("2. syncRedisCategoryTenant end");
	}

	/**
	 * 레디스 층별 테넌트 동기화
	 * @param tenants
	 */
//	@Transactional
//	private void syncRedisFloorTenant(List<TenantWeb> tenants) {
//		ll.info("3. syncRedisFloorTenant start");
//		String prefixKey = "tnt:fl";
//		
//		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
//		ListOperations<String, String> lOps = stringRedisTemplate.opsForList();
//
//		stringRedisTemplate.delete(zOps.range("keys:"+prefixKey, 0, -1));
//		stringRedisTemplate.delete("keys:"+prefixKey);
//			
//		for(TenantWeb tenant : tenants){
//			lOps.rightPush(prefixKey+":"+tenant.fl, tenant.tnt_seq);
//			zOps.add("keys:"+prefixKey, prefixKey+":"+tenant.fl, 0);
//		}
//		ll.info("3. syncRedisFloorTenant end");
//	}
	
	/**
	 * 레디스 층별 테넌트 동기화
	 * @param tenants
	 * @throws BaseException 
	 */
	@Transactional
	public void syncRedisFloorTenant(List<TenantWeb> tenants) throws BaseException {
		ll.info("3. syncRedisFloorTenant start");
		String prefixKey = "tnt:floor";
		
		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();

		stringRedisTemplate.delete(zOps.range("keys:"+prefixKey, 0, -1));
		stringRedisTemplate.delete("keys:"+prefixKey);

		Map<String, Map<String, Map<String, Object>>> floorTenant = makeFloorTenantData(tenants);
		
		for(String floor : floorTenant.keySet()) {
			Map<String, Map<String, Object>> floorInfo = floorTenant.get(floor);
			List<Map<String, Object>> cateList = new ArrayList<>();
			for(String cate : floorInfo.keySet()) {
				 Map<String, Object> cateMap = floorInfo.get(cate);
				 TenantCategory tenantCate = (TenantCategory) cateMap.get("cate");
				 List<TenantWeb> tenantList = (List<TenantWeb>) cateMap.get("tntList");
				 tenantCate.tnt_cnt = tenantList.size();
				cateList.add(floorInfo.get(cate));
			}
			
			Collections.sort(cateList, new Comparator<Map<String, Object>>(){
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return ((Comparable<Integer>) ((TenantCategory) o1.get("cate")).sort_ord).compareTo(((TenantCategory) o2.get("cate")).sort_ord);
				}
				
			});
			
			try {
				vOps.set(prefixKey+":"+floor, new ObjectMapper().writeValueAsString(cateList));
			} catch (IOException e) {
				ll.info("3. syncRedisFloorTenant faild");
				ll.error(e.getMessage());
				BaseException be = new BaseException(ErrorCode.Tenant.FLOORINFO_TO_JSON_FOR_SET_REDIS_ERROR);
				throw be;
			}
			zOps.add("keys:"+prefixKey, prefixKey+":"+floor, 0);

		}
		
		ll.info("3. syncRedisFloorTenant end");
	}
	
	public Map<String, Map<String, Map<String, Object>>> makeFloorTenantData(List<TenantWeb> tenants) {
		
		Map<String, Map<String, Map<String, Object>>> tempResult = new HashMap<>();
		
		for(TenantWeb tenant : tenants) {
			Map<String, Map<String, Object>> tenantDataInFloor = tempResult.get(tenant.fl.replace("신세계백화점 ", ""));
			
			if(tenantDataInFloor == null) {
				tenantDataInFloor = new HashMap<>();
			}
			
			Map<String, Object> allCateInFloor = tenantDataInFloor.get("all");
			
			if(allCateInFloor == null) {
				allCateInFloor = new HashMap<>();
				TenantCategory allCate = new TenantCategory();
				allCate.cate_nm_ko = "ALL";
				allCate.cate_nm_en = "ALL";
				allCate.cate_nm_jp = "ALL";
				allCate.cate_nm_cn = "ALL";
				allCate.cate_seq = "all";
				allCate.sort_ord = 0;
				
				allCateInFloor.put("cate", allCate);
				allCateInFloor.put("tntList", new ArrayList<>());
			}
			
			List<TenantWeb> allCateInFloorTenantList =  (List<TenantWeb>) allCateInFloor.get("tntList");
			allCateInFloorTenantList.add(tenant);
//			allCateInFloor.put("tntList", allCateInFloorTenantList);
			
			tenantDataInFloor.put("all", allCateInFloor);
			
			// cate : cateObject , tntList : tntList
			for(TenantCategory cate : tenant.cate_detail) {
				if(cate.lvl != cate.max_level - 1) {
					continue;
				}
				
				Map<String, Object> cateInFloor = tenantDataInFloor.get(cate.cate_seq);

				if(cateInFloor == null) {
					cateInFloor = new HashMap<>();
					cateInFloor.put("cate", cate);
					cateInFloor.put("tntList", new ArrayList<>());
				}
				
				List<TenantWeb> tntList = (List<TenantWeb>) cateInFloor.get("tntList");
				tntList.add(tenant);
				
//				cateInFloor.put("tntList", tntList);
				tenantDataInFloor.put(cate.cate_seq, cateInFloor);
			}
			
			tempResult.put(tenant.fl.replace("신세계백화점 ", ""), tenantDataInFloor);
			
		}	
		
		return tempResult;
	}
	
	/**
	 * 레디스 태그별 테넌트 동기화
	 * @param tenants
	 */
	@Transactional
	public void syncRedisTagTenant(List<TenantWeb> tenants) {
		ll.info("3. syncRedisTagTenant start");
		String prefixKey = "tnt:tag";
		
		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
		ListOperations<String, String> lOps = stringRedisTemplate.opsForList();
		stringRedisTemplate.delete(zOps.range("keys:"+prefixKey, 0, -1));
		stringRedisTemplate.delete("keys:"+prefixKey);
			
		for(TenantWeb tenant : tenants){
			for(String tag : tenant.tnt_tag_list){
				tag = tag.replaceAll(" ", "").toUpperCase();
				lOps.rightPush(prefixKey+":"+tag, tenant.tnt_seq);
				zOps.add("keys:"+prefixKey, prefixKey+":"+tag, 0);
			}
		}
		ll.info("3. syncRedisTagTenant end");
	}
	
	/**
	 * 레디스 테넌트 버전업데이트
	 * @param
	 */
	@Transactional
	private void syncRedisTenantVersion() {
		ll.info("4. syncRedisTenantVersion start");
		String key = "version:tnt";
		stringRedisTemplate.delete(key);
		stringRedisTemplate.opsForValue().set(key, String.valueOf(new Date().getTime()));
		ll.info("4. syncRedisTenantVersion end");
	}


//	/**
//	 * 테넌트에 연관된 카테고리 순번 목록 
//	 * @param tntSeq
//	 * @return
//	 */
//	public List<String> getCategorySeqsRelatedTenant(String tntSeq){	
//		return atnt001Mapper.getCategorySeqsRelatedTenant(tntSeq);
//	}
	
//	/**
//	 * 카테고리별 레디스 업로드 (수정된 카테고리만 변경)
//	 * @param relationCateSeqList 등록/수정/삭제 시 변화된 카테고리 리스트.
//	 * 등록 : 등록시 연계 카테고리
//	 * 수정 : 수정전 카테고리 + 수정후 카테고리
//	 * 삭제 : 삭제전 카테고리
//	 * 
//	 */
//	public void setRedisCategoryTenant(List<String> relationCateSeqList) {
//		if(relationCateSeqList.size() < 1)
//			return;
//		
//		List<String> categories = actg001Service.getSimpleHierachyCategories(relationCateSeqList);
//		
//		for(String category : categories) {
//			List<String> tenants = atnt001Mapper.getTenantsRelatedCategory(category);
//
//			stringRedisTemplate.delete("tnt:cate:"+category);
//			if(tenants.size() > 0){
//				stringRedisTemplate.opsForList().rightPushAll("tnt:cate:"+ category, tenants);
//			}
//			
//		}
//	}
	
	
//	/**
//	 * 레디스 테넌트 삭제
//	 * @param tntSeq
//	 */
//	public void delRedisTenant(String tntSeq){
//		stringRedisTemplate.delete("tnt:"+tntSeq);
//	}
	
}
