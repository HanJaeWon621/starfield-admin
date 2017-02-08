package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.RecommendKeyword;

public class SKWD001Vo extends BaseVo {
	public String recm_keyword_seq;
	public String bcn_cd;
	public String keyword;
	public Integer sort_ord;
	public Integer sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public RecommendKeyword toModel(){
		RecommendKeyword recommendKeyword = new RecommendKeyword();
		recommendKeyword.recm_keyword_seq = this.recm_keyword_seq;
		recommendKeyword.bcn_cd = this.bcn_cd;
		recommendKeyword.keyword = this.keyword;
		recommendKeyword.sort_ord = this.sort_ord;
		recommendKeyword.sts = this.sts;
		recommendKeyword.sts = this.sts;
		recommendKeyword.reg_dttm = Dt.toStringDateTime(this.reg_dttm);
		recommendKeyword.mod_dttm = Dt.toStringDateTime(this.mod_dttm);
		recommendKeyword.reg_usr = this.reg_usr;
		recommendKeyword.mod_usr = this.mod_usr;
		
		return recommendKeyword;
	}
}
