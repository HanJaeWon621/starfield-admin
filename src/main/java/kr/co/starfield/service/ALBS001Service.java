package kr.co.starfield.service;


import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.httpClient.CampaignHttpClient;
import kr.co.starfield.mapper.ACPN001Mapper;
import kr.co.starfield.mapper.ALBS001Mapper;
import kr.co.starfield.mapper.SAML001Mapper;
import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.ACPN003;
import kr.co.starfield.model.ACPN003Filter;
import kr.co.starfield.model.ALBS001;
import kr.co.starfield.model.ALBS002;
import kr.co.starfield.model.ALBS003;
import kr.co.starfield.model.ALBS004;
import kr.co.starfield.model.ALBS004_D;
import kr.co.starfield.model.ALBS005;
import kr.co.starfield.model.AbstMst;
import kr.co.starfield.model.AppListResult;
import kr.co.starfield.model.Event;
import kr.co.starfield.model.LbsZone;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Member;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SCPN001_D;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.WelcomeZone;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.AITF009Vo;
import kr.co.starfield.model.vo.AITF012Vo;
import kr.co.starfield.model.vo.ALBS001Vo;
import kr.co.starfield.model.vo.ALBS002Vo;
import kr.co.starfield.model.vo.ALBS003Vo;
import kr.co.starfield.model.vo.ALBS004_DVo;
import kr.co.starfield.model.vo.SEVT001Vo;
import kr.co.starfield.model.vo.SMBR001Vo;

/**
 *  REST 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */
  
@Service
public class ALBS001Service {

	@Autowired
	private ALBS001Mapper mapper;

	@Autowired
	private ACPN001Mapper acpn001mapper;
	
	@Autowired
	private SAML001Mapper saml001mapper;
	
	@Autowired
	private CampaignHttpClient campaignHttpClient;
	
	@Value("${accesstoken.lbs.uri}")
	String ACCESSTOKEN_URI;
	 
	@Value("${accesstoken.lbs.username}")
	String ACCESSTOKEN_USERNAME;
	 
	@Value("${accesstoken.lbs.password}")
	String ACCESSTOKEN_PASSWORD;
	 
	@Value("${campaign.lbs.uri}")
	String CAMPAIGN_URI;
	
	
	private static final Logger ll = LoggerFactory.getLogger(ALBS001Mapper.class);

	@Transactional(rollbackFor = Exception.class)
	public SimpleResult insertInbox(ALBS004 albs004) throws BaseException {
		SimpleResult result = new SimpleResult();
		ALBS004_DVo albs004_DVo = new ALBS004_DVo();
		
	//	try{
			mapper.insertPull(albs004);
			
			albs004_DVo.pull_req_seq = albs004.pull_req_seq;
			albs004_DVo.uuid         = albs004.uuid;
			albs004_DVo.pull_div_cd  = albs004.req_div_cd;
			albs004_DVo.cust_id      = albs004.cust_id;
			albs004_DVo.reg_usr      = albs004.reg_usr;
			
			int cnt = mapper.insertInbox(albs004_DVo);
 
			result.code = 0;
			
			if(cnt == 0){
				mapper.deletePull(albs004);
				result.code = 1; 
			}
	/*	} catch (Exception e) { 
			result.code = -1;
		} */
		
		
		
		return result;
	}

	/**
	 * inbox 리스트 
	 * @param ALBS004_DVo
	 * @return 
	 */	
	public List<ALBS004_D> getInboxList(ALBS004_DVo vo) throws BaseException {
		vo.cust_id = "N";
		if(!StringUtils.isEmpty(vo.access_token) && !vo.access_token.equals("null")){
			String cust_id = saml001mapper.getMemCustid(vo.uuid);
			vo.cust_id = cust_id;
		}
		List<ALBS004_D> inboxList = mapper.getInboxList(vo);
		
	
		return inboxList;
	}
	
