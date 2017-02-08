package kr.co.starfield.mapper;

import java.util.ArrayList;
import java.util.List;

import kr.co.starfield.model.HeatMap;
import kr.co.starfield.model.HeatMapFilter;
import kr.co.starfield.model.JoinStats;
import kr.co.starfield.model.JoinStatsFilter;
import kr.co.starfield.model.ListResult;

/**
 * DASHBOARD
 *
 * Copyright Copyright (c) 2016 Company Paprika
 *
 * @author eezy
 * @version 1.0,
 * @see
 * @date 2016.10.07
 */

public interface ASAT001Mapper {

	// 가입자 수 조회 (오늘)
	public int getToDayJoinCount(String bcn_cd);

	// 가입자 수 조회 (당월)
	public int getMonthJoinCount(String bcn_cd);  

	// 가입자 수 조회 (누적)
	public int getTotalJoinCount(String bcn_cd);

	// 앱다운로드 현황 조회 (누적)
	public int getTotalAppDownCount(String bcn_cd);

	// 가입자 수 조회
	public ArrayList<JoinStats> getJoinCount(JoinStatsFilter filter);

	// 다운 수 조회
	public ArrayList<JoinStats> getAppDownCount(JoinStatsFilter filter);
	//
	public void SP_APP_USER_SUM(JoinStatsFilter filter);
	// 앱 사용자현황 조회
	public ArrayList<JoinStats> getAppUserCount(JoinStatsFilter filter);
	
	// 히드맵
	public ArrayList<HeatMap> getHeatmapList(HeatMapFilter heatmap);

	public String getHeatmapMax(HeatMapFilter heatmap);

}
