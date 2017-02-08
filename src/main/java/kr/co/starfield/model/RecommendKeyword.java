package kr.co.starfield.model;

import kr.co.starfield.model.vo.SKWD001Vo;

public class RecommendKeyword extends BaseModel {
	public String recm_keyword_seq;
	public String bcn_cd;
	public String keyword;
	public Integer sort_ord;
	public Integer sts;
	public String reg_dttm;
	public String mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	@Override
	public SKWD001Vo toVo() {
		SKWD001Vo recommendKeywordVo = new SKWD001Vo();
		recommendKeywordVo.recm_keyword_seq = this.recm_keyword_seq;
		recommendKeywordVo.bcn_cd = this.bcn_cd;
		recommendKeywordVo.keyword = this.keyword;
		recommendKeywordVo.sort_ord = this.sort_ord;
		recommendKeywordVo.sts = this.sts;
		recommendKeywordVo.sts = this.sts;
		recommendKeywordVo.reg_usr = this.reg_usr;
		recommendKeywordVo.mod_usr = this.mod_usr;
		
		return recommendKeywordVo;
	}

}
