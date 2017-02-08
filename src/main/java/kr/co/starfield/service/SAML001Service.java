package kr.co.starfield.service;

import java.util.*;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.mapper.ACMS001Mapper;
import kr.co.starfield.mapper.ACPN002Mapper;
import kr.co.starfield.mapper.SAML001Mapper;
import kr.co.starfield.model.ALBS001;
import kr.co.starfield.model.ALBS003;
import kr.co.starfield.model.ALBS004_D;
import kr.co.starfield.model.Blog;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.News;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.ResultPush;
import kr.co.starfield.model.SAML001;
import kr.co.starfield.model.SAML002;
import kr.co.starfield.model.SAML003;
import kr.co.starfield.model.SAML004;
import kr.co.starfield.model.SCPN003;
import kr.co.starfield.model.SCPN005;
import kr.co.starfield.model.SCPN006;
import kr.co.starfield.model.SMBR001;
import kr.co.starfield.model.ScenarioPush;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.Tenant;
import kr.co.starfield.model.vo.AITF009Vo;
import kr.co.starfield.model.vo.ALBS004_DVo;
import kr.co.starfield.model.vo.SAML002Vo;
import kr.co.starfield.model.vo.SAML003Vo;
import kr.co.starfield.model.vo.SAML004Vo;
import kr.co.starfield.model.vo.SMBR003Vo;
import kr.co.starfield.model.vo.SMBR005Vo;
import kr.co.starfield.model.vo.STNT001Vo;


