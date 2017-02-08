package kr.co.starfield.rest.controller;


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
import java.nio.file.Paths;

import javax.net.ssl.SSLEngineResult.Status;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import kr.co.starfield.common.BaseException;

public class FileRestControllerTest extends BaseTest {

	
	byte[] imgContent;
	MockMultipartFile testImgFile;
	
	@Override
	public void setup() throws Exception {
	
		imgContent = FileUtils.readFileToByteArray(new File(System.getProperty("user.home")+"/Downloads/Grand-Openig_banner.png"));
		testImgFile = new MockMultipartFile("file", "Grand-Openig_banner.png ", "image/png", imgContent);
	}
	
	
	@Test
	public void imgUploadTest() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/rest/BCN001/file/images/barcode")
				.file(testImgFile))
		.andExpect(handler().handlerType(FileRestController.class))
		.andExpect(handler().methodName("imgFileUpload"))
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	/*@Test
	public void notImgMimeTypeTest() throws Exception {
		
		testImgFile = new MockMultipartFile("file", "머라는거.jpg", "text/plan", imgContent);
		
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/rest/BCN001/file/images")
				.file(testImgFile))
		.andExpect(handler().handlerType(FileRestController.class))
		.andExpect(handler().methodName("imgFileUpload"))
		.andExpect(status().isBadRequest()); // 400 
//		.andDo(print());
	}*/
	
	

}
