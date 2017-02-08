
package kr.co.starfield.service;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.model.vo.SCTG001Vo;
import kr.co.starfield.rest.controller.BaseTest;

public class ACTG001ServiceTest extends BaseTest {
	
	@Autowired
	ACTG001Service actg001Service;
	
	SCTG001Vo vo;

	@Override
	public void setup() {
		vo = new SCTG001Vo();
	
		vo.mama_cate_seq = "CT201607091816400007";
		vo.cate_cd = "TENANT";
	}
	
	
	@Test
//	@Transactional
	public void testSyncRedisCategories() throws BaseException {
		
		actg001Service.syncRedisCategories("TENANT");

	}
	
}
