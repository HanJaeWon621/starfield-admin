package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.AdminActionLog;
import kr.co.starfield.model.AdminActionLogFilter;
import kr.co.starfield.model.AdminFilter;
import kr.co.starfield.model.AdminRoleCheckFilter;
import kr.co.starfield.model.SADM001;
import kr.co.starfield.model.vo.SADM001Vo;
import kr.co.starfield.model.vo.SADM004Vo;

public interface AACT001Mapper {

	SADM001Vo getAdmin(SADM001 param);

	void lockAdmAccountFailPass(SADM001Vo vo);

	String canIChangePassword(SADM001 param);

	void changePassword(SADM001 param);
	
	// 3개월 패스워드 히스토리
	void insertSADM001_D(SADM001 param);

	SADM001 getAdminInfo(String adm_seq);

	void changeAdminInfo(SADM001 param);

	int overlapCheckAdminId(String adm_id);

	void regAdminAction(SADM004Vo vo);

	// 계정 생성
	void regAccount(SADM001Vo vo);
	
	// 다른 관리자 계정 수정
	void modAccount(SADM001Vo vo);


	// 관리자 액션 로그 조회
	ArrayList<AdminActionLog> getAdminActionLogList(AdminActionLogFilter filter);
	
	// 관리자 액션 로그 COUNT
	int getAdminActionLogTotalCount(AdminActionLogFilter filter);

	// 관리자 목록
	SADM001Vo[] getAdmins(AdminFilter filter);

	// 관리자 목록 총 갯수
	int getAdminsTotalCount(AdminFilter filter);

	// 내 계정으로 다른 관리자 계정 수정 가능한지 여부 확인
	int canIChangeOtherAccount(AdminRoleCheckFilter filter);

	// 다른 관리자 비밀번호 초기화
	void resetPassword(SADM001 param);
	
	// 초기화 패스워드와 3개월간 패스워드 비교
	int resetPasswordCompare(SADM001 param);
}
