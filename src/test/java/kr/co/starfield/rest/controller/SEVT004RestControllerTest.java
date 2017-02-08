package kr.co.starfield.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.model.SEVT004Vo;

public class SEVT004RestControllerTest extends BaseTest {
	
	SEVT004Vo vo;
//	MockHttpSession session;
//	SADM003[] auths;
	
	@Override
	public void setup() throws Exception {
		
		vo = new SEVT004Vo();
		
		vo.bcn_cd  = "01";
		
//		session = new MockHttpSession();
//		
//		session.setAttribute("adm_seq", "test_adm");
//		session.setAttribute("sts", 101);
//		session.setAttribute("bcn_cd", "01");
//		
//		auths = new SADM003[1];
//		auths[0] = new SADM003();
//		auths[0].auth_nm = "system";
//		session.setAttribute("authTypeArr", auths);
		
	}
	
	/**
	 * Stamp Event 조회
	 * http://10.253.42.111:17001/rest/01/stampEvent?uuid=UUID-201611171614318858-045942&cust_id=null
	 * @param  bcn_cd, uuid, cust_id
	 * @return  zone_id, code
	 */
	@Test
	@Transactional
	public void testGetEventList() throws Exception { 
		
		mockMvc.perform(get("/rest/" + vo.bcn_cd + "/stampEvent")
						.header("content-type", "application/json")
						.header("AJAX", "true")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
//						.session(session))
						
						.param("evt_seq", "EV201611211206220230")
					// UUID 와 CUST_ID 둘 다 없을 경우
						.param("uuid", "null")
						.param("cust_id", "null"))
						
					// UUID 있을 경우
//						.param("uuid", "UUID-201611171614318858-045942")
//						.param("cust_id", "null"))
						
					// CUST_ID 있을 경우
//						.param("uuid", "null")
//						.param("cust_id", "C87015362"))
				.andDo(print())		
				.andExpect(handler().handlerType(SEVT004RestController.class))	
				.andExpect(handler().methodName("stampEvent"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
}
