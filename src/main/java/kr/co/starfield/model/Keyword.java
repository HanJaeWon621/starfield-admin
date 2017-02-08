package kr.co.starfield.model;

import kr.co.starfield.model.vo.SKWD003Vo;

public class Keyword extends BaseModel{
	public String keyword_seq;
	public String bcn_cd;
	public String keyword;
	
	@Override
	public SKWD003Vo toVo() {
		SKWD003Vo vo = new SKWD003Vo();
		vo.keyword = this.keyword;
		vo.bcn_cd = this.bcn_cd;
		return vo;
	}
	
	@Override
	public String toString() {
		return "Keyword [keyword_seq=" + keyword_seq + ", bcn_cd=" + bcn_cd + ", keyword=" + keyword + "]";
	}
}
