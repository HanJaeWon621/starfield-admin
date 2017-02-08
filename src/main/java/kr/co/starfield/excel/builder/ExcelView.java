package kr.co.starfield.excel.builder;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import jxl.Workbook;
public class ExcelView extends AbstractExcelView{
    @Override
    protected void buildExcelDocument(Map<String,Object> ModelMap,HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception{
          //String excelName = ModelMap.get("target").toString();
    	String excelName = "file";
          HSSFSheet worksheet = null;
          HSSFRow row = null;
          if(excelName.equals("file")){
                // @SuppressWarnings("unchecked")
                 //List<BoardFile> list = (List<BoardFile>)ModelMap.get("excelList");
                 List list = null;
                 excelName=URLEncoder.encode("파일","UTF-8");
                 worksheet = workbook.createSheet(excelName+ " WorkSheet");
                 row = worksheet.createRow(0);
                 row.createCell(0).setCellValue("파일번호");
                 row.createCell(1).setCellValue("게시글번호");
                 row.createCell(2).setCellValue("파일경로");
                 row.createCell(3).setCellValue("파일이름");
                 row.createCell(4).setCellValue("파일크기");
                 //for(int i=1;i<list.size()+1;i++){
                        row = worksheet.createRow(1);
                        row.createCell(0).setCellValue("a");
                        row.createCell(1).setCellValue("a");
                        row.createCell(2).setCellValue("a");
                        row.createCell(3).setCellValue("a");
                        row.createCell(4).setCellValue("a");
                // }
          }
       //   response.setContentType("Application/Msexcel");
        //  response.setHeader("Content-Disposition", "ATTachment; Filename="+excelName+"-excel");
    }
}
