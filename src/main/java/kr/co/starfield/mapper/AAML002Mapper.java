/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.List;

import kr.co.starfield.model.AAML002;
import kr.co.starfield.model.AAML003;
import kr.co.starfield.model.CampaignStats;
import kr.co.starfield.model.InstallStats;
import kr.co.starfield.model.MovePathStats;

/**
 * HomeMapper interface
 *
 * Copyright Copyright (c) 2016 Company Paprika
 *
 * @author xxx
 * @version 1.0,
 * @see
 * @date 2016.04.11
 */

public interface AAML002Mapper {
	// 테넌트별 방문자통계 > 전체방문자 기준통계
	public List<AAML002> getTntAllVisitorStats(AAML002 vo);

	// 테넌트별 방문자통계 > 회원 기준통계
	public List<AAML003> getTntMbrVisitorStats(AAML003 vo);

	//앱캠페인 효과분석 전체 데이터
	public List<CampaignStats> getCampaignStatsList(CampaignStats campaignstats);

	//앱캠페인 효과분석 회원 데이터
	public List<CampaignStats> getCampaignMembStatsList(CampaignStats campaignstats);

	public List<InstallStats> getInstallStatsDay(InstallStats installStats);

	public List<InstallStats> getInstallStatsWeek(InstallStats installStats);

	public List<InstallStats> getInstallStatsMonth(InstallStats installStats);

	public List<InstallStats> getInstallStatsYear(InstallStats installStats);

	public List<InstallStats> getInstallTermStatsDay(InstallStats installStats);

	public List<InstallStats> getInstallTermStatsWeek(InstallStats installStats);

	public List<InstallStats> getInstallTermStatsMonth(InstallStats installStats);

	public List<InstallStats> getInstallTermStatsYear(InstallStats installStats);
	
	public List<MovePathStats> getMovePathStats(MovePathStats installStats);
	
	public List<MovePathStats> getMovePathStatsDtl(MovePathStats installStats);
}
