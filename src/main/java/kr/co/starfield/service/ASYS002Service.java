package kr.co.starfield.service;


import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.mapper.ASYS002Mapper;
import kr.co.starfield.model.BannerGroupDelete;
import kr.co.starfield.model.Language;
import kr.co.starfield.model.LanguageDelete;
import kr.co.starfield.model.LanguageFilter;
import kr.co.starfield.model.LanguageGroup;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;

/**
 * ASYS002Service(locale) 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.08.31
 */

@Service
public class ASYS002Service {

	@Autowired
	private ASYS002Mapper asys002Mapper;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	private static final Logger ll = LoggerFactory.getLogger(ACMS002Service.class);
	
	/**
	 * 다국어 추가
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regLoclae(Language[] languageList) throws BaseException {
		SimpleResult result = new SimpleResult();

		for(Language language : languageList) {
			asys002Mapper.regLocale(language);
		}
		
		syncLocale(languageList[0].bcn_cd);
		return result;
	}
	
	/**
	 * 다국어 그룹 목록 조회
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<LanguageGroup> getLocaleGroupList(LanguageFilter filter) throws BaseException {
		
		ListResult<LanguageGroup> result = new ListResult<>();
			
		result.list.addAll(asys002Mapper.getLocaleGroupList(filter));

		if(filter.limit > 0){
			int tot_cnt = asys002Mapper.getTotalCount(filter);
	
			Paging paging = new Paging(filter.offset, filter.limit, tot_cnt);
				
			result.paging = paging;
		}
	
		return result;
	}
	
	/**
	 * 다국어 목록 조회
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<Language> getLocaleList(LanguageFilter filter) throws BaseException {
		
		ListResult<Language> result = new ListResult<>();
			
		result.list.addAll(asys002Mapper.getLocaleList(filter));
	
		return result;
	}
	
	/**
	 * 다국어 삭제
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteLocale(LanguageDelete languageDelete) throws BaseException {
		SimpleResult result = new SimpleResult();

		asys002Mapper.deleteLocale(languageDelete);

		syncLocale(languageDelete.bcn_cd);
		
		return result;
	}
	
	
	/**
	 * 다국어 redis
	 * @param bcn_cd
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public void syncLocale(String bcn_cd) throws BaseException {
		stringRedisTemplate.delete(stringRedisTemplate.keys("language:*"));
		
		HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();
		
		LanguageFilter filter = new LanguageFilter();
		filter.bcn_cd = bcn_cd;
		ArrayList<Language> localeList =  asys002Mapper.getLocaleList(filter);

		try {
			for(Language language : localeList){
				hashOps.put("language:ko:"+language.pg_id, language.txt_symb, language.txt_ko);
				hashOps.put("language:en:"+language.pg_id, language.txt_symb, language.txt_en);
				hashOps.put("language:jp:"+language.pg_id, language.txt_symb, language.txt_jp);
				hashOps.put("language:cn:"+language.pg_id, language.txt_symb, language.txt_cn);
			}
		} catch (Exception e) {
			throw new BaseException(ErrorCode.File.LOCALE_SYNC_FAIL_REDIS);
		}
		
		return;
	}
}
