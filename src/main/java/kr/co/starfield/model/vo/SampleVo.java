package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.model.Sample;

public class SampleVo extends BaseVo {
	
	public int num_col;
	public String varchar_col;
	public Date date_col;
	
	public Sample toModel() {
		
		Sample sample = new Sample();
		
		sample.num_col      = this.num_col     ;
		sample.varchar_col  = this.varchar_col ;
		sample.date_col     = this.date_col    ;
		
		return sample;
	}

}
