package kr.co.starfield.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"file:src/main/webapp/WEB-INF/mybatis/mybatis-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
})
@WebAppConfiguration
public class HomeRestControllerTest {

	@Autowired
	WebApplicationContext wac;
	MockMvc mockMvc;


	@Before
	public void init() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	 @Test
	 public void testGetEmp() throws Exception {
		 
		 mockMvc.perform(get("/rest/emps/7935")
			 .header("content-type", "application/json"))
			 .andExpect(handler().handlerType(HomeRestController.class))
			 .andExpect(handler().methodName("getEmp"))
			 .andDo(MockMvcResultHandlers.print());
	 }

	
	@Test
	public void test() {}
	
}
