package kr.co.starfield.rest.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.Crypto;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.JoinStats;
import kr.co.starfield.model.JoinStatsFilter;
import kr.co.starfield.model.Member;
import kr.co.starfield.model.MemberDevice;
import kr.co.starfield.model.MemberWeb;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SMBR001Vo;
import kr.co.starfield.service.AMBR001Service;

/**
 *  맴버 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author yhkim
 * @version 1.0,
 * @see
 * @date 2016.06.14
 */

@RestController
@RequestMapping("/rest")
public class AMBR001RestController extends BaseController {

private Logger ll = LoggerFactory.getLogger(ACTG001RestController.class);
	
	@Autowired
	AMBR001Service ambr001Service;
	
	/**
	 * 사용자 디바이스 정보
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/members/{mbrSeq}/devices/{dvicId}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
	)
	public MemberDevice getMemberDevice(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbrSeq") String mbrSeq
			, @PathVariable(value="bcnCd") String bcnCd
			, @PathVariable(value="dvicId") String dvicId) throws Exception {
		this.initHandler(req, res);
		
		if(Utils.Str.isEmpty(dvicId)) throw new BaseException(ErrorCode.Member.MEMBER_DEVICE_ID_INVALID_DATA);
		
		MemberDevice memberDevice = new MemberDevice();
		memberDevice.mbr_seq = mbrSeq;
		memberDevice.dvic_id = dvicId;
		
		return ambr001Service.getMemberDevice(memberDevice.toVo());
		
	}
	
	/**
	 * 사용자 디바이스 등록
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/members/{mbrSeq}/devices"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
	)
	public SimpleResult regMemberDevice(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbrSeq") String mbrSeq
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestBody MemberDevice memberDevice ) throws Exception {
		this.initHandler(req, res);
		
		ll.info("register target device : " + memberDevice);
		if(Utils.Str.isEmpty(memberDevice.dvic_id)) throw new BaseException(ErrorCode.Member.MEMBER_DEVICE_ID_INVALID_DATA);
		if(memberDevice.pltf_type == 0) throw new BaseException(ErrorCode.Member.MEMBER_DEVICE_ID_INVALID_DATA);
		
		if(!mbrSeq.equals("any")){
			memberDevice.mbr_seq = mbrSeq;
		}
		memberDevice.sts = StatusCode.Common.USE.getCode();
		
		return ambr001Service.regMemberDevice(memberDevice.toVo());
		
	}
	
	/**
	 * 사용자 디바이스 수정
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/members/{mbrSeq}/devices/{dvicId}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
	)
	public SimpleResult modifyMemberDevice(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbrSeq") String mbrSeq
			, @PathVariable(value="bcnCd") String bcnCd
			, @PathVariable(value="dvicId") String dvicId
			, @RequestBody MemberDevice memberDevice ) throws Exception {
		this.initHandler(req, res);
		
		if(Utils.Str.isEmpty(dvicId)) throw new BaseException(ErrorCode.Member.MEMBER_DEVICE_ID_INVALID_DATA);
		
		if(!mbrSeq.equals("any")){
			memberDevice.mbr_seq = mbrSeq;
		}
		memberDevice.dvic_id = dvicId;
		memberDevice.sts = StatusCode.Common.USE.getCode();
		
		return ambr001Service.modifyMemberDevice(memberDevice.toVo());
		
	}
	
	/**
	 * 위치 정보 이용약관 등의
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/members/{mbrSeq}/devices/{dvicId}/agreement"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
	)
	public SimpleResult modifyMemberDeviceLocInfo(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbrSeq") String mbrSeq
			, @PathVariable(value="bcnCd") String bcnCd
			, @PathVariable(value="dvicId") String dvicId
			, @RequestBody MemberDevice memberDevice) throws Exception {
		this.initHandler(req, res);
		
		if(Utils.Str.isEmpty(dvicId)) throw new BaseException(ErrorCode.Member.MEMBER_DEVICE_ID_INVALID_DATA);
		
		
		
		if(memberDevice.app_mket_info_recep_yn == null && memberDevice.loc_info_coln_term_yn == null) {
			memberDevice.app_mket_info_recep_yn = "Y";
			memberDevice.loc_info_coln_term_yn = "Y";
		}
		
		memberDevice.dvic_id = dvicId;
		memberDevice.sts = StatusCode.Common.USE.getCode();
		
		return ambr001Service.modifyMemberDeviceAgreement(memberDevice.toVo());
	}
	
	
	
	
	/**
	 * 사용자 디바이스 삭제
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/members/{mbrSeq}/devices/{dvicId}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
	)
	public SimpleResult deleteMemberDevice(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbrSeq") String mbrSeq
			, @PathVariable(value="bcnCd") String bcnCd 
			, @PathVariable(value="dvicId") String dvicId) throws Exception {
		this.initHandler(req, res);
		
		if(Utils.Str.isEmpty(dvicId)) throw new BaseException(ErrorCode.Member.MEMBER_DEVICE_ID_INVALID_DATA);
		
		MemberDevice memberDevice = new MemberDevice();
		
		memberDevice.dvic_id = dvicId;
		memberDevice.sts = StatusCode.Common.DELETE.getCode();
		memberDevice.alram_yn = false;
		
		return ambr001Service.deleteMemberDevice(memberDevice.toVo());
		
	}
	
	/**
	 * 회원 등록
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/members"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
	)
	public SimpleResult regMember(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcnCd
			, @RequestBody MemberWeb member ) throws Exception {
		this.initHandler(req, res);
		
		member.bcn_cd = bcnCd;
		
		SMBR001Vo vo = member.toVo();
		
		Crypto crypto = new Crypto();
	    
		if(vo.mbr_nm != null){
			vo.mbr_nm = crypto.exeCrypt("1001011010010110", vo.mbr_nm, "D");
		}
		
		if(vo.stf_point_card_num != null){
			vo.stf_point_card_num = crypto.exeCrypt("1001011010010110", vo.stf_point_card_num, "D");
		}
		
		if(member.cel_num != null && !member.cel_num.equals("")){
			member.cel_num = crypto.exeCrypt("1001011010010110", member.cel_num, "D");
			
			if(member.cel_num.length() == 11) {
				vo.mbr_cel_num1 = member.cel_num.substring(0,3);
				vo.mbr_cel_num2 = member.cel_num.substring(3,7);
				vo.mbr_cel_num3 = member.cel_num.substring(7,11);
			} else if(member.cel_num.length() == 10) {
				vo.mbr_cel_num1 = member.cel_num.substring(0,3);
				vo.mbr_cel_num2 = member.cel_num.substring(3,6);
				vo.mbr_cel_num3 = member.cel_num.substring(6,10);
			} else {
				vo.mbr_cel_num1 = member.cel_num;
			}
		}
		
		//TODO reg_usr 로그인 완료시 세션 연동
		vo.reg_usr = "";
		vo.sts = StatusCode.Common.USE.getCode();
		
		return ambr001Service.regMember(vo);
	}
	
	/**
	 * 회원 수정
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/members/{mbr_seq}"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
	)
	public SimpleResult modifyMember(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbr_seq") String mbr_seq
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody MemberWeb member ) throws Exception {
		this.initHandler(req, res);
	
		ll.info("modifyMember member : {}, mbr_seq : {}", member, mbr_seq);
		
		member.bcn_cd = bcn_cd;
		member.mbr_seq = mbr_seq;
		
		
		SMBR001Vo vo = member.toVo();
		
		Crypto crypto = new Crypto();
	    
		if(vo.mbr_nm != null){
			vo.mbr_nm = crypto.exeCrypt("1001011010010110", vo.mbr_nm, "D");
		}
		
		if(vo.stf_point_card_num != null){
			vo.stf_point_card_num = crypto.exeCrypt("1001011010010110", vo.stf_point_card_num, "D");
		}
		
		
		if(member.cel_num != null && !member.cel_num.equals("")){
			member.cel_num = crypto.exeCrypt("1001011010010110", member.cel_num, "D");
			if(member.cel_num.length() == 11) {
				vo.mbr_cel_num1 = member.cel_num.substring(0,3);
				vo.mbr_cel_num2 = member.cel_num.substring(3,7);
				vo.mbr_cel_num3 = member.cel_num.substring(7,11);
			} else if(member.cel_num.length() == 10) {
				vo.mbr_cel_num1 = member.cel_num.substring(0,3);
				vo.mbr_cel_num2 = member.cel_num.substring(3,6);
				vo.mbr_cel_num3 = member.cel_num.substring(6,10);
			} else {
				vo.mbr_cel_num1 = member.cel_num;
			}
		}
		
		//TODO mod_usr 로그인 완료시 세션 연동
		vo.mod_usr = "";
		return ambr001Service.modifyMember(vo);	
	}
	
	/**
	 * 회원 조회
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/members/{mbr_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
	)
	public Member getMember(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbr_seq") String mbr_seq
			, @PathVariable(value="bcn_cd") String bcn_cd) throws Exception {
		this.initHandler(req, res);
		
		Member model = new Member();
		model.mbr_seq = mbr_seq;
		model.bcn_cd = bcn_cd;
		
		SMBR001Vo vo = model.toVo();
		vo.sts = StatusCode.Common.USE.getCode();
		
		return ambr001Service.getMember(vo);
	}
	
	/**
	 * 회원 조회 cust_id
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/members/cust_id/{cust_id}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
	)
	public Member getMemberByCust_id(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="cust_id") String cust_id
			, @PathVariable(value="bcn_cd") String bcn_cd) throws Exception {
		this.initHandler(req, res);
		
		Member model = new Member();
		model.cust_id = cust_id;
		model.bcn_cd = bcn_cd;
		
		SMBR001Vo vo = model.toVo();
		vo.sts = StatusCode.Common.USE.getCode();
		
		return ambr001Service.getMember(vo);
	}
	
	/**
	 * 회원 탈퇴
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/members/{mbr_seq}"
			, method = RequestMethod.DELETE
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
	)
	public SimpleResult deleteMember(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="mbr_seq") String mbr_seq
			, @PathVariable(value="bcn_cd") String bcn_cd) throws Exception {
		this.initHandler(req, res);
	
		ll.info("deleteMember bcn_cd : {}, mbr_seq : {}", bcn_cd, mbr_seq);
		
		
		SMBR001Vo vo = new SMBR001Vo();
		
		vo.bcn_cd = bcn_cd;
		vo.mbr_seq = mbr_seq;
		
		//TODO mod_usr 로그인 완료시 세션 연동
		vo.mod_usr = "";
		return ambr001Service.deleteMember(vo);	
	}
	
	/**
	 * 회원가입 통계
	 * @return
	 */
	@RequestMapping(value = "/{bcn_cd}/members/stats"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
	)
	public ArrayList<JoinStats> getJoinDailyStats(HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestParam(value="start_date") String start_date
 			, @RequestParam(value="end_date") String end_date
 			) throws Exception {
		this.initHandler(req, res);
		
		ll.info("getJoinDailyStats start_date : {}, end_date : {}", start_date, end_date);
		
		JoinStatsFilter filter = new JoinStatsFilter();
		
		filter.bcn_cd = bcn_cd;
		filter.start_date = start_date;
		filter.end_date = end_date;
		
		
		return ambr001Service.getJoinDailyStats(filter);
		
	}
}

