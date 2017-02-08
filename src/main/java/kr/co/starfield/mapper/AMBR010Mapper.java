package kr.co.starfield.mapper;

import java.util.List;

import kr.co.starfield.model.Member;
import kr.co.starfield.model.SMBR001;
import kr.co.starfield.model.vo.ImgFileVo;
import kr.co.starfield.model.vo.SMBR001Vo;
import kr.co.starfield.model.vo.SMBR003Vo;
import kr.co.starfield.model.vo.SMBR005Vo;

/**
 *   interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author ldh
 * @version	1.0,
 * @see
 * @date 2016.04.14
 */

public interface AMBR010Mapper {

	public SMBR001 getMemberCardNm(String uuid);

	public void regPointBcImg(ImgFileVo vo);

	public void updateMemberPointBc(SMBR001 smbr001);

	public String getFileSeq();

	public void regImgFile(ImgFileVo vo);

}
