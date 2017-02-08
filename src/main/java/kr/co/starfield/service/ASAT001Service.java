package kr.co.starfield.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.mapper.ASAT001Mapper;
import kr.co.starfield.model.AAML002;
import kr.co.starfield.model.DashboardStats;
import kr.co.starfield.model.HeatMap;
import kr.co.starfield.model.HeatMapFilter;
import kr.co.starfield.model.JoinStats;
import kr.co.starfield.model.JoinStatsFilter;
import kr.co.starfield.model.ListResult;


/**
 * DASHBOARD
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.10.07
 */

@Service
public class ASAT001Service {

	@Autowired
	private ASAT001Mapper asat001Mapper;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	private static final Logger ll = LoggerFactory.getLogger(ASAT001Service.class);
	
	/**
	 * 대쉬보드 통계
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	
	public DashboardStats getDashboardStats(String bcn_cd) throws BaseException {

		DashboardStats dashboardStats = new DashboardStats();
		dashboardStats.todayJoinCount = asat001Mapper.getToDayJoinCount(bcn_cd);
		dashboardStats.totalJoinCount = asat001Mapper.getTotalJoinCount(bcn_cd);
		dashboardStats.monthJoinCount = asat001Mapper.getMonthJoinCount(bcn_cd);
		//TODO 앱 다운로드 통계 넣어주세요
		dashboardStats.appDownCount = asat001Mapper.getTotalAppDownCount(bcn_cd);;
		
		return dashboardStats;
	}
	
	/**
	 * 가입자 목록 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	
	public ArrayList<JoinStats> getJoinCount(JoinStatsFilter filter) throws BaseException {

		return asat001Mapper.getJoinCount(filter);
	}
	
	/**
	 * AppDown 목록 조회 
	 * @param filter
	 * @return ArrayList<JoinStats>
	 * @throws BaseException 
	 */
	
	public ArrayList<JoinStats> getAppDownCount(JoinStatsFilter filter) throws BaseException {

		return asat001Mapper.getAppDownCount(filter);
	}
	
	/**
	 * AppDown 목록 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	
	public ArrayList<JoinStats> getAppUserCount(JoinStatsFilter filter) throws BaseException {
		//asat001Mapper.SP_APP_USER_SUM(filter);
		return asat001Mapper.getAppUserCount(filter);
	}

	/**
	 * heatmap
	 * @param HeatMap
	 * @return 
	 * @throws BaseException 
	 */
	public HeatMapFilter getHeatmapList(HeatMapFilter heatmap) {
		HeatMapFilter result = new HeatMapFilter();
		result.heatMapList = asat001Mapper.getHeatmapList(heatmap); 
		result.max = asat001Mapper.getHeatmapMax(heatmap); 
		return result;
	}
}
