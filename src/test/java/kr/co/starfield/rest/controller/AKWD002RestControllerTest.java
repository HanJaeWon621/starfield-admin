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
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.model.RecommendTag;
import kr.co.starfield.model.vo.SKWD002Vo;
import kr.co.starfield.service.AKWD002Service;

public class AKWD002RestControllerTest extends BaseTest {
	
	@Autowired
	AKWD002Service akwd002Service;

	RecommendTag recommendTag;
	
	@Override
	public void setup() throws Exception {
		recommendTag = new RecommendTag();
		recommendTag.bcn_cd = "01";
		recommendTag.tag_div = "a";
		recommendTag.tag_nm = "tag";
		recommendTag.sort_ord = 0;
		recommendTag.sts = 1;
	}
	
	/**
	 * RecommendTag 등록
	 * @return
	 */
	@Test
	@Transactional
	public void testRegRecommendTag() throws Exception {
		
		mockMvc.perform(post("/admin/rest/" + recommendTag.bcn_cd + "/recommend/tags")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(new ObjectMapper().writeValueAsString(recommendTag)))
				.andDo(print())		
				.andExpect(handler().handlerType(AKWD002RestController.class))	 
				.andExpect(handler().methodName("regRecommendTag"))					
				.andExpect(status().isOk())	 
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));	

	}
	
	/**
	 * RecommendTag 목록 조회
	 * @return
	 */
	@Test
	@Transactional
	public void testGetRecommendTagList() throws Exception { 
		
		recommendTag.recm_tag_seq = akwd002Service.regRecommendTag(recommendTag.toVo()).extra;
		
		mockMvc.perform(get("/admin/rest/" + recommendTag.bcn_cd + "/recommend/tags")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.param("q", "tag_nm=tag")
						.param("offset", "0")
						.param("limit", "10"))
				.andDo(print())		
				.andExpect(handler().handlerType(AKWD002RestController.class))	
				.andExpect(handler().methodName("getRecommendTagList"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * RecommendTag 상세
	 * @return
	 */
	@Test
	@Transactional
	public void testGetRecommendTag() throws Exception { 
		
		recommendTag.recm_tag_seq = akwd002Service.regRecommendTag(recommendTag.toVo()).extra;
		
		mockMvc.perform(get("/admin/rest/" + recommendTag.bcn_cd + "/recommend/tags/" + recommendTag.recm_tag_seq)	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8"))
				.andDo(print())		
				.andExpect(handler().handlerType(AKWD002RestController.class))	
				.andExpect(handler().methodName("getRecommendTag"))					
				.andExpect(status().isOk())	
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.bcn_cd").value("BCN001"));
	}
	
	/**
	 * RecommendTag 수정
	 * @return
	 */
	@Test
	@Transactional
	public void testModifyRecommendTag() throws Exception {
		
		recommendTag.recm_tag_seq = akwd002Service.regRecommendTag(recommendTag.toVo()).extra;
		
		assertThat(akwd002Service.getRecommendTag(recommendTag.toVo()).recm_tag_seq, is(recommendTag.recm_tag_seq));
		
		recommendTag.tag_nm = "tagName";
		
		mockMvc.perform(put("/admin/rest/" + recommendTag.bcn_cd + "/recommend/tags/" + recommendTag.recm_tag_seq)	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(new ObjectMapper().writeValueAsString(recommendTag)))
				.andDo(print())	
				.andExpect(handler().handlerType(AKWD002RestController.class))	
				.andExpect(handler().methodName("modifyRecommendTag"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));
		
		assertThat(akwd002Service.getRecommendTag(recommendTag.toVo()).tag_nm, is(recommendTag.tag_nm));
	}
	
	/**
	 * RecommendTag 삭제
	 * @return
	 */
	@Test
	@Transactional
	public void testDeleteRecommendTag() throws Exception {
		
		recommendTag.recm_tag_seq = akwd002Service.regRecommendTag(recommendTag.toVo()).extra;
		
		assertThat(akwd002Service.getRecommendTag(recommendTag.toVo()).recm_tag_seq, is(recommendTag.recm_tag_seq));
		
		mockMvc.perform(delete("/admin/rest/" + recommendTag.bcn_cd + "/recommend/tags/" + recommendTag.recm_tag_seq)
						.header("content-type", "application/json")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8"))
				.andDo(print())
				.andExpect(handler().handlerType(AKWD002RestController.class))
				.andExpect(handler().methodName("deleteRecommendTag"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));
		
		SKWD002Vo vo = recommendTag.toVo();
		vo.sts = StatusCode.Common.DELETE.getCode();
		
		assertThat(akwd002Service.getRecommendTag(vo).sts, is(vo.sts));
	}
}
