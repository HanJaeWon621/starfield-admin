package kr.co.starfield.service;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.NotNull;
import org.neo4j.cypher.internal.compiler.v2_1.ast.IsNotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.Notice;
import kr.co.starfield.model.Sample;
import kr.co.starfield.model.vo.SampleVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"file:src/main/webapp/WEB-INF/mybatis/mybatis-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
@WebAppConfiguration
@TransactionConfiguration(defaultRollback=true)
public class SampleServiceTest {
	
	
	private Logger ll = LoggerFactory.getLogger(SampleServiceTest.class);

	@Autowired
	WebApplicationContext wac;
	MockMvc mockMvc;
	
	@Autowired
	SampleService sampleService;
	
	SampleVo vo;
	
	@Transactional
	@Before
	public void init() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		
//		sampleService.delAllSamples();
//		vo = new SampleVo();
//		
//		for(int i = 1; i <= 100; i++) {
//			vo.num_col = i;
//			vo.varchar_col = "안녕하세요!@#$".concat(i+"");
//			vo.date_col = new Date();
//			
//			sampleService.regSample(vo);	
//		}
	}
	
//	@Transactional
//	@Test
//	public void getSampleTest() throws Exception {
//		
//		Sample[] sampleList = sampleService.getSamples();
//		
//		assertThat(sampleList.length, is(100));
//	}
	
//	@Test
//	public void getReadExcelTest() throws Exception {
//		
//		byte[] excelContent = FileUtils.readFileToByteArray(new File("/Users/jojong-gyun/cdn/test.xls"));
//		MultipartFile file = new MockMultipartFile("file", "test.xls", "application/vnd.ms-excel", excelContent);
//		
//		SampleVo[] voList = null;
//		
//		voList = sampleService.getReadExcel(file, voList);
//		
//		assertThat(voList.length, is(10));
//	}
	
	
	@Test
	public void isConnectTest() throws Exception {
		
		String oraTime = sampleService.getOraTime();
		
		ll.info("oraTime is {}", oraTime);
		
		
	}
	
	
	
	
	
	
	
}
