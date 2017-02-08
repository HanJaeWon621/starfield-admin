package kr.co.starfield.model;

import kr.co.starfield.model.vo.BaseVo;
import kr.co.starfield.model.vo.SCTG001Vo;

public class CategoryForList extends BaseModel {

	public int no;
	public String bcn_cd;
	public String cate_seq;
	public String cate_cd;
	public String mama_cate_seq;
	public String cate_nm_ko;
	public String cate_nm_en;
	public String cate_nm_cn;
	public String cate_nm_jp;
	public Integer sort_ord;
	public Integer sts;
	public int tnt_cnt;
	public int child_cnt;
	public int modify_sts; // 0 수정, 1 등록, 9 삭제
	
	
	
	@Override
	public String toString() {
		return "CategoryForList [no=" + no + ", cate_seq=" + cate_seq + ", cate_cd=" + cate_cd + ", mama_cate_seq="
				+ mama_cate_seq + ", cate_nm_ko=" + cate_nm_ko + ", cate_nm_en=" + cate_nm_en + ", cate_nm_cn="
				+ cate_nm_cn + ", cate_nm_jp=" + cate_nm_jp + ", sort_ord=" + sort_ord + ", sts=" + sts
				+ ", modify_sts=" + modify_sts + "]";
	}

	@Override
	public SCTG001Vo toVo() {
		SCTG001Vo vo = new SCTG001Vo();
		vo.bcn_cd = this.bcn_cd;
		vo.cate_seq = this.cate_seq;
		vo.cate_cd = this.cate_cd;
		vo.mama_cate_seq = this.mama_cate_seq;
		vo.cate_nm_ko = this.cate_nm_ko;
		vo.cate_nm_en = this.cate_nm_en;
		vo.cate_nm_cn = this.cate_nm_cn;
		vo.cate_nm_jp = this.cate_nm_jp;
		vo.sort_ord = this.sort_ord;
		vo.sts = this.sts;
		vo.reg_usr = this.user;
		vo.mod_usr = this.user;
		return vo;
	}
}
