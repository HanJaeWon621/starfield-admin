package kr.co.starfield.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.mapper.ATNT001Mapper;
import kr.co.starfield.model.TenantCategory;
import kr.co.starfield.model.TenantWeb;
import kr.co.starfield.rest.controller.BaseTest;

public class ATNT001ServiceTest extends BaseTest {
	
	@Autowired
	ATNT001Service atnt001Service;

	
	List<TenantWeb> tenants;
	
	@Override
	public void setup() throws BaseException {
		tenants = atnt001Service.transTenantForWeb(atnt001Service.getTenantForWebList());
	}

	/**
	 * 테넌트 전체 동기화 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * @throws BaseException
	 */
	@Test
//	@Transactional
	public void testSyncRedisTenantSuite() throws BaseException {
		atnt001Service.syncRedisTenantSuite();
	}
	
//	@Test
////	@Transactional
//	public void testSyncRedisTagTenant() throws BaseException {
//		atnt001Service.syncRedisTagTenant(tenants);
//	}
	
//	
//	@Test
//	@Transactional
//	public void testPrintTenant() throws BaseException, JsonGenerationException, JsonMappingException, IOException {
//		
//		System.out.println(new ObjectMapper().writeValueAsString(tenants));
//	}
//	
//	@Test
//	@Transactional
//	public void testSyncRedisTenant() throws BaseException {
//		atnt001Service.syncRedisTagTenant(tenants);
//	}
	
//	@Test
//	@Transactional
//	public void syncRedisFloorTenant() throws BaseException {
//		atnt001Service.syncRedisFloorTenant(tenants);
//	}
	
//	@Test
//	public void testMakeFloorTenant() throws JsonGenerationException, JsonMappingException, IOException {
//		System.out.println("=============== before tenants : " + new ObjectMapper().writeValueAsString(tenants));
		
//		Map<String, Map<String, Map<String, Object>>> tempResult = atnt001Service.makeFloorTenantData(tenants);
		
//		System.out.println("=============== after tempResult : " + new ObjectMapper().writeValueAsString(tempResult));
		
//		printData(tempResult);
//	}
	
	@SuppressWarnings("unchecked")
	public static void printData(Map<String, Map<String, Map<String, Object>>> floorTenant) throws JsonGenerationException, JsonMappingException, IOException {
		for(String floor : floorTenant.keySet()) {
			
			if(!floor.equals("B1")) {
				continue;
			}
			
			System.out.println("======= Floor : " +floor+ " =======");
			System.out.println("Category Cnt : " +floorTenant.get(floor).size());
//			System.out.println("Category Cnt : " +floorTenant.get(floorInfo));

			Map<String, Map<String, Object>> floorInfo = floorTenant.get(floor);
			List<Map<String, Object>> cateList = new ArrayList<>();
			for(String cate : floorInfo.keySet()) {
				Map<String, Object> cateMap = floorInfo.get(cate);
				TenantCategory tenantCate = (TenantCategory) cateMap.get("cate");
				Set<TenantWeb> tenantList = (Set<TenantWeb>) cateMap.get("tntList");
				tenantCate.tnt_cnt = tenantList.size();
				cateList.add(floorInfo.get(cate));
			}

			Collections.sort(cateList, new Comparator<Map<String, Object>>(){
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					return ((Comparable<Integer>) ((TenantCategory) o1.get("cate")).sort_ord).compareTo(((TenantCategory) o2.get("cate")).sort_ord);
				}
				
			});
			
			System.out.println("====================== tnt:floor:"+floor);
			System.out.println(new ObjectMapper().writeValueAsString(cateList));
		}
	}
}
