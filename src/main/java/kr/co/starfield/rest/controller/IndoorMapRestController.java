/*
 * couponController.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.rest.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.starfield.model.Bcn;
import kr.co.starfield.model.Block;
import kr.co.starfield.model.Floor;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.MapFile;
import kr.co.starfield.model.Poi;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.StyleSet;
import kr.co.starfield.model.TenantPoi;
import kr.co.starfield.model.Tnt;
import kr.co.starfield.service.IndoorMapService;

/**
 * 쿠폰 관리
 * 
 * @author dhlee
 * @version 1.0,
 * @see
 * @date 2016.04.11
 */

@RestController
@RequestMapping("/rest")
public class IndoorMapRestController extends BaseController {

	@Autowired
	IndoorMapService indoorMapService;

	private static final Logger ll = LoggerFactory.getLogger(IndoorMapRestController.class);

	/**
	 * 지점 리스트
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAllBcns", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	public ListResult<Bcn> getAllBcns(HttpServletRequest req, HttpServletResponse res, HttpSession session)
			throws Exception {

		this.initHandler(req, res);

		Bcn vo = new Bcn();
		vo.limit = -1;

		ListResult<Bcn> cplist = indoorMapService.getBcns(vo);

		return cplist;
	}

	/**
	 * 지점 리스트
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBcns", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	public ListResult<Bcn> getBcns(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "offset") String offset, @RequestParam(value = "limit") String limit)
					throws Exception {

		this.initHandler(req, res);

		Bcn vo = new Bcn();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		ListResult<Bcn> cplist = indoorMapService.getBcns(vo);

		return cplist;
	}

	/**
	 * 지점 상세
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBcn", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	@ResponseBody
	public Bcn getBcn(HttpServletRequest req, HttpServletResponse res, HttpSession session
	// , @PathVariable(value="bcn_seq") String bcn_seq
	, @RequestParam(value = "bcn_seq") String bcn_seq

	) throws Exception {

		this.initHandler(req, res);

		Bcn vo = new Bcn();
		vo.bcn_seq = bcn_seq;

		Bcn bl = indoorMapService.getBcn(vo);

		return bl;
	}

	/**
	 * 지점 등록
	 * 
	 * @return
	 */
	@RequestMapping(value = "/regBcn", method = RequestMethod.POST, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult regBcn(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestBody Bcn vo) throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		SimpleResult result = new SimpleResult();
		System.out.println("체크>>>" + vo.bcn_cd);
		result = indoorMapService.regBcn(vo);

		return result;
	}

	/**
	 * 지점 수정
	 * 
	 * @return
	 */
	@RequestMapping(value = "/modifyBcn", method = RequestMethod.POST, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult modifyBcn(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestBody Bcn vo) throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		SimpleResult result = new SimpleResult();
		result = indoorMapService.modifyBcn(vo);

		return result;
	}

