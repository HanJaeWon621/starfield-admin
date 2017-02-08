package kr.co.starfield.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.mapper.AMBR001Mapper;
import kr.co.starfield.mapper.AMBR010Mapper;
import kr.co.starfield.mapper.FileMapper;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Member;
import kr.co.starfield.model.MemberDevice;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SCPN001;
import kr.co.starfield.model.SMBR001;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.ImgFileVo;
import kr.co.starfield.model.vo.SCPN002Vo;
import kr.co.starfield.model.vo.SCPN006Vo;
import kr.co.starfield.model.vo.SMBR001Vo;
import kr.co.starfield.model.vo.SMBR003Vo;
import kr.co.starfield.model.vo.SMBR005Vo;

@Service
public class AMBR010Service {
	
	private Logger ll = LoggerFactory.getLogger(AMBR010Service.class);
	
	@Autowired
	AMBR010Mapper ambr010Mapper;
	@Autowired
	FileMapper fileMapper;

	@Autowired
	ACPN002Service acpn002service;
	
	/*@Value("${file.img.host}")
	String imgHost;
	@Value("${file.barcodeimg.path}")
	String imgPath;
	@Value("${cdn.barcodeimg.path}")
	String cndImgPath;
	
	TODO : 이미지 업로드 방식 변경으로 인해 소스 수정 필요
	
	*/
	

	String imgHost;
	String imgPath;
	String cndImgPath;
	
	public SimpleResult memberPointBc(String cardNum) {
		SimpleResult result = new SimpleResult();
		
		//Member member = ambr010Mapper.getMemberSeq(cardNum);
		
		SMBR001 member  = ambr010Mapper.getMemberCardNm(cardNum);
		if(!StringUtils.isEmpty(member)) {
			if(!StringUtils.isEmpty(member.point_card_bcd)) {
				result.code = 0;
				result.extra =  member.point_card_bcd_uri;
			}else{
				//SMBR001 smbr001 = new SMBR001();
				//smbr001.stf_point_card_num = cardNum;
				result = inItCreateBarcode(member);
			}
		}else{
			result.code = -1;
			result.desc = "일치하는 카드번호가 없습니다";
		}
		
		
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult inItCreateBarcode(SMBR001 smbr001) {
		SimpleResult result = new SimpleResult();
		
		try {
			//바코드 이미지 저장경로 생성
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
			Calendar cal = Calendar.getInstance(); 
			
			String yymm = dateFormat.format(cal.getTime()).substring(0,4); 
 
			String dir             = yymm;
			String imgDir          = imgPath + "point/"+smbr001.fnum+"/";
			String cndImgDir	   = cndImgPath + "point/"+smbr001.fnum+"/";
			new File(imgDir).mkdirs();    	
			
			//파일 seq
			String fileSeq = ambr010Mapper.getFileSeq();
			 
			//바코드 이미지 생성
			final int dpi          = 203;
			boolean isAntiAliasing = false;
			String barcodeType     = "code128";
			String barcodeData     = smbr001.stf_point_card_num;//바코드 번호
			String fileFormat      = "jpg";
			String fileName        = fileSeq;
			String outputFile      = imgDir + fileName+"."+fileFormat;
			String cndFullUrl      = cndImgDir + fileName+"."+fileFormat;
	    	result = acpn002service.createBarcode(barcodeType, barcodeData, fileFormat, isAntiAliasing, dpi, outputFile);
	    	result.extra =  cndFullUrl.replaceAll("\\\\", "/");
	    	
	    	if(result.code != -1){
	    		ImgFileVo vo = new ImgFileVo();
	    		vo.bcn_cd = "01";
	    		vo.img_pys_loc = imgDir;
	    		vo.img_uri = cndImgDir;
	    		vo.img_thmb = imgDir;
	    		vo.img_thmb_uri = cndImgDir;
	    		vo.img_seq = fileSeq;
	    		ambr010Mapper.regImgFile(vo);
	    		
	    		smbr001.point_card_bcd = vo.img_seq;
	    		ambr010Mapper.updateMemberPointBc(smbr001);	    		
	    	}
	    	
	    	result.code = 0;
			result.desc = "바코드 생성 완료";
		} catch (Exception e) {
        	result.code = -1;
			result.desc = "바코드 생성중 문제발생";
        } 
    	
    	return result;
	}
}
