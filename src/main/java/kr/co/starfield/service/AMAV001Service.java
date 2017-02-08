package kr.co.starfield.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.mapper.AMAV001Mapper;
import kr.co.starfield.model.AppVer;
import kr.co.starfield.model.AppVerFilter;
import kr.co.starfield.model.AppVerListResult;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;


/**
 * 앱 버전 관리
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.10.12
 */

@Service
public class AMAV001Service {

	@Autowired
	private AMAV001Mapper amav001Mapper;
	
	private static final Logger ll = LoggerFactory.getLogger(AMAV001Service.class);
	
	/**
	 * AppVer 등록
	 * @param model
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regAppVer(AppVer appVer) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		amav001Mapper.regAppVer(appVer);
		
		return result;
	}
	
	/**
	 * AppVer 목록 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	
	public AppVerListResult<AppVer> getAppVerList(AppVerFilter filter) throws BaseException {
		
		AppVerListResult<AppVer> result = new AppVerListResult<>();
			
		result.Android_ver = amav001Mapper.getAndroidVer(filter.bcn_cd);
		result.iOS_ver = amav001Mapper.getiOSVer(filter.bcn_cd);
		result.list.addAll(amav001Mapper.getAppVerList(filter));
		
		
		if(filter.limit > 0){
			int tot_cnt = amav001Mapper.getTotalCount(filter);
	
			Paging paging = new Paging(filter.offset, filter.limit, tot_cnt);
				
			result.paging = paging;
		}
		
		return result;
	}
	
	
	/**
	 * AppVer 수정
	 * @param model
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyAppVer(AppVer appVer) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		amav001Mapper.modifyAppVer(appVer);
		
		return result;
	}
	
	/**
	 * AppVer 삭제 
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteAppVer(CommonDeleteModel commonDeleteModel) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		amav001Mapper.deleteAppVer(commonDeleteModel);
		
		return result;
	}
}
