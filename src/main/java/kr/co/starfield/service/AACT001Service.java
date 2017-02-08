package kr.co.starfield.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.CommonCode;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.Utils;
import kr.co.starfield.mapper.AACT001Mapper;
import kr.co.starfield.model.AdminActionLog;
import kr.co.starfield.model.AdminActionLogFilter;
import kr.co.starfield.model.AdminFilter;
import kr.co.starfield.model.AdminRoleCheckFilter;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.LoginResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SADM001;
import kr.co.starfield.model.SADM003;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SADM001Vo;
import kr.co.starfield.model.vo.SADM004Vo;

/**
 * AACT001 관리자 계정 서비스
 *
 * Copyright Copyright (c) 2016
 *
 * @author jg.jo
 * @version	1.0,
 * @see
 * @date 2016.10.05
 */

@Service
public class AACT001Service {
	
	@Autowired
	private AATH001Service aath001Service;

	@Autowired
	private AACT001Mapper aact001mapper;
	
	private static final Logger ll = LoggerFactory.getLogger(AACT001Service.class);

	/**
	 * 관리자 로그인 체크
	 *
	 * @param param
	 * @param session
	 * @return
	 */
	public LoginResult loginCheck(SADM001 param, HttpSession session) {
		
		LoginResult result = new LoginResult();
		
		SADM001Vo vo = aact001mapper.getAdmin(param);
		
		// 관리자 자체가 없을 경우 102코드 정보 없음을 반환
		if(vo == null || vo.equals(null)) {
			
			result.code = Integer.parseInt(CommonCode.AdminLogin.NOT_FOUND.getCodeCd());
			result.desc = CommonCode.AdminLogin.NOT_FOUND.getCodeNm();
			
			return result;
		}
		
		ll.info("session info is {}", session);
		
		session.setAttribute("adm_seq", vo.adm_seq);
		session.setAttribute("bcn_cd", vo.bcn_cd);
		session.setAttribute("role_nm", vo.role_nm);
		session.setAttribute("adm_id", vo.adm_id);
		session.setAttribute("adm_dept", vo.adm_dept);
		session.setAttribute("adm_rank", vo.adm_rank);
		session.setAttribute("adm_nm", vo.adm_nm);
		session.setAttribute("sts", vo.sts);
		
		/**
		 * 패스워드 미일치시
		 */
		if(vo.sts == 105) {
			
			int loginFailCnt = 1;
			try {
				
				Object tempFailCnt = session.getAttribute("adm_log_in_fail_cnt");
				
				if(tempFailCnt == null) {
					session.setAttribute("adm_log_in_fail_cnt", 1);
				} else {
					loginFailCnt = (Integer) tempFailCnt + 1;
					session.setAttribute("adm_log_in_fail_cnt", loginFailCnt);
				}
				
				// 3회이상 패스워드 미일치시
				if(loginFailCnt >= 3) {
					
					aact001mapper.lockAdmAccountFailPass(vo);
					
					result.code = Integer.parseInt(CommonCode.AdminLogin.ACCOUNT_LOCK_PASS_FAIL.getCodeCd());
					result.desc = CommonCode.AdminLogin.ACCOUNT_LOCK_PASS_FAIL.getCodeNm();
					
					return result;
				}
				
			} catch(Exception e) {
				session.setAttribute("adm_log_in_fail_cnt", 1);
			}
		}
	
		
		switch(vo.sts) {
		
			case 103:
			
				// 계정 사용중지
				result.code = Integer.parseInt(CommonCode.AdminLogin.ACCOUNT_LOCK.getCodeCd());
				result.desc = CommonCode.AdminLogin.ACCOUNT_LOCK.getCodeNm();
				break;
		
			case 104:
				
				// 계정 잠김
				result.code = Integer.parseInt(CommonCode.AdminLogin.ACCOUNT_LOCK_PASS_FAIL.getCodeCd());
				result.desc = CommonCode.AdminLogin.ACCOUNT_LOCK_PASS_FAIL.getCodeNm();
				break;
			
			case 105:
				
				// 패스워드 미일치
				result.code = Integer.parseInt(CommonCode.AdminLogin.MATCH_FAIL_PASS.getCodeCd());
				result.desc = CommonCode.AdminLogin.MATCH_FAIL_PASS.getCodeNm();
				break;
				
			case 106:
				
				// 패스워드 미변경 3개월 이상
				result.code = Integer.parseInt(CommonCode.AdminLogin.OLD_ACCOUNT_PASS.getCodeCd());
				result.desc = CommonCode.AdminLogin.OLD_ACCOUNT_PASS.getCodeNm();
				break;
				
			case 107:
				
				// 관리자 계정 미활성화
				result.code = Integer.parseInt(CommonCode.AdminLogin.STAT_STAND_BY.getCodeCd());
				result.desc = CommonCode.AdminLogin.STAT_STAND_BY.getCodeNm();
				break;
				
			default:
				result.code = Integer.parseInt(CommonCode.AdminLogin.SUCESS.getCodeCd());
				result.desc = CommonCode.AdminLogin.SUCESS.getCodeNm();
				session.setAttribute("adm_log_in_fail_cnt", 0);
				
				// 권한 타입 정보 리스트를 세션에 담아 둔다.
				SADM003[] authTypeArr = aath001Service.getAdminAuthTypes(vo.adm_seq);
				session.setAttribute("authTypeArr", authTypeArr);
				
		}
		
		return result;
	}