	/**
	 * inbox 삭제 
	 * @param inbox_seq
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteInbox(String[] inbox_seq) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		if(inbox_seq.length == 0){
			result.code = -1;
			result.desc = "인박스 삭제 오류";
			return result;
		}
		mapper.deleteInbox(inbox_seq);
		
		result.code = 0;
		result.desc = "인박스 삭제 성공";
	
		
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public SimpleResult updateinboxUuid(ALBS004_DVo vo) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		try{
			mapper.updateinboxUuid(vo);
			
		} catch (Exception e) {
			result.code = -1;
			result.desc = "인박스 cust_id 수정 오류";
		} 
		
		result.code = 0;
		result.desc = "인박스 cust_id 수정 성공";
		
		return result;
	}

	/**
	 * 웰컴 리스트
	 * @param ALBS003Vo
	 * @return 
	 */	
	public ListResult<ALBS003> getWcPushMsgs(ALBS003Vo vo) {
		ListResult<ALBS003> result = new ListResult<>();
		
		List<ALBS003> wcPushMsgList = mapper.getWcPushMsgs(vo);
		result.list = wcPushMsgList;
	
		/*for(ALBS003Vo resultVo : wcPushMsgList){
			result.list.add(resultVo.toModel());
		}	*/
			 
		int totCnt = wcPushMsgList.size() > 0 ? wcPushMsgList.get(0).tot_cnt: 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;
		
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult reqWcPushMsg(ALBS003 albs003) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.regWcPushMsg(albs003);
		if(!StringUtils.isEmpty(albs003.cp_seq)){
			mapper.regWcPushMsgCp(albs003);
		}
		
		mapper.deleteWcPushMsgZoneId(albs003);
		for(int i = 0 ; i < albs003.selTenantList.size() ; i ++){
			albs003.selTenantList.get(i).wel_msg_push_seq = albs003.wel_msg_push_seq;
			albs003.selTenantList.get(i).bcn_cd = "01";
			
			mapper.regWcPushMsgZoneId(albs003.selTenantList.get(i));
		}
		
		result.extra = albs003.wel_msg_push_seq;
	 	return result;
	}


	public ALBS003 getWcPushMsg(String wel_msg_push_seq) {
		
		ALBS003 getWcPushMsg = mapper.getWcPushMsg(wel_msg_push_seq);
		getWcPushMsg.selTenantList = mapper.getWcPushMsgZone(wel_msg_push_seq);
		
		return getWcPushMsg;
	}

	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyWcPushMsg(ALBS003 albs003) {
	
		SimpleResult result = new SimpleResult();
		mapper.modifyWcPushMsg(albs003);
		
		mapper.deleteWcPushMsgCp(albs003);
		
		if(!StringUtils.isEmpty(albs003.cp_seq)){
			mapper.regWcPushMsgCp(albs003);
		}
		
		mapper.deleteWcPushMsgZoneId(albs003);
		for(int i = 0 ; i < albs003.selTenantList.size() ; i ++){
			albs003.selTenantList.get(i).wel_msg_push_seq = albs003.wel_msg_push_seq;
			albs003.selTenantList.get(i).bcn_cd = "01";
			
			mapper.regWcPushMsgZoneId(albs003.selTenantList.get(i));
		}
		result.extra = albs003.wel_msg_push_seq;
		return result;
	}


	public ListResult<ACPN001> getWelcomeCoupons(ACPN001Vo vo) {
		ListResult<ACPN001> result = new ListResult<>();
		
		List<ACPN001Vo> cplist = mapper.getWelcomeCoupons(vo);
		List<ACPN001Vo> cplistCnt = mapper.getWelcomeCouponsAllCnt(vo);
			
		for(ACPN001Vo acpn001vo : cplist){
			result.list.add(acpn001vo.toModel());
		}
			
		int tot_cnt = cplist.size() > 0 ? cplist.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
			
		result.paging = paging;
		if(cplistCnt.size() > 0 ){
			result.srch_cnt = "검색결과 " + tot_cnt +"개/전체 " + cplistCnt.get(0).all_tot_cnt+"개";
		}
		
		return result;
		
	}

