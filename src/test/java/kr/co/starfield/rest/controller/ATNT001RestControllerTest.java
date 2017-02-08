package kr.co.starfield.rest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.model.Tenant;
import kr.co.starfield.model.TenantCategory;
import kr.co.starfield.model.TenantSimpleCategory;
import kr.co.starfield.service.ATNT001Service;

public class ATNT001RestControllerTest extends BaseTest{

	@Autowired
	ATNT001Service atnt001Service;
	
	Tenant tenant;
	
	String bcnCd;
	
	/**
	 * 테스트 초기화
	 * @return
	 */
	@Before
	@Override
	public void setup() throws Exception {
		
		TenantSimpleCategory tenantCategory1 = new TenantSimpleCategory();
		tenantCategory1.cate_seq = "CT201606161634010001";
		TenantSimpleCategory tenantCategory2 = new TenantSimpleCategory();
		tenantCategory2.cate_seq = "CT201606161634010004";
		
		tenant = new Tenant();
		tenant.tnt_cd = "TNT999";
		tenant.bcn_cd = "01";
		
		//TODO Caregory, Response 모델 별도 생성 필요.
		tenant.cate_list.add(tenantCategory1);
		tenant.cate_list.add(tenantCategory2);
		tenant.img_main_bg_web_uri = "/";
		tenant.img_main_bg_mobi_uri ="/";
		tenant.img_main_bg_logo_uri = "/";
		tenant.img_logo_uri = "/";
		tenant.img_thmb_uri = "/";
		tenant.fl = "GL";
		tenant.room_num = "1245";
		tenant.tnt_nm_ko = "프라다";
		tenant.tnt_nm_en = "PRADA";
		tenant.tnt_nm_en = "PRADA";
		tenant.tnt_nm_en = "PRADA";
		tenant.tnt_tel_num1 = "02";
		tenant.tnt_tel_num2 = "111";
		tenant.tnt_tel_num3 = "1234";
		tenant.open_hr_min = "9000";
		tenant.end_hr_min = "2100";
		tenant.irgu_open_hr_min = "1000";
		tenant.irgu_end_hr_min = "2400";
		
		bcnCd = "01";
	}
	
	/**
	 * 테넌트 등록 테스트 
	 * @return
	 */
	@Test
	@Transactional
	public void testRegTenant() throws Exception {
		/*
		 * perform 요청 사전 조건 
		 * expect 응답 관련 테스트
		 * do 테스트시 직접 실행
		 * return 테스트 결과 반환(?)
		 */
		
		tenant.bcn_cd = null;
		System.out.println(new ObjectMapper().writeValueAsString(tenant));
		mockMvc.perform(post("/admin/rest/"+bcnCd+"/tenants")	// http method를 post로 설정, /rest/tenant 호출 uri 설정 
					 	.header("content-type", "application/json")	// 헤더에 content-type 설정 
					 	.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(new ObjectMapper().writeValueAsString(tenant)))
				.andDo(print())		// 응답 결과에 대한 모든 정보 출력 
				.andExpect(handler().handlerType(ATNT001RestController.class))	// 요청에 대한 매핑된 MVC컨트롤러클래스 확인 
				.andExpect(handler().methodName("regTenant"))					// 요청에 대한 매핑된 MVC컨트롤러메소드 확인
				.andExpect(status().isOk())	// 응답 받은 상태 코드가 200 인지 확인 
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));	// jsonPath를 이용하여 json 응답값 확인 
	}
	
	/**
	 * 테넌트 상세 조회 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testGetTenant() throws Exception { 
		
		String tntSeq = atnt001Service.regTenant(tenant).extra;
		
		tenant.bcn_cd = null;
		
		mockMvc.perform(get("/admin/rest/"+bcnCd+"/tenants/"+tntSeq)
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8"))
				.andDo(print())		
				.andExpect(handler().handlerType(ATNT001RestController.class))	
				.andExpect(handler().methodName("getTenant"))					
				.andExpect(status().isOk())	
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.tnt_nm_en").value(tenant.tnt_nm_en));
	}
	
	/**
	 * 테넌트 목록 조회 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testGetTenants() throws Exception {
		atnt001Service.regTenant(tenant);
		
		tenant.bcn_cd = null;
		
		mockMvc.perform(get("/admin/rest/"+bcnCd+"/tenants/")
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.param("offset", "0")
						.param("limit", "5"))
				.andDo(print())		
				.andExpect(handler().handlerType(ATNT001RestController.class))	
				.andExpect(handler().methodName("getTenants"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * 테넌트 수정 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testModidfyTenant() throws Exception {
		
		String tntSeq = atnt001Service.regTenant(tenant).extra;
		
		assertThat(atnt001Service.getTenant(tntSeq).room_num, is(tenant.room_num));
		
		tenant.room_num = "1111";
		tenant.bcn_cd = null;
		   
		TenantSimpleCategory tenantCategory3 = new TenantSimpleCategory();
		tenantCategory3.cate_seq = "CT201606161634010006";
		
		tenant.cate_list.add(tenantCategory3);
		
		System.out.println(new ObjectMapper().writeValueAsString(tenant));
		mockMvc.perform(put("/admin/rest/"+bcnCd+"/tenants/"+tntSeq)	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(new ObjectMapper().writeValueAsString(tenant)))
				.andDo(print())	
				.andExpect(handler().handlerType(ATNT001RestController.class))	
				.andExpect(handler().methodName("modifyTenant"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));		

		assertThat(atnt001Service.getTenant(tntSeq).room_num, is(tenant.room_num));
		assertThat(atnt001Service.getTenant(tntSeq).cate_list.size(), is(2));
		assertThat(atnt001Service.getTenant(tntSeq).tnt_nm_ko, is(tenant.tnt_nm_ko));
	}
	
	/**
	 * 테넌트 삭제 테스트
	 * 삭제된 테넌트 확인 일 위해 getTenant 사용. 삭제 후 테넌트 미 존재로 Exception 발생.
	 * @return
	 */
//	@Test(expected=BaseException.class)
	@Transactional
	public void testDeleteTenant() throws Exception {
		
		String tntSeq = atnt001Service.regTenant(tenant).extra;
		
		assertThat(atnt001Service.getTenant(tntSeq).tnt_seq, is(tntSeq));
		
		tenant.bcn_cd = null;
		
		mockMvc.perform(delete("/admin/rest/"+bcnCd+"/tenants/"+tntSeq)	// http method를 post로 설정, /rest/tenant 호출 uri 설정 
						.header("content-type", "application/json")	// 헤더에 content-type 설정 
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8"))
				.andDo(print())		// 응답 결과에 대한 모든 정보 출력 
				.andExpect(handler().handlerType(ATNT001RestController.class))	// 요청에 대한 매핑된 MVC컨트롤러클래스 확인 
				.andExpect(handler().methodName("deleteTenant"))					// 요청에 대한 매핑된 MVC컨트롤러메소드 확인
				.andExpect(status().isOk())	// 응답 받은 상태 코드가 200 인지 확인 
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));	// jsonPath를 이용하여 json 응답값 확인
		
		assertThat(atnt001Service.getTenant(tntSeq).sts, is(9));
	}
}
