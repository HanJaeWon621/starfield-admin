package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.JoinStats;
import kr.co.starfield.model.JoinStatsFilter;
import kr.co.starfield.model.SCPN001;
import kr.co.starfield.model.vo.SMBR001Vo;
import kr.co.starfield.model.vo.SMBR005Vo;

/**
 *  AMBR001Mapper
 *
 * @author dhlee
 * @version	1.0,
 * @see
 * @date 2016.04.14
 */

public interface AMBR001Mapper {

	SMBR005Vo getMemberDevice(SMBR005Vo vo);
	
//	List<SMBR005Vo> getSyncNeedMemberDevices();
	
	/* isFirstLogin check */
	int countMemberDeviceByMbrSeq(String mbr_seq);

	void regMemberDevice(SMBR005Vo vo);
	
	int modifyMemberDevice(SMBR005Vo vo);
	
	int modifyMemberDeviceAgreement(SMBR005Vo vo);
	
	int deleteMemberDevice(SMBR005Vo vo);
	
	SMBR001Vo getMember(SMBR001Vo vo);
	
	void regMember(SMBR001Vo vo);
	
	int modifyMember(SMBR001Vo vo);
	
	void deleteMember(SMBR001Vo vo);
	
	ArrayList<JoinStats> getJoinDailyStats(JoinStatsFilter filter);

}
