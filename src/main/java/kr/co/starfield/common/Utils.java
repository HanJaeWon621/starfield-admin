/*
 * Utils.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.common;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import kr.co.starfield.model.BaseFilter;
import kr.co.starfield.model.SADM003;

/**
 *  Utils
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class Utils {
	
	private static final Logger ll = LoggerFactory.getLogger(Utils.class);
	
	public static class Str {
		
		public static boolean isEmpty(String s) {
			if(s == null || "".equals(s)) {
				return true;
			} else {
				return false;
			}
		}
		
		public static String generateRandomNumber() {
			StringBuffer sb = new StringBuffer();;
			Random randomGenerator = new Random();	
			for(int i=0; i < 6; ++i) {
						    
			    int n = randomGenerator.nextInt(9);				
				sb.append(n);
			}
			
			return sb.toString();
		}
		
		public static String fillZero(int num, int len) {
			
			String n = Integer.toString(num);
			StringBuffer sb = new StringBuffer();
			sb.append(n);
			for(int i=n.length(); i<len; ++i) {
				sb.insert(0, '0');
			}
			
			return sb.toString();
		}
		
		/* vo 는 search 용도가 아님. 사용 자제요! */
		public static void qParser(BaseFilter filter, String qStr) throws BaseException {
			
			try {
				
				if(!Utils.Str.isEmpty(qStr)) {
					
					Class<?> c = filter.getClass();
					
					//System.out.println(String.format("qStr is %s", qStr));
					
					String[] querys = qStr.split(",");
					
					for(String q : querys) {
						
						//System.out.println(String.format("q is %s", q));
						
						Field field = c.getDeclaredField(q.split("=")[0]);
						
						/*//System.out.println(String.format("field type is %s", field.getType().toString()));*/
						
						if(field.getType().toString().equals("int")) {
						
							if(q.split("=").length > 1) {
								field.setInt(filter, Integer.parseInt(q.split("=")[1]));
							} else {
								field.setInt(filter, 0);
							}
						} else if (field.getType().toString().equals("boolean")){
							if(q.split("=").length > 1) {
								field.setBoolean(filter, Boolean.parseBoolean(q.split("=")[1]));
							} else {
								field.setBoolean(filter, false);
							}
						} else if (field.getType().toString().equals("class java.lang.Integer")){
							if(q.split("=").length > 1) {
								field.set(filter, new Integer(q.split("=")[1]));
							} else {
								field.set(filter, null);
							}
						} else if(field.getType().toString().equals("class java.util.Date")) {
							String dateFormat = "yyyy-MM-dd HH:mm:ss";
							String simpleDateFormat = "yyyy-MM-dd";
							if(q.split("=").length > 1) {
								if(q.split("=")[1].length() == dateFormat.length()) {
									SimpleDateFormat ee = new SimpleDateFormat(dateFormat);
									field.set(filter, ee.parse(q.split("=")[1].replaceAll("\\.", "-")));
								} else if(q.split("=")[1].length() == simpleDateFormat.length()) {
									SimpleDateFormat ee = new SimpleDateFormat(simpleDateFormat);
									field.set(filter, ee.parse(q.split("=")[1].replaceAll("\\.", "-")));
								} else {
									field.set(filter, null);
								}
							} else {
								field.set(filter, null);
							}
							
						} else {
							if(q.split("=").length > 1) {
								field.set(filter, q.split("=")[1]);
							} else {
								field.set(filter, null);
							}
						}
					}
				}
				
			} catch(NumberFormatException e) {
				BaseException be = new BaseException(ErrorCode.Common.Q_PARSER_DATE_PARSE_EXCEPTION);
				be.setErrMsg(String.format("숫자 포맷이 잘못되었습니다. %s", qStr));
				throw be;
				
			} catch(ParseException e) {
				BaseException be = new BaseException(ErrorCode.Common.Q_PARSER_DATE_PARSE_EXCEPTION);
				be.setErrMsg(String.format("Date 포맷이 잘못되었습니다. 'yyyy-MM-dd HH:mm:ss'  %s", qStr));
				throw be;
				
			} catch(Exception e) {
				e.printStackTrace();
				ll.error(e.getMessage());
				throw new BaseException(ErrorCode.Common.UNKNOWN);
			}
		}
		
		public static String substringByBytes(String str, int maxlength){
			if(str == null) {
				return str;
			}
			maxlength = (maxlength/3)*3;
			return (String) (str.getBytes().length >= maxlength ? new String(str.getBytes(), 0, maxlength) : str);
		}
		
		public static String maskingName(String name) {
			if(name == null || name.isEmpty()){
				return name;
			}
			
			StringBuilder masked = new StringBuilder();
			if(name.length() == 2) {
				masked.append(name.charAt(0));
				masked.append('*');
			} else {
				for (int i = 0; i < name.length(); i++) {
			         char c = name.charAt(i);
			         
			         if (i == 0 || i == name.length() - 1) {
			            masked.append(c);
			         } else {
			            masked.append('*');
			         }
				}
			}
			return masked.toString();
		}
		
		public static String masking(String name, int start_num, int end_num) {
			StringBuilder masked = new StringBuilder();

			for (int i = 0; i < name.length(); i++) {
				char c = name.charAt(i);
				
				if (i >= start_num && i <= end_num) {
					masked.append('*');
				} else {
					masked.append(c);
				}
			}
	
			return masked.toString();
		}
		
		public static String allMasking(String txt) {
			StringBuilder masked = new StringBuilder();
		
			for (int i = 0; i < txt.length(); i++) {
	            masked.append('*');
		 
			}
			return masked.toString();
		}
	}
	
	public static class Dt {
		
		private static SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
		private static SimpleDateFormat datetimeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		public static String validateStartWeek(String s){
			if(Utils.Str.isEmpty(s)){
				return "197001";
			} else {
				return validateWeek(s);
			}
		}
		
		public static String validateEndWeek(String s){
			if(Utils.Str.isEmpty(s)) {
				return "300001";
			} else {
				return validateWeek(s);
			}
		}
		
		public static String validatePrevDate(String s) {
			if(Utils.Str.isEmpty(s)) {
				return "19700101";
			} else {
				return s;
			}
		}
		
		public static String validateNextDate(String s) {
			if(Utils.Str.isEmpty(s)) {
				return "30001231";
			} else {
				return s;
			}
		}
		
		public static String validateWeekToYYYYMMDD(String s) {

			Calendar cal = Calendar.getInstance();
			String year = s.substring(0,4);
			String week = s.substring(4);
			cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));
			cal.set(Calendar.YEAR, Integer.parseInt(year));
			
			String r = cal.get(Calendar.YEAR) + "-";
			if(cal.get(Calendar.MONTH)+1 < 10)
				r+="0"+(cal.get(Calendar.MONTH)+1)+"-";
			else
				r+=(cal.get(Calendar.MONTH)+1)+"-";
					
			if(cal.get(Calendar.DATE) < 10)
				r+="0"+(cal.get(Calendar.DATE));
			else 
				r+=(cal.get(Calendar.DATE));
			
			return r;
		}
		
		public static String toString(Date dt) {
			if(dt != null)
				return dateFmt.format(dt);
			else
				return null;
		}
		public static String toStringDateTime(Date dt) {
			if(dt != null)
				return datetimeFmt.format(dt);
			else 
				return null;
		}
		
		private static String validateWeek(String s){
			Calendar cal = Calendar.getInstance();
			String year = s.substring(0,4);
			String month = s.substring(4,6);
			String date = s.substring(6);
			
			//System.out.println(year+month+date);
			
			cal.set(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(date)-1);
			
			String week = cal.get(Calendar.YEAR) + "";
			
			if(cal.get(Calendar.WEEK_OF_YEAR) < 10)
				week+="0"+cal.get(Calendar.WEEK_OF_YEAR);
			else
				week+=cal.get(Calendar.WEEK_OF_YEAR);
			
			return week;
		}
		
		public static String getToday(){
			Calendar cal = Calendar.getInstance();
			String result = cal.get(Calendar.YEAR)+"";
			if(cal.get(Calendar.MONTH)+1 < 10)
				result+="0"+(cal.get(Calendar.MONTH)+1)+"";
			else
				result+=(cal.get(Calendar.MONTH)+1)+"";
					
			if(cal.get(Calendar.DATE) < 10)
				result+="0"+(cal.get(Calendar.DATE));
			else 
				result+=(cal.get(Calendar.DATE));
			
			return result;
		}
		
		public static String getYesterday(){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DATE, cal.get(Calendar.DATE)-1);
			
			String result = cal.get(Calendar.YEAR)+"";
			if(cal.get(Calendar.MONTH)+1 < 10)
				result+="0"+(cal.get(Calendar.MONTH)+1)+"";
			else
				result+=(cal.get(Calendar.MONTH)+1)+"";
			
			if(cal.get(Calendar.DATE) < 10)
				result+="0"+(cal.get(Calendar.DATE));
			else 
				result+=(cal.get(Calendar.DATE));
			
			return result;
		}
		
		public static String getToWeek(int index){
			Calendar cal = Calendar.getInstance();
			
			if(index == 1){
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)-7);
			}
			
			String result = cal.get(Calendar.YEAR)+"";
			
			if(cal.get(Calendar.WEEK_OF_YEAR) < 10)
				result+="0"+cal.get(Calendar.WEEK_OF_YEAR);
			else
				result+=cal.get(Calendar.WEEK_OF_YEAR);
			
			return result;
		}
		
		public static String getToMonth(int index){
			Calendar cal = Calendar.getInstance();
			
			if(index == 1){
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
			}
			
			String result = cal.get(Calendar.YEAR)+"";
			
			if(cal.get(Calendar.MONTH)+1 < 10)
				result+="0"+(cal.get(Calendar.MONTH)+1);
			else
				result+=(cal.get(Calendar.MONTH)+1);
			
			return result;
		}
		
		public static List<String> getOneWeekDays(){
			List<String> list = new ArrayList<String>();
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DATE, cal.get(Calendar.DATE)-7);
			
			for(int i=0; i<7; i++){
				String result = cal.get(Calendar.YEAR)+"";
				if(cal.get(Calendar.MONTH)+1 < 10)
					result+="0"+(cal.get(Calendar.MONTH)+1)+"";
				else
					result+=(cal.get(Calendar.MONTH)+1)+"";
						
				if(cal.get(Calendar.DATE) < 10)
					result+="0"+(cal.get(Calendar.DATE));
				else 
					result+=(cal.get(Calendar.DATE));
				
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
				
				//System.out.println(result+ " : 일자.");
				list.add(result);
			}
			
			//System.out.println(list+ " : 최종일자");
			
			return list;
		}
		
	}
	
	public static class Excel {
		
		public static ArrayList<Object[]> getReadExcel(MultipartFile mFile) throws BaseException, Exception {
			
			ArrayList<Object[]> rArr = new ArrayList<Object[]>();
			Object[] rRow = null;
			
			if(!mFile.getContentType().contains("excel")) {
				throw new BaseException(ErrorCode.File.MIME_TYPE_NOT_MATCH_BY_EXCEL);
			}
			
			HSSFWorkbook workbook = new HSSFWorkbook(mFile.getInputStream());
			
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			
			int rowCount = sheet.getPhysicalNumberOfRows();
			
			for(int rowIdx = 1; rowIdx < rowCount; rowIdx++) {
			
				row = sheet.getRow(rowIdx);
				
				if(row != null) {
					
					int cellCnt = row.getPhysicalNumberOfCells();
					
					rRow = new Object[cellCnt];
					for(int i = 0; i < cellCnt; i++) {
						
						cell = row.getCell(i);
						
						if(cell == null) {
							continue;
						} else {
							rRow[i] = cell.getNumericCellValue();
						} // cell is not check end
					}
					
					rArr.add(rRow);
				} // row is not check end
				
			} // end for
			
			workbook.close();
				
			return rArr;
		}
		
	}
	
	
	public static class Obj {
		
		public static void print(Object o) {
			
			Field[] fields = o.getClass().getFields();
			if(fields.length > 0) {
				//System.out.println("print  " + o.getClass().getName() + " -> ");
				System.out.print("{");
				for(Field f : fields) {
					f.setAccessible(true);
					//System.out.println();
					System.out.print("  " + f.getName() + " : ");
					try { System.out.print(f.get(o)); } catch (Exception e) { System.out.print("unknown"); e.printStackTrace();}
				}
				//System.out.println();
				//System.out.println("}");
				
			} else {
				System.out.print(o.getClass().getName());
				//System.out.println("{}");
			}
		}
		
	}
	
	public static class Adm {
		
		public static boolean authTypeCheck(HttpSession session, String authType) {
			boolean check = false;
			
			SADM003[] authTypeArr = (SADM003[]) session.getAttribute("authTypeArr");
			for(SADM003 auth : authTypeArr) {
				if(auth.auth_nm.equals(authType)) {
					check = true;
				}
			}
			
			return check;
		}
		
	}
	
}