	/**
	 * 웰컴 푸시 팝업 상세
	 * @param wc_seq
	 * @return 
	 */	 
	public ALBS005 getWelcomeDetail(String wc_seq) {
		ALBS005 albs005 = new ALBS005();
		if(wc_seq.substring(0,2).equals("WC")){
			albs005 = mapper.getWelcomeDetail(wc_seq);
			albs005.inbox_type = "W";
		}else{
			albs005 = mapper.getScenarioDetail(wc_seq);
			albs005.inbox_type = "S";
		}
		
		
		return albs005;
	}


	public ListResult<LbsZone> getApplyTenants(AITF009Vo vo) {
		ListResult<LbsZone> result = new ListResult<>();
		
		List<AITF009Vo> tenants = mapper.getApplyTenants(vo);
		List<AITF009Vo> tenantsAllCnt = mapper.getApplyTenantsAllCnt(vo);
			
		for(AITF009Vo aitf009Vo : tenants){
			result.list.add(aitf009Vo.toModel());
		}
			
  		int tot_cnt = tenants.size() > 0 ? tenants.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
		
		
		result.paging = paging;
		if(tenantsAllCnt.size()>0){
			result.srch_cnt = tenantsAllCnt.get(0).all_tot_cnt;//수정필요 ..
		}
		return result;
	}


	public ListResult<Member> getPushMembs(SMBR001Vo vo, String app_tgt_mbr_div_cd) {
		ListResult<Member> result = new ListResult<>();
		List<SMBR001Vo> pushMembs = new ArrayList<>();
		
		if(app_tgt_mbr_div_cd.equals("1")){ //일반회원
			pushMembs = mapper.getPushMembs(vo);
			
		}else if(app_tgt_mbr_div_cd.equals("3")){//관심회원
			pushMembs = mapper.getAttentionMembs(vo);
			
		}else if(app_tgt_mbr_div_cd.equals("4")){//vip
			pushMembs = mapper.getVipMembs(vo);
			
		}
		
		
		for(SMBR001Vo smbr001vo : pushMembs){
			result.list.add(smbr001vo.toModel());
		}
			
  		int tot_cnt = pushMembs.size() > 0 ? pushMembs.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
			
		result.paging = paging;
		return result;
	}


	public List<AbstMst> getAbstMsts() {
		List<AbstMst> abstMstList = mapper.getAbstMsts();
		return abstMstList;
	}


	public SimpleResult deleteInbox(String inbox_seq) {
		SimpleResult result = new SimpleResult();
		if(StringUtils.isEmpty(inbox_seq)){
			result.code = -1;
			result.desc = "삭제 할 인박스가 없습니다.";
			
			return result;
		}
		
		mapper.deleteInbox(inbox_seq);
		result.code = 0;
		result.desc = "삭제 성공";
		
		return result;
	}
 
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regScenario(ALBS001 albs001) throws BaseException {
		SimpleResult result = new SimpleResult();
		
	//	try{
		
			if(albs001.push_time_div_cd.equals("3")){
				albs001.stay_time = albs001.stay_time2;
				albs001.stay_time_cd = albs001.stay_time_cd2;
			}else if(albs001.push_time_div_cd.equals("5")){
				albs001.day_cnt = albs001.day_cnt2;
			}
			
			mapper.regScenario(albs001);
			
			if(albs001.delMemSeq.length > 0){
				albs001.delMemYn = "Y";
				mapper.regScenarioMem(albs001);
			}
			if(!StringUtils.isEmpty(albs001.cp_seq)){
				mapper.regScenarioCp(albs001);
			}
			if(albs001.selTenantList != null){
				for(int i = 0 ; i < albs001.selTenantList.size() ; i++){
					SCPN001_D scpn001_d = new SCPN001_D();
					scpn001_d = albs001.selTenantList.get(i);
					scpn001_d.scn_otb_cp_push_seq = albs001.scn_otb_cp_push_seq;
					//System.out.println(scpn001_d.zone_id);
					mapper.regScenarioZoneId(scpn001_d);
				}
			}
			
			result.extra = albs001.scn_otb_cp_push_seq;
			
			
	/*	} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.Scenario.SCENARIO_REG_FAILED);
			throw be;
		}*/
		
		return result;
	}
	