	/**
	 * 관리자 패스워드 변경
	 * @param param
	 * @return
	 * @throws BaseException 
	 */
	public SimpleResult changePassword(SADM001 param, HttpSession session) throws BaseException {

		SimpleResult result = new SimpleResult();
		
		param.adm_seq = (String) session.getAttribute("adm_seq");
		
		if(Utils.Str.isEmpty(param.adm_seq)) {
			ll.debug("session admin seq is empty");
			throw new BaseException(ErrorCode.Auth.ADM_SESSION_UNUSUAL);
		}
		
		// 패스워드 체크
		String passChkCd = aact001mapper.canIChangePassword(param);
		
		if(Utils.Str.isEmpty(passChkCd)) {
			
			ll.info("admin info is empty adminInfo is {}", param);
			result.code = Integer.parseInt(CommonCode.AdminChangePass.NOT_FOUND.getCodeCd());
			result.desc = CommonCode.AdminChangePass.NOT_FOUND.getCodeNm();
			
			return result;
		}
		
		switch(passChkCd) {
			case "103":
				result.code = Integer.parseInt(CommonCode.AdminChangePass.MATCH_FAIL_ORIGIN_PASS.getCodeCd());
				result.desc = CommonCode.AdminChangePass.MATCH_FAIL_ORIGIN_PASS.getCodeNm();
				return result;
			case "104":
				result.code = Integer.parseInt(CommonCode.AdminChangePass.SAME_PRE_PASSWORD.getCodeCd());
				result.desc = CommonCode.AdminChangePass.SAME_PRE_PASSWORD.getCodeNm();
				return result;
			case "105":
				result.code = Integer.parseInt(CommonCode.AdminChangePass.SAME_THREE_MONTHS_PRE_PASSWORD.getCodeCd());
				result.desc = CommonCode.AdminChangePass.SAME_THREE_MONTHS_PRE_PASSWORD.getCodeNm();
				return result;
		}
		
		
		if(passChkCd.equals("101")) {
			
			_changePassword(param);
			
			result.code = Integer.parseInt(CommonCode.AdminChangePass.SUCESS.getCodeCd());
			result.desc = CommonCode.AdminChangePass.SUCESS.getCodeNm();
		}
		
		return result;
	}
	
