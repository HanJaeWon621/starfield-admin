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

import java.util.ArrayList;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.StatusCode;
import kr.co.starfield.model.TenantHoliday;
import kr.co.starfield.model.vo.SOPR004Vo;
import kr.co.starfield.service.AOPR004Service;

public class AOPR004RestControllerTest extends BaseTest {

	@Autowired
	AOPR004Service aopr004Service;

	TenantHoliday tenantHoliday;
	ArrayList<SOPR004Vo> holidayVoList;
	
	@Override
	public void setup() throws Exception {
		tenantHoliday = new TenantHoliday();
		tenantHoliday.bcn_cd = "01";
		tenantHoliday.tnt_seq = "TN201607111120023651";
		tenantHoliday.year = "2016";
		tenantHoliday.mont = "01";
		tenantHoliday.day = "01";
		tenantHoliday.open_hr_min = "0900";
		tenantHoliday.end_hr_min = "2000";
		
		tenantHoliday.sts = 1;
		
		holidayVoList = new ArrayList<>();
		holidayVoList.add(tenantHoliday.toVo());
	}
	
	
	/**
	 * 레디스 동기화
	 * @return
	 */
	@Test
	public void testRedisSync() throws Exception {
		aopr004Service.syncTenantHoliday("01");
	}
	/**
	 * Holiday 등록
	 * @return
	 */
	@Test
	@Transactional
	public void testRegStarFieldHoliday() throws Exception {
		
		
		TenantHoliday[] modelList = new TenantHoliday[1]; 
		modelList[0] = tenantHoliday;
		
		mockMvc.perform(post("/admin/rest/" + tenantHoliday.bcn_cd + "/holidays/ir/tnt")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(new ObjectMapper().writeValueAsString(modelList)))
				.andDo(print())					
				.andExpect(status().isOk())	 
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));	

	}
	
	/**
	 * Holiday 목록 조회
	 * @return
	 */
	@Test
	@Transactional
	public void testGetStarFieldHolidayList() throws Exception { 
		
		tenantHoliday.tnt_irgu_opr_seq = aopr004Service.regTenantHolidays(holidayVoList).extra;
		
		mockMvc.perform(get("/admin/rest/" + tenantHoliday.bcn_cd + "/holidays/ir/tnt")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.param("q", "year=2016"))
				.andDo(print())						
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * Holiday 상세
	 * @return
	 */
	@Test
	@Transactional
	public void testGetStarFieldHoliday() throws Exception { 
		
		tenantHoliday.tnt_irgu_opr_seq = aopr004Service.regTenantHolidays(holidayVoList).extra;
								
		mockMvc.perform(get("/admin/rest/" + tenantHoliday.bcn_cd + "/holidays/ir/tnt/" + tenantHoliday.tnt_irgu_opr_seq)	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8"))
				.andDo(print())						
				.andExpect(status().isOk())	
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.bcn_cd").value(tenantHoliday.bcn_cd));
	}
	
	/**
	 * Holiday 수정
	 * @return
	 */
	@Test
	@Transactional
	public void testModifyStarFieldHoliday() throws Exception {
		
		tenantHoliday.tnt_irgu_opr_seq = aopr004Service.regTenantHolidays(holidayVoList).extra;
		
		assertThat(aopr004Service.getTenantHoliday(tenantHoliday.toVo()).sts, is(tenantHoliday.sts));
		
		tenantHoliday.sts = 1;
		
		mockMvc.perform(put("/admin/rest/" + tenantHoliday.bcn_cd + "/holidays/ir/tnt/" + tenantHoliday.tnt_irgu_opr_seq)	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(new ObjectMapper().writeValueAsString(tenantHoliday)))
				.andDo(print())				
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));
		
		assertThat(aopr004Service.getTenantHoliday(tenantHoliday.toVo()).sts, is(tenantHoliday.sts));
	}
	
	/**
	 * Holiday 삭제
	 * @return
	 */
	@Test
	@Transactional
	public void testDeleteStarFieldHoliday() throws Exception {
		
		tenantHoliday.tnt_irgu_opr_seq = aopr004Service.regTenantHolidays(holidayVoList).extra;
		
		mockMvc.perform(delete("/admin/rest/" + tenantHoliday.bcn_cd + "/holidays/ir/tnt/" + tenantHoliday.tnt_irgu_opr_seq)
						.header("content-type", "application/json")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));
		
		
		SOPR004Vo vo = tenantHoliday.toVo();
		vo.sts = StatusCode.Common.DELETE.getCode();
		assertThat(aopr004Service.getTenantHoliday(vo).sts, is(vo.sts));
	}
}