	/**
	 * 지점 삭제
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deleteBcn", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult deleteBcn(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "bcn_seq") String bcn_seq) throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		SimpleResult result = new SimpleResult();
		Bcn vo = new Bcn();
		vo.bcn_seq = bcn_seq;
		result = indoorMapService.deleteBcn(vo);

		return result;
	}

	/**
	 * Poi 리스트
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getPois/{fl}", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	public ListResult<Poi> getPois(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@PathVariable(value = "bcnCd") String bcnCd, @PathVariable(value = "fl") String fl

	) throws Exception {

		this.initHandler(req, res);
		System.out.println("fl>>>" + fl);
		Poi vo = new Poi();
		vo.bcn_cd = bcnCd;
		vo.sh_fl = fl;

		ListResult<Poi> cplist = indoorMapService.getPois(vo);

		return cplist;
	}

	/**
	 * 테넌트 리스트
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getTenents/{fl}", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	public ListResult<Tnt> getTenents(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@PathVariable(value = "bcnCd") String bcnCd, @PathVariable(value = "fl") String fl

	) throws Exception {

		this.initHandler(req, res);

		Tnt vo = new Tnt();
		vo.bcn_cd = bcnCd;
		vo.sh_fl = fl;
		ListResult<Tnt> cplist = indoorMapService.getTenents(vo);

		return cplist;
	}

	/**
	 * 지점별 층 리스트
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBcnFloors", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	public ListResult<Floor> getFloors(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "sh_bcn_cd") String sh_bcn_cd

	) throws Exception {

		this.initHandler(req, res);

		Floor vo = new Floor();
		vo.limit = -1;
		vo.bcn_cd = sh_bcn_cd;

		ListResult<Floor> cplist = indoorMapService.getFloors(vo);

		return cplist;
	}

	/**
	 * 층 리스트
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getFloors", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	public ListResult<Floor> getFloors(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "offset") String offset, @RequestParam(value = "limit") String limit,
			@RequestParam(value = "sh_bcn_cd") String sh_bcn_cd

	) throws Exception {

		this.initHandler(req, res);

		Floor vo = new Floor();
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.bcn_cd = sh_bcn_cd;

		ListResult<Floor> cplist = indoorMapService.getFloors(vo);

		return cplist;
	}

	/**
	 * 층 상세
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getFloor", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	@ResponseBody
	public Floor getFloor(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "bcn_cd") String bcn_cd, @RequestParam(value = "fl_seq") String fl_seq

	) throws Exception {

		this.initHandler(req, res);

		Floor vo = new Floor();
		vo.fl_seq = fl_seq;
		vo.bcn_cd = bcn_cd;

		Floor bl = indoorMapService.getFloor(vo);

		return bl;
	}

	/**
	 * 층 상세
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{bcnCd}/getFloorMapFile/{fl}", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	@ResponseBody
	public Floor getFloorMapFile(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@PathVariable(value = "bcnCd") String bcnCd, @PathVariable(value = "fl") String fl
	// , @RequestParam(value="style_set_seq") String style_set_seq

	) throws Exception {

		this.initHandler(req, res);

		Floor vo = new Floor();
		vo.bcn_cd = bcnCd;
		vo.fl = fl;

		Floor bl = indoorMapService.getFloorMapFile(vo);

		return bl;
	}

	/**
	 * 층 등록
	 * 
	 * @return
	 */
	@RequestMapping(value = "/regFloor", method = RequestMethod.POST, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult regFloor(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestBody Floor vo) throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		SimpleResult result = new SimpleResult();
		result = indoorMapService.regFloor(vo);

		return result;
	}

	/**
	 * 층 수정
	 * 
	 * @return
	 */
	@RequestMapping(value = "/modifyFloor", method = RequestMethod.POST, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult modifyFloor(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestBody Floor vo) throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		SimpleResult result = new SimpleResult();
		result = indoorMapService.modifyFloor(vo);

		return result;
	}

	/**
	 * 층별 맵파일 순번 수정
	 * 
	 * @return
	 */
	@RequestMapping(value = "/modifyFloorFileSeq", method = RequestMethod.POST, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult modifyFloorFile(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestBody Floor vo) throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		SimpleResult result = new SimpleResult();
		result = indoorMapService.modifyFloorFileSeq(vo);

		return result;
	}

	/**
	 * 맵 파일에서 일괄 등록
	 * 
	 * @return
	 */
	@RequestMapping(value = "/regFloorBlockFromMapFile", method = RequestMethod.POST, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult regFloorBlockFromMapFile(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestBody Floor vo) throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		SimpleResult result = new SimpleResult();
		result = indoorMapService.regFloorBlockFromMapFile(vo);

		return result;
	}

