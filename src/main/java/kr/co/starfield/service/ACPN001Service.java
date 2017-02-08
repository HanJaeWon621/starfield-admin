package kr.co.starfield.service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.mapper.ACPN001Mapper;
import kr.co.starfield.mapper.ACPN002Mapper;
import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.ACPN003;
import kr.co.starfield.model.ACPN003Filter;
import kr.co.starfield.model.ACPN005;
import kr.co.starfield.model.Category;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SCPN001;
import kr.co.starfield.model.SCPN001_D;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.TenantMapping;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.SCPN002_D2Vo;


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
public class ACPN001Service {

	@Autowired
	private ACPN001Mapper mapper;
	
	private static final Logger ll = LoggerFactory.getLogger(ACPN002Mapper.class);

	/**
	 * 스타일 세트 리스트
	 * @param ACPN001Vo
	 * @return 
	 */
	public ListResult<ACPN001> getCoupons(ACPN001Vo vo) {
		
		ListResult<ACPN001> result = new ListResult<>();
		
		List<ACPN001> cpList = mapper.getCoupons(vo);
		result.list = cpList;
		/*for(ACPN001Vo resultVo : cpList){
			result.list.add(resultVo.toModel());
		}	*/
			
		int totCnt = cpList.size() > 0 ? cpList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;
	
		return result;
	}
	
	/**
	 * 쿠폰 테넌트 팝업 리스트
	 * @param ACPN003Filter
	 * @return 
	 */	
	public ListResult<ACPN003> getTenants(ACPN003Filter filter) {
			
		ListResult<ACPN003> result = new ListResult<>();
		
		List<ACPN003> list = mapper.getTenants(filter);
		result.list = list;
			
		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(filter.offset, filter.limit, totCnt);
		result.paging = paging;
	
		return result;
	}
	
	/**
	 * 쿠폰 관리 등록
	 * @param ACPN001
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult reqCoupon(ACPN001 acpn001) {
		 	
		SimpleResult result = new SimpleResult();
	 	mapper.reqCoupon(acpn001);
	 	result.extra = acpn001.cp_seq;
	 	if(!acpn001.cp_kind_cd.equals("3")){
	 		
	 		if(!StringUtils.isEmpty(acpn001.all_tnt) && acpn001.all_tnt.equals("Y")){//테넌트 전체 선택
	 			ACPN003Filter filter = new ACPN003Filter();
	 			filter.limit = -1;
	 			List<ACPN003> list = mapper.getTenants(filter);
	 			for(ACPN003 acpn003 : list){
	 				SCPN001_D scpn001_D = new SCPN001_D();
	 				scpn001_D.cp_seq = acpn001.cp_seq;
	 				scpn001_D.tnt_seq = acpn003.tnt_seq;
	 				scpn001_D.busi_tnt_cd = acpn003.busi_tnt_cd;
	 				mapper.reqTenant(scpn001_D);
	 			}
	 		}else{
	 			List<SCPN001_D> tenantList = acpn001.selTenantList;
	 			for(SCPN001_D scpn001_D : tenantList){
	 				scpn001_D.cp_seq = acpn001.cp_seq;
	 				mapper.reqTenant(scpn001_D);
	 			}	
	 		}
	 		
	 		
	 	}
	 	
	 	return result;
	}
	
	/**
	 * 쿠폰 상세 조회
	 * @param cp_seq
	 * @return 
	 */	
	public ACPN001 getCoupon(String cp_seq) throws BaseException {
		
		ACPN001 coupon = mapper.getCoupon(cp_seq);
		
	//	List<Category> category = mapper.getCategory();
	//	coupon.category = category; 
		
		if(!StringUtils.isEmpty(coupon)){
			List<SCPN001_D> list = mapper.cpTenant(cp_seq);
			coupon.selTenantList = list;
			
		}
		
		return coupon;
		
	}
	
