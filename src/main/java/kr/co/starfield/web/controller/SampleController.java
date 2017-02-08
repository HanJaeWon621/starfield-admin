package kr.co.starfield.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jxl.Sheet;
import jxl.Workbook;
import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.Utils;
import kr.co.starfield.model.ImgFile;
import kr.co.starfield.model.Sample;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SampleVo;
import kr.co.starfield.rest.controller.BaseController;
import kr.co.starfield.service.SampleService;



@Controller
public class SampleController extends BaseController {

	
	@Autowired
	SampleService sampleService;
	
	@RequestMapping(value = "/excel/samples"
			, method = RequestMethod.GET
			, produces = "application/vnd.ms-excel")
	@ResponseBody
	public ModelAndView empListPage(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		
		this.initHandler(req, res);
		res.setHeader("Content-Disposition", "attachment; filename=\" " + "test" + ".xls\"");
		
		Sample[] sampleList = sampleService.getSamples();
		
		Map<String, Object> dataList = new LinkedHashMap<String, Object>();
		
		String[] arrTitle = {"숫자컬럼", "문자컬럼", "날짜컬럼"};
		String[] colNameList = {"num_col", "varchar_col", "date_col"};
		
		dataList.put("sheetName", "샘플엑셀시트");
		dataList.put("titleList", arrTitle);
		dataList.put("colNameList", colNameList);
		dataList.put("dataList", sampleList);
		
		ModelAndView mav = new ModelAndView("testExcelBuilder", "data", dataList);
		
		return mav;
	}
	
	@RequestMapping(value = "/excel/samples"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8")
	@ResponseBody
    public SimpleResult excelUploadSample(
    		HttpServletRequest req, HttpServletResponse res, HttpSession session
    		, @RequestParam("file") MultipartFile file) throws BaseException, Exception {
		
		SimpleResult result = sampleService.regSample(file);
			
		return result;
	}
	
	@RequestMapping(value = "/view/images", method = RequestMethod.GET)
    public ModelAndView imgFileUploadPage(
    		HttpServletRequest req, HttpServletResponse res, HttpSession session
    		) throws BaseException, Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sample/imgMultiUpload");
		
		return mav;
	}
	
	@RequestMapping(value = "/view/excels", method = RequestMethod.GET)
    public ModelAndView excelUploadPage(
    		HttpServletRequest req, HttpServletResponse res, HttpSession session
    		) throws BaseException, Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sample/excelUploadSample");
		
		return mav;
	}
	
	
	
//	@RequestMapping(value = "getMachineExcelFile", method = RequestMethod.POST, produces = "application/vnd.ms-excel")
//	@ResponseBody
//	public ModelAndView getMachineExcelFile(MachineData data, HttpSession session, HttpServletResponse response) {
//		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
//		String fn ="MachinDetail "+ dt.format(new Date());
//		response.setHeader("Content-Disposition", "attachment; filename=\" " + fn + ".xls\"");
//		response.setHeader("Cache-Control", "no-cache");
//		ModelAndView mav = this.machineService.getMachineExcelFile(data,session);
//		return mav;
//	}
	
}
