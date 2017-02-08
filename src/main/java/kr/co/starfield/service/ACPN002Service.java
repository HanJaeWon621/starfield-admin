package kr.co.starfield.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.poi.util.StringUtil;
import org.krysalis.barcode4j.BarcodeClassResolver;
import org.krysalis.barcode4j.DefaultBarcodeClassResolver;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.MimeTypes;
import org.krysalis.barcode4j.tools.UnitConv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonMappingException;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.Utils;
import kr.co.starfield.mapper.ACPN002Mapper;
import kr.co.starfield.mapper.AMBR001Mapper;
import kr.co.starfield.mapper.SAML001Mapper;
import kr.co.starfield.model.ACPN001;
import kr.co.starfield.model.ACPN002;
import kr.co.starfield.model.ACPN003;
import kr.co.starfield.model.ACPN004;
import kr.co.starfield.model.ACPN006;
import kr.co.starfield.model.ALBS004_D;
import kr.co.starfield.model.CdnUploadBean;
import kr.co.starfield.model.CouponTenant;
import kr.co.starfield.model.LikeTenant;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Member;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SAML003;
import kr.co.starfield.model.SCPN001;
import kr.co.starfield.model.SCPN002;
import kr.co.starfield.model.SCPN003;
import kr.co.starfield.model.SCPN004;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.ACPN002Vo;
import kr.co.starfield.model.vo.ALBS004_DVo;
import kr.co.starfield.model.vo.SCPN001Vo;
import kr.co.starfield.model.vo.SCPN002Vo;
import kr.co.starfield.model.vo.SCPN002_D2Vo;
import kr.co.starfield.model.vo.SCPN006Vo;
import kr.co.starfield.model.vo.SCTG001Vo;
import kr.co.starfield.model.vo.SMBR001Vo;
import kr.co.starfield.model.vo.SMBR003Vo;
import kr.co.starfield.model.vo.STNT001Vo;

/**
 * 쿠폰 REST 서비스 클래스
 *
 * @author dhlee
 * @version 1.0,
 * @see
 * @date 2016.08.01
 */

@Service
public class ACPN002Service {

	@Autowired
	private ACPN002Mapper mapper;

	@Autowired
	private AMBR001Mapper ambr001Mapper;

	@Autowired
	private SAML001Service sopr001service;

	@Autowired
	private SAML001Mapper saml001mapper;

	@Autowired
	FileService fileService;

	@Value("${file.barcode.temp.path}")
	String imgPath;
	@Value("${file.img.path}")
	String barcodePath;
	@Value("${cdn.img.path}")
	String cdnImgPath;

	private static final Logger ll = LoggerFactory.getLogger(ACPN002Mapper.class);

	public SCPN001 getIssuCoupon(String cp_seq) throws BaseException {
		SCPN001 scpn001p = new SCPN001();
		scpn001p.cp_seq = cp_seq;
		scpn001p.cust_id = "";
		SCPN001 scpn001 = mapper.getIssuCoupon(scpn001p);

		if (scpn001 == null) {
			BaseException be = new BaseException(ErrorCode.Common.CATEGORY_NOT_FOUND_DATA);
			throw be;
		}

		return scpn001;
	}

	@Transactional(rollbackFor = Exception.class)
	public void reqIssuCoupon(SCPN002Vo vo) {

		mapper.reqIssuCoupon(vo);

	}

	@Transactional(rollbackFor = Exception.class)
	public void regBcImg(SCPN006Vo scpn006Vo) {

		mapper.regBcImg(scpn006Vo);

	}