	/**
	 * 관리자 패스워드 재생성
	 * @param param
	 * @param session
	 * @return
	 * @throws BaseException 
	 */
	@Transactional
	public SimpleResult resetPassword(SADM001 param, HttpSession session) throws BaseException {

		SimpleResult result = new SimpleResult();
		
		param.mod_usr = (String) session.getAttribute("adm_seq");
		
		if(Utils.Str.isEmpty(param.mod_usr)) {
			ll.debug("session admin seq is empty");
			throw new BaseException(ErrorCode.Auth.ADM_SESSION_UNUSUAL);
		}
		
		String newPassword = "";
		
		do {
			
			newPassword = this.makeTempPassword().desc;
			param.adm_new_pw = newPassword;
		 
		} while ( aact001mapper.resetPasswordCompare(param) != 0 );
		
		aact001mapper.resetPassword(param);
		aact001mapper.insertSADM001_D(param);
		
		result.code = CommonCode.Common.SUCCESS.getCode();
		result.desc = newPassword;
		
		return result;
	}
	
	
	
	/**
	 * 관리자 정보 가져오기
	 * @param session
	 * @return
	 * @throws BaseException 
	 */
	public SADM001 getAdminInfo(HttpSession session) throws BaseException {

		String adm_seq = (String) session.getAttribute("adm_seq");
		
		if(Utils.Str.isEmpty(adm_seq)) {
			ll.debug("session admin seq is empty");
			throw new BaseException(ErrorCode.Auth.ADM_SESSION_UNUSUAL);
		}
		
		SADM001 samd001 = aact001mapper.getAdminInfo(adm_seq);
		
		switch (samd001.sts) {
			case 101:
				samd001.sts_desc = "사용중";
			break;
			case 102:
				samd001.sts_desc = "사용중지";
			break;
			case 103:
				samd001.sts_desc = "비번 3회 오류";
			break;
			case 104:
				samd001.sts_desc = "비번 미변경(3개월)";
			break;
			case 105:
				samd001.sts_desc = "등록";
			break;
		}
		
		return samd001;
	}
	
	/**
	 * 관리자 정보 가져오기
	 * @param adm_seq
	 * @return
	 * @throws BaseException 
	 */
	public SADM001 getAdminInfo(String adm_seq) throws BaseException {
		
		if(Utils.Str.isEmpty(adm_seq)) {
			ll.debug("getAdminInfo adm_seq is empty");
			throw new BaseException(ErrorCode.Common.MUST_REQUIRE_PARAMETER_EMPTY);
		}
		
		SADM001 samd001 = aact001mapper.getAdminInfo(adm_seq);
		
		switch (samd001.sts) {
			case 101:
				samd001.sts_desc = "사용중";
			break;
			case 102:
				samd001.sts_desc = "사용중지";
			break;
			case 103:
				samd001.sts_desc = "비번 3회 오류";
			break;
			case 104:
				samd001.sts_desc = "비번 미변경(3개월)";
			break;
			case 105:
				samd001.sts_desc = "등록";
			break;
		}
		
		return samd001;
	}
	
	/**
	 * 관리자 정보 수정하기
	 * @param param
	 * @param session
	 * @return
	 * @throws BaseException 
	 */
	public SimpleResult changeAdminInfo(SADM001 param, HttpSession session) throws BaseException {
		
		String adm_seq = (String) session.getAttribute("adm_seq");
		
		if(Utils.Str.isEmpty(adm_seq)) {
			ll.debug("session admin seq is empty");
			throw new BaseException(ErrorCode.Auth.ADM_SESSION_UNUSUAL);
		}
		
		param.adm_seq = adm_seq;
		
		SimpleResult result = new SimpleResult();
		
		_changeAdminInfo(param);
		
		result.code = CommonCode.Common.SUCCESS.getCode();
		result.desc = CommonCode.Common.SUCCESS.getMessage();
		
		SADM001 sadm001 = this.getAdminInfo(session);
		
		session.setAttribute("adm_seq", sadm001.adm_seq);
		session.setAttribute("adm_id", sadm001.adm_id);
		session.setAttribute("bcn_cd", sadm001.bcn_cd);
		session.setAttribute("role_nm", sadm001.role_nm);
		session.setAttribute("adm_dept", sadm001.adm_dept);
		session.setAttribute("adm_rank", sadm001.adm_rank);
		session.setAttribute("adm_nm", sadm001.adm_nm);
		session.setAttribute("sts", sadm001.sts);
		
		return result;
	}
	
