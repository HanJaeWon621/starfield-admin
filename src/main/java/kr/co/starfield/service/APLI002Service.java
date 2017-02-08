package kr.co.starfield.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.Utils;
import kr.co.starfield.mapper.APLI002Mapper;
import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.LocInfoManage;
import kr.co.starfield.model.LocInfoManage2;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.Utils;
import kr.co.starfield.mapper.APLI001Mapper;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.LocInfoManage;
import kr.co.starfield.model.LocInfoManageExcel;
import kr.co.starfield.model.LocInfoManageFilter;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.ACPN001Vo;

/**
 * 개인 위치정보 삭제 관리
 *
 * Copyright Copyright (c) 2016 Company Paprika
 *
 * @author ldh
 * @version 1.0,
 * @see
 * @date 2016.10.10
 */

@Service
public class APLI002Service {

	@Autowired
	private APLI002Mapper apli002Mapper;

	private static final Logger ll = LoggerFactory.getLogger(APLI002Service.class);

	/**
	 * LocInfoManage 목록 조회
	 * 
	 * @param LocInfoManageFilter
	 * @return
	 * @throws BaseException
	 */
	/*
	public ListResult<LocInfoManage> getLocInfoManageList2(LocInfoManage filter, boolean masking) throws BaseException {

		ListResult<LocInfoManage> result = new ListResult<LocInfoManage>();

		result.list.addAll(apli002Mapper.getLocInfoManageList(filter));

		if (masking) {
			for (LocInfoManage locInfoManage : result.list) {
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

				StringBuilder masked2 = new StringBuilder();
				for (int i = 0; i < locInfoManage.card_no.length(); i++) {
					char c = locInfoManage.card_no.charAt(i);

					if (i >= 3 && i <= 6) {
						masked2.append('*');

					} else {
						masked2.append(c);
					}
				}

				locInfoManage.card_no = masked2.toString();
			}
		}

		if (filter.limit > 0) {
			int tot_cnt = apli002Mapper.getTotalCount(filter);

			Paging paging = new Paging(filter.offset, filter.limit, tot_cnt);

			result.paging = paging;
		}

		return result;
	}
	*/
	public ListResult<LocInfoManage> getLocInfoManageList(LocInfoManage vo, boolean masking) throws BaseException {

		ListResult<LocInfoManage> result = new ListResult<>();

		List<LocInfoManage> cpList = apli002Mapper.getLocInfoManageList(vo);
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

	/**
	 * LocInfoManage 수정
	 * 
	 * @param locInfoManage
	 * @return
	 * @throws BaseException
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deletePersonLocInfo(LocInfoManage locInfoManage) throws BaseException {
		SimpleResult result = new SimpleResult();

		apli002Mapper.deletePersonLocInfo(locInfoManage);// 삭제
		apli002Mapper.modifyLocInfoManage(locInfoManage);// 정보 넣기
		
		
		return result;
	}

}
