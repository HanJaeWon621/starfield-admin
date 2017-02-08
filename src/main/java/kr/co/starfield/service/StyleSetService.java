package kr.co.starfield.service;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.mapper.StyleSetMapper;
import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SCPN001_D;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.StyleSet;



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
public class StyleSetService {

	@Autowired
	private StyleSetMapper mapper;
	
	private static final Logger ll = LoggerFactory.getLogger(StyleSetMapper.class);

	/**
	 * 스타일 세트 리스트
	 * @param ACPN001Vo
	 * @return 
	 */
	public ListResult<StyleSet> getStyleSets(StyleSet vo) {
		
		ListResult<StyleSet> result = new ListResult<>();
		
		List<StyleSet> cpList = mapper.getStyleSets(vo);
		result.list = cpList;

		int totCnt = cpList.size() > 0 ? cpList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;
		result.tot_cnt = totCnt;
	
		return result;
	}
	
	/**
	 * 스타일 세트 상세
	 * @param ACPN001Vo
	 * @return 
	 */
	public StyleSet getStyleSet(StyleSet vo) {
		
		ListResult<StyleSet> result = new ListResult<>();
		System.out.println("3style_set_seq>>>"+vo.style_set_seq);
		StyleSet ss = mapper.getStyleSet(vo);
		System.out.println("important_cd>>>"+ss.important_cd);
		System.out.println("style_nm>>>"+ss.style_nm);
		//result.list = cpList;
	
		return ss;
	}
	
	/**
	 * 스타일셋 등록
	 * @param StyleSet
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regStyleSet(StyleSet styleSet) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.regStyleSet(styleSet);
	 	
	 	result.extra =styleSet.style_set_seq;
	 	return result;
	}
	
	/**
	 * 스타일셋 수정
	 * @param StyleSet
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyStyleSet(StyleSet styleSet) {
		
		SimpleResult result = new SimpleResult();
		
		System.out.println("important_cd>>>"+styleSet.important_cd);
		System.out.println("style_nm>>>"+styleSet.style_nm);
	 	mapper.modifyStyleSet(styleSet);
	 	
	 	result.extra =styleSet.style_set_seq;
	 	return result;
	}

	/**
	 * 스타일셋 삭제
	 * @param StyleSet
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteStyleSet(StyleSet styleSet) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.deleteStyleSet(styleSet);
	 	
	 	result.extra =styleSet.style_set_seq;
	 	return result;
	}
	
	/**
	 * 스타일 세트 리스트
	 * @param ACPN001Vo
	 * @return 
	 */
	public ListResult<StyleSet> saveCss(StyleSet vo) {
		
		ListResult<StyleSet> result = new ListResult<>();
		
		List<StyleSet> cpList = mapper.getStyleSets(vo);
		result.list = cpList;

		int totCnt = cpList.size() > 0 ? cpList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;
		result.tot_cnt = totCnt;
		writeFile("D:\\","a");
		for (StyleSet ss : cpList) {// 854
			System.out.println("no>>"+ss.style_nm);
			//System.out.println("count>>"+result.cnt);
			//sqlSession.insert("starfield.mapper.insertLbsLocInfo", result);
		}
		
		return result;
	}
	
	/*
	 * p:전문
	 */
	public static void writeFile(String logDir, String p_logStr) {
		Date d = new Date();
		String now = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String now_time = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
		// System.out.println("dateFormat2>>>>" + now);

		// usr/local/src/posd/logs
		try {
			String rootDir = "D:\\";
			// rootDir="D:\\logs\\"+div+"\\";
			// rootDir = "/usr/local/src/posd/logs/" + div + "/";
			//rootDir = "/home/cpadm/";
			File dir = new File(rootDir);
			// dir.mkdir();
			// dir.mkdirs();
			if (!dir.exists()) {
				// dir.mkdir();

				if (dir.mkdirs()) {
					// System.out.println("Directory is created!");
				} else {
					// System.out.println("Failed to create directory!");
				}

			}

			String path = logDir + now + ".log"; // 임시
			File file = new File(path);
			// true 지정시 파일의 기존 내용에 이어서 작성
			FileWriter fw = new FileWriter(file, true);
			String logStr = "[로그일시 : " + now_time + "]:"  + p_logStr;
			// 파일안에 문자열 쓰기
			fw.write("\n" + logStr);
			fw.flush();

			// 객체 닫기
			fw.close();

		} catch (Exception e) {
			System.out.println("파일로그남기기>>" + e.toString());
			// e.printStackTrace();
		}

	}
	
}