	/**
	 * 쿠폰 복사
	 * @param cp_seq
	 * @return 
	 */	 
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult copyCoupon(String cp_seq) throws BaseException {
		SimpleResult result = new SimpleResult();
		ACPN001 vo = new ACPN001();
		vo.cp_seq = cp_seq;
		mapper.copyCoupon(cp_seq);
		
		// 쿠폰 게시 로그
		/*
		String log_type = "A";
		String val = cp_seq;
		String evt_div_cd1="A1000";
		*/
		//sopr001service.appActionLogAdm(log_type, evt_div_cd1, val);
		
		return result;
	
	}
	/**
	 * 가승인 취소 처리
	 * @param log_seq
	 * @return 
	 */	 
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult cnclCpUsePreAppr(String log_seq) throws BaseException {
		SimpleResult result = new SimpleResult();
		ACPN001 vo = new ACPN001();
		vo.log_seq = log_seq;
		mapper.cnclCpUsePreAppr(vo);
		
		result.code=Integer.parseInt(vo.result);		
		return result;
	}
	/**
	 * 대사 처리
	 * @param log_seq
	 * @return 
	 */	 
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult compareEqaulCp(String log_seq) throws BaseException {
		SimpleResult result = new SimpleResult();
		ACPN001 vo = new ACPN001();
		vo.log_seq = log_seq;
		mapper.compareEqaulCp(vo);
		
		result.code=Integer.parseInt(vo.result);		
		return result;
	
	}
	/**
	 * 쿠폰 관리 수정
	 * @param ACPN001
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyCoupon(ACPN001 acpn001) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.modifyCoupon(acpn001);
	 	
	 	if(!acpn001.cp_kind_cd.equals("3")){
	 		List<SCPN001_D> tenantList = acpn001.selTenantList;
	 		if(tenantList.size() > 0){
	 			mapper.deleteTenant(acpn001.cp_seq);

	 			for(SCPN001_D scpn001_D : tenantList){
	 				scpn001_D.cp_seq = acpn001.cp_seq;
	 				mapper.reqTenant(scpn001_D);
	 			}	
	 		}
	 	}
	 	result.extra =acpn001.cp_seq;
	 	return result;
	}
	
	
	public ListResult<Category> getCategorys() throws BaseException, UnsupportedEncodingException {
		ListResult<Category> result = new ListResult<>();
		
		List<Category> list = mapper.getCategorys();
		result.list = list;
	
		return result;
	}

	/**
	 * 쿠폰다운로드 내역조회
	 * @param cp_seq
	 * @return 
	 */	 
	public ListResult<ACPN005> getDownCoupons(ACPN005 acpn005) {
		
		ListResult<ACPN005> result = new ListResult<>();
		
		List<ACPN005> list = mapper.getDownCoupons(acpn005);
		result.list = list;
			
		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(acpn005.offset, acpn005.limit, totCnt);
		result.paging = paging;
	
		return result;
		
	}
	
	
	public ListResult<ACPN005> getUseCoupons(ACPN005 acpn005) {
		ListResult<ACPN005> result = new ListResult<>();
		
		List<ACPN005> list = mapper.getUseCoupons(acpn005);
		result.list = list;
			
		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(acpn005.offset, acpn005.limit, totCnt);
		result.paging = paging;
	
		return result;
	}
	
	public ListResult<ACPN005> getUseWaitCoupons(ACPN005 acpn005) {
		ListResult<ACPN005> result = new ListResult<>();
		
		List<ACPN005> list = mapper.getUseWaitCoupons(acpn005);
		result.list = list;
			
		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(acpn005.offset, acpn005.limit, totCnt);
		result.paging = paging;
	
		return result;
	}
	public ListResult<ACPN005> getUseCompareCoupons(ACPN005 acpn005) {
		ListResult<ACPN005> result = new ListResult<>();
		
		List<ACPN005> list = mapper.getUseCompareCoupons(acpn005);
		result.list = list;
			
		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(acpn005.offset, acpn005.limit, totCnt);
		result.paging = paging;
	
		return result;
	}
	/**
	 * 쿠폰 다운로드 내역 상세
	 * @param cp_seq
	 * @return 
	 */	 
	public SCPN001 getCouponDownDetail(SCPN001 scpn001) {

		SCPN001 getCouponDownDetail = mapper.getCouponDownDetail(scpn001);
		
		if(!StringUtils.isEmpty(getCouponDownDetail)){
			List<SCPN001_D> list = mapper.cpTenant(scpn001.cp_seq);
			getCouponDownDetail.selTenantList = list;
		}
		return getCouponDownDetail;
	}

