package kr.co.starfield.service;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.rest.controller.BaseTest;

public class LocaleServiceExcelTest extends BaseTest {

	@Autowired
	LocaleService localeService;
	
	MultipartFile xlsFile  = null;
	MultipartFile xlsxFile = null;
	
	@Override
	public void setup() throws Exception {
		
		byte[] xlsxContent = FileUtils.readFileToByteArray(new File(this.getClass().getResource("/excel/locale.xlsx").getFile()));
		xlsxFile = new MockMultipartFile("file", "locale.xlsx", "application/vnd.ms-excel", xlsxContent);

	}
	
	
	@Test
	public void xlsxUploadTest() throws Exception {
		
		SimpleResult result = localeService.regLanguage(xlsxFile);
		
		assertThat(result.code, is(0));
	}
	
	
}
