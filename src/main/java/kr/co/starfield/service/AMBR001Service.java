package kr.co.starfield.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.helper.AwsSnsRelayHelper;
import kr.co.starfield.mapper.AMBR001Mapper;
import kr.co.starfield.model.BannerFilter;
import kr.co.starfield.model.BannerGroup;
import kr.co.starfield.model.JoinStats;
import kr.co.starfield.model.JoinStatsFilter;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Member;
import kr.co.starfield.model.MemberDevice;
import kr.co.starfield.model.ModifyMemberDeviceResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.TargetArnInfo;
import kr.co.starfield.model.vo.SMBR001Vo;
import kr.co.starfield.model.vo.SMBR005Vo;

@Service
public class AMBR001Service {
	
	private Logger ll = LoggerFactory.getLogger(AMBR001Service.class);
	
	@Autowired
	AwsSnsRelayHelper awsSnsRelayHelper;
	
	@Autowired
	AMBR001Mapper ambr001Mapper;

	/**
	 * 사용자 디바이스검색
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	public MemberDevice getMemberDevice(SMBR005Vo vo) throws BaseException {
		
		SMBR005Vo memberDeviceVo = ambr001Mapper.getMemberDevice(vo);
		if(memberDeviceVo == null){
			BaseException be = new BaseException(ErrorCode.Member.MEMBER_DEVICE_NOT_FOUND_DATA);
			throw be;
		}
		return memberDeviceVo.toModel();
	}
	
	/**
	 * 사용자 디바이스 추가
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regMemberDevice(SMBR005Vo vo) throws BaseException {
		SimpleResult result = new SimpleResult();

		//중복 체크
		SMBR005Vo oldVo = ambr001Mapper.getMemberDevice(vo);
		
		if(oldVo != null) {
			BaseException be = new BaseException(ErrorCode.Member.MEMBER_DEVICE_DUPLICATE_KEY);
			throw be;
		}

		//TODO 첫번째 로그인 체크
		if(vo.push_token != null) {
			//Device ID 생성 로직 변경에 따른 예외 처리
			SMBR005Vo tempVo = new SMBR005Vo();
			tempVo.push_token = vo.push_token;
			int duplCnt = ambr001Mapper.deleteMemberDevice(tempVo);
			ll.debug("중복된 PUSH 토큰 개수 :"+duplCnt);
			
			TargetArnInfo targetArnInfo = awsSnsRelayHelper.createEndpoint(vo);
			vo.end_arn = targetArnInfo.end_arn;
			vo.glob_arn = targetArnInfo.glob_arn;
		}
		
		ambr001Mapper.regMemberDevice(vo);

		return result;
	} 

	@Transactional(rollbackFor = Exception.class)
	public ModifyMemberDeviceResult modifyMemberDevice(SMBR005Vo newVo) throws BaseException {
		ModifyMemberDeviceResult result = new ModifyMemberDeviceResult();
		
		SMBR005Vo oldVo = ambr001Mapper.getMemberDevice(newVo);
		
		if(oldVo == null) {
			BaseException be = new BaseException(ErrorCode.Member.MEMBER_DEVICE_NOT_FOUND_DATA);
			throw be;
		}
		
//		boolean isFirstLogin = false;
		
		// 다비오 요청으로 isFirstLogin 고정
//		if(newVo.mbr_seq != null) {
//			int cntMbrSeq = ambr001Mapper.countMemberDeviceByMbrSeq(newVo.mbr_seq);
//			ll.debug("inserted Mbrseq count = {}", cntMbrSeq);
//			isFirstLogin = cntMbrSeq <= 0;
//		}
//		result.isFirstLogin = isFirstLogin;		
//		ll.debug("{} is FirstLogin ", isFirstLogin);
		
		boolean isPushTokenChange = false;
		boolean isAlramYnChange = false;

		if(newVo.push_token != null && !newVo.push_token.equals(oldVo.push_token)) {	
			isPushTokenChange = true;
		}
		
		if(newVo.alram_yn != null && !newVo.alram_yn.equals(oldVo.alram_yn)) {
			isAlramYnChange = true;
		}
		
		TargetArnInfo targetArnInfo = new TargetArnInfo();
		
		// 생성
		if(oldVo.end_arn == null && newVo.push_token != null) {
			targetArnInfo = awsSnsRelayHelper.createEndpoint(newVo, oldVo);
		} else if(oldVo.end_arn != null){
			targetArnInfo = awsSnsRelayHelper.updateEndpoint(newVo, oldVo, isPushTokenChange, isAlramYnChange);
		}

		newVo.end_arn = targetArnInfo.end_arn;
		newVo.glob_arn = targetArnInfo.glob_arn;
		
		ambr001Mapper.modifyMemberDevice(newVo);
		

		return result;
	}
	
	//디바이스 이용약
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyMemberDeviceAgreement(SMBR005Vo vo) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		ambr001Mapper.modifyMemberDeviceAgreement(vo);
		
		return result;
	}
	

	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteMemberDevice(SMBR005Vo vo) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		int deleteCnt = ambr001Mapper.deleteMemberDevice(vo);
		
		if(deleteCnt < 1) {
			BaseException be = new BaseException(ErrorCode.Member.MEMBER_DEVICE_NOT_FOUND_DATA);
			throw be;
		}
		
		// TODO 글로벌 해제

		return result;
	}
	
	/**
	 * 회원 조회
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	public Member getMember(SMBR001Vo vo) throws BaseException {
		
		SMBR001Vo memberVo = ambr001Mapper.getMember(vo);
		if(memberVo == null){
			BaseException be = new BaseException(ErrorCode.Member.MEMBER_NOT_FOUND_DATA);
			throw be;
		}
		return memberVo.toModel();
	}
	
	/**
	 * 회원 등록
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regMember(SMBR001Vo vo) throws BaseException {
		SimpleResult result = new SimpleResult();

		SMBR001Vo memberVo = ambr001Mapper.getMember(vo);
		
		if(memberVo == null){
			ambr001Mapper.regMember(vo);	
			result.extra = vo.mbr_seq;
		} else {
			throw new BaseException(ErrorCode.Member.MEMBER_DUPLICATE_REG);
		}
		
		return result;
	}
	
	/**
	 * 회원 수정
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyMember(SMBR001Vo vo) throws BaseException {
		SimpleResult result = new SimpleResult();

		ambr001Mapper.modifyMember(vo);			

		result.extra = vo.mbr_seq;
		return result;
	}
	
	/**
	 * 회원 탈퇴
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteMember(SMBR001Vo vo) throws BaseException {
		SimpleResult result = new SimpleResult();

		ambr001Mapper.deleteMember(vo);			

		result.extra = vo.mbr_seq;
		return result;
	}
	
	
	
	/**
	 * 회원가입 통계
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public ArrayList<JoinStats> getJoinDailyStats(JoinStatsFilter filter) throws BaseException {
		return ambr001Mapper.getJoinDailyStats(filter);
		
	}
}