	@Transactional(rollbackFor = Exception.class)
	public SimpleResult updateCouponUseSts(SCPN002_D2Vo vo) {
		SimpleResult result = new SimpleResult();
		mapper.updateCouponUseSts(vo);
		return result;
	}

	/**
	 * 테넌트 매핑 리스트
	 * @param TenantMapping
	 * @return 
	 */	 
	public Map<Object, Object> tntMappings(TenantMapping tenantmapping) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		
		List<TenantMapping> tenantlist = mapper.getMappingTenants(tenantmapping);
		List<TenantMapping> salestenantlist = mapper.getSalesTenants(tenantmapping);
		
		TenantMapping tenantlistCnt = mapper.getMappingTenantsCnt(tenantmapping);
		String all_tnt_tot_cnt= tenantlistCnt.all_tnt_tot_cnt;
		String all_faci_tot_cnt= tenantlistCnt.all_faci_tot_cnt;
		String all_busi_tnt_cnt = tenantlistCnt.all_busi_tnt_cnt;
		String all_zone_cnt = tenantlistCnt.all_zone_cnt;
		String all_faci_cnt = tenantlistCnt.all_faci_cnt;
		
		map.put("all_busi_tnt_cnt", "전체테넌트 " + all_tnt_tot_cnt + "개 매핑테넌트  " + all_busi_tnt_cnt +"개"); 
		map.put("all_zone_cnt", "전체테넌트 " +  all_tnt_tot_cnt    + "개 매핑테넌트  " + all_zone_cnt +"개");
		map.put("all_faci_cnt", "전체편의시설 " + all_faci_tot_cnt    + "개 매핑편의시설  " + all_faci_cnt +"개");
		
		map.put("tenantlist", tenantlist);
		map.put("salestenantlist", salestenantlist);
		
