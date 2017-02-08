package kr.co.starfield.service;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.mapper.ACMS006Mapper;
import kr.co.starfield.model.FAQ;
import kr.co.starfield.model.FAQCategory;
import kr.co.starfield.model.FAQExcel;
import kr.co.starfield.model.FAQFilter;
import kr.co.starfield.model.FAQWeb;
import kr.co.starfield.model.SimpleResult;


/**
 * FAQ 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.10.05
 */

@Service
public class ACMS006Service {

	@Autowired
	private ACMS006Mapper amcs006Mapper;

	@Autowired
	private ACTG001Service actg001Service;
	
	
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	private static final Logger ll = LoggerFactory.getLogger(ACMS006Service.class);
	
	
	/**
	 * FAQ 카테고리 목록 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public ArrayList<FAQCategory> getFAQCategoryList(FAQFilter filter) throws BaseException {
		
		ArrayList<FAQCategory> faqCateList = amcs006Mapper.getFAQCategoryList(filter);
		
		return faqCateList;
	}
	
	/**
	 * FAQ 카테고리 상세 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public FAQCategory getFAQCategory(FAQFilter filter) throws BaseException {
		
		FAQCategory faqCate = amcs006Mapper.getFAQCategory(filter);
		
		return faqCate;
	}
	
	/**
	 * FAQ 카테고리 등록
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regCategory(FAQCategory[] faqCateList, FAQFilter filter) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		for(FAQCategory faqCategory : faqCateList) {
			if(faqCategory.cate_seq.equals("create")) {
				faqCategory.mama_cate_seq = amcs006Mapper.getFAQMAMACategory(filter);
				amcs006Mapper.regCategory(faqCategory);
			} else {
				amcs006Mapper.modifyCategory(faqCategory);
			}
			
		}
		
		syncFAQ(faqCateList[0].bcn_cd);
		return result;
	}
	
	
	
	/**
	 * FAQ 등록
	 * @param faqList
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regFAQ(FAQ[] faqList) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		for(FAQ faq : faqList) {
			if(faq.faq_seq.equals("create")) {
				amcs006Mapper.regFAQ(faq);
			} else {
				amcs006Mapper.modifyFAQ(faq);
			}
			
		}
		
		syncFAQ(faqList[0].bcn_cd);
		return result;
	}
	
	/**
	 * FAQ 목록 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public ArrayList<FAQ> getFAQList(FAQFilter filter) throws BaseException {
		

		ArrayList<FAQ> faqList = amcs006Mapper.getFAQList(filter);
		
		return faqList;
	}
	
	/**
	 * FAQExcel 목록 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public ArrayList<FAQExcel> getFAQExcelList(String bcn_cd) throws BaseException {
		
		FAQFilter filter = new FAQFilter();
		filter.bcn_cd = bcn_cd;

		return amcs006Mapper.getFAQExcelList(filter);
	}
	
	/**
	 * FAQWeb 목록 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public ArrayList<FAQWeb> getFAQWebList(String bcn_cd) throws BaseException {
		
		FAQFilter filter = new FAQFilter();
		filter.bcn_cd = bcn_cd;

		return amcs006Mapper.getFAQWebList(filter);
	}
	
	/**
	 * FAQ redis sync
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	
	@Transactional
	public SimpleResult syncFAQ(String bcn_cd) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
		
		stringRedisTemplate.delete(zOps.range("keys:FAQ", 0, -1));
		stringRedisTemplate.delete("keys:FAQ");
		

		List<FAQWeb> faqList = getFAQWebList(bcn_cd);
		
		
		try {
			int count = 0;
			for(FAQWeb faq : faqList){
				vOps.set("FAQ:" + faq.faq_seq, new ObjectMapper().writeValueAsString(faq));
				zOps.add("keys:FAQ", "FAQ:" + faq.faq_seq, count);				
				count++;
			}
		} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.Board.FAQ_SYNC_FAIL_REDIS);
			throw be;
		}
		
		actg001Service.syncRedisCategories("FAQ");
		
		return result;
	}
}
