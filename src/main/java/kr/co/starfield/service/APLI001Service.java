package kr.co.starfield.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.Utils;
import kr.co.starfield.mapper.APLI001Mapper;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.LocInfoManage;
import kr.co.starfield.model.LocInfoManageExcel;
import kr.co.starfield.model.LocInfoManageFilter;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;


/**
 * 개인 위치정보 삭제 관리
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.10.10
 */

@Service
public class APLI001Service {

	@Autowired
	private APLI001Mapper apli001Mapper;
	
	private static final Logger ll = LoggerFactory.getLogger(APLI001Service.class);
	
	/**
	 * LocInfoManage 등록
	 * @param locInfoManage
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regLocInfoManage(LocInfoManage locInfoManage) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		apli001Mapper.regLocInfoManage(locInfoManage);
		
		return result;
	}
	
	/**
	 * LocInfoManage 목록 조회
	 * @param LocInfoManageFilter
	 * @return 
	 * @throws BaseException 
	 */
	
	public ListResult<LocInfoManage> getLocInfoManageList(LocInfoManageFilter filter, boolean masking) throws BaseException {
		
		ListResult<LocInfoManage> result = new ListResult<LocInfoManage>();
			
		result.list.addAll(apli001Mapper.getLocInfoManageList(filter));
			
		
		if(masking) {
			for(LocInfoManage locInfoManage : result.list) {
				locInfoManage.name = Utils.Str.maskingName(locInfoManage.name);
				
		
				StringBuilder masked = new StringBuilder();
				for (int i = 0; i < locInfoManage.phone_num.length(); i++) {
			         char c = locInfoManage.phone_num.charAt(i);
			         
			         if (i >= 3 && i <= 6) {
			        	 masked.append('*');
			            
			         } else {
			        	 masked.append(c);
			         }
				}
				
				locInfoManage.phone_num = masked.toString();
			}
		}
		
		
		if(filter.limit > 0){
			int tot_cnt = apli001Mapper.getTotalCount(filter);
	
			Paging paging = new Paging(filter.offset, filter.limit, tot_cnt);
				
			result.paging = paging;
		}
		
		return result;
	}
	
	
	/**
	 * LocInfoManage 상세
	 * @param filter
	 * @return
	 * @throws BaseException 
	 */
	public LocInfoManage getLocInfoManage(LocInfoManageFilter filter, boolean masking) throws BaseException {
		
		LocInfoManage locInfoManage = apli001Mapper.getLocInfoManage(filter);
		
		if(locInfoManage == null) {
			BaseException be = new BaseException(ErrorCode.Member.LOCATION_INFO_NOT_FOUND_DATA);
			throw be;
		}
		
		if(masking) {
			locInfoManage.name = Utils.Str.maskingName(locInfoManage.name);
			
			StringBuilder masked = new StringBuilder();

			masked = new StringBuilder();
			for (int i = 0; i < locInfoManage.phone_num.length(); i++) {
		         char c = locInfoManage.phone_num.charAt(i);
		         
		         if (i >= 3 && i <= 6) {
		        	 masked.append('*');
		            
		         } else {
		        	 masked.append(c);
		         }
			}
			
			locInfoManage.phone_num = masked.toString();
		}
		

		return locInfoManage;
	}
	
	/**
	 * LocInfoManage 수정
	 * @param locInfoManage
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyLocInfoManage(LocInfoManage locInfoManage) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		apli001Mapper.modifyLocInfoManage(locInfoManage);
		
		return result;
	}
	
	/**
	 * LocInfoManage Excel
	 * @param 
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public ArrayList<LocInfoManageExcel> getLocInfoManageExcelList(String bcn_cd, boolean masking) throws BaseException {
		
		LocInfoManageFilter filter = new LocInfoManageFilter();
		filter.bcn_cd = bcn_cd;

		ArrayList<LocInfoManageExcel> locInfoManageExcelList = apli001Mapper.getLocInfoManageExcelList(filter);
		
		
		if(masking) {
			//TODO 관리자 권한에 따른 이름 마스킹
			for(LocInfoManageExcel locInfoManageExcel : locInfoManageExcelList) {
				
				locInfoManageExcel.name = Utils.Str.maskingName(locInfoManageExcel.name);
				
				
				StringBuilder masked = new StringBuilder();
				for (int i = 0; i < locInfoManageExcel.phone_num.length(); i++) {
			         char c = locInfoManageExcel.phone_num.charAt(i);
			         
			         if (i >= 3 && i <= 6) {
			        	 masked.append('*');
			            
			         } else {
			        	 masked.append(c);
			         }
				}
				
				locInfoManageExcel.phone_num = masked.toString();
			}
		}
		
		for(LocInfoManageExcel locInfoManageExcel : locInfoManageExcelList) {
			
			locInfoManageExcel.name = Utils.Str.maskingName(locInfoManageExcel.name);
			
			
			StringBuilder masked = new StringBuilder();
			for (int i = 0; i < locInfoManageExcel.phone_num.length(); i++) {
		         char c = locInfoManageExcel.phone_num.charAt(i);
		         
		         if (i >= 3 && i <= 6) {
		        	 masked.append('*');
		            
		         } else {
		        	 masked.append(c);
		         }
			}
			
			locInfoManageExcel.phone_num = masked.toString();
			
			switch(locInfoManageExcel.sts){
				case 0:
					locInfoManageExcel.status = "접수";
					break;
				case 1:
					locInfoManageExcel.status = "확인중";
					break;
				case 8:
					locInfoManageExcel.status = "삭제불가";
					break;
				case 9:
					locInfoManageExcel.status = "삭제완료";
					break;
			}
		}
		
		return locInfoManageExcelList;
	}
}
