package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.SEVT004Vo;
import kr.co.starfield.model.SEVT004VoResult;

/**
 *  SEVT004Mapper (stamp_event) Interface
 *
 * @author 
 * @version	1.0,
 * @see
 * @date 2016.11.21
 */

public interface SEVT004Mapper {
	
	// STAMP UUID로 조회 ( APP )
	public ArrayList<SEVT004VoResult> stampEventApp(SEVT004Vo vo);
	
	// SATMP 교환 COUNT
	public String getGiftCount(SEVT004Vo vo);
	
	// STAMP 교환 확인
	public int stampChangeCheck(SEVT004Vo vo);
	
	// STAMP 받음 ( INSERT SEVT004_D )
	public int insertStamp(SEVT004Vo vo);
	
}