	public String getAccessToken() throws BaseException, URISyntaxException{
		
		
		String access_token = "";
		String path = ACCESSTOKEN_URI;
		String param = "{\"username\":\""+ACCESSTOKEN_USERNAME+"\",\"password\":\""+ACCESSTOKEN_PASSWORD+"\"}";
		String atk = campaignHttpClient.post(path, param, access_token);
		Map<String, Object> map = campaignHttpClient.jsonToMap(atk);
		access_token = (String) map.get("access_token");
     
		return access_token;  
		
	}
	
	
	public String regLbsCampaing(ALBS001 albs001) throws BaseException, URISyntaxException, UnsupportedEncodingException{
		String result = "";
		String lbs_campaign_id = ""; 
		String path = "";
		String e_cmp_titl = "";
		String access_token = getAccessToken();
        AITF012Vo aitf012vo = mapper.getComp();
        String zoneId = "";
        
        
        
        if(albs001.push_time_div_cd.equals("1") || albs001.scn_otb_div_cd.equals("2")){
        	return result;
        }else if(albs001.push_time_div_cd.equals("6")){
        	ACPN003Filter filter = new ACPN003Filter();
        	filter.limit = -1;
        	List<ACPN003> list = acpn001mapper.getTenants(filter);
        	if(list != null){
            	for(int i = 0 ; i < list.size() ; i++){
            		ACPN003 tnt = list.get(i);
            		if(i == 0){
            			zoneId = tnt.zone_id;
            		}else{
            			zoneId += "," + tnt.zone_id;
            		}
            	}
            }
        	
    		if( !StringUtils.isEmpty(access_token) ){ //임시관심매장 설정을 위한 체류시간 설정
            
    			for(int i = 0 ; i < 2 ; i ++){
    				
	            	String json = "";
	            	json += "[{";
	            	json += "\"id\":-1,";
	            	json += "\"app_id\":3,";
	            	json += "\"owner_group\":1,";
	            	if(i == 0){
	            		json += "\"name\":\""+ albs001.scn_cp_push_titl +"\",";
	            		json += "\"working_condition\":3,"; //3체류시간  1웰컴 
	            		json += "\"loitering_time\":"+albs001.stay_time+",";  //체류기간  . 웰컴 =0
	            	}else{
	            		json += "\"name\":\""+ e_cmp_titl +"\",";
	            		json += "\"working_condition\":2,"; //3체류시간  1웰컴 
	            		json += "\"loitering_time\":0,";  //체류기간  . 웰컴 =0
	            	}
	            	json += "\"type\":1,";  //1캠페인 2 웰컴
	            	json += "\"marketing_type\":1,";
	            	json += "\"max_count_per_customer\":10,";
	            	json += "\"off_week\":\"2/4\",";
	            	json += "\"off_day\":1,";
	            	json += "\"interval\":3,";
	            	json += "\"str_start_date\":\""+ albs001.push_strt_dt +"\",";
	            	json += "\"str_end_date\":\""+ albs001.push_end_dt +"\",";
	            	json += "\"start_time\":\"00:00\",";
	            	json += "\"end_time\":\"23:59\",";
	            	json += "\"app_specific_data\":\"\",";
	            	json += "\"zone_list\":["+zoneId+"],";  //존
	            	json += "\"company_id\":"+ aitf012vo.comp_id +",";
	            	json += "\"company_brand_id\":"+ aitf012vo.brnd_id +",";
	            	json += "\"branch_id\":"+ aitf012vo.lbs_bcn_id +"";
	            	json += "}]";
	     
	            	path = CAMPAIGN_URI;
	            	lbs_campaign_id = campaignHttpClient.post(path, json, access_token);
	            	JSONObject jobj = (JSONObject) JSONValue.parse(lbs_campaign_id);
	            	JSONArray jarr =  (JSONArray) jobj.get("campaign_id_array");
	            	Map<String, Object> map = campaignHttpClient.jsonToMap(jarr.get(0).toString());
	            	
	            	if(i == 0){
	            		e_cmp_titl = map.get("campaign_id").toString();
	            		albs001.cmp_id = map.get("campaign_id").toString(); 
	            	}else{
	            		albs001.exit_cmp_id = map.get("campaign_id").toString(); 
	            	}
    			}
    			mapper.updateCmpId(albs001);
            	
            }else{
            	BaseException be = new BaseException(ErrorCode.Scenario.TOKEN_NOT_FOUND_DATA);
    			throw be;
            }
        	
        }else{
        	if(albs001.selTenantList != null){
            	for(int i = 0 ; i < albs001.selTenantList.size() ; i++){
            		SCPN001_D tnt = albs001.selTenantList.get(i);
            		if(i == 0){
            			zoneId = tnt.zone_id;
            		}else{
            			zoneId += "," + tnt.zone_id;
            		}
            	}
            }
        	
        	if( !StringUtils.isEmpty(access_token) ){ //캠페인 insert 

            	String json = "";
            	json += "[{";
            	json += "\"id\":-1,";
            	json += "\"name\":\""+ albs001.scn_cp_push_titl +"\",";
            	json += "\"app_id\":3,";
            	json += "\"owner_group\":1,";
            	json += "\"working_condition\":3,"; //3체류시간  1웰컴 
            	json += "\"type\":1,";  //1캠페인 2 웰컴
            	json += "\"marketing_type\":1,";
            	if(albs001.push_time_div_cd.equals("2")){
            		json += "\"loitering_time\":"+albs001.stay_time+",";  //체류기간  . 웰컴 =0
            	}else{
            		json += "\"loitering_time\":"+albs001.stay_time2+",";  //체류기간  . 웰컴 =0
            	}
            	json += "\"max_count_per_customer\":10,";
            	json += "\"off_week\":\"2/4\",";
            	json += "\"off_day\":1,";
            	json += "\"interval\":3,";
            	json += "\"str_start_date\":\""+ albs001.push_strt_dt +"\",";
            	json += "\"str_end_date\":\""+ albs001.push_end_dt +"\",";
            	json += "\"start_time\":\"00:00\",";
            	json += "\"end_time\":\"23:59\",";
            	json += "\"app_specific_data\":\"\",";
            	json += "\"zone_list\":["+zoneId+"],";  //존
            	json += "\"company_id\":"+ aitf012vo.comp_id +",";
            	json += "\"company_brand_id\":"+ aitf012vo.brnd_id +",";
            	json += "\"branch_id\":"+ aitf012vo.lbs_bcn_id +"";
            	json += "}]";
     
            	path = CAMPAIGN_URI;
            	lbs_campaign_id = campaignHttpClient.post(path, json, access_token);
            	JSONObject jobj = (JSONObject) JSONValue.parse(lbs_campaign_id);
            	JSONArray jarr =  (JSONArray) jobj.get("campaign_id_array");
            	Map<String, Object> map = campaignHttpClient.jsonToMap(jarr.get(0).toString());
            	albs001.cmp_id = map.get("campaign_id").toString();
            	
            	mapper.updateCmpId(albs001);
            	
            }else{
            	BaseException be = new BaseException(ErrorCode.Scenario.TOKEN_NOT_FOUND_DATA);
    			throw be;
            }
        }
		
		return result;
	}
	
	

	
	private String deleteLbsCampaing(ALBS001 albs001) throws BaseException, URISyntaxException{
		String access_token = getAccessToken();
		
		String path = CAMPAIGN_URI;
		
		
		String atk = campaignHttpClient.delete(path, albs001.cmp_id, access_token);
		
		return atk;
	}
	
