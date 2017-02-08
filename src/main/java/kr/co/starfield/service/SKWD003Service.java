package kr.co.starfield.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.starfield.common.Utils;
import kr.co.starfield.mapper.SKWD003Mapper;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SKWD003Vo;

/**
 * SKWD003(테넌트 키워드 로그) 서비스 클래스
 *
 * @author yhkim
 * @version	1.0,
 * @see
 * @date 2016.08.17
 */

@Service
public class SKWD003Service {
	private Logger ll = LoggerFactory.getLogger(SMBR004Service.class);
	
	@Autowired
	SKWD003Mapper skwd003Mapper;
	
	/**
	 * 테넌트 키워드 로그 등록
	 * @param vo
	 * @return 
	 */
	public SimpleResult regKeywordLog(SKWD003Vo vo) {
		SimpleResult result = new SimpleResult();
		vo.keyword = Utils.Str.substringByBytes(vo.keyword, 100);
		skwd003Mapper.regKeywordLog(vo);

		return result;
	}
}
