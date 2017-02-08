package kr.co.starfield.service;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.mapper.ACMS010Mapper;
import kr.co.starfield.mapper.ACMS011Mapper;
import kr.co.starfield.mapper.AMAV001Mapper;
import kr.co.starfield.model.OldAppMainInit;
import kr.co.starfield.model.OldAppMainResult;
import kr.co.starfield.model.AppMainInit;
import kr.co.starfield.model.Banner;
import kr.co.starfield.model.BannerDelete;
import kr.co.starfield.model.BannerFilter;
import kr.co.starfield.model.BannerGroup;
import kr.co.starfield.model.BannerGroupDelete;
import kr.co.starfield.model.BannerRedis;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.WebMainResult;
import kr.co.starfield.model.WhatsNewFilter;
import kr.co.starfield.model.WhatsNewRedis;


/**
 * ACMS010(Banner) 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.09.07
 */

@Service
public class ACMS010Service {

	@Autowired
	private ACMS010Mapper acms010Mapper;
	
	@Autowired
	private ACMS011Mapper acms011Mapper;
	
	@Autowired
	private AMAV001Mapper amav001Mapper;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	private static final Logger ll = LoggerFactory.getLogger(ACMS010Service.class);
	
	/**
	 * 배너그룹 등록
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regBannerGroup(BannerGroup banneGroup) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms010Mapper.regBannerGroup(banneGroup);
		
		for(Banner banner : banneGroup.bannerList) {
			banner.bn_group_seq = banneGroup.bn_group_seq;
			acms010Mapper.regBanner(banner);
		}
		
		result.extra = banneGroup.bn_group_seq;
		

		return result;
	}
	
	/**
	 * 배너그룹 수정
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyBannerGroup(BannerGroup banneGroup) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms010Mapper.modifyBannerGroup(banneGroup);
		
		for(Banner banner : banneGroup.bannerList){
			if(banner.bn_seq.equals("create")) {
				banner.sts = StatusCode.Common.USE.getCode();
				acms010Mapper.regBanner(banner);
			} else {
				acms010Mapper.modifyBanner(banner);
			}
		}

		return result;
	}
	
	/**
	 * 배너그룹 삭제
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteBannerGroup(BannerGroupDelete bannerGroupDelete) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms010Mapper.deleteBannerGroup(bannerGroupDelete);

		return result;
	}
	
	/**
	 * 배너그룹 목록 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<BannerGroup> getBannerGroupList(BannerFilter filter) throws BaseException {

		ListResult<BannerGroup> result = new ListResult<BannerGroup>();
		List<BannerGroup> bannerGroupList = acms010Mapper.getBannerGroupList(filter);
		

		result.list.addAll(bannerGroupList);

		if(filter.limit > 0){
			int tot_cnt = acms010Mapper.getTotalCount(filter);
	
			Paging paging = new Paging(filter.offset, filter.limit, tot_cnt);
				
			result.paging = paging;
		}		
		return result;
	}
	
	/**
	 * 배너그룹 상세 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public BannerGroup getBannerGroup(BannerFilter filter) throws BaseException {

		BannerGroup bannerGroup = acms010Mapper.getBannerGroup(filter);

		if(bannerGroup == null){
			BaseException be = new BaseException(ErrorCode.Contents.BANNER_GROUP_NOT_FOUND_DATA);
			throw be;
		} 
		
		bannerGroup.bannerList.addAll(acms010Mapper.getBannerList(filter));
		
		return bannerGroup;
	}
	
	/**
	 * 배너 등록
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regBanner(Banner banner) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms010Mapper.regBanner(banner);
		
		result.extra = banner.bn_seq;

		return result;
	}
	
	/**
	 * 배너 수정
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyBanner(Banner banner) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms010Mapper.modifyBanner(banner);

		return result;
	}
	
	/**
	 * 배너 삭제
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteBanner(BannerDelete bannerDelete) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		acms010Mapper.deleteBanner(bannerDelete);

		return result;
	}
	
	/**
	 * 배너 상세 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public Banner getBanner(BannerFilter filter) throws BaseException {
		Banner banner = acms010Mapper.getBanner(filter);

		if(banner == null){
			BaseException be = new BaseException(ErrorCode.Contents.BANNER_NOT_FOUND_DATA);
			throw be;
		}
		return banner;
	}
	
	
	
	/**
	 * 배너 Redis
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult redisBanner(BannerFilter bannerfilter) throws BaseException {
		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
		
		WhatsNewFilter whatsNewFilter = new WhatsNewFilter();
		whatsNewFilter.bcn_cd = bannerfilter.bcn_cd;
		
		WebMainResult webMainResult = new WebMainResult();
		
		webMainResult.bannerList = acms010Mapper.getBannerWebList(bannerfilter);
		webMainResult.whatsNewList = acms011Mapper.getWhatsNewWebList(whatsNewFilter);
		
		Long webResourceVer = 0L;
		for(int i = 0; i < webMainResult.bannerList.size(); i ++) {
			webResourceVer += webMainResult.bannerList.get(i).mod_dttm.getTime();
		}
		for(int i = 0; i < webMainResult.whatsNewList.size(); i ++) {
			webResourceVer += webMainResult.whatsNewList.get(i).mod_dttm.getTime();
		}
		
		
		AppMainInit appMainInit = new AppMainInit();
		
		appMainInit.result.top_section = acms010Mapper.getBannerAppList(bannerfilter);
		appMainInit.result.whats_new = acms011Mapper.getWhatsNewAppList(whatsNewFilter);
		
		Long resourceVer = 0L;
		for(int i = 0; i < appMainInit.result.top_section.size(); i ++) {
			resourceVer += appMainInit.result.top_section.get(i).mod_dttm.getTime();
		}
		for(int i = 0; i < appMainInit.result.whats_new.size(); i ++) {
			resourceVer += appMainInit.result.whats_new.get(i).mod_dttm.getTime();
		}
		appMainInit.v_resource = String.valueOf(resourceVer);
		appMainInit.v_ios = amav001Mapper.getiOSVer(bannerfilter.bcn_cd);
		appMainInit.v_and = amav001Mapper.getAndroidVer(bannerfilter.bcn_cd);
		
		
		
		OldAppMainResult oldAppMainResult = new OldAppMainResult();
		
		oldAppMainResult.top_section = acms010Mapper.getBannerOldList(bannerfilter);		
		oldAppMainResult.whats_new = acms011Mapper.getWhatsNewOldList(whatsNewFilter);

		Long oldResourceVer = 0L;
		for(int i = 0; i < oldAppMainResult.top_section.size(); i ++) {
			oldResourceVer += oldAppMainResult.top_section.get(i).mod_dttm.getTime();
		}
		for(int i = 0; i < oldAppMainResult.whats_new.size(); i ++) {
			oldResourceVer += oldAppMainResult.whats_new.get(i).mod_dttm.getTime();
		}
		
		OldAppMainInit oldAppInit = new OldAppMainInit();
		
		oldAppInit.v_ios = amav001Mapper.getiOSVer(bannerfilter.bcn_cd);
		oldAppInit.v_and = amav001Mapper.getAndroidVer(bannerfilter.bcn_cd);
		
		oldAppInit.result = oldAppMainResult;
		oldAppInit.v_resource = String.valueOf(oldResourceVer);
		
		try {

			vOps.set("init:web:main", new ObjectMapper().writeValueAsString(webMainResult));
			vOps.set("version:init:web:main", String.valueOf(webResourceVer));

			vOps.set("init:app:main", new ObjectMapper().writeValueAsString(appMainInit));
			vOps.set("version:init:app:main", String.valueOf(appMainInit.v_resource));

			vOps.set("init:app:old:main", new ObjectMapper().writeValueAsString(oldAppInit));
			vOps.set("version:init:app:old:main", String.valueOf(oldResourceVer));

		} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.Contents.BANNER_SYNC_FAIL_REDIS);
			throw be;
		}
		SimpleResult result = new SimpleResult();
		return result;
	}
	
	

}
