package kr.co.starfield.excel.builder;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class TestExcelBuilder extends AbstractExcelView {

	@Override
	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String sheetName = (String) ((Map<String, Object>) model.get("data")).get("sheetName");
		String[] titleList = (String[]) ((Map<String, Object>) model.get("data")).get("titleList");
		String[] colNameList = (String[]) ((Map<String, Object>) model.get("data")).get("colNameList");
		Object[] dataList = (Object[]) ((Map<String, Object>) model.get("data")).get("dataList");
		
		HSSFSheet sheet = workbook.createSheet(sheetName);
		sheet.setDefaultColumnWidth(30);
		
		// 액셀 첫 줄에 대한 스타일 정보 생성
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("맑은고딕");
        style.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
        
        // 철 줄 (헤더) 생성
        HSSFRow header = sheet.createRow(0); // <-- 0 번째 로우
        for(int i = 0; i < titleList.length; i++){
        	header.createCell(i).setCellValue(titleList[i]); 
            header.getCell(i).setCellStyle(style);
        }
        
        // dataList를 생성
        HSSFRow row;
        for(int i = 0 ; i < dataList.length ; i++) {
        	
        	row = sheet.createRow(i+1);
        	Object data = dataList[i];
        	
        	for(int j = 0; j < colNameList.length; j++) {
        		
        		Field field = data.getClass().getDeclaredField(colNameList[j]);
        		
        		// field type이 date라면 ??
        		row.createCell(j).setCellValue(field.get(data) + "");
        	}
        }
	}

}