	private String deleteLbsExitCampaing(ALBS001 albs001) throws BaseException, URISyntaxException{
		String access_token = getAccessToken();
		
		String path = CAMPAIGN_URI;
		
		
		String atk = campaignHttpClient.delete(path, albs001.exit_cmp_id, access_token);
		
		return atk;
	}
	
	
	
	public ALBS001 getScenario(String scn_otb_cp_push_seq) {
		ALBS001 albs001 = mapper.getScenario(scn_otb_cp_push_seq);
		albs001.selTenantList = mapper.getScenarioZoneId(scn_otb_cp_push_seq);
		albs001.delMemSeq = mapper.getScenarioCustId(scn_otb_cp_push_seq);
		 
		if(albs001.push_time_div_cd.equals("3")){
			albs001.stay_time2 = albs001.stay_time;
			albs001.stay_time_cd2 = albs001.stay_time_cd;
			albs001.stay_time = "0";
			albs001.stay_time_cd = "0"; 
		}
		
		return albs001;
	}


	public SimpleResult modifyScenario(ALBS001 albs001) {
		SimpleResult result = new SimpleResult();
		
		if(albs001.push_time_div_cd.equals("3")){
			albs001.stay_time = albs001.stay_time2;
			albs001.stay_time_cd = albs001.stay_time_cd2;
		}else if(albs001.push_time_div_cd.equals("5")){
			albs001.day_cnt = albs001.day_cnt2;
		}
		
		mapper.modifyScenario(albs001);
		mapper.modifyScenarioCp(albs001);
		
		mapper.deleteScenarioMemb(albs001);
		if(albs001.delMemSeq.length > 0){
			albs001.delMemYn = "Y";
			mapper.regScenarioMem(albs001);
		}
		
		mapper.deleteScenarioZone(albs001);
		if(albs001.selTenantList != null){ 
			for(int i = 0 ; i < albs001.selTenantList.size() ; i++){
				SCPN001_D scpn001_d = new SCPN001_D();
				scpn001_d = albs001.selTenantList.get(i);
				scpn001_d.scn_otb_cp_push_seq = albs001.scn_otb_cp_push_seq;
				//System.out.println(scpn001_d.zone_id);
				mapper.regScenarioZoneId(scpn001_d);
			}
		}
		
		result.extra = albs001.scn_otb_cp_push_seq;
		return result;
	}

	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regScenarioUnPosting(String scn_otb_cp_push_seq, ALBS001 albs001) throws BaseException, URISyntaxException {
		SimpleResult result = new SimpleResult();
		mapper.regScenarioUnPosting(scn_otb_cp_push_seq);
		if(!albs001.push_time_div_cd.equals("1") && !albs001.scn_otb_div_cd.equals("2")){
			albs001.scn_otb_cp_push_seq = scn_otb_cp_push_seq;
			String campaingId = deleteLbsCampaing(albs001);
		}
		
		if(albs001.push_time_div_cd.equals("6")){
			albs001.scn_otb_cp_push_seq = scn_otb_cp_push_seq;
			String campaingId = deleteLbsExitCampaing(albs001);
		}
		
		
		return result;
	}


	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regScenarioPosting(String scn_otb_cp_push_seq, ALBS001 albs001) throws BaseException, URISyntaxException, UnsupportedEncodingException {
		SimpleResult result = new SimpleResult();
		mapper.regScenarioPosting(scn_otb_cp_push_seq);
		albs001.scn_otb_cp_push_seq = scn_otb_cp_push_seq;
		
		String campaingId = regLbsCampaing(albs001);
		return result;
	}

