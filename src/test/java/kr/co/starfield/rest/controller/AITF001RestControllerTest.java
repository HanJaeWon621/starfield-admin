package kr.co.starfield.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.model.MegaBoxP200;
import kr.co.starfield.model.MegaBoxP201;
import kr.co.starfield.model.MegaBoxP202;
import kr.co.starfield.service.AITF001Service;

public class AITF001RestControllerTest extends BaseTest {
	
	@Autowired
	AITF001Service aitf001Service;

	MegaBoxP200 P200;
	MegaBoxP201 P201;
	MegaBoxP202 P202;
	
	@Override
	public void setup() throws Exception {
		P200 = new MegaBoxP200();
		P200.bcn_cd="01";
		
		P201 = new MegaBoxP201();
		P201.bcn_cd="01";
		
		P202 = new MegaBoxP202();
		P202.bcn_cd="01";
	}

	/**
	 * MegaBox P200(박스오피스-메가박스 전체 기준) 목록 조회
	 * @return
	 */
	@Test
	@Transactional
	public void testGetP200List() throws Exception { 
		
		mockMvc.perform(get("/admin/rest/" + P200.bcn_cd + "/megaboxs/boxoffice")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.param("offset", "0")
						.param("limit", "8"))
				.andDo(print())		
				.andExpect(handler().handlerType(AITF001RestController.class))	
				.andExpect(handler().methodName("getP200List"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	/**
	 * MegaBox P201(현재 상영작-하남 개별 기준) 목록 조회
	 * @return
	 */
	@Test
	@Transactional
	public void testGetP201List() throws Exception { 
		
		mockMvc.perform(get("/admin/rest/" + P201.bcn_cd + "/megaboxs/current")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.param("offset", "0")
						.param("limit", "8"))
				.andDo(print())		
				.andExpect(handler().handlerType(AITF001RestController.class))	
				.andExpect(handler().methodName("getP201List"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	/**
	 * MegaBox P202(상영 예정작-메가박스 전체 기준) 목록 조회
	 * @return
	 */
	@Test
	@Transactional
	public void testGetP202List() throws Exception { 
		
		mockMvc.perform(get("/admin/rest/" + P202.bcn_cd + "/megaboxs/plan")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.param("offset", "0")
						.param("limit", "8"))
				.andDo(print())		
				.andExpect(handler().handlerType(AITF001RestController.class))	
				.andExpect(handler().methodName("getP202List"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
}
