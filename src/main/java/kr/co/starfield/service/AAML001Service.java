package kr.co.starfield.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.starfield.mapper.AAML001Mapper;
import kr.co.starfield.mapper.AAML002Mapper;
import kr.co.starfield.model.AAML001;
import kr.co.starfield.model.AAML002;
import kr.co.starfield.model.AAML003;
import kr.co.starfield.model.CampaignStats;
import kr.co.starfield.model.InstallStats;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.MovePathStats;
import kr.co.starfield.model.Paging;

/**
 * REST 서비스 클래스
 *
 * Copyright Copyright (c) 2016 Company Paprika
 *
 * @author xxx
 * @version 1.0,
 * @see
 * @date 2016.04.11
 */

@Service
public class AAML001Service {

	@Autowired
	private AAML001Mapper mapper;

	@Autowired
	private AAML002Mapper mapper002;

	private static final Logger ll = LoggerFactory.getLogger(AAML001Mapper.class);

	// 일단위 방문자 통계 조회
	public ListResult<AAML001> getAllVisitorStatsDay(AAML001 vo) {

		ListResult<AAML001> result = new ListResult<>();

		List<AAML001> cpList = mapper.getAllVisitorStatsDay(vo);
		result.list = cpList;
		/*
		 * for(ACPN001Vo resultVo : cpList){
		 * result.list.add(resultVo.toModel()); }
		 */

		int totCnt = cpList.size() > 0 ? cpList.get(0).tot_cnt : 0;

		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;

		return result;
	}

	// 주단위 방문자 통계 조회
	public ListResult<AAML001> getAllVisitorStatsWeek(AAML001 vo) {

		ListResult<AAML001> result = new ListResult<>();

		List<AAML001> cpList = mapper.getAllVisitorStatsWeek(vo);
		result.list = cpList;
		/*
		 * for(ACPN001Vo resultVo : cpList){
		 * result.list.add(resultVo.toModel()); }
		 */

		int totCnt = cpList.size() > 0 ? cpList.get(0).tot_cnt : 0;

		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;

		return result;
	}

	// 월단위 방문자 통계 조회
	public ListResult<AAML001> getAllVisitorStatsMonth(AAML001 vo) {

		ListResult<AAML001> result = new ListResult<>();

		List<AAML001> cpList = mapper.getAllVisitorStatsMonth(vo);
		result.list = cpList;
		/*
		 * for(ACPN001Vo resultVo : cpList){
		 * result.list.add(resultVo.toModel()); }
		 */

		int totCnt = cpList.size() > 0 ? cpList.get(0).tot_cnt : 0;

		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;

		return result;
	}

	// 년단위 방문자 통계 조회
	public ListResult<AAML001> getAllVisitorStatsYear(AAML001 vo) {

		ListResult<AAML001> result = new ListResult<>();

		List<AAML001> cpList = mapper.getAllVisitorStatsYear(vo);
		result.list = cpList;
		/*
		 * for(ACPN001Vo resultVo : cpList){
		 * result.list.add(resultVo.toModel()); }
		 */

		int totCnt = cpList.size() > 0 ? cpList.get(0).tot_cnt : 0;

		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;

		return result;
	}

	// 테넌트 방문자 통계 > 전체방문자 통계
	public ListResult<AAML002> getTntAllVisitorStats(AAML002 vo) {

		ListResult<AAML002> result = new ListResult<>();

		List<AAML002> cpList = mapper002.getTntAllVisitorStats(vo);
		result.list = cpList;

		int totCnt = cpList.size() > 0 ? cpList.get(0).tot_cnt : 0;

		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;

		return result;
	}

	// 테넌트 방문자 통계 > 회원기준 통계
	public ListResult<AAML003> getTntMbrVisitorStats(AAML003 vo) {

		ListResult<AAML003> result = new ListResult<>();

		List<AAML003> cpList = mapper002.getTntMbrVisitorStats(vo);
		result.list = cpList;

		int totCnt = cpList.size() > 0 ? cpList.get(0).tot_cnt : 0;

		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;

		return result;
	}

	// 앱캠페인 효과분석 전체 데이터
	public ListResult<CampaignStats> getCampaignStatsList(CampaignStats campaignstats) {
		ListResult<CampaignStats> result = new ListResult<>();

		List<CampaignStats> list = mapper002.getCampaignStatsList(campaignstats);
		result.list = list;

		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;

		Paging paging = new Paging(campaignstats.offset, campaignstats.limit, totCnt);
		result.paging = paging;

		return result;
	}

	public ListResult<CampaignStats> getCampaignMembStatsList(CampaignStats campaignstats) {
		ListResult<CampaignStats> result = new ListResult<>();

		List<CampaignStats> list = mapper002.getCampaignMembStatsList(campaignstats);
		result.list = list;

		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;

		Paging paging = new Paging(campaignstats.offset, campaignstats.limit, totCnt);
		result.paging = paging;

		return result;
	}

	public ListResult<InstallStats> getInstallStats(InstallStats installStats) {
		ListResult<InstallStats> result = new ListResult<>();
		List<InstallStats> list = new ArrayList<InstallStats>();
		if (installStats.sh_dt_type.equals("day")) {
			list = mapper002.getInstallStatsDay(installStats);
		} else if (installStats.sh_dt_type.equals("week")) {
			list = mapper002.getInstallStatsWeek(installStats);
		} else if (installStats.sh_dt_type.equals("month")) {
			list = mapper002.getInstallStatsMonth(installStats);
		} else if (installStats.sh_dt_type.equals("year")) {
			list = mapper002.getInstallStatsYear(installStats);
		}

		result.list = list;

		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;

		Paging paging = new Paging(installStats.offset, installStats.limit, totCnt);
		result.paging = paging;

		return result;
	}

	public ListResult<InstallStats> getInstallTermStats(InstallStats installStats) {
		ListResult<InstallStats> result = new ListResult<>();
		List<InstallStats> list = new ArrayList<InstallStats>();
		if (installStats.sh_dt_type.equals("day")) {
			list = mapper002.getInstallTermStatsDay(installStats);
		} else if (installStats.sh_dt_type.equals("week")) {
			list = mapper002.getInstallTermStatsWeek(installStats);
		} else if (installStats.sh_dt_type.equals("month")) {
			list = mapper002.getInstallTermStatsMonth(installStats);
		} else if (installStats.sh_dt_type.equals("year")) {
			list = mapper002.getInstallTermStatsYear(installStats);
		}

		result.list = list;

		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;

		Paging paging = new Paging(installStats.offset, installStats.limit, totCnt);
		result.paging = paging;

		return result;//getMovePathStatsDtl
	}

	/*
	 * 이동 경로 분석
	 */
	public ListResult<MovePathStats> getMovePathStats(MovePathStats installStats) {
		ListResult<MovePathStats> result = new ListResult<>();
		List<MovePathStats> list = new ArrayList<MovePathStats>();

		list = mapper002.getMovePathStats(installStats);

		result.list = list;

		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;

		Paging paging = new Paging(installStats.offset, installStats.limit, totCnt);
		result.paging = paging;

		return result;
	}
	
	/*
	 * 이동 경로 분석
	 */
	public ListResult<MovePathStats> getMovePathStatsDtl(MovePathStats installStats) {
		ListResult<MovePathStats> result = new ListResult<>();
		List<MovePathStats> list = new ArrayList<MovePathStats>();

		list = mapper002.getMovePathStatsDtl(installStats);

		result.list = list;

		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;

		Paging paging = new Paging(installStats.offset, installStats.limit, totCnt);
		result.paging = paging;

		return result;
	}
}