	/**
	 * 쿠폰 발급 //담기
	 * 
	 * @param bcn_cd,
	 *            cust_id, cp_seq
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult issuCouponNew(String bcn_cd, String cust_id, String cp_seq) throws BaseException {
		SimpleResult result = new SimpleResult();

		SCPN001 scpn001p = new SCPN001();
		scpn001p.bcn_cd = bcn_cd;
		scpn001p.cp_seq = cp_seq;
		scpn001p.cust_id = cust_id;
		mapper.SP_SCPN002_PRE(scpn001p);//
		// member.uuid
		String resultCd = scpn001p.result;
		if (resultCd.equals("MEMBER_IS_NOT")) {
			result.code = -9;
			result.desc = "회원정보없음";
		} else {
			// System.out.println("11111>>");
			try {

				if (resultCd.equals("E")) {
					result.code = -1;
					result.desc = "쿠폰번호체번시 에러";
					return result;
				}
				// 중복 담기 체크
				if (resultCd.equals("0000000D")) {
					result.code = -11;
					result.desc = "기발급";
					return result;
				}

				if (resultCd.equals("0000000E")) {
					result.code = -1;
					result.desc = "쿠폰번호체번시 에러";
					return result;
				}
				// 담기 수량 체크
				if (resultCd.equals("DN_OVER")) {
					result.code = -10;
					result.desc = "다운수량 초과";
					return result;
				}

				// 엑션 로그(쿠폰 담기)
				/*
				 * String log_type = "A"; String evt_div_cd1 = "F1000"; String
				 * key = "cp_seq"; String val = cp_seq; String uuid =
				 * scpn001p.uuid; sopr001service.appActionLog(log_type,
				 * evt_div_cd1, key, val, uuid, cust_id);
				 */
				if (scpn001p.cp_div_cd.equals("1")) { // 모바일
					result = issuMbCouponNew(scpn001p.uuid, cust_id, cp_seq, scpn001p);
					// result = issuMbCoupon( "112233", cust_id, cp_seq, scpn001
					// );
				} else {
					result = useNmCoupon(scpn001p.uuid, cust_id, cp_seq, scpn001p);
					// result = useNmCoupon( "112233", cust_id, cp_seq, scpn001
					// );

				}
			} catch (Exception e) {
				// System.out.println("2222>>");
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 쿠폰 발급 //담기
	 * 
	 * @param bcn_cd,
	 *            cust_id, cp_seq
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult issuCoupon(String bcn_cd, String cust_id, String cp_seq) throws BaseException {
		SimpleResult result = new SimpleResult();

		SMBR001Vo smbr001vo = new SMBR001Vo();
		smbr001vo.bcn_cd = bcn_cd;
		smbr001vo.cust_id = cust_id;
		smbr001vo.sts = 1;

		SMBR001Vo member = ambr001Mapper.getMember(smbr001vo);

		if (member == null) {
			result.code = -9;
			result.desc = "회원정보없음";
		} else {
			// System.out.println("11111>>");
			try {
				SCPN001 scpn001p = new SCPN001();
				scpn001p.cp_seq = cp_seq;
				scpn001p.cust_id = cust_id;
				SCPN001 scpn001 = mapper.getIssuCoupon(scpn001p);
				String cp_iss_sub_seq = scpn001.cp_iss_sub_seq;
				if (cp_iss_sub_seq == null) {
					result.code = -1;
					result.desc = "쿠폰번호체번시 에러";
					return result;
				}
				if (cp_iss_sub_seq.equals("0000000D")) {
					result.code = -11;
					result.desc = "기밥급";
					return result;
				}

				if (cp_iss_sub_seq.equals("0000000E")) {
					result.code = -1;
					result.desc = "쿠폰번호체번시 에러";
					return result;
				}
				// 중복 담기 체크
				/*
				 * SCPN001 cpck = new SCPN001(); cpck.cp_seq = cp_seq;
				 * cpck.cust_id = cust_id; int use_dn_cnt =
				 * mapper.getUseDnCntCk(cpck); // 다운 수량 if (use_dn_cnt > 0) {
				 * result.code = -11; result.desc = "기밥급"; return result; }
				 */
				// 담기 수량 체크
				int cp_iss_cnt = Integer.parseInt(scpn001.cp_iss_cnt); // 쿠폰 수량
				int iss_cnt = mapper.getIssCntCk(cp_seq); // 다운 수량
				if (cp_iss_cnt <= iss_cnt) {
					result.code = -10;
					result.desc = "다운수량 초과";
					return result;
				}

				// 엑션 로그(쿠폰 담기)
				String log_type = "A";
				String evt_div_cd1 = "F1000";
				String key = "cp_seq";
				String val = cp_seq;
				String uuid = member.uuid;
				sopr001service.appActionLog(log_type, evt_div_cd1, key, val, uuid, cust_id);

				if (scpn001.cp_div_cd.equals("1")) { // 모바일
					result = issuMbCoupon(member.uuid, cust_id, cp_seq, scpn001);
					// result = issuMbCoupon( "112233", cust_id, cp_seq, scpn001
					// );
				} else {
					result = useNmCoupon(member.uuid, cust_id, cp_seq, scpn001);
					// result = useNmCoupon( "112233", cust_id, cp_seq, scpn001
					// );

				}
			} catch (Exception e) {
				// System.out.println("2222>>");
				e.printStackTrace();
			}
		}
		return result;
	}

	// 모바일 쿠폰
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult issuMbCouponNew(String uuid, String cust_id, String cp_seq, SCPN001 scpn001) throws BaseException {
		// 쿠폰 데이터
		// SCPN001 scpn001 = mapper.getIssuCoupon(cp_seq);
		scpn001.cust_id = cust_id;
		scpn001.uuid = uuid;
		scpn001.cp_seq = cp_seq;

		// 쿠폰 바코드 생성, 쿠폰 발급 등록
		SimpleResult result = inItCreateBarcodeNew(scpn001);
		/*
		 * //주석 처리 if (result.code == 0) { scpn001.uuid = uuid; scpn001.cust_id
		 * = cust_id; scpn001.cp_seq = cp_seq; mapper.useMbCoupon(scpn001); }
		 */
		return result;
	}

	// 모바일 쿠폰
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult issuMbCoupon(String uuid, String cust_id, String cp_seq, SCPN001 scpn001) throws BaseException {
		// 쿠폰 데이터
		// SCPN001 scpn001 = mapper.getIssuCoupon(cp_seq);
		scpn001.cust_id = cust_id;
		scpn001.uuid = uuid;
		scpn001.cp_seq = cp_seq;

		// 쿠폰 바코드 생성, 쿠폰 발급 등록
		SimpleResult result = inItCreateBarcode(scpn001);
		/*
		 * //주석 처리 if (result.code == 0) { scpn001.uuid = uuid; scpn001.cust_id
		 * = cust_id; scpn001.cp_seq = cp_seq; mapper.useMbCoupon(scpn001); }
		 */
		return result;
	}

	// 일반 쿠폰 노출
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult nmCouponPosting(String cp_seq) throws BaseException {
		SimpleResult result = new SimpleResult();
		SCPN001 scpn001p = new SCPN001();
		scpn001p.cp_seq = cp_seq;
		scpn001p.cust_id = "";
		// 쿠폰 데이터
		SCPN001 scpn001 = mapper.getIssuCoupon(scpn001p);

		int cnt = mapper.getUseCouponCnt(cp_seq);

		// 쿠폰 게시 로그
		String log_type = "A";
		String key = "cp_seq";
		String val = cp_seq;
		String evt_div_cd1 = "A1000";

		if (cnt == 0) {

			String cp_div_cd = scpn001.cp_div_cd;
			String cp_kind_cd = scpn001.cp_kind_cd;
			String cp_iss_type_cd = scpn001.cp_iss_type_cd;

			if (cp_kind_cd.equals("3")) {// 교환 쿠폰
				SCPN002Vo scpn002Vo = new SCPN002Vo();
				scpn002Vo.cp_seq = scpn001.cp_seq;
				scpn002Vo.cp_iss_sub_seq = scpn001.cp_iss_sub_seq;
				// scpn002Vo.reg_usr = "ldh";
				mapper.reqIssuCoupon(scpn002Vo);

				result.code = 0;
				result.extra = scpn001.img_thmb_uri;

				mapper.updatePosting(cp_seq);
			} else {// 일반 쿠폰
					// 쿠폰 바코드 생성, 쿠폰 발급 등록
				result = inItCreateBarcode(scpn001);

				mapper.updatePosting(cp_seq);
			}
			sopr001service.appActionLogAdm(log_type, evt_div_cd1, val);
		} else {
			mapper.updatePosting(cp_seq);
			sopr001service.appActionLogAdm(log_type, evt_div_cd1, val);
		}

		return result;
	}

	// 일반 쿠폰 발급
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult useNmCoupon(String uuid, String cust_id, String cp_seq, SCPN001 scpn001) {
		SimpleResult result = new SimpleResult();
		int cnt = 0;

		try {
			scpn001.uuid = uuid;
			scpn001.cust_id = cust_id;
			scpn001.cp_seq = cp_seq;

			/////// 1차용 2차떄 삭제///////////
			int downCnt = mapper.useCouponCnt(scpn001);
			if (downCnt > 0) {
				cnt = mapper.reCoupon(scpn001);

			} else {
				cnt = useCoupon(scpn001);
			}
			////////////////////////////

			/////// 2차용 ///////////////////
			// int cnt = useCoupon(scpn001);
			///////////////////////////////

			if (cnt > 0) {
				if (scpn001.cp_kind_cd.equals("3")) { // 교환
					result.extra = scpn001.img_uri;
					result.desc = "성공";
					result.code = 0;
				} else {
					SCPN002 scpn002 = mapper.getNmCpBcImg(cp_seq);
					result.extra = scpn002.bcd_img_uri;
					result.desc = "성공";
					result.code = 0;
				}
			} else {
				result.desc = "실패";
				result.code = -1;
			}

		} catch (DataAccessException e) {
			if (e instanceof DataIntegrityViolationException) {
				result.desc = "이미 발급 받은 쿠폰 입니다";
				result.code = -2;
			}
		}

		return result;
	}

	// 쿠폰 사용
	@Transactional(rollbackFor = Exception.class)
	private int useCoupon(SCPN001 scpn001) {
		int cnt = mapper.useCoupon(scpn001);
		return cnt;
	}

	public int prccnt = 0;// 실행 카운트

	@Transactional(rollbackFor = Exception.class)
	public SimpleResult inItCreateBarcodeNew(SCPN001 scpn001) {
		SimpleResult result = new SimpleResult();

		try {

			// 바코드 이미지 저장경로 생성
			String dir = scpn001.yymm;
			String sub_dir = scpn001.sub_dir;
			String imgDir = imgPath + dir + "/" + sub_dir + "/";
			String cndImgDir = cdnImgPath + "barcode/" + dir + "/" + sub_dir + "/";
			String barcodeDir = barcodePath + "barcode/" + dir + "/" + sub_dir + "/";
			new File(imgDir).mkdirs();
			// System.out.println("바코드생성 시작>>");
			// 바코드 이미지 생성
			final int dpi = 400;
			boolean isAntiAliasing = false;
			String barcodeType = "code128";
			String barcodeData = scpn001.cp_iss_bcd_id;// 바코드 번호
			String fileFormat = "jpg";
			String fileName = barcodeData;
			String outputFile = imgDir + fileName + "." + fileFormat;
			String cndFullUrl = cndImgDir + fileName + "." + fileFormat;
			String barcodeUrl = barcodeDir + fileName + "." + fileFormat;
			result = createBarcode(barcodeType, barcodeData, fileFormat, isAntiAliasing, dpi, outputFile);
			result.extra = cndFullUrl;
			// System.out.println("444444>>");
			/*
			 * if (prccnt == 4) { result.code = 0; } else { result.code = -1;
			 * Exception be = new
			 * BaseException(ErrorCode.Common.CATEGORY_NOT_FOUND_DATA); throw
			 * be; }
			 */
			// System.out.println(prccnt + "result.code>>" + result.code);
			// 쿠폰 발급 등록
			if (result.code == -1) {
				result.code = -1;
				result.desc = "바코드 생성중 문제발생";
				// inItCreateBarcode(scpn001);
				// }else(result.code != -1) {
			} else {
				// System.out.println("쿠폰다운정보 입력>>");

				CdnUploadBean been = new CdnUploadBean();
				been.srcPath = outputFile;
				been.ext = fileFormat;
				been.dir = barcodeDir;
				been.seq = fileName;
				been.filename = fileName + "." + fileFormat;
				fileService.uploadWithFilePath(been);
				/*
				 * SCPN006Vo scpn006Vo = new SCPN006Vo(); scpn006Vo.bcn_cd =
				 * scpn001.bcn_cd; scpn006Vo.bcd_img_pys_loc = barcodeUrl;
				 * scpn006Vo.bcd_img_uri = cndFullUrl; scpn006Vo.reg_usr = "";
				 * mapper.regBcImg(scpn006Vo);
				 * 
				 * SCPN002Vo scpn002Vo = new SCPN002Vo(); scpn002Vo.reg_usr =
				 * ""; scpn002Vo.cp_seq = scpn001.cp_seq; scpn002Vo.img_seq =
				 * scpn006Vo.bcd_img_seq; scpn002Vo.cp_iss_sub_seq =
				 * scpn001.cp_iss_sub_seq; scpn002Vo.cp_iss_bcd_id =
				 * scpn001.cp_iss_bcd_id; scpn002Vo.reg_usr = "";
				 * mapper.reqIssuCoupon(scpn002Vo); scpn001.cp_iss_mst_seq =
				 * scpn002Vo.cp_iss_mst_seq;
				 * 
				 * scpn001.uuid = scpn001.uuid; scpn001.cust_id =
				 * scpn001.cust_id; scpn001.cp_seq = scpn001.cp_seq;
				 * mapper.useMbCoupon(scpn001);
				 */
				/*
				 * SCPN001 scpn001p= new SCPN001(); scpn001p.cp_seq=cp_seq;
				 * scpn001p.cust_id=cust_id;
				 */
				/*
				 * 
				 * #{bcn_cd, mode=IN, jdbcType=VARCHAR} ,#{cp_iss_bcd_id,
				 * mode=IN, jdbcType=VARCHAR} ,#{cp_iss_sub_seq, mode=IN,
				 * jdbcType=VARCHAR} ,#{cp_seq, mode=IN, jdbcType=VARCHAR}
				 * ,#{cust_id, mode=IN, jdbcType=VARCHAR} ,#{uuid, mode=IN,
				 * jdbcType=VARCHAR}
				 * 
				 * ,#{bcd_img_pys_loc, mode=IN, jdbcType=VARCHAR}
				 * ,#{bcd_img_uri, mode=IN, jdbcType=VARCHAR} ,#{result,
				 * mode=OUT, jdbcType=VARCHAR}
				 */
				scpn001.bcd_img_pys_loc = barcodeUrl;
				scpn001.bcd_img_uri = cndFullUrl;
				mapper.SP_SCPN002(scpn001);
				// insertDnCp(outputFile, fileFormat, barcodeDir, fileName,
				// barcodeUrl, cndFullUrl, scpn001);
			}
			// System.out.println("바코드 생성 완료>>");
			result.code = 0;
			result.desc = "바코드 생성 완료";

		} catch (Exception e) {
			// System.out.println("재수행횟수>>" + prccnt);
			ll.debug("e.getMessage() :" + e);
			// inItCreateBarcode(scpn001);
			result.code = -1;
			result.desc = "바코드 생성중 문제발생";
		}

		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public SimpleResult inItCreateBarcode(SCPN001 scpn001) {
		SimpleResult result = new SimpleResult();

		try {

			// 바코드 이미지 저장경로 생성
			String dir = scpn001.yymm;
			String sub_dir = scpn001.sub_dir;
			String imgDir = imgPath + dir + "/" + sub_dir + "/";
			String cndImgDir = cdnImgPath + "barcode/" + dir + "/" + sub_dir + "/";
			String barcodeDir = barcodePath + "barcode/" + dir + "/" + sub_dir + "/";
			new File(imgDir).mkdirs();
			// System.out.println("바코드생성 시작>>");
			// 바코드 이미지 생성
			final int dpi = 400;
			boolean isAntiAliasing = false;
			String barcodeType = "code128";
			String barcodeData = scpn001.cp_iss_bcd_id;// 바코드 번호
			String fileFormat = "jpg";
			String fileName = barcodeData;
			String outputFile = imgDir + fileName + "." + fileFormat;
			String cndFullUrl = cndImgDir + fileName + "." + fileFormat;
			String barcodeUrl = barcodeDir + fileName + "." + fileFormat;
			result = createBarcode(barcodeType, barcodeData, fileFormat, isAntiAliasing, dpi, outputFile);
			result.extra = cndFullUrl;
			// System.out.println("444444>>");
			/*
			 * if (prccnt == 4) { result.code = 0; } else { result.code = -1;
			 * Exception be = new
			 * BaseException(ErrorCode.Common.CATEGORY_NOT_FOUND_DATA); throw
			 * be; }
			 */
			// System.out.println(prccnt + "result.code>>" + result.code);
			// 쿠폰 발급 등록
			if (result.code == -1) {

				// inItCreateBarcode(scpn001);
				// }else(result.code != -1) {
			} else {
				// System.out.println("쿠폰다운정보 입력>>");

				CdnUploadBean been = new CdnUploadBean();
				been.srcPath = outputFile;
				been.ext = fileFormat;
				been.dir = barcodeDir;
				been.seq = fileName;
				been.filename = fileName + "." + fileFormat;
				fileService.uploadWithFilePath(been);

				SCPN006Vo scpn006Vo = new SCPN006Vo();
				scpn006Vo.bcn_cd = scpn001.bcn_cd;
				scpn006Vo.bcd_img_pys_loc = barcodeUrl;
				scpn006Vo.bcd_img_uri = cndFullUrl;
				scpn006Vo.reg_usr = "";
				mapper.regBcImg(scpn006Vo);

				SCPN002Vo scpn002Vo = new SCPN002Vo();
				scpn002Vo.reg_usr = "";
				scpn002Vo.cp_seq = scpn001.cp_seq;
				scpn002Vo.img_seq = scpn006Vo.bcd_img_seq;
				scpn002Vo.cp_iss_sub_seq = scpn001.cp_iss_sub_seq;
				scpn002Vo.cp_iss_bcd_id = scpn001.cp_iss_bcd_id;
				scpn002Vo.reg_usr = "";
				mapper.reqIssuCoupon(scpn002Vo);
				scpn001.cp_iss_mst_seq = scpn002Vo.cp_iss_mst_seq;

				scpn001.uuid = scpn001.uuid;
				scpn001.cust_id = scpn001.cust_id;
				scpn001.cp_seq = scpn001.cp_seq;
				mapper.useMbCoupon(scpn001);

				// insertDnCp(outputFile, fileFormat, barcodeDir, fileName,
				// barcodeUrl, cndFullUrl, scpn001);
			}
			// System.out.println("바코드 생성 완료>>");
			result.code = 0;
			result.desc = "바코드 생성 완료";

		} catch (Exception e) {
			// System.out.println("재수행횟수>>" + prccnt);
			ll.debug("e.getMessage() :" + e);
			// inItCreateBarcode(scpn001);
			result.code = -1;
			result.desc = "바코드 생성중 문제발생";
		}

		return result;
	}

	public void insertDnCp(String outputFile, String fileFormat, String barcodeDir, String fileName, String barcodeUrl,
			String cndFullUrl, SCPN001 scpn001) {
		CdnUploadBean been = new CdnUploadBean();
		been.srcPath = outputFile;
		been.ext = fileFormat;
		been.dir = barcodeDir;
		been.seq = fileName;
		been.filename = fileName + "." + fileFormat;
		try {
			// System.out.println("fileUpload>>");
			fileService.uploadWithFilePath(been);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// System.out.println("에러>>" + e.toString());
			e.printStackTrace();
		}

		SCPN006Vo scpn006Vo = new SCPN006Vo();
		scpn006Vo.bcn_cd = scpn001.bcn_cd;
		scpn006Vo.bcd_img_pys_loc = barcodeUrl;
		scpn006Vo.bcd_img_uri = cndFullUrl;
		scpn006Vo.reg_usr = "";
		mapper.regBcImg(scpn006Vo);

		SCPN002Vo scpn002Vo = new SCPN002Vo();
		scpn002Vo.reg_usr = "";
		scpn002Vo.cp_seq = scpn001.cp_seq;
		scpn002Vo.img_seq = scpn006Vo.bcd_img_seq;
		scpn002Vo.cp_iss_sub_seq = scpn001.cp_iss_sub_seq;
		scpn002Vo.cp_iss_bcd_id = scpn001.cp_iss_bcd_id;
		scpn002Vo.reg_usr = "";
		mapper.reqIssuCoupon(scpn002Vo);
		scpn001.cp_iss_mst_seq = scpn002Vo.cp_iss_mst_seq;

		scpn001.uuid = scpn001.uuid;
		scpn001.cust_id = scpn001.cust_id;
		scpn001.cp_seq = scpn001.cp_seq;
		mapper.useMbCoupon(scpn001);
		// System.out.println("insertDnCp완료>>");

	}

	/**
	 * 바코드 생성
	 * 
	 * @param barcodeType
	 * @param barcodeData
	 * @param dpi
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult createBarcode(String barcodeType, String barcodeData, String fileFormat, boolean isAntiAliasing,
			int dpi, String outputFile) {
		SimpleResult result = new SimpleResult();
		try {
			AbstractBarcodeBean bean = null;

			BarcodeClassResolver resolver = new DefaultBarcodeClassResolver();
			Class clazz = resolver.resolveBean(barcodeType);
			bean = (AbstractBarcodeBean) clazz.newInstance();
			bean.doQuietZone(true);
			OutputStream out = new FileOutputStream(new File(outputFile));

			try {
				String mimeType = MimeTypes.expandFormat(fileFormat);
				int imageType = BufferedImage.TYPE_BYTE_BINARY;
				BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, mimeType, dpi, imageType, isAntiAliasing,
						0);
				// System.out.println(bean.getModuleWidth());
				bean.setModuleWidth(0.30999999344348907);
				bean.setFontSize(UnitConv.pt2mm(6.5));
				bean.generateBarcode(canvas, barcodeData);

				canvas.finish();

			} finally {
				out.close();
			}
		} catch (Exception e) {
			result.code = -1;
			result.desc = "바코드 생성중 문제발생";

		}
		return result;
	}

	/**
	 * 테넌트 쿠폰 api 조회
	 * 
	 * @param tntSeq
	 * @return
	 */
	public List<ACPN004> getCoupons(String tntSeq) {

		List<ACPN004> cpList = mapper.getCoupons(tntSeq);

		return cpList;
	}

	@Transactional(rollbackFor = Exception.class)
	public SimpleResult cpUseProcess(SCPN002_D2Vo vo, String use_type) throws BaseException {
		SimpleResult result = new SimpleResult();
		SCPN001 scpn001p = new SCPN001();
		scpn001p.cp_seq = vo.cp_seq;
		scpn001p.cust_id = vo.cust_id;
		// 쿠폰 데이터
		SCPN001 scpn001 = mapper.getIssuCoupon(scpn001p);
		if (scpn001.use_flag.equals("N")) {
			result.code = 10000;
			result.desc = "쿠폰 유효기간을 확인해주세요";
			return result;
		}

		vo.cp_iss_mst_seq = scpn001.cp_iss_mst_seq;
		vo.cp_iss_sub_seq = scpn001.cp_iss_sub_seq;

		int cnt = 0;
		String evt_div_cd1 = "";

		if (use_type.equals("my")) {
			cnt = mapper.myCpUseProcess(vo);
			evt_div_cd1 = "F1002";
		} else {
			cnt = mapper.cpUseProcess(vo);
			evt_div_cd1 = "F1001";
		}

		// 쿠폰 사용 로그
		String log_type = "A";
		String key = "cp_seq";
		String val = vo.cp_seq;
		String uuid = "";
		sopr001service.appActionLog(log_type, evt_div_cd1, key, val, uuid, vo.cust_id);

		if (cnt > 0) {
			result.code = 0;
			result.desc = "사용처리 완료";
		}

		return result;
	}

	public ListResult<SCPN003> getBrandCpuons(SCPN001Vo vo) {
		ListResult<SCPN003> result = new ListResult<>();

		List<SCPN003> list = mapper.getBrandCpuons(vo);

		result.list = list;

		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;

		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;

		return result;
	}

	/**
	 * 일반 쿠폰 리스트 조회
	 * 
	 * @param SCPN003
	 * @return
	 */
	public ListResult<SCPN003> getNmCpuons(SCPN003 param) {

		ListResult<SCPN003> result = new ListResult<>();

		List<SCPN003> list = mapper.getNmCpuons(param);

		result.list = list;

		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;

		Paging paging = new Paging(param.offset, param.limit, totCnt);
		result.paging = paging;

		return result;
	}

	/**
	 * 보유 쿠폰 리스트 조회
	 * 
	 * @param SCPN002_D2Vo
	 * @return
	 */
	public ListResult<SCPN003> getMyCpuons(SCPN002_D2Vo vo) {
		ListResult<SCPN003> result = new ListResult<>();

		List<SCPN003> list = mapper.getMyCpuons(vo);

		result.list = list;

		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;

		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;

		return result;
	}

	/**
	 * 쿠폰 상세 조회
	 * 
	 * @param SCPN002_D2Vo
	 * @return
	 */
	public SCPN004 getCouponDetail(SCPN002_D2Vo vo) throws BaseException {
		SCPN004 cpDetail = mapper.getCouponDetail(vo);

		if (cpDetail == null) {
			BaseException be = new BaseException(ErrorCode.Coupon.COUPON_NOT_FOUND_DATA);
			throw be;
		}

		List<CouponTenant> tenantList = mapper.cpTenants(vo);
		if (tenantList != null) {
			cpDetail.tenantList = tenantList;
		}

		return cpDetail;
	}

	/**
	 * 쿠폰이 있는 테넌트
	 * 
	 * @param SCPN002_D2Vo
	 * @return
	 */
	public List<ACPN006> getTenantUseCp() {

		List<ACPN006> cpList = mapper.getTenantUseCp();

		return cpList;
	}

	/**
	 * 자동회수형 쿠폰 쿠폰 노출
	 * 
	 * @param cp_seq
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult updatePosting(String cp_seq) throws BaseException {
		SimpleResult result = new SimpleResult();
		mapper.updatePosting(cp_seq);

		// 쿠폰 게시 로그
		String log_type = "A";
		String val = cp_seq;
		String evt_div_cd1 = "A1000";

		sopr001service.appActionLogAdm(log_type, evt_div_cd1, val);

		return result;
	}

	/**
	 * 보유쿠펀 삭제
	 * 
	 * @param SCPN002_D2Vo
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult myCpDelete(SCPN002_D2Vo vo) throws BaseException {
		SimpleResult result = new SimpleResult();
		try {
			mapper.myCpDelete(vo);
			result.code = 0;
			result.desc = "쿠폰 삭제 성공";
		} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.Coupon.COUPON_DATA_DELETE_FAILED);

		}
		return result;
	}

	public ListResult<SCPN003> getCpTenants(STNT001Vo vo) {
		ListResult<SCPN003> result = new ListResult<>();

		List<SCPN003> list = mapper.getCpTenants(vo);
		result.list = list;

		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;

		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;

		return result;
	}

	/**
	 * 사용한 쿠폰 리스트
	 * 
	 * @param SCPN002_D2Vo
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public ListResult<SCPN003> usedMyCpuons(SCPN002_D2Vo vo) {
		ListResult<SCPN003> result = new ListResult<>();

		List<SCPN003> list = mapper.usedMyCpuons(vo);

		result.list = list;

		int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;

		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;

		return result;
	}

	/**
	 * 쿠폰 사용 여부
	 * 
	 * @param SCPN002_D2Vo
	 * @return
	 */
	public SimpleResult getCpUseCk(SCPN002_D2Vo vo) {
		SimpleResult result = new SimpleResult();

		SCPN002_D2Vo ckVo = mapper.getCpUseCk(vo);
		if (ckVo != null && ckVo.cp_use_sts_cd.equals("01")) {
			result.code = 1;
			result.desc = "사용";
		} else {
			result.code = 0;
			result.desc = "미사용";
		}
		return result;
	}

	/*
	 * public ListResult<ACPN006> cpTenants(SCPN001Vo vo) { ListResult<ACPN006>
	 * result = new ListResult<>();
	 * 
	 * List<ACPN006> list = mapper.cpTenants(vo);
	 * 
	 * result.list = list;
	 * 
	 * int totCnt = list.size() > 0 ? list.get(0).tot_cnt : 0;
	 * 
	 * Paging paging = new Paging(vo.offset, vo.limit, totCnt); result.paging =
	 * paging;
	 * 
	 * return result; }
	 */

}