	/**
	 * 에누리 쿠폰 기본 이미지
	 * @param 
	 * @return 
	 */	 
	public ALBS001 getScnObImg(ALBS001 vo) throws BaseException {
		ALBS001 albs001 = mapper.getScnObImg(vo);
		if(albs001 == null){
			albs001 = new ALBS001();
		}
		return albs001;
	}
	
	public ListResult<ALBS001> getScenarios(ALBS001Vo vo) {
		ListResult<ALBS001> result = new ListResult<>();
		
		List<ALBS001> scenarioList = mapper.getScenarios(vo);
		result.list = scenarioList;
	
		int totCnt = scenarioList.size() > 0 ? scenarioList.get(0).tot_cnt: 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;
		
		return result;
	}


	public ListResult<Event> getEvents(SEVT001Vo vo) {
		ListResult<Event> result = new ListResult<>();
		
		List<Event> event = mapper.getEvents(vo);
		result.list = event;
		
  		int tot_cnt = event.size() > 0 ? event.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
			
		result.paging = paging;
		return result;
	}


	public SimpleResult regBanner(ALBS002 albs002) {
		SimpleResult result = new SimpleResult();
		int cnt = mapper.regBanner(albs002);
		result.extra = albs002.bn_seq;
		return result;
	}
	
	
	public SimpleResult modifyBanner(ALBS002 albs002) {
		SimpleResult result = new SimpleResult();
		
		mapper.modifyBanner(albs002);
		result.extra = albs002.bn_seq;
		return result;
	}


