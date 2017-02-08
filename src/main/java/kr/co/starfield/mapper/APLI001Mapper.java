package kr.co.starfield.mapper;

import java.util.ArrayList;
import java.util.List;

import kr.co.starfield.model.LocInfoManage;
import kr.co.starfield.model.LocInfoManageExcel;
import kr.co.starfield.model.LocInfoManageFilter;
import kr.co.starfield.model.SAML007;

public interface APLI001Mapper {

	// LocInfoManage 목록 조회
	public ArrayList<LocInfoManage> getLocInfoManageList(LocInfoManageFilter filter);
	
	// LocInfoManage 카운트
	public int getTotalCount(LocInfoManageFilter filter);
	
	// LocInfoManage 상세 조회
	public LocInfoManage getLocInfoManage(LocInfoManageFilter filter);
	
	// LocInfoManage 등록
	public void regLocInfoManage(LocInfoManage model);
		
	// LocInfoManage 수정
	public void modifyLocInfoManage(LocInfoManage model);
	
	// LocInfoManage Excel목록 조회
	public ArrayList<LocInfoManageExcel> getLocInfoManageExcelList(LocInfoManageFilter filter);


}
