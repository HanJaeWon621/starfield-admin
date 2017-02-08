package kr.co.starfield.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.model.SimpleResult;

/**
 * Locale 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.06.28
 */

@Service
public class LocaleService {
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	private static final Logger ll = LoggerFactory.getLogger(LocaleService.class);
	
	public HashMap<String, String> getLanguagePack(String pageUri, String lang) {
		if(lang == null) {
			lang = "ko"; 
		}
		
		HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();
		
		return (HashMap<String, String>) hashOps.entries("language:" +lang+ ":" + pageUri);
	}
	
	public SimpleResult regLanguage(MultipartFile file) throws BaseException, Exception {
		SimpleResult result = new SimpleResult();
		
		getReadExcel(file);
		
		result.code = 0;
		
		return result;
	}
	
	//excel파일 redis에 넣음
	public void getReadExcel(MultipartFile mFile) throws BaseException, Exception {
		stringRedisTemplate.delete(stringRedisTemplate.keys("language:*"));
		HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();
		
		String ext = mFile.getOriginalFilename().split("\\.")[1];
		
		ll.info("file content type is {}", mFile.getContentType());
		ll.info("file extention is {}", ext);
		
		if(!ext.contains("xls")) {
			throw new BaseException(ErrorCode.File.MIME_TYPE_NOT_MATCH_BY_EXCEL);
		}
		
		Workbook workbook;
		if (ext.equals("xlsx")){
			workbook = new XSSFWorkbook(mFile.getInputStream());
		} else {
			workbook = new HSSFWorkbook(mFile.getInputStream());
		}

		int sheetCnt = workbook.getNumberOfSheets();
		
		ArrayList<Map<String, String>> languageArr = new ArrayList<>();
		Map<String, String> languageMap = new HashMap<>();
		
		for(int i = 0; i < sheetCnt; i++){
			Sheet sheet = workbook.getSheetAt(i);
			String locale = workbook.getSheetName(i);
			int rowCnt = sheet.getPhysicalNumberOfRows();
			
			for(int j = 1; j < rowCnt; j++){
				Row row = sheet.getRow(j);
				if(row != null) {
					String pageUri = row.getCell(0).getStringCellValue();
					String languageKey = row.getCell(1).getStringCellValue();
					String languageValue = row.getCell(2).getStringCellValue();
					if(!pageUri.equals("")){
						languageMap.put("key", "language:"+locale+":"+pageUri);
						languageMap.put("languageKey", languageKey);
						languageMap.put("languageValue", languageValue);
						languageArr.add(languageMap);
					}
				}
			}
		}
		
		
		stringRedisTemplate.multi();
		try {
			for(Map<String, String> map : languageArr){
				hashOps.put(map.get("key"), map.get("languageKey"), map.get("languageValue"));
			}
		} catch (Exception e) {
			throw new BaseException(ErrorCode.File.LOCALE_SYNC_FAIL_REDIS);
		} finally {
			stringRedisTemplate.exec();
			workbook.close();
		}
		
		
		
		return;
	}
	
}
