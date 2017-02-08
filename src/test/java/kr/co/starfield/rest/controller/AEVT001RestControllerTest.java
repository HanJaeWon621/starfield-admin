package kr.co.starfield.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.model.Event;
import kr.co.starfield.model.SADM003;
import kr.co.starfield.service.AEVT001Service;

public class AEVT001RestControllerTest extends BaseTest {
	
	@Autowired
	AEVT001Service aevt001Service;

	Event event;
	MockHttpSession session;
	SADM003[] auths;
	
	@Override
	public void setup() throws Exception {
		event = new Event();
		
		event.bcn_cd = "01";
		event.sts    = 1;
		
		session = new MockHttpSession();
		
		session.setAttribute("adm_seq", "test_adm");
		session.setAttribute("sts", 101);
		session.setAttribute("bcn_cd", "01");
		
		auths = new SADM003[1];
		auths[0] = new SADM003();
		auths[0].auth_nm = "system";
		session.setAttribute("authTypeArr", auths);
		
	}
	
	/**
	 * Event 목록 조회
	 * @return
	 */
	@Test
	@Transactional
	public void testGetEventList() throws Exception { 
		
		mockMvc.perform(get("/admin/rest/" + event.bcn_cd + "/events")	
						.header("content-type", "application/json")
						.header("AJAX", "true")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.session(session)
//						.sessionAttr("authTypeArr", "auths")
						//.param("q", "blog_titl=title")
						.param("offset", "0")
						.param("limit", "10"))
				.andDo(print())		
				.andExpect(handler().handlerType(AEVT001RestController.class))	
				.andExpect(handler().methodName("getEventList"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
}
