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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.model.RecommendKeyword;
import kr.co.starfield.model.vo.SKWD001Vo;
import kr.co.starfield.service.AKWD001Service;

public class AKWD001RestControllerTest extends BaseTest {

	@Autowired
	AKWD001Service akwd001Service;

	RecommendKeyword recommendKeyword;
	SKWD001Vo vo;
	
	String bcnCd;
	
	/**
	 * 추천 키워드 초기화
	 * @return
	 */
	@Before
	@Override
	public void setup() throws Exception {
		recommendKeyword = new RecommendKeyword();
		recommendKeyword.bcn_cd = "BCN001";
		recommendKeyword.keyword = "여름";
		recommendKeyword.sort_ord = 1;
		recommendKeyword.sts = 1;
		
		bcnCd = recommendKeyword.bcn_cd;
		
		vo = recommendKeyword.toVo();
	}
	
	/**
	 * 추천 키워드등록 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testRegRecommendKeyword() throws Exception {

		recommendKeyword.bcn_cd = null;
				
		mockMvc.perform(post("/admin/rest/"+bcnCd+"/recommends/keywords")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(new ObjectMapper().writeValueAsString(recommendKeyword)))
				.andDo(print())		
				.andExpect(handler().handlerType(AKWD001RestController.class))	 
				.andExpect(handler().methodName("regRecommendKeyword"))					
				.andExpect(status().isOk())	 
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));	
	}
	
	/**
	 * 추천 키워드 목록 조회 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testGetRecommendKeywords() throws Exception { 
		
		akwd001Service.regRecommendKeyword(vo);
		
		mockMvc.perform(get("/admin/rest/"+bcnCd+"/recommends/keywords")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.param("offset", "1")
						.param("limit", "5"))
				.andDo(print())		
				.andExpect(handler().handlerType(AKWD001RestController.class))	
				.andExpect(handler().methodName("getRecommendKeywords"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * 추천 키워드 상세 조회 테스트 
	 * @return
	 */
	@Test
	@Transactional
	public void testGetRecommendKeyword() throws Exception { 
		
		String recmKeywordSeq = akwd001Service.regRecommendKeyword(vo).extra;
		
		mockMvc.perform(get("/admin/rest/"+bcnCd+"/recommends/keywords/"+recmKeywordSeq)	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8"))
				.andDo(print())		
				.andExpect(handler().handlerType(AKWD001RestController.class))	
				.andExpect(handler().methodName("getRecommendKeyword"))					
				.andExpect(status().isOk())	
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.keyword").value(recommendKeyword.keyword));
	}
	
	/**
	 * 추천 키워드 수정 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testModidfyRecommendKeyword() throws Exception {
		
		String recmKeywordSeq = akwd001Service.regRecommendKeyword(vo).extra;
		
		assertThat(akwd001Service.getRecommendKeyword(recmKeywordSeq).sts, is(recommendKeyword.sts));
		
		recommendKeyword.sts = 2;
		recommendKeyword.bcn_cd = null;
		
		mockMvc.perform(put("/admin/rest/"+bcnCd+"/recommends/keywords/"+recmKeywordSeq)	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(new ObjectMapper().writeValueAsString(recommendKeyword)))
				.andDo(print())	
				.andExpect(handler().handlerType(AKWD001RestController.class))	
				.andExpect(handler().methodName("modifyRecommendKeyword"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));
		
		assertThat(akwd001Service.getRecommendKeyword(recmKeywordSeq).sts, is(recommendKeyword.sts));
	}
	
	/**
	 * 추천 키워드 삭제 테스트
	 * @return
	 */
	@Test(expected=BaseException.class)
	@Transactional
	public void testDeleteRecommendKeyword() throws Exception {
		
		String recmKeywordSeq = akwd001Service.regRecommendKeyword(vo).extra;
		
		assertThat(akwd001Service.getRecommendKeyword(recmKeywordSeq).recm_keyword_seq, is(recmKeywordSeq));
		
		mockMvc.perform(delete("/admin/rest/"+bcnCd+"/recommends/keywords/"+recmKeywordSeq)	// http method를 post로 설정, /rest/tenant 호출 uri 설정 
						.header("content-type", "application/json")	// 헤더에 content-type 설정 
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8"))
				.andDo(print())		// 응답 결과에 대한 모든 정보 출력 
				.andExpect(handler().handlerType(AKWD001RestController.class))	// 요청에 대한 매핑된 MVC컨트롤러클래스 확인 
				.andExpect(handler().methodName("deleteRecommendKeyword"))					// 요청에 대한 매핑된 MVC컨트롤러메소드 확인
				.andExpect(status().isOk())	// 응답 받은 상태 코드가 200 인지 확인 
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));	// jsonPath를 이용하여 json 응답값 확인
		
		assertThat(akwd001Service.getRecommendKeyword(recmKeywordSeq).recm_keyword_seq, nullValue());
	}

}
