package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.OldAppMainTopWhatsNew;
import kr.co.starfield.model.WhatsNew;
import kr.co.starfield.model.WhatsNewDelete;
import kr.co.starfield.model.WhatsNewFilter;
import kr.co.starfield.model.WhatsNewGroup;
import kr.co.starfield.model.WhatsNewGroupDelete;
import kr.co.starfield.model.WhatsNewRedis;

/**
 *  ACMS011Mapper(WhatsNew) interface
 *  
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.09.07
 */

public interface ACMS011Mapper {

	// 왓츠뉴 그룹 등록
	public void regWhatsNewGroup(WhatsNewGroup whatsNewGroup);
	
	//왓츠뉴 그룹 수정
	public void modifyWhatsNewGroup(WhatsNewGroup whatsNewGroup);
	
	//왓츠뉴 그룹 삭제
	public void deleteWhatsNewGroup(WhatsNewGroupDelete whatsNewGroupDelete);
	
	// 왓츠뉴 그룹 리스트
	public ArrayList<WhatsNewGroup> getWhatsNewGroupList(WhatsNewFilter filter);
	
	// 왓츠뉴 그룹 리스트 카운트
	public int getTotalCount(WhatsNewFilter filter);
	
	// 왓츠뉴 그룹 상세 조회
	public WhatsNewGroup getWhatsNewGroup(WhatsNewFilter filter);
	
	// 왓츠뉴 리스트
	public ArrayList<WhatsNew> getWhatsNewList(WhatsNewFilter filter);
	
	// 왓츠뉴 조회
	public WhatsNew getWhatsNew(WhatsNewFilter filter);
	
	// 왓츠뉴 등록
	public void regWhatsNew(WhatsNew whatsNew);
	
	// 왓츠뉴 수정
	public void modifyWhatsNew(WhatsNew whatsNew);
	
	// 왓츠뉴 삭제
	public void deleteWhatsNew(WhatsNewDelete whatsNewDelete);
	
	// 왓츠뉴 그룹 레디스 조회
	public ArrayList<WhatsNewRedis> getWhatsNewWebList(WhatsNewFilter filter);
	
	// 왓츠뉴 그룹 레디스 조회
	public ArrayList<WhatsNewRedis> getWhatsNewAppList(WhatsNewFilter filter);
	
	// 모바일 왓츠뉴 레디스 조회
	public ArrayList<OldAppMainTopWhatsNew> getWhatsNewOldList(WhatsNewFilter filter);
}
