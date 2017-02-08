package kr.co.starfield.service;

import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.mapper.SampleMapper;
import kr.co.starfield.model.Sample;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SampleVo;

@Service
public class SampleService {
	
private Logger ll = LoggerFactory.getLogger(SampleService.class);
	
	@Autowired
	SampleMapper sampleMapper;
	
	public SimpleResult regSample(SampleVo vo) {
		
		SimpleResult result = new SimpleResult();	
		
		sampleMapper.regSample(vo);
		
		return result;
	}
	
	public Sample[] getSamples() {
		
		SampleVo[] voList = sampleMapper.getSamples();
		Sample[] samples = new Sample[voList.length];
		
		for(int i = 0; i < voList.length; i++) {
			
			samples[i] = voList[i].toModel();
		}
		
		return samples;
	}
	
	public void delAllSamples() {
		
		sampleMapper.delAllSample();
	}

	// 업로드된 엑셀 파일을 활용해서 db에 저장하
	public SimpleResult regSample(MultipartFile file) throws BaseException, Exception {

		SimpleResult result = new SimpleResult();
		
		SampleVo[] voList = null;
		voList = getReadExcel(file, voList);
		
		for(int i = 0; i < voList.length; i++) {
			_regSample(voList[i]);
		}
			
		result.code = 0;
		
		return result;
	}
	
	@Transactional
	public void _regSample(SampleVo vo) {
		
		sampleMapper.regSample(vo);
	}
	
	
	public String getOraTime() throws Exception {
		
		String oraTime = sampleMapper.getOraTime();
		
		return oraTime;
	}
	
	
	
	public SampleVo[] getReadExcel(MultipartFile mFile, SampleVo[] voList) throws BaseException, Exception {
		
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
			
			SampleVo vo = null;
			
			int rowCnt = sheet.getPhysicalNumberOfRows();
			voList = new SampleVo[rowCnt-1];
			
			for(int i = 1; i < rowCnt; i++) {
				
				row = sheet.getRow(i);
				
				if(row != null) {
					
					vo = new SampleVo();
					
					vo.num_col = (int) row.getCell(0).getNumericCellValue();
					vo.varchar_col = row.getCell(1).getStringCellValue();
					
					XSSFCell dateCell = row.getCell(2);
					if(DateUtil.isCellDateFormatted(dateCell)) {
						vo.date_col = DateUtil.getJavaDate(row.getCell(2).getNumericCellValue());
					} else {
						vo.date_col = sdf.parse(row.getCell(2).getStringCellValue());
					}
					
					voList[i - 1] = vo; 
				}
			}
			
			workbook.close();
			
		} // end if xlsx
		
		
		if(ext.equals("xls")) {
			
			HSSFWorkbook workbook = new HSSFWorkbook(mFile.getInputStream());
			
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow row;
			
			SampleVo vo = null;
			
			int rowCnt = sheet.getPhysicalNumberOfRows();
			voList = new SampleVo[rowCnt - 1];
			
			for(int i = 1; i < rowCnt; i++) {
			
				row = sheet.getRow(i);
				
				if(row != null) {
					
					vo = new SampleVo();
					
					vo.num_col = (int) row.getCell(0).getNumericCellValue();
					vo.varchar_col = row.getCell(1).getStringCellValue();
					
					HSSFCell dateCell = row.getCell(2);
					
					if(DateUtil.isCellDateFormatted(dateCell)) {
						vo.date_col = dateCell.getDateCellValue();
					} else {
						vo.date_col = sdf.parse(dateCell.getStringCellValue());
					}
					
					voList[i - 1] = vo;
				}
			}
			
			workbook.close();
			
		}
		
		return voList;
	}
	
}
