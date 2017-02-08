package kr.co.starfield.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.mapper.SEVT004Mapper;
import kr.co.starfield.model.SEVT004ListResult;
import kr.co.starfield.model.SEVT004Vo;
import kr.co.starfield.model.SEVT004VoResult;
import kr.co.starfield.rest.controller.SEVT004RestController;

/**
 * SEVT004(stamp_event) 서비스 클래스
 *
 * @author 
 * @version	1.0,
 * @see
 * @date 2016.11.21
 */

@Service
public class SEVT004Service {
	
	private static final Logger logger = LoggerFactory.getLogger(SEVT004RestController.class);

	@Autowired
	private SEVT004Mapper sevt004Mapper;
		
	/**
	 * Stamp Event 조회
	 * 
	 * @param  bcn_cd, evt_seq, cust_id, uuid
	 * @return code, message, giftCount, list[inbox_cont]
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SEVT004ListResult<SEVT004VoResult> stampEvent(SEVT004Vo vo) throws BaseException {
		
		SEVT004ListResult<SEVT004VoResult> result = new SEVT004ListResult<SEVT004VoResult>();
		
		int count;
		
		logger.info("### Stamp Event 조회 ### " + vo.toString());
		
		// UUID 없음
		if( vo.uuid.equals("null") ){
			
			result.code    = "-1";
			result.message = "디바이스 ID가 없습니다.";
			
			logger.debug("*** UUID 없음 ***");
			
			return result;
			
		}
		
		// STAMP 교환 확인
		count = sevt004Mapper.stampChangeCheck(vo);
		
		logger.debug("*** STAMP 교환 확인 [ " + count + " ] ***");
		
		if( count != 0 ){
			
			result.code     = "1";
			result.exchange = "1";
			result.message  = "해당 기기 또는 해당 사용자가 STAMP를 이미 교환하였습니다.";
			
			return result;
			
		}
		
		// STAMP 조회 ( ALBS004_D )
		result.list.addAll(sevt004Mapper.stampEventApp(vo));
		
		logger.debug("*** STAMP 조회 결과 [ " + vo.uuid + result.list.size() + " ] ***");
		
		// SATMP 교환 COUNT
		result.giftCount = sevt004Mapper.getGiftCount(vo);
		
		result.code     = "0";
		result.exchange = "0";
		result.message  = "GIFT_COUNT [ " + result.giftCount + " ] STAMP [ " + result.list.size() + " ]";
		
		return result;
		
	}

	
	/**
	 * Stamp 교환
	 * 
	 * @param  bcn_cd, evt_seq, mbr_seq, cust_id, uuid
	 * @return code
	 */
	@Transactional(rollbackFor = Exception.class)
	public HashMap<String, String> stampEventConfirm(SEVT004Vo vo) throws BaseException {
		
		SEVT004ListResult<SEVT004VoResult> result = new SEVT004ListResult<SEVT004VoResult>();
		HashMap<String, String> send = new HashMap<String, String>();
		
		int count;
		int insert;
		
		logger.debug("### Stamp 교환 ### " + vo.toString());
		
		// UUID 또는 MBR_SEQ 없음
		if( vo.uuid.equals("null") || vo.mbr_seq.equals("null") ){
			
			logger.debug("*** UUID 또는 CUST_ID 없음 ***");
			
			send.put("code", "-1");
			send.put("message", "UUID 또는 MBR_SEQ 없습니다.");
			
			return send;
			
		}
		
		// STAMP 교환 확인
		count = sevt004Mapper.stampChangeCheck(vo);
		
		logger.debug("*** STAMP 교환 확인 [ " + count + " ] ***");
		
		if( count != 0 ){
			
			send.put("code", "1");
			send.put("message", "해당 기기 또는 해당 사용자가 STAMP를 이미 교환하였습니다.");
			
			return send;
			
		}
		
		// STAMP 조회 ( ALBS004_D )
		result.list.addAll(sevt004Mapper.stampEventApp(vo));
		
		logger.debug("*** STAMP 조회 [ " + result.list.size() + " ] ***");
		
		if( result.list.size() < 5 ){
			
			logger.debug("*** STAMP 갯수 부족 ***");
			
			send.put("code", "-1");
			send.put("message", "STAMP가 부족합니다.");
			
			return send;
			
		}
		
		// STAMP 교환 완료 ( SEVT004_D )
		if( result.list.size() >= 5 && count == 0 ){
			
			insert = sevt004Mapper.insertStamp(vo);
			
			logger.debug("*** STAMP 교환 완료 [ " + insert + " ] ***");
			
			if( insert != 1 ){
				
				send.put("code", "-1");
				send.put("message", "STAMP INSERT ERROR");
				
				return send;
				
			}
			
			send.put("code", "0");
			send.put("message", "STAMP 교환안함");
			
		}
		
		return send;
		
	}
	
}