	public AppListResult<ALBS002> randomBanners(String map_id) {
		AppListResult<ALBS002> result = new AppListResult<>();

		int cnt = mapper.bannerCnt();
		if(cnt > 0){
			List<ALBS002> banners = mapper.randomBanners(map_id);
			if(banners.size() > 0){
				result.list = banners;
				result.code = 0;
			}else{
				result.code = -1;
			}
		}else{
			result.code = -1;
		}
		
		return result;
	}


	public ALBS002 getBannerDetail(String bn_seq) {
		ALBS002 albs002 = mapper.getBannerDetail(bn_seq);
		
		return albs002;
	}


	public SimpleResult regBannerPosting(ALBS002 albs002) {
		SimpleResult result = new SimpleResult();
		
		if(albs002.bn_exp_yn.equals("Y")){
			mapper.modifyBannerOrd(albs002);
		}
		
		int cnt = mapper.regBannerPosting(albs002);
		return result;
	}


	public ListResult<ALBS002> getBanners(ALBS002Vo vo) {
		ListResult<ALBS002> result = new ListResult<>();
		
		List<ALBS002> banner = mapper.getBanners(vo);
		result.list = banner;
		
  		int tot_cnt = banner.size() > 0 ? banner.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
			
		result.paging = paging;
		return result;
	}


	public int getBannerOrdCnt(ALBS002 albs002) {
		
		int cnt = mapper.getBannerOrdCnt(albs002);
		
		return cnt;
	}


	public SimpleResult reqWcPosting(ALBS003 albs003) throws BaseException, URISyntaxException {
		SimpleResult result = new SimpleResult();
		mapper.reqWcPosting(albs003);
		String campaingId = reqWcLbsCampaing(albs003);
		return result;
	}


	public SimpleResult reqWcUnPosting(ALBS003 albs003) throws BaseException, URISyntaxException {
		SimpleResult result = new SimpleResult();
		mapper.reqWcUnPosting(albs003);
		deleteWcLbsCampaing(albs003);
		return result;
	}