	/**
	 * 층 삭제
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deleteFloor", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult deleteFloor(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "bcn_cd") String bcn_cd, @RequestParam(value = "fl_seq") String fl_seq)
					throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		SimpleResult result = new SimpleResult();
		Floor vo = new Floor();
		vo.bcn_cd = bcn_cd;
		vo.fl_seq = fl_seq;
		result = indoorMapService.deleteFloor(vo);

		return result;
	}

	/**
	 * 블록 리스트
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getFloorBlocks", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	public ListResult<Block> getFloorBlocks(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "offset") String offset, @RequestParam(value = "limit") String limit,
			@RequestParam(value = "sh_bcn_cd") String sh_bcn_cd, @RequestParam(value = "sh_fl") String sh_fl,
			@RequestParam(value = "sh_room_num") String sh_room_num

	) throws Exception {

		this.initHandler(req, res);

		Block vo = new Block();
		vo.bcn_cd = sh_bcn_cd;
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.bcn_cd = sh_bcn_cd;
		vo.sh_fl = sh_fl;
		vo.sh_room_num = sh_room_num;

		ListResult<Block> cplist = indoorMapService.getFloorBlocks(vo);
		return cplist;
	}

	/**
	 * 블록 상세
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getFloorBlock", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	@ResponseBody
	public Block getFloorBlock(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "sh_bcn_cd") String sh_bcn_cd, @RequestParam(value = "sh_fb_seq") String sh_fb_seq
	// , @RequestParam(value="style_set_seq") String style_set_seq

	) throws Exception {

		this.initHandler(req, res);

		Block vo = new Block();
		vo.bcn_cd = sh_bcn_cd;
		vo.fb_seq = sh_fb_seq;

		Block bl = indoorMapService.getFloorBlock(vo);

		return bl;
	}

	/**
	 * 블록 등록
	 * 
	 * @return
	 */
	@RequestMapping(value = "/regFloorBlock", method = RequestMethod.POST, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult regFloorBlock(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestBody Block vo) throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		SimpleResult result = new SimpleResult();
		result = indoorMapService.regFloorBlock(vo);

		return result;
	}

	/**
	 * 블록 수정
	 * 
	 * @return
	 */
	@RequestMapping(value = "/modifyFloorBlock", method = RequestMethod.POST, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult modifyFloorBlock(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestBody Block vo) throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		SimpleResult result = new SimpleResult();
		result = indoorMapService.modifyFloorBlock(vo);

		return result;
	}

	/**
	 * 블록 삭제
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deleteFloorBlock", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult deleteFloorBlock(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "sh_bcn_cd") String sh_bcn_cd, @RequestParam(value = "sh_fb_seq") String sh_fb_seq)
					throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		SimpleResult result = new SimpleResult();
		Block vo = new Block();
		vo.bcn_cd = sh_bcn_cd;
		vo.fb_seq = sh_fb_seq;
		result = indoorMapService.deleteFloorBlock(vo);

		return result;
	}

	/**
	 * 층별 블록 전체삭제
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deleteAllFloorBlock", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult deleteFloorAllBlock(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "sh_bcn_cd") String sh_bcn_cd, @RequestParam(value = "sh_fl") String sh_fl)
					throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		SimpleResult result = new SimpleResult();
		Block vo = new Block();
		vo.bcn_cd = sh_bcn_cd;
		vo.fl = sh_fl;
		result = indoorMapService.deleteAllFloorBlock(vo);

		return result;
	}

	/**
	 * 맵파일 생성
	 * 
	 * @return
	 */
	@RequestMapping(value = "/makeMapStyleFile", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult makeMapStyleFile(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "sh_bcn_cd") String sh_bcn_cd) throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		MapFile vo1 = new MapFile();
		vo1.bcn_cd = sh_bcn_cd;
		SimpleResult result = indoorMapService.deleteMapStyleFile(vo1);
		if (result.code == 0) {
			indoorMapService.makeMapStyleFile(vo1);
		}

