/*
 * couponController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.model.AAML001;
import kr.co.starfield.model.AAML002;
import kr.co.starfield.model.AAML003;
import kr.co.starfield.model.CampaignStats;
import kr.co.starfield.model.InstallStats;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.MovePathStats;
import kr.co.starfield.service.AAML001Service;

/**
 * 통계 관리
 * 
 * @author dhlee
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

@RestController
@RequestMapping("/rest")  
public class AAML001RestController extends BaseController {

	@Autowired
	AAML001Service aaml001Service;
	
	private static final Logger ll = LoggerFactory.getLogger(AAML001RestController.class);
	
	/**
	 * 전체 방문자 통계 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getAllVisitorStats"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<AAML001> getAllVisitorStats(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_dt_type") String sh_dt_type
			, @RequestParam(value="sh_strt_dt") String sh_strt_dt
			, @RequestParam(value="sh_end_dt") String sh_end_dt
			, @RequestParam(value="sortColumn") String sortColumn
			, @RequestParam(value="sortColumnAscDesc") String sortColumnAscDesc
			
	) throws Exception {
		
		//System.out.println("HELLLLO>>>");
		this.initHandler(req, res);

		AAML001 vo = new AAML001();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.sh_strt_dt = sh_strt_dt;
		vo.sh_end_dt = sh_end_dt;
		vo.sortColumn = sortColumn;
		vo.sortColumnAscDesc = sortColumnAscDesc;
		

		ListResult<AAML001> cplist  = null;
		if(sh_dt_type.equals("day")){
			cplist  = aaml001Service.getAllVisitorStatsDay(vo);
		}else if(sh_dt_type.equals("week")){
			cplist  = aaml001Service.getAllVisitorStatsWeek(vo);
		}else if(sh_dt_type.equals("month")){
			cplist  = aaml001Service.getAllVisitorStatsMonth(vo);
		}else if(sh_dt_type.equals("year")){
			cplist  = aaml001Service.getAllVisitorStatsYear(vo);
		}
		
		return cplist;
	}
	
	/**
	 * 전체 방문자 테넌트별 방문자 통계 리스트
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getTntAllVisitorStats"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<AAML002> getAllTntVisitorStats(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_strt_dt") String sh_strt_dt
			, @RequestParam(value="sh_end_dt") String sh_end_dt
			, @RequestParam(value="sh_tnt_nm") String sh_tnt_nm
			, @RequestParam(value="sortColumn") String sortColumn
			, @RequestParam(value="sortColumnAscDesc") String sortColumnAscDesc
			
	) throws Exception {
		
		//System.out.println("HELLLLO>>>");
		this.initHandler(req, res);

		AAML002 vo = new AAML002();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.sh_strt_dt = sh_strt_dt;
		vo.sh_end_dt = sh_end_dt;
		vo.sh_tnt_nm = sh_tnt_nm;
		vo.sortColumn = sortColumn;
		vo.sortColumnAscDesc = sortColumnAscDesc;
		

		ListResult<AAML002> cplist  = null;
		//if(sh_dt_type.equals("day")){
		cplist  = aaml001Service.getTntAllVisitorStats(vo);

		
		return cplist;
	}
	
	/**
	 * 테넌트별 방문자 회원기준
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getTntMbrVisitorStats"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ListResult<AAML003> getTntMbrVisitorStats(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_strt_dt") String sh_strt_dt
			, @RequestParam(value="sh_end_dt") String sh_end_dt
			, @RequestParam(value="sh_tnt_nm") String sh_tnt_nm
			, @RequestParam(value="sortColumn") String sortColumn
			, @RequestParam(value="sortColumnAscDesc") String sortColumnAscDesc
			
	) throws Exception {
		
		//System.out.println("HELLLLO>>>");
		this.initHandler(req, res);

		AAML003 vo = new AAML003();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.sh_strt_dt = sh_strt_dt;
		vo.sh_end_dt = sh_end_dt;
		vo.sh_tnt_nm = sh_tnt_nm;
		vo.sortColumn = sortColumn;
		vo.sortColumnAscDesc = sortColumnAscDesc;
		

		ListResult<AAML003> cplist  = null;
		//if(sh_dt_type.equals("day")){
		cplist  = aaml001Service.getTntMbrVisitorStats(vo);

		
		return cplist;
	}

	
	/**
	 * 앱캠페인 효과분석 전체 데이터
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getCampaignStatsList"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	public ListResult<CampaignStats> getCampaignStatsList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_strt_dt") String sh_strt_dt
			, @RequestParam(value="sh_end_dt") String sh_end_dt
			, @RequestParam(value="sh_titl") String sh_titl
			
			
			) throws Exception {
		
		this.initHandler(req, res);
		
		CampaignStats campaignstats = new CampaignStats();
		campaignstats.offset = Integer.parseInt(offset);
		campaignstats.limit = Integer.parseInt(limit);
		campaignstats.sh_strt_dt = sh_strt_dt;
		campaignstats.sh_end_dt = sh_end_dt;
		campaignstats.sh_titl = sh_titl;
		
		
		ListResult<CampaignStats> campaignStatsList  = aaml001Service.getCampaignStatsList(campaignstats);
		
		
		return campaignStatsList;
	}

	
	/**
	 * 앱캠페인 효과분석 회원 데이터
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getCampaignMembStatsList"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	public ListResult<CampaignStats> getCampaignMembStatsList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_strt_dt") String sh_strt_dt
			, @RequestParam(value="sh_end_dt") String sh_end_dt
			, @RequestParam(value="sh_titl") String sh_titl
			
			
			) throws Exception {
		
		this.initHandler(req, res);
		
		CampaignStats campaignstats = new CampaignStats();
		campaignstats.offset = Integer.parseInt(offset);
		campaignstats.limit = Integer.parseInt(limit);
		campaignstats.sh_strt_dt = sh_strt_dt;
		campaignstats.sh_end_dt = sh_end_dt;
		campaignstats.sh_titl = sh_titl;
		
		
		ListResult<CampaignStats> campaignStatsList  = aaml001Service.getCampaignMembStatsList(campaignstats);
		
		
		return campaignStatsList;
	}

	
	/**
	 * app설치 동계 전체 누적
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getInstallStats"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	public ListResult<InstallStats> getInstallStats(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_strt_dt") String sh_strt_dt
			, @RequestParam(value="sh_end_dt") String sh_end_dt
			, @RequestParam(value="sh_dt_type") String sh_dt_type
			, @RequestParam(value="sortColumn") String sortColumn
			, @RequestParam(value="sortColumnAscDesc") String sortColumnAscDesc
			
			) throws Exception {
		
		this.initHandler(req, res);
		
		InstallStats installStats = new InstallStats();
		installStats.offset = Integer.parseInt(offset);
		installStats.limit = Integer.parseInt(limit);
		installStats.sh_strt_dt = sh_strt_dt;
		installStats.sh_end_dt = sh_end_dt;
		installStats.sh_dt_type = sh_dt_type;
		installStats.sortColumn = sortColumn;
		installStats.sortColumnAscDesc = sortColumnAscDesc;
		
		ListResult<InstallStats> getInstallStats  = aaml001Service.getInstallStats(installStats);
		
		
		return getInstallStats;
	}

	
	/**
	 * app설치 동계 기간별
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getInstallTermStats"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	public ListResult<InstallStats> getInstallTermStats(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_strt_dt") String sh_strt_dt
			, @RequestParam(value="sh_end_dt") String sh_end_dt
			, @RequestParam(value="sh_dt_type") String sh_dt_type
			, @RequestParam(value="sortColumn") String sortColumn
			, @RequestParam(value="sortColumnAscDesc") String sortColumnAscDesc
			
			) throws Exception {
		
		this.initHandler(req, res);
		
		InstallStats installStats = new InstallStats();
		installStats.offset = Integer.parseInt(offset);
		installStats.limit = Integer.parseInt(limit);
		installStats.sh_strt_dt = sh_strt_dt;
		installStats.sh_end_dt = sh_end_dt;
		installStats.sh_dt_type = sh_dt_type;
		installStats.sortColumn = sortColumn;
		installStats.sortColumnAscDesc = sortColumnAscDesc;
		
		ListResult<InstallStats> getInstallStats  = aaml001Service.getInstallTermStats(installStats);
		
		
		return getInstallStats;
	}
	
	/*
	public String input_tnt_nm;
	public String input_cnt;
	public String input_rt;
	public String exit_tnt_nm;
	public String exit_cnt;
	public String exit_rt;
	/**
	 * 동선 통계
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getMovePathStats"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	public ListResult<MovePathStats> getMovePathStats(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcnCd") String bcn_cd
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_dt") String sh_dt
			, @RequestParam(value="sortColumn") String sortColumn
			, @RequestParam(value="sortColumnAscDesc") String sortColumnAscDesc
			
			) throws Exception {
		
		this.initHandler(req, res);
		
		MovePathStats installStats = new MovePathStats();
		installStats.offset = Integer.parseInt(offset);
		installStats.limit = Integer.parseInt(limit);
		installStats.sh_dt = sh_dt;
		installStats.sortColumn = sortColumn;
		installStats.sortColumnAscDesc = sortColumnAscDesc;
		installStats.bcn_cd = bcn_cd;
		
		ListResult<MovePathStats> getInstallStats  = aaml001Service.getMovePathStats(installStats);
		
		
		return getInstallStats;
	}
	
	
	/**
	 * 동선 통계
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getMovePathStatsDtl"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
			)
	public ListResult<MovePathStats> getMovePathStatsDtl(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @RequestParam(value="offset") String offset
			, @RequestParam(value="limit" ) String limit
			, @RequestParam(value="sh_grade") String sh_grade
			, @RequestParam(value="sh_zone_id") String sh_zone_id
			, @RequestParam(value="sh_tnt_seq") String sh_tnt_seq
			, @RequestParam(value="sh_dt") String sh_dt
			, @PathVariable(value="bcnCd") String bcn_cd

			) throws Exception {
		
		this.initHandler(req, res);
		
		MovePathStats installStats = new MovePathStats();
		installStats.bcn_cd = bcn_cd;
		installStats.offset = Integer.parseInt(offset);
		installStats.limit = Integer.parseInt(limit);
		installStats.sh_tnt_seq = sh_tnt_seq;
		installStats.c_zone_id = sh_zone_id;
		installStats.sh_grade = sh_grade;
		installStats.sh_dt = sh_dt;

		ListResult<MovePathStats> getInstallStats  = aaml001Service.getMovePathStatsDtl(installStats);
		
		
		return getInstallStats;
	}
}