	/**
	 * 관리자 아이디 중복 체크
	 * @param adm_id
	 * @return
	 */
	public SimpleResult overlapCheckAdminId(String adm_id) {

		SimpleResult result = new SimpleResult();
		
		int has_adm_cd = aact001mapper.overlapCheckAdminId(adm_id);
		
		ll.info(" check admin id is {}", adm_id);
		ll.info(" has admin id ?? {}", has_adm_cd);
		
		switch(has_adm_cd) {
			case 101:
				// 이미 아이디 존재
				result.code = CommonCode.Common.FAIL.getCode();
				result.desc = CommonCode.Common.FAIL.getMessage();
				break;
			case 102:
				// 아이디 사용 가능
				result.code = CommonCode.Common.SUCCESS.getCode();
				result.desc = CommonCode.Common.SUCCESS.getMessage();
				break;
			default:
				result.code = CommonCode.Common.FAIL.getCode();
				result.desc = CommonCode.Common.FAIL.getMessage();
		}
		
		return result;
	}
	
	/**
	 * 임시 관리자 비밀번호 생성
	 * @return
	 */
	public SimpleResult makeTempPassword() {

		SimpleResult result = new SimpleResult();
		
		String pwd = null;
		
		StringBuffer sb = new StringBuffer();
		StringBuffer sc = new StringBuffer("!@#$%^&*-=?~");  // 특수문자 모음, {}[] 같은 비호감문자는 뺌
		
		// 대문자 4개를 임의 발생
		sb.append((char)((Math.random() * 26) + 65));
		
		for( int i = 0; i < 3; i++) {
		   sb.append((char)((Math.random() * 26) + 65));  // 아스키번호 65(A) 부터 26글자 중에서 택일
		} 

		// 소문자 4개를 임의발생
		for( int i = 0; i < 4; i++) {
		    sb.append((char)((Math.random() * 26) + 97)); // 아스키번호 97(a) 부터 26글자 중에서 택일
		}  

		// 숫자 2개를 임의 발생
		for( int i = 0; i < 2; i++) {
		    sb.append((char)((Math.random() * 10) + 48)); //아스키번호 48(1) 부터 10글자 중에서 택일
		}

		// 특수문자를 두개 발생시켜 랜덤하게 중간에 끼워 넣는다 
		sb.setCharAt(((int)(Math.random() * 3) + 1), sc.charAt((int)(Math.random() * sc.length()-1))); //대문자3개중 하나   
		sb.setCharAt(((int)(Math.random() * 4) + 4), sc.charAt((int)(Math.random() * sc.length()-1))); //소문자4개중 하나

		pwd = sb.toString();
		
		ll.info("new temp password is {}", pwd);
		
		result.code = CommonCode.Common.SUCCESS.getCode();
		result.desc = pwd;
		
		return result;
	}
	
	
	/**
	 * 관리자 수행업무 로그기록
	 * @param req
	 * @param session
	 * @param accPefm 수행업무 상세
	 * @param accPefmDtl 수행업무 상세
	 */

	public void regAdminAction(HttpServletRequest req, HttpSession session, String accPefmDtl, String infoUsrId) {
		
		SADM004Vo vo = new SADM004Vo();
		
		String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
        	ip = req.getRemoteAddr();
        }
        
        String adm_seq = (String)session.getAttribute("adm_seq");
        