		return result;
	}

	/**
	 * 맵데이터 생성
	 * 
	 * @return
	 */
	@RequestMapping(value = "/saveMapFile", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult saveMapFile(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "sh_bcn_cd") String sh_bcn_cd) throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		MapFile vo1 = new MapFile();
		vo1.bcn_cd = sh_bcn_cd;

		indoorMapService.saveMapFile(vo1);
		SimpleResult result = new SimpleResult();
		result.code = 0;
		return result;
	}

	/**
	 * 맵파일 생성
	 * 
	 * @return
	 */
	@RequestMapping(value = "/makeMapFile", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult makeMapFile(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "sh_bcn_cd") String sh_bcn_cd) throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		MapFile vo1 = new MapFile();
		vo1.bcn_cd = sh_bcn_cd;
		SimpleResult result = indoorMapService.deleteMapFile(vo1);
		if (result.code == 0) {
			indoorMapService.makeMapFile(vo1);
		}

		return result;
	}

	/**
	 * 테넌트/편의시설 리스트
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getTenantPois", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	public ListResult<TenantPoi> getTenantPois(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "offset") String offset, @RequestParam(value = "limit") String limit,
			@RequestParam(value = "sh_bcn_cd") String sh_bcn_cd, @RequestParam(value = "sh_fl") String sh_fl,
			@RequestParam(value = "sh_div_cd") String sh_div_cd

	) throws Exception {

		this.initHandler(req, res);

		TenantPoi vo = new TenantPoi();
		vo.bcn_cd = sh_bcn_cd;
		vo.offset = Integer.parseInt(offset);
		vo.limit = Integer.parseInt(limit);
		vo.sh_fl = sh_fl;
		vo.sh_div_cd = sh_div_cd;

		ListResult<TenantPoi> cplist = indoorMapService.getTenantPois(vo);
		return cplist;
	}

	/**
	 * 테넌트/편의시설 상세
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getTenantPoi", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	@ResponseBody
	public TenantPoi getTenantPoi(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "sh_bcn_cd") String sh_bcn_cd,
			@RequestParam(value = "sh_tenant_poi_seq") String sh_tenant_poi_seq
	// , @RequestParam(value="style_set_seq") String style_set_seq

	) throws Exception {

		this.initHandler(req, res);

		TenantPoi vo = new TenantPoi();
		vo.bcn_cd = sh_bcn_cd;
		vo.tenant_poi_seq = sh_tenant_poi_seq;

		TenantPoi bl = indoorMapService.getTenantPoi(vo);

		return bl;
	}

	/**
	 * 테넌트/편의시설 등록
	 * 
	 * @return
	 */
	@RequestMapping(value = "/regTenantPoi", method = RequestMethod.POST, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult regTenantPoi(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestBody TenantPoi vo) throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		SimpleResult result = new SimpleResult();
		result = indoorMapService.regTenantPoi(vo);

		return result;
	}

	/**
	 * 테넌트/편의시설 수정
	 * 
	 * @return
	 */
	@RequestMapping(value = "/modifyTenantPoi", method = RequestMethod.POST, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult modifyTenantPoi(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestBody TenantPoi vo) throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		SimpleResult result = new SimpleResult();
		result = indoorMapService.modifyTenantPoi(vo);

		return result;
	}

	/**
	 * 테넌트/편의시설 삭제
	 * 
	 * @return
	 */
	@RequestMapping(value = "deleteTenantPoi", method = RequestMethod.GET, produces = "application/json; charset=utf-8", headers = "content-type=application/json")
	/* @ResponseBody */
	public SimpleResult deleteTenantPoi(HttpServletRequest req, HttpServletResponse res, HttpSession session,
			@RequestParam(value = "sh_bcn_cd") String sh_bcn_cd,
			@RequestParam(value = "sh_tenant_poi_seq") String sh_tenant_poi_seq) throws Exception {
		// String login_id = (String) session.getAttribute("login_id");

		SimpleResult result = new SimpleResult();
		TenantPoi vo = new TenantPoi();
		vo.bcn_cd = sh_bcn_cd;
		vo.tenant_poi_seq = sh_tenant_poi_seq;
		result = indoorMapService.deleteTenantPoi(vo);

		return result;
	}

	/** Campaign 파일 등록 map/floorblocksup */
	@RequestMapping(value = "/{bcnCd}/map/floorblocksup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> handleWritePostFileInsert(MultipartHttpServletRequest multiRequest,
			HttpServletRequest request
	// ,@ModelAttribute("jtoCampaignContentsVO") JtoCampaignContentsVO
	// jtoCampaignContentsVO
	) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		saveContentsFile2(multiRequest);
		/*
		 * String imageFileId="";
		 * if(StringUtil.isEmpty(jtoCampaignContentsVO.getImageFileId())){
		 * imageFileId = jtoCampaignFileIdGnr.getNextStringId();
		 * jtoCampaignContentsVO.setImageFileId(imageFileId); }
		 * 
		 * List<JtoCampaignFileVO> fileVOList = saveContentsFile2(multiRequest,
		 * jtoCampaignContentsVO);
		 * 
		 * 
		 * imageFileId=jtoCampaignContentsVO.getImageFileId();
		 * //FileLog.writeFile("result 셋팅>>"); result.put("imageFileId",
		 * imageFileId); result.put("data",fileVOList); //FileLog.writeFile(
		 * "result 이후>>"); //return fileVOList;
		 */
		return result;
	}

	/** File Upload */
	// private List<JtoCampaignFileVO>
	// saveContentsFile2(MultipartHttpServletRequest multiRequest,
	// JtoCampaignContentsVO vo)
	private void saveContentsFile2(MultipartHttpServletRequest multiRequest) throws Exception {

		// private List<JtoCampaignFileVO>
		// saveContentsFile2(MultipartHttpServletRequest multiRequest,
		// JtoCampaignContentsVO vo)
		// throws Exception {
		// List<JtoCampaignFileVO> fileVOList = new
		// ArrayList<JtoCampaignFileVO>();

		if ((multiRequest.getFileMap() != null) && (multiRequest.getFileMap().size() > 0)) {

			// C://Workspace/eGovFrameDev-3.5.1-64bit/jto/jtotour/src/main/webapp
			String rootPath = "C://gitNew2/starfield-admin/src/main/webapp";// egovMessageSource.getMessage("Globals.RootPath");
			String uploadPath = "/upload_data";// egovMessageSource.getMessage("Globals.UploadPath");
			String extension = "";// egovMessageSource.getMessage("Globals.jto.contents.extension");

			Map<String, String[]> atchFileInfo = new HashMap<String, String[]>();
			// atchFileInfo.put("image", new String[] { vo.getImageFileId(),
			// JtoGlobalConst.CONTENTS_UPLOAD_SUBDIR_CAMPAIGN });
			// FileLog.writeFile("1parseFile 이전>>");
			// <String, List<JtoCampaignFileVO>> uploadFileData =
			parseFile(multiRequest, rootPath, uploadPath + "/mapfile", extension, atchFileInfo);
			// FileLog.writeFile("2parseFile 이후>>");

			// String atchFileId = vo.getImageFileId();
			// String fileinputNm = "image";
			// fileVOList = uploadFileData.get(fileinputNm);
			// if (!empty(fileVOList)) {
			// if (empty(atchFileId)) {
			// atchFileId =
			// jtoCampaignFileService.insertJtoCampaignFile(fileVOList);
			// vo.setImageFileId(atchFileId);
			// } else {
			// FileLog.writeFile("3insertJtoCampaignFile이전>>");
			// jtoCampaignFileService.insertJtoCampaignFile(fileVOList);
			// FileLog.writeFile("4insertJtoCampaignFile이후>>");
			// }
			// }
		}
		// FileLog.writeFile("5종료>>");
		// return fileVOList;
	}

	public void parseFile(MultipartHttpServletRequest multiRequest, String rootPath, String userUploadPath,
			String limitFileExt, Map<String, String[]> atachFileIdMap) throws Exception {

		String uploadPath = rootPath + userUploadPath;
		MultiValueMap<String, MultipartFile> files = multiRequest.getMultiFileMap();

		Iterator<Map.Entry<String, List<MultipartFile>>> itr = files.entrySet().iterator();
		// List<JtoCampaignFileVO> fileVOList = new ArrayList();

		// String[] fileName = multiRequest.getParameterValues("imageName");
		// FileLog.writeFile("6파일처리이전>>");
		while (itr.hasNext()) {
			Map.Entry<String, List<MultipartFile>> entry = (Map.Entry) itr.next();
			String inputfileNm = entry.getKey();
			if (atachFileIdMap.get(inputfileNm) == null)
				continue; // 약속된 file input name 이외에는 저장하지 않음

			// String[] atchFileInfo = atachFileIdMap.get(inputfileNm);
			// String atchFileId = atchFileInfo[0]; // 파일첨부아이디

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/yyyyMM", Locale.getDefault());
			String uploadSubDir = sdf.format(cal.getTime());

			// String subdir = atchFileInfo[1]; // 파일 저장 sub directory
			String subdir = uploadSubDir; // 파일 저장 sub directory

			// List<JtoCampaignFileVO> list;

			List<MultipartFile> listFiles = (List) entry.getValue();
			Iterator localIterator = listFiles.iterator();

			int index = 0;
		}
		// FileLog.writeFile("9파일처리이후>>");
		// return result;
	}

}