		return map;
	}
	
	/**
	 * 테넌트 매핑  수정
	 * @param TenantMapping
	 * @return 
	 */	 
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyTntMapping(TenantMapping tenantmapping) {
		SimpleResult result = new SimpleResult();
		List<TenantMapping> tenantlist = null;
		
		//busi_tnt_cd 중복 체크 
		if(!StringUtils.isEmpty(tenantmapping.busi_tnt_cd)){
			TenantMapping vo = new TenantMapping();
			vo.busi_tnt_cd = tenantmapping.busi_tnt_cd;
			tenantlist = mapper.getMappingTenants(vo);
			
			if(tenantlist.size() > 0){
				result.code = -1;
				result.desc = "영업테넌트코드("+tenantmapping.busi_tnt_cd+")는 "+tenantlist.get(0).tnt_nm_ko+" 테넌트에 이미 등록 되어있습니다.";
				return result;
			}
		}
		
		if(StringUtils.isEmpty(tenantmapping.busi_tnt_cd)){
			mapper.modifyTntMapping(tenantmapping);
			mapper.deleteTntMapping(tenantmapping);
		}else{
			mapper.modifyTntMapping(tenantmapping);
			mapper.reqTntMapping(tenantmapping);
		}
		
		return result;
	}

	/**
	 * lbs 존 매핑 리스트
	 * @param TenantMapping
	 * @return 
	 */	 
	public Map<Object, Object> tntZoneMappings(TenantMapping tenantmapping) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		List<TenantMapping> tenantlist = mapper.getMappingTenants(tenantmapping);
		List<TenantMapping> lbslist = mapper.getLbsZones(tenantmapping);
		
		TenantMapping tenantlistCnt = mapper.getMappingTenantsCnt(tenantmapping);
		String all_tnt_tot_cnt= tenantlistCnt.all_tnt_tot_cnt;
		String all_faci_tot_cnt= tenantlistCnt.all_faci_tot_cnt;
		String all_busi_tnt_cnt = tenantlistCnt.all_busi_tnt_cnt;
		String all_zone_cnt = tenantlistCnt.all_zone_cnt;
		String all_faci_cnt = tenantlistCnt.all_faci_cnt;
		
		map.put("all_busi_tnt_cnt", "전체테넌트 " + all_tnt_tot_cnt + "개 매핑테넌트  " + all_busi_tnt_cnt +"개");
		map.put("all_zone_cnt", "전체테넌트 " +  all_tnt_tot_cnt    + "개 매핑테넌트  " + all_zone_cnt +"개");
		map.put("all_faci_cnt", "전체편의시설 " + all_faci_tot_cnt    + "개 매핑편의시설  " + all_faci_cnt +"개");
		
		map.put("tenantlist", tenantlist);
		map.put("lbslist", lbslist);
		
		return map;
	}
	
	/**
	 * lbs 존 매핑  수정
	 * @param TenantMapping
	 * @return 
	 */	 
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyLbsZoneMapping(TenantMapping tenantmapping) {
		SimpleResult result = new SimpleResult();
		List<TenantMapping> tenantlist = null;
		
		//zone_id 중복 체크 
		if(!StringUtils.isEmpty(tenantmapping.zone_id)){
			TenantMapping vo = new TenantMapping();
			vo.zone_id = tenantmapping.zone_id;
			tenantlist = mapper.getMappingTenants(vo);
			
			if(tenantlist.size() > 0){
				result.code = -1;
				result.desc = "존아이디("+tenantmapping.zone_id+")는 "+tenantlist.get(0).tnt_nm_ko+" 테넌트에 이미 등록 되어있습니다.";
				return result; 
			}
		}
		
		mapper.modifyLbsZoneMapping(tenantmapping);
		
		return result;
	}

	/**
	 * lbs 존 전체 매핑
	 * @param
	 * @return 
	 */	 
	public SimpleResult allMapping() {
		SimpleResult result = new SimpleResult();
		mapper.allMapping();
		
		return result;
	}

	/**
	 * lbs 편의시설 존 매핑 리스트
	 * @param
	 * @return 
	 */	
	public Map<Object, Object> faciZoneMappings(TenantMapping tenantmapping) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		List<TenantMapping> facilist = mapper.getFacis(tenantmapping);
		List<TenantMapping> lbslist = mapper.getLbsFacis(tenantmapping);
		
		map.put("facilist", facilist);
		map.put("lbslist", lbslist);
		
		return map;
	}
	
	/**
	 * lbs 편의시설 존 매핑  수정
	 * @param TenantMapping
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyFaciZoneMapping(TenantMapping tenantmapping) {
		SimpleResult result = new SimpleResult();
		List<TenantMapping> tenantlist = null;
		
		//zone_id 중복 체크 
		if(!StringUtils.isEmpty(tenantmapping.zone_id)){
			TenantMapping vo = new TenantMapping();
			vo.zone_id = tenantmapping.zone_id;
			tenantlist = mapper.getFacis(vo);
			
			if(tenantlist.size() > 0){
				result.code = -1;
				result.desc = "존아이디("+tenantmapping.zone_id+")는 "+tenantlist.get(0).conv_faci_nm_ko+" 편의시설에 이미 등록 되어있습니다.";
				return result; 
			}
		}
		
		mapper.modifyFaciZoneMapping(tenantmapping);
		
		return result;
	}

	/**
	 * 자동회서형 쿠폰 미노출
	 * @param cp_seq
	 * @return 
	 */	 
	public SimpleResult unposted(String cp_seq) {
		SimpleResult result = new SimpleResult();
		mapper.unposted(cp_seq);
		return result;
	}

	/**
	 * 에누리 쿠폰 기본 이미지
	 * @param 
	 * @return 
	 */	 
	public ACPN001 getCouponImg() throws BaseException {
		ACPN001 acpn001 = mapper.getCouponImg();
		if(acpn001 == null){
			acpn001 = new ACPN001();
		}
		return acpn001;
	}
	/**
	 * 쿠폰 다운로드 내역 엑셀 다운
	 * @param ACPN005
	 * @return 
	 */	
	
	public ACPN005[] getExDownCoupons(ACPN005 acpn005) {
		ACPN005[] dwList = mapper.getExDownCoupons(acpn005);
		return dwList;
	}


	public SimpleResult modifyCpUseProcess(SCPN001 scpn001) {
		SimpleResult result = new SimpleResult();
		mapper.modifyCpUseProcess(scpn001);
		return result;
	}

	/**
	 * 쿠폰 사용 내역 상세
	 * @param SCPN001
	 * @return 
	 */	 
	public SCPN001 getCouponUseDetail(SCPN001 scpn001) {
		SCPN001 getCouponDownDetail = mapper.getCouponUseDetail(scpn001);
		
		if(!StringUtils.isEmpty(getCouponDownDetail)){
			List<SCPN001_D> list = mapper.cpTenant(scpn001.cp_seq);
			getCouponDownDetail.selTenantList = list;
		}
		return getCouponDownDetail;	
	}

	/**
	 * 쿠폰 사용처리 변경
	 * @param SCPN001
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyCpUseProcess2(SCPN001 scpn001) throws BaseException {
		SimpleResult result = new SimpleResult();
		int cnt = mapper.updateCpCancel(scpn001); 
		if(cnt > 0){
			cnt += mapper.insertCpCancel(scpn001); 
		}
		result.code = cnt;
		
		return result;
	}

	/**
	 * 쿠폰 엑셀 업로드
	 * @param MultipartFile
	 * @return 
	 */	
	public SimpleResult excelUploadCoupon(MultipartFile file) throws BaseException, Exception{
		
		SimpleResult result = new SimpleResult();
	
		ACPN001[] voList = null;
		voList = getReadExcel(file, voList);
		String errIdx = "";
		for(int i = 0; i < voList.length; i++) {
			
			ACPN001 excel = voList[i];
			if(excel.excel_err_yn.equals("Y")){
				if(StringUtils.isEmpty(errIdx)){
					errIdx = Integer.toString(i);
				}else{
					errIdx += " ,"+ Integer.toString(i);
				}
			}else{
				mapper.insertExcelCoupon(voList[i]); 
			}
		}
		
		result.extra = errIdx;
		result.code = 0;
		
		return result;
	}

	
	public ACPN001[] getReadExcel(MultipartFile mFile, ACPN001[] voList) throws BaseException, Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String ext = mFile.getOriginalFilename().split("\\.")[1];
		
		ll.info("file content type is {}", mFile.getContentType());
		ll.info("file extention is {}", ext);
		
