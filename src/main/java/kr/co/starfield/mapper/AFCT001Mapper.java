/*
 * SFCT001Mapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.List;

import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.Facility;
import kr.co.starfield.model.FacilityDetail;
import kr.co.starfield.model.FacilityFilter;
import kr.co.starfield.model.FacilityForExcel;
import kr.co.starfield.model.FacilityGroupFilter;
import kr.co.starfield.model.FacilityForList;
import kr.co.starfield.model.FacilityGroup;
import kr.co.starfield.model.vo.SFCT001Vo;
import kr.co.starfield.model.vo.SFCT001_DVo;

/**
 *  AFCT001Mapper(facility) interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author hhLee
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

public interface AFCT001Mapper {
	
	// 편의시설 그룹 목록 조회 (어드민용)
	public List<FacilityForList> getFacilityGroupListForAdmin(FacilityGroupFilter filter);
	
	// 편의시설 그룹 목록 조회 (엑셀용)
	public List<FacilityForExcel> getFacilityGroupListForExcel(FacilityGroupFilter filter);
	
	// 편의시설 그룹 상세 조회
	public FacilityGroup getFacilityGroup(String conv_faci_seq);
	
	// 편의시설 그룹 등록
	public void regFacilityGroup(SFCT001Vo vo);
	
	// 편의시설 그룹 수정
	public int modifyFacilityGroup(SFCT001Vo vo);
	
	// 편의시설 그룹 삭제
	public void deleteFacilityGroup(CommonDeleteModel facilityGroupDelete);
	
	
	// 편의시설 상세 목록 조회 (어드민용)
	public List<FacilityDetail> getFacilityDetailListForAdmin(FacilityFilter filter);
	
	// 편의시설 상세 상세 조회
	public FacilityDetail getFacilityDetail(String conv_faci_dtl_seq);
	
	// 편의시설 상세 등록
	public void regFacilityDetail(SFCT001_DVo vo);
	
	// 편의시설 상세 수정
	public int modifyFacilityDetail(SFCT001_DVo vo);
	
	// 편의시설 상세 삭제
	public void deleteFacilityDetail(CommonDeleteModel facilityGroupDelete);

	
	// 편의시설 목록 조회 (레디스용)
	public List<Facility> getFacilityList(SFCT001Vo vo);
	
	// 편의시설 디테일 목록 조회 (레디스용)
	public List<SFCT001_DVo> getFacilityDetailList(SFCT001_DVo vo);
}
