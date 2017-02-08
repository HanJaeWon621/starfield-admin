package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.Date;

import kr.co.starfield.model.vo.SCMS011Vo;

/**
 *  AppMainInit model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.09.20
 */

public class OldAppMainResult {
	public ArrayList<OldAppMainTopSection> top_section = new ArrayList<>();
	public ArrayList<OldAppMainTopWhatsNew> whats_new = new ArrayList<>();
	public ArrayList<Brand> brands = new ArrayList<>();
	
	public OldAppMainResult() {
		Brand brand1 = new Brand();
		brand1.cate1_seq = "";
		brand1.image_uri = "/cdn/images/brands/main_brands.png";
		brand1.cate_text_en = "MAIN BRANDS";
		brand1.cate_text_ko = "주요매장";
		brands.add(brand1);
		
		Brand brand2 = new Brand();
		brand2.cate1_seq = "CT201608182015333483";
		brand2.image_uri = "/images/thumb_brand1.png";
		brand2.cate_text_en = "LUXURY FASHION";
		brand2.cate_text_ko = "해외유명브랜드";
		brands.add(brand2);
		
		Brand brand3 = new Brand();
		brand3.cate1_seq = "CT201608182015333481";
		brand3.image_uri = "/images/thumb_brand2.png";
		brand3.cate_text_en = "FASHION";
		brand3.cate_text_ko = "패션";
		brands.add(brand3);
		
		Brand brand4 = new Brand();
		brand4.cate1_seq = "CT201608182015333480";
		brand4.image_uri = "/images/thumb_brand3.png";
		brand4.cate_text_en = "KIDS";
		brand4.cate_text_ko = "키즈";
		brands.add(brand4);
		
		Brand brand5 = new Brand();
		brand5.cate1_seq = "CT201608182015333474";
		brand5.image_uri = "/images/thumb_brand4.png";
		brand5.cate_text_en = "RESTAURANTS & CAFE";
		brand5.cate_text_ko = "레스토랑 & 까페";
		brands.add(brand5);
		
		Brand brand6 = new Brand();
		brand6.cate1_seq = "CT201608182015333473";
		brand6.image_uri = "/images/thumb_brand5.png";
		brand6.cate_text_en = "LIFE STYLE";
		brand6.cate_text_ko = "라이프스타일";
		brands.add(brand6);
		
		Brand brand7 = new Brand();
		brand7.cate1_seq = "CT201608182015333479";
		brand7.image_uri = "/images/Aqua-Field.png";
		brand7.cate_text_en = "ENTERTAINMENT";
		brand7.cate_text_ko = "엔터테인먼트";
		brands.add(brand7);
	}
}

class Brand {
	public String cate1_seq;
	public String image_uri;
	public String cate_text_en;
	public String cate_text_ko;
}