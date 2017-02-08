package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.model.Category;
import kr.co.starfield.model.CategoryForList;

public class SCTG001Vo extends BaseVo{
	
	public int no;
	public int level;
	public String cate_seq;
	public String cate_cd;
	public String bcn_cd;
	public String mama_cate_seq;
	public String cate_nm_ko;
	public String cate_nm_en;
	public String cate_nm_cn;
	public String cate_nm_jp;
	public Integer sort_ord;
	public Integer sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	public int tnt_cnt;
	public int child_cnt;
	public boolean isleaf;
	
	public <T> Category<T> toModel(){
		Category<T> category = new Category<>();
		
		category.cate_seq = this.cate_seq;
		category.cate_cd = this.cate_cd;
		category.mama_cate_seq = this.mama_cate_seq;
		category.cate_nm_ko = this.cate_nm_ko;
		category.cate_nm_en = this.cate_nm_en;
		category.cate_nm_cn = this.cate_nm_cn;
		category.cate_nm_jp = this.cate_nm_jp;
		category.sort_ord = this.sort_ord;
		
		return category; 
	}
	
	public <T> CategoryForList toListModel(){
		CategoryForList category = new CategoryForList();
		
		category.no = this.no;
		category.cate_seq = this.cate_seq;
		category.cate_cd = this.cate_cd;
		category.mama_cate_seq = this.mama_cate_seq;
		category.cate_nm_ko = this.cate_nm_ko;
		category.cate_nm_en = this.cate_nm_en;
		category.cate_nm_cn = this.cate_nm_cn;
		category.cate_nm_jp = this.cate_nm_jp;
		category.sort_ord = this.sort_ord;
		category.tnt_cnt = this.tnt_cnt;
		category.child_cnt = this.child_cnt;
		category.sts = this.sts;
		
		return category; 
	}

	@Override
	public String toString() {
		return "CategoryVo [cate_seq=" + cate_seq + ", cate_cd=" + cate_cd + ", bcn_cd=" + bcn_cd + ", mama_cate_seq="
				+ mama_cate_seq + ", cate_nm_ko=" + cate_nm_ko + ", cate_nm_en=" + cate_nm_en + ", cate_nm_cn="
				+ cate_nm_cn + ", cate_nm_jp=" + cate_nm_jp + ", sts=" + sts + ", reg_dttm=" + reg_dttm + ", mod_dttm="
				+ mod_dttm + ", reg_usr=" + reg_usr + ", mod_usr=" + mod_usr + "]";
	}
	
	

}