/**
 * SAML001Service 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author dhlee
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

@Service
public class SAML001Service {

	@Autowired
	private SAML001Mapper saml001mapper;
	
	@Autowired
	private ACPN002Mapper acpn002mapper;
	
	/**
	 * action 로그
	 * @param log_type, evt_div_cd1, key, val, uuid
	 * @return 
	 * @throws BaseException 
	 */
	public SimpleResult appActionLog(String log_type, String evt_div_cd1, String key, String val, String uuid, String cust_id) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		try{
		
			SAML003 saml003 = new SAML003();
			saml003.log_type = log_type;
			saml003.evt_div_cd1 = evt_div_cd1;
			saml003.key = key;
			saml003.val = val;
			saml003.uuid = uuid;
			saml003.cust_id = cust_id;
		//	saml003.cust_id = cust_id;
		
			String evt_val = "";
			if(!StringUtils.isEmpty(saml003.key)){
				String akey[] = saml003.key.split(",");
				String aval[] = saml003.val.split(",");
				for(int i = 0 ; i < akey.length ; i ++){
					if(i > 0){
						evt_val += "^";
					}
					evt_val += akey[i] + "|" + aval[i];
				}
				saml003.evt_val = evt_val; 
				
			}
			
			/*where log_type    = #{log_type}
			and   evt_div_cd1 = #{evt_div_cd1}
			and   evt_val 	  = #{evt_val}*/
			
			int chk_cnt = 0;
			if(!StringUtils.isEmpty("evt_div_cd1")){
				if(evt_div_cd1.equals("F1010") || evt_div_cd1.equals("F1011") || evt_div_cd1.equals("F1012")
						|| evt_div_cd1.equals("F1013") || evt_div_cd1.equals("F1014")){
					chk_cnt = saml001mapper.getActionCnt(saml003);
					
				}
			}
			if(chk_cnt == 0){
				int cnt = saml001mapper.appActionLog(saml003);
			}
			
			result.code = 0;
			result.desc = "로그 저장 성공";		
		} catch (Exception e) {
			result.code = -1;
			result.desc = "로그 저장 실패";		
		}
			
		return result;
	}

	/**
	 * 앱 실행 로그
	 * @param saml001
	 * @return 
	 * @throws BaseException 
	 */
	public SimpleResult appExecuteLog(SAML001 saml001) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		if(!StringUtils.isEmpty(saml001.execute_yn) && saml001.execute_yn.equals("Y")){
			saml001mapper.appExecuteLog(saml001);
		}

		SAML003 saml003_2 = new SAML003();
		saml003_2.evt_div_cd1 = "D1000";
		saml003_2.uuid = saml001.uuid;
		saml001mapper.appActionLog(saml003_2);
		
		String cust_id = saml001mapper.getMemCustid(saml001.uuid);
		saml003_2.cust_id = cust_id;
		
		saml001mapper.updateInboxCustid(saml003_2);
		
		result.code = 0;
		
		return result;
	}
	
	/**
	 * 앱 최초 로그인(교환 쿠폰 푸시)
	 * @param uuid
	 * @return 
	 * @throws BaseException 
	 */
	public SCPN005 appFirstLoginLog(String uuid) throws BaseException {
		SCPN005 scpn005 = new SCPN005();
		scpn005.cp_titl = "";
		SAML003 saml003_2 = new SAML003();
		
		String cust_id = saml001mapper.getMemCustid(uuid);
		saml003_2.cust_id = cust_id;
		saml003_2.evt_div_cd1 = "D1001";
		saml003_2.evt_div_cd2 = "CC";
		saml003_2.uuid = uuid;
		
		int cnt = saml001mapper.appActionLogCnt(saml003_2);
		if(cnt == 0){
			scpn005 = saml001mapper.getExecutePush();
			if(!StringUtils.isEmpty(scpn005)){
				if(!StringUtils.isEmpty(scpn005.cp_seq)){
					saml003_2.evt_val = scpn005.cp_seq;
				}
				saml001mapper.appActionLog(saml003_2);
			}else{
				scpn005 = new SCPN005();
				scpn005.cp_titl = "";
			}
		}
		
		return scpn005;
	}
	
	/**
	 * 위치(이동) 로그 (웰컴 쿠폰 푸시) 구버전
	 * @param model
	 * @return 
	 * @throws BaseException 
	 */
	public List<SCPN006> appMoveLog(List<SAML002> saml002) throws BaseException {
		
		///////////////////////////// 신버전용으로 다막음/////////////////////////////////////
		SCPN006 result = new SCPN006();
		//이동 로그 등록
		String zone_id = "0";
		String uuid = "";
		
		for(int i = 0 ; i < saml002.size() ; i++){
			SAML002 log = saml002.get(i);
			saml001mapper.appMoveLog(log);
			
			if(StringUtils.isEmpty(log.zone_id)){
				log.zone_id = "0";
			}
			
			if(i == 0){
				zone_id = log.zone_id;
				uuid = log.uuid;
				
			}else{
				zone_id += ","+log.zone_id;
				
			}
		}	
			
		//웰컴 푸시 내용
		SAML002 param = new SAML002();
		param.zone_id = zone_id;
		param.uuid = uuid;
		List<SCPN006> scpn006 = saml001mapper.getAppMoveLogPush(param);
		
		for(int i = 0 ; i < scpn006.size() ; i ++){
			SCPN006 push = scpn006.get(i);
			push.uuid = uuid;

			saml001mapper.updatePush(push);
			
		}

		String app_mket_info_recep_yn = saml001mapper.getAppMketInfoRecepYn(uuid);
		if("N".equals(app_mket_info_recep_yn)){
			scpn006 = new ArrayList<SCPN006>();
		}
		
		///////////////////////////////////////////////////////////////////////////<<//구버전
		
		
		//List<SCPN006> scpn006 = new ArrayList<SCPN006>(); // 신버전용으로는 주석 푼다.
		
		
		return scpn006;
		
	}
	
	public ResultPush appMoveLog2(SAML002 saml002) {
		ResultPush result = new ResultPush();
		
		//이동 로그 등록

		saml001mapper.appMoveLog2(saml002);
		
		saml001mapper.SP_SAML013(saml002);
		
		ScenarioPush scenarioPush = new ScenarioPush();
		
		AITF009Vo vo = saml001mapper.getZoneType(saml002);
		
		String app_mket_info_recep_yn = saml001mapper.getAppMketInfoRecepYn(saml002.uuid);
		
		if(!StringUtils.isEmpty(saml002.scn_cp_push_titl) && !"N".equals(app_mket_info_recep_yn)){
			String log_cd = "F1013";
			if(vo.zone_type.equals("1") && vo.tnt_type.equals("-1")){
				log_cd = "F1012";
			}
			
			scenarioPush.cp_seq            = saml002.cp_seq;     
			scenarioPush.push_titl  	   = saml002.scn_cp_push_titl;     
			scenarioPush.push_msg      	   = saml002.scn_push_msg;     
			scenarioPush.img_titl_uri      = saml002.img_titl_uri;     
			scenarioPush.img_dtl_uri       = saml002.img_dtl_uri;        
			scenarioPush.cp_act_strt_dt    = saml002.cp_act_strt_dt;    
			scenarioPush.cp_act_end_dt     = saml002.cp_act_end_dt;      
			scenarioPush.push_seq          = saml002.scn_otb_cp_push_seq;
			scenarioPush.sys_push_msg      = saml002.sys_push_msg;
			scenarioPush.titl_type         = saml002.titl_type;
			scenarioPush.dtl_type          = saml002.dtl_type;
			scenarioPush.log_cd            = log_cd;
			result.code = 0;
		}else{
			result.code = -1;
		}
		result.scenarioPush = scenarioPush;
		
	
		return result;
	}
	
	
	/**
	 * 위치정보 로그 리스트
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<SAML002> getLocationLogs(SAML002Vo vo) throws BaseException {
		
		ListResult<SAML002> result = new ListResult<>();
		
		List<SAML002Vo> list = saml001mapper.getLocationLogs(vo);
		
		String uuid = "";
		String cust_id = "";
		String corp_cd = "";
		String prc_info = "";
		int i = 0;
		for(SAML002Vo news : list){
			i++;
			result.list.add(news.toModel());
			
			String comma = "";
			if(i != list.size()){
				comma = ",";
			}
			
			uuid = news.uuid+comma;
			cust_id = news.cust_id+comma;
			corp_cd += news.corp_cd+comma;
			prc_info += news.zone_id + "|"+ news.x_ctn_cord +"|"+ news.y_ctn_cord + comma;
		}
		
		SAML004Vo saml004vo	= new SAML004Vo();
		saml004vo.uuid = uuid;
		saml004vo.cust_id = cust_id;
		saml004vo.prc_div = "1";
		saml004vo.prc_info = prc_info;
		saml001mapper.reqLocationReadingLog(saml004vo);
		
		int tot_cnt = list.size() > 0 ? list.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
			
		result.paging = paging;
		
		return result;
	}
	
	/**
	 * 위치정보 로그 열람 리스트
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<SAML004> getLocationReadingLogs(SAML004Vo vo) throws BaseException {
		
		ListResult<SAML004> result = new ListResult<>();
		
		List<SAML004Vo> list = saml001mapper.getLocationReadingLogs(vo);
		
		for(SAML004Vo news : list){
			result.list.add(news.toModel());
		}
		
		int tot_cnt = list.size() > 0 ? list.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
			
		result.paging = paging;
		
		return result;
	}

	/**
	 * 액션 로그 리스트
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<SAML003> getActionLogs(SAML003Vo vo) throws BaseException {
		ListResult<SAML003> result = new ListResult<>();
		
		List<SAML003Vo> list = saml001mapper.getActionLogs(vo);
		
		for(SAML003Vo news : list){
			result.list.add(news.toModel());
		}
		
		int tot_cnt = list.size() > 0 ? list.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
			
		result.paging = paging; 
		return result;
	}

	/**
	 * uuid 생성
	 * @param 
	 * @return 
	 * @throws BaseException 
	 */
	public SimpleResult getUuid() {
		SimpleResult result = new SimpleResult();
		
		String uuid = saml001mapper.getUuid();
		if(StringUtils.isEmpty(uuid)){
			result.code = -1;
			result.desc = "uuid 생성 실패";
		}else{
			result.code = 0;
			result.desc = "uuid 생성 성공";
			result.extra = uuid;
		}
		
		return result;
	}

	public ResultPush appScenarioMoveLog(SAML002 saml002) {
		
		ResultPush result = new ResultPush();
		result.code = 0;
		List<SCPN003> scpn003 = new ArrayList<SCPN003>();
		String type = "";
		
		String app_mket_info_recep_yn = saml001mapper.getAppMketInfoRecepYn(saml002.uuid);
		ScenarioPush scenarioPush = new ScenarioPush();
		if("N".equals(app_mket_info_recep_yn)){
			ScenarioPush scenarioPush1 = new ScenarioPush();
			result.code = -1;
			result.scenarioPush = scenarioPush1;
			return result;
		}
		String campaign_id_null_check="Y";//Null일경우 Y
		if(saml002.campaign_id !=null || !StringUtils.isEmpty(saml002.campaign_id)){
			campaign_id_null_check ="Y";
		}else{
			campaign_id_null_check ="N";
		}
		if(campaign_id_null_check.equals("Y")){ //시나리오
		//if(saml002.campaign_id !=null){ //시나리오	
		//if(!StringUtils.isEmpty(saml002.campaign_id)){ //시나리오			
			ALBS001 scenario = saml001mapper.getScenarioCk(saml002.campaign_id);
			String div_cd = ""; 
			if(scenario == null){
				div_cd = "5";
			}else if(scenario.push_time_div_cd.equals("6")){//임시 관심 매장
				div_cd = "3";
			}else{
				div_cd = "2";
			}
			
			saml002.div_cd = div_cd;
			saml001mapper.appScenarioMoveLog(saml002);//체류 이동 로그 남긴다.
			
			if(scenario != null && !StringUtils.isEmpty(scenario.cp_seq)){//쿠폰이 있는 경우 
				if(scenario.push_time_div_cd.equals("2")){
					saml001mapper.SP_ALBS001(saml002);
					saml002.img_titl_uri = scenario.bi_img;
					
				}else if(scenario.push_time_div_cd.equals("3")){
					saml001mapper.SP_ALBS001_2(saml002);
					saml002.img_titl_uri = scenario.bi_img;
				}
				
			}else if(scenario != null && StringUtils.isEmpty(scenario.cp_seq)){//쿠폰이 없는 경우 
				if(scenario.push_time_div_cd.equals("2")){
					saml001mapper.SP_ALBS001_3(saml002);
					saml002.img_titl_uri = scenario.bi_img;
					
				}else if(scenario.push_time_div_cd.equals("3")){
					saml001mapper.SP_ALBS001_5(saml002);
					saml002.img_titl_uri = scenario.bi_img;
					
				}else if(scenario.push_time_div_cd.equals("6")){//관심,임시
					//if(saml002.stay_time.equals("1")){//관심, 임시push
						SMBR003Vo smbr003vo = saml001mapper.getLikeTenantCnt(saml002); //관심 테넌트 ck
						
						//관심/임시 매장 체크
						if(!StringUtils.isEmpty(smbr003vo)){//관심 테넌트
							STNT001Vo vo = new STNT001Vo();
							vo.tnt_cd = smbr003vo.tnt_seq;
							vo.limit = -1;
							scpn003 = acpn002mapper.getCpTenants(vo);
							type = "1";
							
						}else{//임시
							
							String pushYn = saml001mapper.getAppScenarioMoveLog(saml002);
							//if(!StringUtils.isEmpty(pushYn) && pushYn.equals("Y")){
								STNT001Vo vo = new STNT001Vo(); 
								vo.zone_id = saml002.zone_id;
								vo.limit = -1;
								scpn003 = acpn002mapper.getCpZones(vo);//쿠폰을 가져오는 거
								type = "2";
							//}
						}
					//}
				}
			}//end if //시나리오 체크
			
			
			System.out.println("saml002.scn_cp_push_titl>>>"+saml002.scn_cp_push_titl);
			if(StringUtils.isEmpty(saml002.scn_cp_push_titl)){
				String inbox_cont = "";
				String pushYn ="Y";
				if(scpn003.size() > 0){//관심, 임시 매장이 존재한다.
					for(int i = 0 ; i < scpn003.size() ; i++){
						SCPN003 cp = scpn003.get(i);
						cp.uuid = saml002.uuid;
						int cnt = saml001mapper.getInboxCpCnt(cp);
						System.out.println("Check>>>"+cnt);
						if(cnt == 0){//str if 2 아직 등록이 안된 경우
							ALBS004_DVo albs004_d = new ALBS004_DVo();
							albs004_d.cp_seq = cp.cp_seq;
							albs004_d.uuid = saml002.uuid;
							albs004_d.zone_id = saml002.zone_id;
							albs004_d.push_seq = cp.cp_seq;
							
							if(type.equals("1")){//관심 매장
								albs004_d.push_div_cd = "IC";
								inbox_cont = "저희 매장을 관심매장으로 등록해 주신 분들께 할인 쿠폰을 보내드립니다.";
								pushYn="Y";
							}else{//임시 관심 매장
								albs004_d.push_div_cd = "TC";
								inbox_cont = "저희 매장을 다시 방문 해주셔서 감사합니다. 재방문 해주신분들께 할인 쿠폰을 보내드립니다.";
								
								pushYn = saml001mapper.getAppScenarioMoveLog(saml002);
								System.out.println("pushYn>>>>>>>>>>>>"+pushYn);
							}//end if
							
							System.out.println(albs004_d.push_div_cd+" pushYn>>>>>>>>>>>>"+pushYn);
							if(pushYn.equals("Y")){//푸시 발송
								albs004_d.inbox_cont = inbox_cont;
								albs004_d.inbox_titl = cp.cp_titl;
								//albs004_d.fav_reg_yn = "Y";
								saml001mapper.insertLikeInbox(albs004_d);
								saml001mapper.insertLikeInbox2(albs004_d);
								
								scenarioPush.cp_seq            = cp.cp_seq;     
								scenarioPush.push_titl  	   = cp.cp_titl;    
								scenarioPush.push_msg      	   = inbox_cont;
								scenarioPush.img_titl_uri      = cp.img_logo_uri;     
								//scenarioPush.img_dtl_uri       = cp.img_thmb_uri;     
								scenarioPush.cp_act_strt_dt    = cp.cp_act_strt_dt;    
								scenarioPush.cp_act_end_dt     = cp.cp_act_end_dt;
								scenarioPush.push_seq          = cp.cp_seq; ;
								scenarioPush.log_cd            = "F1011";
								result.code = 0;
							}else{
								result.code = -1;
							}
							
							
						}else{//str if 1//안받아야 함
							/*
							if(StringUtils.isEmpty(scenarioPush.cp_seq)){
								result.code = -2;
								scenarioPush.cp_seq = "";
							}else{
								result.code = 0;
							}//end if 1
							*/
							result.code = -1;
						}//end if 2
					}//end for
				}else{
					result.code = -1;
					scenarioPush.cp_seq = "";
				}//end if 3
				
			}else{//캠페인id가 없을때
				//saml002.div_cd = "4";
				//saml001mapper.appScenarioMoveLog(saml002);
				
				//saml001mapper.SP_ALBS001_4(saml002);
				
				//ScenarioPush scenarioPush = new ScenarioPush();
				//saml001mapper.SP_ALBS001_4(saml002);
				scenarioPush.cp_seq            = saml002.cp_seq;     
				scenarioPush.push_titl  	   = saml002.scn_cp_push_titl;     
				scenarioPush.push_msg      	   = saml002.scn_push_msg;     
				scenarioPush.img_titl_uri      = saml002.img_titl_uri;     
				//scenarioPush.img_dtl_uri       = "";     
				scenarioPush.cp_act_strt_dt    = saml002.cp_act_strt_dt;    
				scenarioPush.cp_act_end_dt     = saml002.cp_act_end_dt;     
				scenarioPush.push_seq          = saml002.scn_otb_cp_push_seq;
				scenarioPush.log_cd            = "F1010";
				
			}
		}else{//캠페인 ID가 없을 때
			saml002.div_cd = "4";//앱실행
			saml001mapper.appScenarioMoveLog(saml002);
			
			saml001mapper.SP_ALBS001_4(saml002);
			
			//ScenarioPush scenarioPush = new ScenarioPush();
			//saml001mapper.SP_ALBS001_4(saml002);
			try{
			//System.out.println("saml002.scn_cp_push_titl"+saml002.scn_cp_push_titl.length());
			//System.out.println("saml002.scn_cp_push_titl"+saml002.scn_cp_push_titl);
			}catch(NullPointerException e){
				result.code = -1;
				scenarioPush.cp_seq = "";
			}
			scenarioPush.cp_seq            = saml002.cp_seq;     
			scenarioPush.push_titl  	   = saml002.scn_cp_push_titl;     
			scenarioPush.push_msg      	   = saml002.scn_push_msg;     
			scenarioPush.img_titl_uri      = saml002.img_titl_uri;     
			//scenarioPush.img_dtl_uri       = "";     
			scenarioPush.cp_act_strt_dt    = saml002.cp_act_strt_dt;    
			scenarioPush.cp_act_end_dt     = saml002.cp_act_end_dt;     
			scenarioPush.push_seq          = saml002.scn_otb_cp_push_seq;
			scenarioPush.log_cd            = "F1010";
			
			if(saml002.scn_cp_push_titl==null || saml002.scn_cp_push_titl.equals("") || saml002.scn_cp_push_titl.equals("null")){
				result.code = -1;
				scenarioPush.cp_seq = "";
			}
				
		}

		
		result.scenarioPush = scenarioPush;
			
		
			
			
			//임시관심매장
			
			
			
			
			
		//	result.desc = "F1010";
			
			
			
			
		/*}else if(StringUtils.isEmpty(saml002.campaign_id) && StringUtils.isEmpty(saml002.zone_id)){ //시나리오 
			
			saml001mapper.SP_ALBS001_4(saml002);
			
			if(!StringUtils.isEmpty(saml002.push_time_div_cd) && saml002.push_time_div_cd.equals("1")){
				result.desc = "F1010";
			}
			
		}else if(StringUtils.isEmpty(saml002.campaign_id) && !StringUtils.isEmpty(saml002.zone_id) 
				&& !saml002.stay_time.equals("30")){ //임시,관심 매장

			//관심매장
			
			
		}
		*/
		
		return result;
	}

	public ResultPush appExecution(SAML002 saml002) {
		ResultPush result = new ResultPush();
		result.code = 0;
		
		saml002.div_cd = "4";
		saml001mapper.appScenarioMoveLog(saml002);
		
		saml001mapper.SP_ALBS001_4(saml002);
		
		ScenarioPush scenarioPush = new ScenarioPush();
		
		if(StringUtils.isEmpty(saml002.scn_cp_push_titl)){
			result.code = -1;
			scenarioPush.cp_seq = "";
		}else{
			scenarioPush.cp_seq            = saml002.cp_seq;     
			scenarioPush.push_titl  	   = saml002.scn_cp_push_titl;     
			scenarioPush.push_msg      	   = saml002.scn_push_msg;     
			scenarioPush.img_titl_uri      = saml002.img_titl_uri;     
			scenarioPush.cp_act_strt_dt    = saml002.cp_act_strt_dt;    
			scenarioPush.cp_act_end_dt     = saml002.cp_act_end_dt;     
			scenarioPush.push_seq = saml002.scn_otb_cp_push_seq;
		}
		
		result.scenarioPush = scenarioPush;
		return result;
	}
	
	/**
	 * action 로그
	 * @param log_type, evt_div_cd1, key, val, uuid
	 * @return 
	 * @throws BaseException 
	 */
	public SimpleResult appActionLogAdm(String log_type, String evt_div_cd1, String val) throws BaseException {
		SimpleResult result = new SimpleResult();
		try{
		
			SAML003 saml003 = new SAML003();
			saml003.log_type = log_type;
			saml003.evt_div_cd1 = evt_div_cd1;
			saml003.val = val;
			saml003.evt_val = val;
			int chk_cnt = saml001mapper.getActionCnt(saml003);
			if(chk_cnt==0){
				int cnt = saml001mapper.appActionLog(saml003); 
			}
			
			result.code = 0;
			result.desc = "로그 저장 성공";		
		} catch (Exception e) {
			result.code = -1;
			result.desc = "로그 저장 실패";		
		}
			
		return result;
	}
}
