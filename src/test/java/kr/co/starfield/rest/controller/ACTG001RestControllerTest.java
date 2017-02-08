package kr.co.starfield.rest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.model.vo.SCTG001Vo;
import kr.co.starfield.service.ACTG001Service;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ACTG001RestControllerTest extends BaseTest {
	@Autowired
	ACTG001Service actg001Service;

	SCTG001Vo category;
	
	String bcnCd;
	String mamaCateSeq;
	
	/**
	 * 테스트 초기화
	 * @return
	 */
	@Before
	@Override
	public void setup() throws Exception {
		category = new SCTG001Vo();
//		category.cate_seq = "CT201606171415010001";
		category.bcn_cd = "01";
		category.mama_cate_seq = "CT201606161634010001";
		category.cate_cd = "Fashion Clothing";
		category.cate_nm_ko = "패션의류";
		category.cate_nm_en = "패션의류";
		category.cate_nm_cn = "패션의류";
		category.cate_nm_jp = "패션의류";
		category.sts = 0;
		
		bcnCd = category.bcn_cd;
		mamaCateSeq = category.mama_cate_seq;
	}
	
	/**
	 * 카테고리 등록 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testARegCategory() throws Exception {
		
//		category.bcn_cd = null;
//		
//		mockMvc.perform(post("/admin/rest/"+bcnCd+"/categories/TENANT/any")	
//						.header("content-type", "application/json")	
//						.contentType(MediaType.APPLICATION_JSON)
//						.characterEncoding("UTF-8")
//						.content(new ObjectMapper().writeValueAsString(category)))
//				.andDo(print())		
//				.andExpect(handler().handlerType(ACTG001RestController.class))	 
//				.andExpect(handler().methodName("regCategory"))					
//				.andExpect(status().isOk())	 
//				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.code").value(0));
	}
	
	/**
	 * 카테고리 상세 조회 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testBGetCategory() throws Exception { 
		
//		String cateSeq = actg001Service.regCategory(category, "TENANT").extra;
//		
//		category.bcn_cd = null;
//		
//		mockMvc.perform(get("/admin/rest/"+bcnCd+"/categories/TENANT/any/"+cateSeq)	
//						.header("content-type", "application/json")	
//						.contentType(MediaType.APPLICATION_JSON)
//						.characterEncoding("UTF-8"))
//				.andDo(print())		
//				.andExpect(handler().handlerType(ACTG001RestController.class))	
//				.andExpect(handler().methodName("getCategory"))					
//				.andExpect(status().isOk())	
//				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.cate_cd").value("Fashion Clothing"));
	}
	
	/**
	 * 카테고리 목록 조회 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testCGetCategories() throws Exception {
//		actg001Service.regCategory(category, "TENANT");
//		
//		category.bcn_cd = null;
//		category.mama_cate_seq = null;
//		
//		mockMvc.perform(get("/admin/rest/"+bcnCd+"/categories/TENANT/"+mamaCateSeq)
//						.header("content-type", "application/json")	
//						.contentType(MediaType.APPLICATION_JSON)
//						.characterEncoding("UTF-8")
//						.param("offset", "1")
//						.param("limit", "10"))
//				.andDo(print())		
//				.andExpect(handler().handlerType(ACTG001RestController.class))	
//				.andExpect(handler().methodName("getCategories"))					
//				.andExpect(status().isOk())
//				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * 카테고리 목록 조회 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testDGetAllCategories() throws Exception {
//		actg001Service.regCategory(category, "TENANT");
//		
//		category.bcn_cd = null;
//		category.mama_cate_seq = null;
//		
//		mockMvc.perform(get("/admin/rest/"+bcnCd+"/categories/TENANT/all")
//						.header("content-type", "application/json")	
//						.contentType(MediaType.APPLICATION_JSON)
//						.characterEncoding("UTF-8")
//						.param("offset", "1")
//						.param("limit", "10"))
//				.andDo(print())		
//				.andExpect(handler().handlerType(ACTG001RestController.class))	
//				.andExpect(handler().methodName("getCategories"))					
//				.andExpect(status().isOk())
//				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * 카테고리 수정 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testEModidfyCategory() throws Exception {
		
//		String cateSeq = actg001Service.regCategory(category, "TENANT").extra;
//		
//		SCTG001Vo vo = new SCTG001Vo();
//		vo.cate_seq = cateSeq;
//		
//		assertThat(actg001Service.getCategory(vo).cate_nm_en, is(category.cate_nm_en));
//		
//		category.sts = 3;
//		category.cate_seq = null;
//		category.bcn_cd = null;
//		
//		mockMvc.perform(put("/admin/rest/"+bcnCd+"/categories/TENANT/any/"+cateSeq)	
//						.header("content-type", "application/json")	
//						.contentType(MediaType.APPLICATION_JSON)
//						.characterEncoding("UTF-8")
//						.content(new ObjectMapper().writeValueAsString(category)))
//				.andDo(print())	
//				.andExpect(handler().handlerType(ACTG001RestController.class))	
//				.andExpect(handler().methodName("modifyCategory"))					
//				.andExpect(status().isOk())
//				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.code").value(0));		
//		
//		assertThat(actg001Service.getCategory(vo).cate_nm_en, is(category.cate_nm_en));
	}
	
	/**
	 * 카테고리 삭제 테스트
	 * 삭제된 카테고리 확인 일 위해 getCategory 사용. 삭제 후 카테고리 미 존재로 Exception 발생.
	 * @return
	 */
	@Test(expected=BaseException.class)
	@Transactional
	public void testFDeleteCategory() throws Exception {
		
//		String cateSeq = actg001Service.regCategory(category, "TENANT").extra;
//		
//		SCTG001Vo vo = new SCTG001Vo();
//		vo.cate_seq = cateSeq;
//		
//		assertThat(actg001Service.getCategory(vo).cate_seq, is(cateSeq));
//		
//		mockMvc.perform(delete("/admin/rest/"+bcnCd+"/categories/TENANT/any/"+cateSeq)	// http method를 post로 설정, /rest/tenant 호출 uri 설정 
//						.header("content-type", "application/json")	// 헤더에 content-type 설정 
//						.contentType(MediaType.APPLICATION_JSON)
//						.characterEncoding("UTF-8"))
//				.andDo(print())		// 응답 결과에 대한 모든 정보 출력 
//				.andExpect(handler().handlerType(ACTG001RestController.class))	// 요청에 대한 매핑된 MVC컨트롤러클래스 확인 
//				.andExpect(handler().methodName("deleteCategory"))					// 요청에 대한 매핑된 MVC컨트롤러메소드 확인
//				.andExpect(status().isOk())	// 응답 받은 상태 코드가 200 인지 확인 
//				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.code").value(0));	// jsonPath를 이용하여 json 응답값 확인
//		
//		assertThat(actg001Service.getCategory(vo), nullValue());
	}

}
