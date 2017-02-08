package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.OldAppMainTopSection;
import kr.co.starfield.model.Banner;
import kr.co.starfield.model.BannerDelete;
import kr.co.starfield.model.BannerFilter;
import kr.co.starfield.model.BannerGroup;
import kr.co.starfield.model.BannerGroupDelete;
import kr.co.starfield.model.BannerRedis;

/**
 *  ACMS010Mapper(banner) interface
 *  
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.09.07
 */

public interface ACMS010Mapper {

	// 배너 그룹 등록
	public void regBannerGroup(BannerGroup vo);
	
	//배너 그룹 수정
	public void modifyBannerGroup(BannerGroup vo);
	
	//배너 그룹 수정
	public void deleteBannerGroup(BannerGroupDelete bannerGroupDelete);
	
	// 배너 그룹 리스트
	public ArrayList<BannerGroup> getBannerGroupList(BannerFilter filter);

	// 배너 그룹 리스트 카운트
	public int getTotalCount(BannerFilter filter);
	
	// 배너 그룹 상세 조회
	public BannerGroup getBannerGroup(BannerFilter filter);
	
	// 배너 리스트 조회
	public ArrayList<Banner> getBannerList(BannerFilter filter);
	
	// 배너 상세 조회
	public Banner getBanner(BannerFilter filter);
	
	// 배너 등록
	public void regBanner(Banner vo);
	
	// 배너 수정
	public void modifyBanner(Banner vo);
	
	// 배너 삭제
	public void deleteBanner(BannerDelete bannerDelete);
	
	// 배너 Web 리스트 조회
	public ArrayList<BannerRedis> getBannerWebList(BannerFilter filter);

	// 배너 App 리스트 조회
	public ArrayList<BannerRedis> getBannerAppList(BannerFilter filter);
	
	// app 배너 리스트 조회
	public ArrayList<OldAppMainTopSection> getBannerOldList(BannerFilter filter);
}