        if(adm_seq != null) {
        	
        	vo.acc_id = adm_seq;
    		vo.acc_ip = ip;
    		vo.acc_pefm = req.getMethod();
    		vo.info_usr_id = infoUsrId;	
    		vo.acc_pefm_dtl = accPefmDtl; 
    		vo.req_uri = req.getRequestURI();
    		
    		_regAdminAction(vo);
        }
	}
	
	/**
	 * 관리자 생성
	 * @param param
	 * @return
	 */
	public SimpleResult regAccount(SADM001 param) {

		SimpleResult result = new SimpleResult();
		
		_regAccount(param.toVo());
		
		param.adm_new_pw = param.adm_pw;
		aact001mapper.insertSADM001_D(param); // 3개월 패스워드 히스토리
		
		result.code = CommonCode.Common.SUCCESS.getCode();
		result.desc = CommonCode.Common.SUCCESS.getMessage();
		
		return result;
	}
	
	/**
	 * 관리자 계정 수정
	 * @param param
	 * @return
	 */
	public SimpleResult modAccount(SADM001 param) {

		SimpleResult result = new SimpleResult();
		
		_modAccount(param.toVo());
		
		result.code = CommonCode.Common.SUCCESS.getCode();
		result.desc = CommonCode.Common.SUCCESS.getMessage();
		
		return result;
	}

	/**
	 * 관리자 액션 로그 목록 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	
	public ListResult<AdminActionLog> getAdminActionLogList(AdminActionLogFilter filter, boolean masking) throws BaseException {
		
		ListResult<AdminActionLog> result = new ListResult<AdminActionLog>();
			
		result.list.addAll(aact001mapper.getAdminActionLogList(filter));
			
		
		if(masking) {
			for(AdminActionLog adminActionLog : result.list) {
				adminActionLog.adm_nm = Utils.Str.maskingName(adminActionLog.adm_nm);
			}
		}
		
		
		if(filter.limit > 0){
			int tot_cnt = aact001mapper.getAdminActionLogTotalCount(filter);
	
			Paging paging = new Paging(filter.offset, filter.limit, tot_cnt);
				
			result.paging = paging;
		}
		
		return result;
	}
	
	/**
	 * 관리자 목록을 가져온다.
	 * @param filter
	 * @return
	 */
	public SADM001[] getAdmins(AdminFilter filter) {

		SADM001Vo[] voList = aact001mapper.getAdmins(filter);
		
		SADM001[] admins = new SADM001[voList.length];
		
		for(int i = 0; i < voList.length; i++) {
			admins[i] = voList[i].toModel();
			
			switch (admins[i].sts) {
				case 101:
					admins[i].sts_desc = "사용중";
				break;
				case 102:
					admins[i].sts_desc = "사용중지";
				break;
				case 103:
					admins[i].sts_desc = "비번 3회 오류";
				break;
				case 104:
					admins[i].sts_desc = "비번 미변경(3개월)";
				break;
				case 105:
					admins[i].sts_desc = "등록";
				break;
			}
		}
		
		return admins;
	}
	

	/**
	 * 내 권한으로 수정 대상 정보를 수정 가능 한지 여부 성공은 101
	 * 
	 * @param filter
	 * @return
	 */
	public int canIChangeOtherAccount(AdminRoleCheckFilter filter) {
		
		int canIDoThat = aact001mapper.canIChangeOtherAccount(filter);
		
		return canIDoThat;
	}
	
	@Transactional
	public void _regAccount(SADM001Vo vo) {
		aact001mapper.regAccount(vo);
	}
	
	@Transactional
	public void _modAccount(SADM001Vo vo) {
		aact001mapper.modAccount(vo);
	}
	
	@Transactional
	public void _regAdminAction(SADM004Vo vo) {
		aact001mapper.regAdminAction(vo);
	}
	
	@Transactional
	public void _changePassword(SADM001 param) {
		aact001mapper.changePassword(param);
		aact001mapper.insertSADM001_D(param); // 3개월 패스워드 히스토리
	}
	
	@Transactional
	public void _changeAdminInfo(SADM001 param) {
		aact001mapper.changeAdminInfo(param);
	}

}
