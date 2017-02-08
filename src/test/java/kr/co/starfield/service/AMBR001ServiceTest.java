
package kr.co.starfield.service;


import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.helper.AwsSnsRelayHelper;
import kr.co.starfield.mapper.AMBR001Mapper;
import kr.co.starfield.model.TargetArnInfo;
import kr.co.starfield.model.vo.SCTG001Vo;
import kr.co.starfield.model.vo.SMBR005Vo;
import kr.co.starfield.rest.controller.BaseTest;

public class AMBR001ServiceTest extends BaseTest {
	
	@Autowired
	AMBR001Mapper ambr001Mapper;
	
	@Autowired
	AwsSnsRelayHelper awsSnsRelayHelper;
	
	@Override
	public void setup() {
		
	}
	
	
//	@Test
//	@Transactional
//	public void testSyncGlobalArn() {
//		
//		List<SMBR005Vo> list = ambr001Mapper.getSyncNeedMemberDevices();
//
//		TargetArnInfo targetArnInfo = new TargetArnInfo();
//
//		
//		for(SMBR005Vo vo : list) {
//			try {
//				targetArnInfo = awsSnsRelayHelper.updateEndpoint(vo, vo, false, true);
//				vo.end_arn = targetArnInfo.end_arn;
//				vo.glob_arn = targetArnInfo.glob_arn;
//				
//				ambr001Mapper.modifyMemberDevice(vo);
//			} catch (BaseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//	}
}
