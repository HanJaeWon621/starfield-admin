package kr.co.starfield.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.model.LikeTenant;
import kr.co.starfield.model.LikeEvent;
import kr.co.starfield.model.MemberDevice;
import kr.co.starfield.model.vo.SMBR005Vo;
import kr.co.starfield.model.vo.SMBR002Vo;
import kr.co.starfield.model.vo.SMBR003Vo;
import kr.co.starfield.service.AMBR001Service;
import kr.co.starfield.service.AMBR002Service;

public class AMBR002RestControllerTest extends BaseTest {

	@Autowired
	AMBR002Service ambr002Service;

	
	String bcn_cd = "01";
	String mbr_seq = "MR201607162135014482";
	String evt_seq = "EV201607141428031341";
	String tnt_seq = "TN201607111120023651";
	
	LikeEvent likeEvent;
	LikeTenant likeTenant;
	
	/**
	 * 테스트 초기화
	 * @return
	 */
	@Before
	@Override
	public void setup() throws Exception {
		likeEvent = new LikeEvent();
		likeEvent.evt_seq = evt_seq;
		likeEvent.mbr_seq = mbr_seq;
		
		likeTenant = new LikeTenant();
		likeTenant.tnt_seq = tnt_seq;
		likeTenant.mbr_seq = mbr_seq;
		likeTenant.bcn_cd = bcn_cd;
	}
	
	/**
	 * 관심 이벤트 등록 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testRegLikeEvent() throws Exception {
				
		mockMvc.perform(post("/rest/"+bcn_cd+"/members/"+likeEvent.mbr_seq+"/likes/events")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(new ObjectMapper().writeValueAsString(likeEvent)))
				.andDo(print())					
				.andExpect(status().isOk())	 
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));	

	}
	
	/**
	 * 관심이벤트 목록 조회 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testGetLikeEventList() throws Exception { 
		SMBR002Vo vo = likeEvent.toVo();
		vo.sts = StatusCode.Common.USE.getCode();
		
		ambr002Service.regLikeEvent(vo);
		
		mockMvc.perform(get("/rest/"+bcn_cd+"/members/"+likeEvent.mbr_seq+"/likes/events")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.param("offset", "0")
						.param("limit", "-1"))
				.andDo(print())				
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * 관심이벤트 조회 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testGetLikeEvent() throws Exception { 
		SMBR002Vo vo = likeEvent.toVo();
		vo.sts = StatusCode.Common.USE.getCode();
		
		ambr002Service.regLikeEvent(vo);
		
		mockMvc.perform(get("/rest/"+bcn_cd+"/members/"+likeEvent.mbr_seq+"/likes/events/" + likeEvent.evt_seq)	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8"))
				.andDo(print())				
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * 관심이벤트 삭제 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testDeleteLikeEvent() throws Exception { 
		SMBR002Vo vo = likeEvent.toVo();
		vo.sts = StatusCode.Common.USE.getCode();
		
		ambr002Service.regLikeEvent(vo);
		
		mockMvc.perform(delete("/rest/"+bcn_cd+"/members/"+likeEvent.mbr_seq+"/likes/events/" + likeEvent.evt_seq)	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8"))
				.andDo(print())				
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	
	/**
	 * 관심 테넌트 등록 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testRegLikeTenant() throws Exception {
				
		mockMvc.perform(post("/rest/"+bcn_cd+"/members/"+likeTenant.mbr_seq+"/likes/tenants")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(new ObjectMapper().writeValueAsString(likeTenant)))
				.andDo(print())					
				.andExpect(status().isOk())	 
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));	

	}
	
	/**
	 * 관심 테넌트 목록 조회 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testGetLikeTenantList() throws Exception { 
		SMBR003Vo vo = likeTenant.toVo();
		vo.sts = StatusCode.Common.USE.getCode();
		
		ambr002Service.regLikeTenant(vo);
		
		mockMvc.perform(get("/rest/"+bcn_cd+"/members/"+likeTenant.mbr_seq+"/likes/tenants")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.param("offset", "0")
						.param("limit", "-1"))
				.andDo(print())				
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * 관심 테넌트 조회 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testGetLikeTenant() throws Exception { 
		SMBR003Vo vo = likeTenant.toVo();
		vo.sts = StatusCode.Common.USE.getCode();
		
		ambr002Service.regLikeTenant(vo);
		
		mockMvc.perform(get("/rest/"+bcn_cd+"/members/"+likeTenant.mbr_seq+"/likes/tenants/" + likeTenant.tnt_seq)	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8"))
				.andDo(print())				
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * 관심 테넌트 삭제 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testDeleteLikeTenant() throws Exception { 
		SMBR002Vo vo = likeEvent.toVo();
		vo.sts = StatusCode.Common.USE.getCode();
		
		ambr002Service.regLikeEvent(vo);
		
		mockMvc.perform(delete("/rest/"+bcn_cd+"/members/"+likeTenant.mbr_seq+"/likes/tenants/" + likeTenant.tnt_seq)	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8"))
				.andDo(print())				
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
}
