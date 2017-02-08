
package kr.co.starfield.service;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.model.vo.SCTG001Vo;
import kr.co.starfield.rest.controller.BaseTest;

public class RedisServiceTest extends BaseTest {
	
	@Autowired
	ASYS002Service asys002Service;
	
	@Autowired
	ACMS006Service acms006Service;
	
	@Autowired
	AFCT001Service afct001Service;

	@Override
	public void setup() {

	}
	
	
	@Test
	public void localeRedisSync() throws BaseException {
		asys002Service.syncLocale("01");
	}
	
	@Test
	public void faqRedisSync() throws BaseException {
		acms006Service.syncFAQ("01");
	}
	
	@Test
	public void syncRedisFacilitySuite() throws BaseException {
		afct001Service.syncRedisFacilitySuite();
	}
	
	
}
