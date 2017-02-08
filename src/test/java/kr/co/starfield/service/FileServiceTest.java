package kr.co.starfield.service;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.ImgFile;
import kr.co.starfield.model.vo.ImgFileVo;
import kr.co.starfield.rest.controller.BaseTest;

public class FileServiceTest extends BaseTest {
	
	@Autowired
	FileService fileService;
	
	ImgFileVo vo;

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
		vo = new ImgFileVo();
	
		vo.bcn_cd = "BCN001";
		vo.img_pys_loc = "~/cdn/images/20160621/test.jpg";
		vo.img_uri = "/cdn/images/20160621/test.jpg";
		vo.img_thmb = "~/cdn/images/20160621/thumbnail/test.jpg";
		vo.img_thmb_uri = "/cdn/images/20160621/thumbnail/test.jpg";
		vo.reg_usr = "tester";
		vo.mod_usr = "tester";
	}
	
	
	@Test
	@Transactional
	public void regImgFileTest() throws Exception {
		
		ImgFile imgFile = fileService.regImgFile(vo);
		
		Utils.Obj.print(imgFile);
		
		assertThat(imgFile.bcn_cd, is(vo.bcn_cd));
		assertThat(imgFile.img_seq, is(notNullValue()));
	}
	
}
