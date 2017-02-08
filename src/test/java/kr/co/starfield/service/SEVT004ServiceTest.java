package kr.co.starfield.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import kr.co.starfield.model.SEVT004ListResult;
import kr.co.starfield.model.SEVT004Vo;
import kr.co.starfield.model.SEVT004VoResult;

/**
 * SEVT004(stamp_event) 서비스 클래스 TEST
 *
 * @author  
 * @version 1.0,
 * @see
 * @date 2016.11.21
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"file:src/test/resources/mybatis-context-for-test.xml",
	"file:src/test/resources/servlet-context-for-test.xml"
})
@WebAppConfiguration
@TransactionConfiguration(defaultRollback=true)
public class SEVT004ServiceTest {
	
	
	private Logger logger = LoggerFactory.getLogger(SEVT004Service.class);

	@Autowired
	WebApplicationContext wac;
	MockMvc mockMvc;
	
	@Autowired
	SEVT004Service SEVT004Service;
	
	SEVT004Vo vo;
	
	@Transactional
	@Before
	public void init() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		
		vo = new SEVT004Vo();
		
//		vo.bcn_cd  = "01";
//		vo.evt_seq = "EV201611211206220230";
		
		// UUID 와 CUST_ID 둘 다 없을 경우
//		vo.uuid    = "null";
//		vo.cust_id = "null";
		
		// UUID 있을 경우 
//		vo.uuid    = "586F4140-EA75-492D-AD72-40BA5A71E622";
//		vo.cust_id = "null";
		
		// CUST_ID 있을 경우
		vo.uuid    = "null";
		vo.cust_id = "C87015335";
		
	}
	
	@Test
	public void isConnectTest() throws Exception {
		
		logger.info("param >> " + vo.toString());
		
		SEVT004ListResult<SEVT004VoResult> stampResult = SEVT004Service.stampEvent(vo);
		
		logger.debug("SEVT004Service >>>>> " + stampResult.toString() + " <<<<<");
		
	}
}
