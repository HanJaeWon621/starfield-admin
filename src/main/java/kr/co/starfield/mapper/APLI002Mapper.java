package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.LocInfoManage;
import kr.co.starfield.model.LocInfoManage2;
import kr.co.starfield.model.LocInfoManageExcel;
import kr.co.starfield.model.LocInfoManageFilter;

public interface APLI002Mapper {

	// LocInfoManage 목록 조회
	public ArrayList<LocInfoManage> getLocInfoManageList(LocInfoManage filter);

	// LocInfoManage 수정
	public void modifyLocInfoManage(LocInfoManage model);

	// 개인 위치 정보 삭제
	public void deletePersonLocInfo(LocInfoManage model);

}
