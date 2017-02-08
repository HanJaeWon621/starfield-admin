package kr.co.starfield.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.mapper.SMBR004Mapper;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SMBR004Vo;

@Service
public class SMBR004Service {
	
	private Logger ll = LoggerFactory.getLogger(SMBR004Service.class);
	
	@Autowired
	SMBR004Mapper smbr004Mapper;
	
	/**
	 * 로그인 로그 등록
	 * @param vo
	 * @return 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regLoginLog(SMBR004Vo vo) {
		SimpleResult result = new SimpleResult();
		
		smbr004Mapper.regLoginLog(vo);

		return result;
	}
}
