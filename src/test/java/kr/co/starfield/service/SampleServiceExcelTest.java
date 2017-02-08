package kr.co.starfield.service;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.starfield.model.Sample;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SampleVo;
import kr.co.starfield.rest.controller.BaseTest;

public class SampleServiceExcelTest extends BaseTest {

	@Autowired
	SampleService sampleService;
	
	MultipartFile xlsFile  = null;
	MultipartFile xlsxFile = null;
	
	@Override
	public void setup() throws Exception {
		
		byte[] xlsContent = FileUtils.readFileToByteArray(new File("/Users/jojong-gyun/cdn/test.xls"));
		byte[] xlsxContent = FileUtils.readFileToByteArray(new File("/Users/jojong-gyun/cdn/test.xlsx"));

		xlsFile = new MockMultipartFile("file", "test.xls", "application/vnd.ms-excel", xlsContent);
		xlsxFile = new MockMultipartFile("file", "test.xlsx", "application/vnd.ms-excel", xlsxContent);
		
		sampleService.delAllSamples();
	}
	
	
	@Test
	@Transactional
	public void xlsxUploadTest() throws Exception {
		
		SimpleResult result = sampleService.regSample(xlsxFile);
		
		Sample[] list = sampleService.getSamples();
		
		assertThat(result.code, is(0));
		assertThat(list.length, is(10));
	}
	
	@Test
	public void xlsUploadTest() throws Exception {
		
		SimpleResult result = sampleService.regSample(xlsFile);
		
		Sample[] list = sampleService.getSamples();
		
		assertThat(result.code, is(0));
		assertThat(list.length, is(10));
	}
	
}