//		if(!mFile.getContentType().contains("excel") || !ext.contains("xls")) {
		if(!ext.contains("xls")) {
			throw new BaseException(ErrorCode.File.MIME_TYPE_NOT_MATCH_BY_EXCEL);
		}
		
		if(ext.equals("xlsx")) {
			 
			XSSFWorkbook workbook = new XSSFWorkbook(mFile.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			XSSFRow row = null;
			
			ACPN001 vo = null;
			
			int rowCnt = sheet.getPhysicalNumberOfRows();
			String errIdx = "";
			voList = new ACPN001[rowCnt-2];
			
			for(int i = 4; i < rowCnt; i++) {
				
				row = sheet.getRow(i);
				
				if(row != null) {
					
					try {
						vo = new ACPN001();
						int dafsdf = (int)row.getCell(1).getNumericCellValue();
						vo.excel_err_yn = "N";
						vo.cp_div_cd =  String.valueOf( (int) row.getCell(1).getNumericCellValue()); //쿠폰 구분
						vo.cp_kind_cd = String.valueOf( (int) row.getCell(3).getNumericCellValue()); //종류
						vo.cp_iss_type_cd = String.valueOf( (int) row.getCell(4).getNumericCellValue()); //발급타입
						vo.cp_titl = row.getCell(5).getStringCellValue(); //쿠폰 타이틀
						vo.cp_iss_strt_dt = String.valueOf( (int) row.getCell(7).getNumericCellValue()); //발급시작일 
						vo.cp_iss_end_dt = String.valueOf( (int) row.getCell(8).getNumericCellValue()); //발급종료일
						vo.cp_act_strt_dt = String.valueOf( (int) row.getCell(9).getNumericCellValue());
						vo.cp_act_end_dt = String.valueOf( (int) row.getCell(10).getNumericCellValue());
						
						
						//할인방식 체크
						if((int) row.getCell(11).getNumericCellValue() > 0
								&& (int) row.getCell(12).getNumericCellValue() > 0
								&& (int) row.getCell(13).getNumericCellValue() == 0
								){
							vo.cp_sale_div_cd = "2";
							vo.cp_sum_sale_rt =(int) row.getCell(11).getNumericCellValue();
							vo.cp_max_sale_amt = String.valueOf( (int) row.getCell(12).getNumericCellValue());
							
						}else if((int) row.getCell(11).getNumericCellValue() == 0
								&& (int) row.getCell(12).getNumericCellValue() == 0
								&& (int) row.getCell(13).getNumericCellValue() > 0
								){
							vo.cp_sale_div_cd = "1";
							vo.cp_ded_amt = (int) row.getCell(13).getNumericCellValue();
							
						}else{
							vo.excel_err_yn = "Y";
						}
						
						vo.cp_iss_cnt = (int) row.getCell(14).getNumericCellValue();
						vo.cp_use_cond_amt = (int) row.getCell(15).getNumericCellValue();
						vo.cp_dtl_cont = row.getCell(17).getStringCellValue();
						vo.cp_att_part_cont = row.getCell(18).getStringCellValue();
						
						voList[i - 4] = vo;
					} catch(Exception e) {
						vo.excel_err_yn = "Y";
					}
					 
				}
			}
			
			workbook.close();
			
		} // end if xlsx
		
		
		if(ext.equals("xls")) {
			
			HSSFWorkbook workbook = new HSSFWorkbook(mFile.getInputStream());
			
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow row;
			
			ACPN001 vo = null;
			
			int rowCnt = sheet.getPhysicalNumberOfRows();
			voList = new ACPN001[rowCnt - 2];
			
			for(int i = 1; i < rowCnt; i++) {
			
				row = sheet.getRow(i);
				
				if(row != null) {
					
					try {
						vo = new ACPN001();
						
						vo.cp_div_cd = row.getCell(1).getStringCellValue(); //쿠폰 구분
						vo.cp_kind_cd = row.getCell(3).getStringCellValue(); //종류
						vo.cp_iss_type_cd = row.getCell(4).getStringCellValue(); //발급타입
						vo.cp_titl = row.getCell(5).getStringCellValue(); //쿠폰 타이틀
						vo.cp_iss_strt_dt = row.getCell(7).getStringCellValue(); //발급시작일 
						vo.cp_iss_end_dt = row.getCell(8).getStringCellValue(); //발급종료일
						vo.cp_act_strt_dt = row.getCell(9).getStringCellValue();
						vo.cp_act_end_dt = row.getCell(9).getStringCellValue();
						
						
						
						//할인방식 체크
						if(!StringUtils.isEmpty(row.getCell(10).getStringCellValue())
								&& !StringUtils.isEmpty(row.getCell(11).getStringCellValue())
								&& StringUtils.isEmpty(row.getCell(12).getStringCellValue())
								){
							vo.cp_sale_div_cd = "2";
							
						}else if(StringUtils.isEmpty(row.getCell(10).getStringCellValue())
								&& StringUtils.isEmpty(row.getCell(11).getStringCellValue())
								&& !StringUtils.isEmpty(row.getCell(12).getStringCellValue())
								){
							vo.cp_sale_div_cd = "1";
							
						}else{
							vo.excel_err_yn = "Y";
						}
						
						vo.cp_iss_cnt = (int) row.getCell(13).getNumericCellValue();
						vo.cp_use_cond_amt = (int) row.getCell(14).getNumericCellValue();
						vo.cp_dtl_cont = row.getCell(16).getStringCellValue();
						vo.cp_att_part_cont = row.getCell(17).getStringCellValue();
						
						voList[i - 2] = vo;
					} catch(Exception e) {
						vo.excel_err_yn = "Y";
					}
				}
			}
			
			workbook.close();
			
		}
		
		return voList;
	}

	


	

	
}