	public String reqWcLbsCampaing(ALBS003 albs003) throws BaseException, URISyntaxException{
		String result = "";
		String lbs_campaign_id = ""; 
		String path = "";
		
		String access_token = getAccessToken();
        AITF012Vo aitf012vo = mapper.getComp();
        
        String zoneId = "";
        if(albs003.selTenantList != null){
        	for(int i = 0 ; i < albs003.selTenantList.size() ; i++){
        		WelcomeZone tnt = albs003.selTenantList.get(i);
        		if(i == 0){
        			zoneId = tnt.zone_id;
        		}else{
        			zoneId += "," + tnt.zone_id;
        		}
        	}
        }
        
        if( !StringUtils.isEmpty(access_token) ){ //캠페인 insert 
        	//System.out.println(albs003.exp_strt_dt.substring(0,4)+"-"+ albs003.exp_strt_dt.substring(4,6)+"-"+ albs003.exp_strt_dt.substring(6,8));
        	String json = "";
        	json += "[{";
        	json += "\"id\":-1,";
        	json += "\"name\":\""+ albs003.push_titl +"\",";
        	json += "\"app_id\":3,";
        	json += "\"owner_group\":1,";
        	json += "\"working_condition\":1,"; //3체류시간  1웰컴
        	json += "\"type\":2,";  //1캠페인 2 웰컴
        	json += "\"marketing_type\":1,";
        	json += "\"loitering_time\":0,";  //체류기간  . 웰컴 =0
        	json += "\"max_count_per_customer\":10,";
        	json += "\"off_week\":\"2/4\",";
        	json += "\"off_day\":1,";
        	json += "\"interval\":3,";
        	json += "\"str_start_date\":\""+albs003.exp_strt_dt.substring(0,4)+"-"+ albs003.exp_strt_dt.substring(4,6)+"-"+ albs003.exp_strt_dt.substring(6,8)+"\",";  //기간
        	json += "\"str_end_date\":\""+albs003.exp_end_dt.substring(0,4)+"-"+ albs003.exp_end_dt.substring(4,6)+"-"+ albs003.exp_end_dt.substring(6,8)+"\",";   //ㄱ;긴
        	json += "\"start_time\":\"00:00\",";
        	json += "\"end_time\":\"23:59\",";
        	json += "\"app_specific_data\":\"\",";
        	json += "\"zone_list\":["+zoneId+"],";  //존
        	json += "\"company_id\":"+ aitf012vo.comp_id +",";
        	json += "\"company_brand_id\":"+ aitf012vo.brnd_id +",";
        	json += "\"branch_id\":"+ aitf012vo.lbs_bcn_id +"";
        	json += "}]";
        	//
        	
        	path = CAMPAIGN_URI;
        	lbs_campaign_id = campaignHttpClient.post(path, json, access_token);
        	JSONObject jobj = (JSONObject) JSONValue.parse(lbs_campaign_id);
        	JSONArray jarr =  (JSONArray) jobj.get("campaign_id_array");
        	Map<String, Object> map = campaignHttpClient.jsonToMap(jarr.get(0).toString());
        	albs003.cmp_id = map.get("campaign_id").toString();
        	
        	mapper.updateWcCmpId(albs003);
        	
        }else{
        	BaseException be = new BaseException(ErrorCode.Scenario.TOKEN_NOT_FOUND_DATA);
			throw be;
        }
        
		
		return result;
	}
	
	
	private String deleteWcLbsCampaing(ALBS003 albs003) throws BaseException, URISyntaxException{
		String access_token = getAccessToken();
		String atk = "";
		String path = CAMPAIGN_URI;
		atk = campaignHttpClient.delete(path, albs003.cmp_id, access_token);
		
		return atk;
	}


	public int pushMembCnt(String type) {
		int cnt = 0;
		if(type.equals("1")){ //일반회원
			cnt = mapper.getMembCnt();
			
		}else if(type.equals("3")){//관심회원
			cnt = mapper.getAttentionMembCnt();
			
		}else if(type.equals("4")){//vip
			cnt = mapper.getVipMembCnt();
			
		}
		
		return cnt;
	}


	public ListResult<ACPN003> getSnTenants(ACPN003Filter filter) {
		ListResult<ACPN003> result = new ListResult<>();
		
		List<ACPN003> list = mapper.getSnTenants(filter);
		result.list = list; 
			
		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(filter.offset, filter.limit, totCnt);
		result.paging = paging;
	
		return result;
	}
}
