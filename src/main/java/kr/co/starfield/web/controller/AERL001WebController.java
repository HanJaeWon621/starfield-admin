package kr.co.starfield.web.controller;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.starfield.service.AERL001Service;

@Controller
@RequestMapping("/admin")
public class AERL001WebController {
	
	private Logger ll = LoggerFactory.getLogger(AERL001WebController.class);

	@Autowired
	AERL001Service errorLogService;
	
	//에러 로그 목록 페이지
	@RequestMapping(value = "/{bcn_cd}/getErrorLogs"
			, method = RequestMethod.GET)
	public ModelAndView errorLog(@PathVariable(value="bcn_cd") String bcn_cd) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("bcn_cd", bcn_cd);
		mv.setViewName("log/AERL001");
		return mv;
	}
	
}
