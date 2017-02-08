package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.FAQ;
import kr.co.starfield.model.FAQCategory;
import kr.co.starfield.model.FAQExcel;
import kr.co.starfield.model.FAQFilter;
import kr.co.starfield.model.FAQWeb;

/**
 * FAQ interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.10.05
 */

public interface ACMS006Mapper {
	
	// FAQ MAMACategory
	public String getFAQMAMACategory(FAQFilter filter);
	
	//FAQ 카테고리 list
	public ArrayList<FAQCategory> getFAQCategoryList(FAQFilter filter);
	
	//FAQ 카테고리 상세
	public FAQCategory getFAQCategory(FAQFilter filter);
	
	//FAQ 카테고리 등록
	public void regCategory(FAQCategory faqCategory);
	
	//FAQ 카테고리 수정
	public void modifyCategory(FAQCategory faqCategory);
	
	//FAQ 등록
	public void regFAQ(FAQ faq);
	
	//FAQ list
	public ArrayList<FAQ> getFAQList(FAQFilter filter);
	
	//FAQ 카테고리 수정
	public void modifyFAQ(FAQ faq);
	
	//FAQWeb list
	public ArrayList<FAQWeb> getFAQWebList(FAQFilter filter);
	
	//FAQExcel list
	public ArrayList<FAQExcel> getFAQExcelList(FAQFilter filter);
		
}
