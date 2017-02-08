package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.vo.ImgFileVo;

public interface FileMapper {

	public void regImgFile(ImgFileVo vo);
	
	public ArrayList<ImgFileVo> getImgFileList(ImgFileVo vo);

	public String getImgSeq();

}
