package kr.co.starfield.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  BaseController
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class BaseController {

	protected void initHandler(HttpServletRequest req, HttpServletResponse res) {
		res.setHeader("Cache-Control", "no-cache");
	}
	
//	protected void initHandler(HttpServletRequest req, HttpServletResponse res
//			, BaseModel model
//			) throws Exception {
//		
//		initHandler(req, res);
//		
//		String user = req.getHeader("x-initiator");
//		if(Utils.Str.isEmpty(user)) throw new Exception("unknown initiator");
//		model.user = user;
//	}
//	
//	protected void initHandler(HttpServletRequest req, HttpServletResponse res
//			, BaseModel[] models
//			) throws Exception {
//		
//		initHandler(req, res);
//		
//		String user = req.getHeader("x-initiator");
//		if(Utils.Str.isEmpty(user)) throw new Exception("unknown initiator");
//		
//		for(BaseModel m : models) {
//			m.user = user;
//		}
//		
//	}
	
}
