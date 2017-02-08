package kr.co.starfield.excel.builder;

import java.lang.reflect.Field;
import java.util.List;
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
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import kr.co.starfield.common.ICommonCode;

@Component
public class ListExcelBuilder extends AbstractExcelView {

	@Override
	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String sheetName = (String) ((Map<String, Object>) model.get("setting")).get("sheetName");
		String[] titleList = (String[]) ((Map<String, Object>) model.get("setting")).get("titleList");
		String[] colNameList = (String[]) ((Map<String, Object>) model.get("setting")).get("colNameList");
		List<Object> dataList = (List<Object>) ((Map<String, Object>) model.get("setting")).get("dataList");
		
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
        
        for(int i = 0 ; i < dataList.size() ; i++) {
        	row = sheet.createRow(i+1);
        	Object obj = dataList.get(i);
        	for(int j = 0; j < colNameList.length; j++) {
        		Field field = obj.getClass().getDeclaredField(colNameList[j]);

        		if(field.getType().toString().contains("CommonCode")) {
        			String value = ((ICommonCode) field.get(obj)).getCodeNm();
        			row.createCell(j).setCellValue(value);
        		} else {
        			String value = field.get(obj) + "";
            		if(value == null || value.equals("null")) {
            			value = "";
            		}
            		row.createCell(j).setCellValue(value);
        		}
        	}
        }
	}
}
