package kr.co.starfield.rest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.starfield.model.Operation;
import kr.co.starfield.service.AOPR001Service;

public class AOPR001RestControllerTest extends BaseTest {
	
	@Autowired
	AOPR001Service aopr001Service;

	Operation operation;
	
	String bcn_cd;
	
	@Override
	public void setup() throws Exception {
		operation = new Operation();
		operation.opr_info_seq          		= "OP201606101230240001";
		operation.bcn_cd                			= "01";
		operation.norm_day_open_hr_min		= "1000";
		operation.norm_day_end_hr_min = "2300";
		operation.weekend_open_hr_min     	= "1100";
		operation.weekend_end_hr_min		= "2200";
		operation.reps_num1            			= "031";
		operation.reps_num2             		= "251";
		operation.reps_num3             		= "3526";
		operation.srvc_cntr_num1        		= "031";
		operation.srvc_cntr_num2        		= "555";
		operation.srvc_cntr_num3        		= "2345";
		operation.reps_fax_num1         		= "031";
		operation.reps_fax_num2         		= "666";
		operation.reps_fax_num3         		= "2345";
		operation.reps_email            		= "11test@gmail.com";
		operation.reps_nm               		= "이기자!!!!!!!!!!";
		operation.reps_addr1            		= "경기도";
		operation.reps_addr2            		= "하남";
		operation.sts                  				= 1;
		operation.reg_dttm              			= "sysdate";
		operation.mod_dttm              		= "sysdate";
		operation.reg_usr               			= "star";
		operation.mod_usr               		= "srat";
		
		bcn_cd = operation.bcn_cd;
	}
	
	/**
	 * 운영정보 레디스 동기화
	 * @return
	 */
	@Test
	public void testRedisSync() throws Exception {
		aopr001Service.syncOperation("01");
	}
	
	@Test
	@Transactional
	public void testGetOperation() throws Exception { 
		mockMvc.perform(get("/admin/rest/"+bcn_cd+"/operations")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(new ObjectMapper().writeValueAsString(operation)))
				.andDo(print())		
				.andExpect(handler().handlerType(AOPR001RestController.class))	
				.andExpect(handler().methodName("getOperation"))					
				.andExpect(status().isOk())	
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
				//.andExpect(jsonPath("$.reps_email").value("test@gmail.com"));
	}

	@Test
//	@Transactional
	public void testModidfyOperation() throws Exception {
		mockMvc.perform(put("/admin/rest/"+bcn_cd+"/operations")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(new ObjectMapper().writeValueAsString(operation)))
				.andDo(print())
				.andExpect(handler().handlerType(AOPR001RestController.class))	
				.andExpect(handler().methodName("modifyOperation"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
				//.andExpect(jsonPath("$.code").value(0));
		
		//assertThat(aopr001Service.getOperation(bcnCd).reps_email, is(operation.reps_email));
		
	}
}
