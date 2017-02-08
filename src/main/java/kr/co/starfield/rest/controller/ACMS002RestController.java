package kr.co.starfield.rest.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.model.InstagramFilter;
import kr.co.starfield.model.InstagramImage;
import kr.co.starfield.model.InstagramTag;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.AACT001Service;
import kr.co.starfield.service.ACMS002Service;

/**
 *  ACMS004(instagram) REST 컨트롤러 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika.
 *
 * @author hhLee
 * @version 1.0,
 * @see
 * @date 2016.06.20
 */
@RestController
@RequestMapping("/admin/rest")
public class ACMS002RestController extends BaseController {
	
	private Logger ll = LoggerFactory.getLogger(ACMS002RestController.class);
	
	@Autowired
	ACMS002Service acms002Service;
	
	@Autowired
    AACT001Service aact001service;
	
	/**
	 * instagram tag 목록 조회
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/instagram"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public ArrayList<InstagramTag> getInstagramTagList(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getInstagramTagList bcn_cd : {}", new Object[] {bcn_cd});
		
		InstagramFilter filter = new InstagramFilter();
		
		filter.bcn_cd = bcn_cd;
		
		ArrayList<InstagramTag> instagramTagList = acms002Service.getInstagramTagList(filter);
		
		aact001service.regAdminAction(req, session, "Instagram tag 목록 조회", null);
		
		return instagramTagList;
	}

	/**
	 * instagram tag 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/instagram"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regInstagramTag(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody InstagramTag[] instagramTagList ) throws Exception {
		
		this.initHandler(req, res);
		

		for(InstagramTag instagramTag : instagramTagList) {
			instagramTag.reg_usr = (String) session.getAttribute("adm_seq");
			instagramTag.mod_usr = (String) session.getAttribute("adm_seq");
		}
		
		SimpleResult result = acms002Service.regInstagramTag(instagramTagList);
		
		aact001service.regAdminAction(req, session, "Instagram tag 등록/수정", null);
		
		return result;
	}
	
	/**
	 * instagram tag 상세
	 * @return
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub", "handle_personal_info"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/instagram/{insta_tag_seq}"
			, method = RequestMethod.GET
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public InstagramTag getInstagramTag(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="insta_tag_seq") String insta_tag_seq
	) throws Exception {
		
		this.initHandler(req, res);
		
		ll.info("getInstagramTag bcn_cd : {}, insta_tag_seq : {}", new Object[] {bcn_cd, insta_tag_seq});
		
		InstagramFilter filter = new InstagramFilter();
		
		filter.bcn_cd = bcn_cd;
		
		filter.insta_tag_seq = insta_tag_seq;
		
		InstagramTag instagramTag = acms002Service.getInstagramTag(filter);
		
		aact001service.regAdminAction(req, session, "Instagram 이미지 리스트 조회", null);
		
		return instagramTag;
	}
	
	/**
	 * instagram image 등록
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/instagram/{insta_tag_seq}"
			, method = RequestMethod.POST
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult regInstagramImage(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @PathVariable(value="insta_tag_seq") String insta_tag_seq
			, @RequestBody InstagramImage[] instagramImageList ) throws Exception {
		
		this.initHandler(req, res);
		

		for(InstagramImage instagramImage : instagramImageList) {
			instagramImage.reg_usr = (String) session.getAttribute("adm_seq");
			instagramImage.mod_usr = (String) session.getAttribute("adm_seq");
			instagramImage.insta_tag_seq = insta_tag_seq;
		}
		
		SimpleResult result = acms002Service.regInstagramImage(instagramImageList, bcn_cd);
		
		aact001service.regAdminAction(req, session, "Instagram Image 등록/수정", null);
		
		return result;
	}
	
	
	/**
	 * instagram tag 업데이트
	 * @return 
	 */
	@AuthRequired(authArr={"system", "starfield", "store_master", "store_sub"})
	@RequestMapping(value = "/{bcn_cd}/starfieldStory/token"
			, method = RequestMethod.PUT
			, produces = "application/json; charset=utf-8"
			, headers = "content-type=application/json"
		)
	public SimpleResult updateToken(
			HttpServletRequest req, HttpServletResponse res, HttpSession session
			, @PathVariable(value="bcn_cd") String bcn_cd
			, @RequestBody String token) throws Exception {
		
		this.initHandler(req, res);
		
		token = token.replaceAll("\"", "");

		SimpleResult result = acms002Service.setToken(token);
		
		aact001service.regAdminAction(req, session, "Instagram token 업데이트", null);
		
		return result;
	}
}
