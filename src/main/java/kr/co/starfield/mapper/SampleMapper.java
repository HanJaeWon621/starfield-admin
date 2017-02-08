package kr.co.starfield.mapper;

import kr.co.starfield.model.vo.SampleVo;

public interface SampleMapper {

	public SampleVo[] getSamples();
	
	public void regSample(SampleVo vo);
	
	public void delAllSample();
	
	public String getOraTime();
	
}
