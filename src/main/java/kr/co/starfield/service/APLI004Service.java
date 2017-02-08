package kr.co.starfield.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.Utils;
import kr.co.starfield.mapper.APLI001Mapper;
import kr.co.starfield.mapper.APLI004Mapper;
import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.APPLOG;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.LocInfoManage;
import kr.co.starfield.model.LocInfoManageExcel;
import kr.co.starfield.model.LocInfoManageFilter;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SAML002;
import kr.co.starfield.model.SAML007;
import kr.co.starfield.model.SMBR001;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.SAML002Vo;
import kr.co.starfield.model.vo.SMBR001Vo;


/**
 * 개인 위치정보 삭제 관리
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author ldh
 * @version	1.0,
 * @see
 * @date 2016.10.10
 */

@Service
public class APLI004Service {

	@Autowired
	private APLI004Mapper apli004Mapper;

	public ListResult<SAML007> getLocationReqViewList(SAML007 saml007) {
		ListResult<SAML007> result = new ListResult<>();
		
		List<SAML007> locationReqView = apli004Mapper.getLocationReqViewList(saml007);
		result.list = locationReqView;

		
		int totCnt = locationReqView.size() > 0 ? locationReqView.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(saml007.offset, saml007.limit, totCnt);
		result.paging = paging;
	
		return result;
	}

	public ListResult<SMBR001> getRequester(SMBR001Vo vo) {
		ListResult<SMBR001> result = new ListResult<>();
		
		List<SMBR001> getRequester = apli004Mapper.getRequester(vo);
		result.list = getRequester;

		
		int totCnt = getRequester.size() > 0 ? Integer.parseInt(getRequester.get(0).tot_cnt) : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;
	
		return result;
	}
 
	public SimpleResult regLocationReqView(SAML007 saml007) {
		SimpleResult result = new SimpleResult();
		apli004Mapper.regLocationReqView(saml007);
		return result;
	}

	public ListResult<SAML002> getLocationReqViewExcel(String mem_seq) {
		ListResult<SAML002> result = new ListResult<>();
		List<SAML002> saml002 = apli004Mapper.getLocationReqViewExcel(mem_seq);
		result.list = saml002;
		return result;
	}

	public SimpleResult regLocationDnYn(SAML007 saml007) {
		SimpleResult result = new SimpleResult();
		apli004Mapper.regLocationDnYn(saml007);

		if(!StringUtils.isEmpty(saml007.use_dttm)){
			result.extra = saml007.use_dttm;
		}
		return result;
	}

	public ListResult<APPLOG> getAppLogList(APPLOG applog) {
		ListResult<APPLOG> result = new ListResult<>();
		
		List<APPLOG> locationReqView = apli004Mapper.getAppLogList(applog);
		result.list = locationReqView;

		
		int totCnt = locationReqView.size() > 0 ? locationReqView.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(applog.offset, applog.limit, totCnt);
		result.paging = paging;
	
		return result;
	}

	public ListResult<SAML002> getLocationUseList(SAML002Vo vo) {
		ListResult<SAML002> result = new ListResult<>();
		
		List<SAML002> locationUseList = apli004Mapper.getLocationUseList(vo);
		result.list = locationUseList;

		
		int totCnt = locationUseList.size() > 0 ? locationUseList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;
	
		return result;
	}
	
}
