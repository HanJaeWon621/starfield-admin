package kr.co.starfield.model;

import kr.co.starfield.model.vo.STNT001Vo;
import kr.co.starfield.model.vo.STNT002Vo;

public class TenantCategory {
	
//	public String tnt_seq;
	public String cate_seq;
	public String cate_cd;
	public String cate_nm_ko;
	public String cate_nm_en;
	public String cate_nm_cn;
	public String cate_nm_jp;
	public int lvl;	// level
	public int max_level;
	public int sort_ord;
	public int tnt_cnt;
	
	public STNT002Vo toVo(STNT001Vo tenantVo) {

		STNT002Vo vo = new STNT002Vo();
		vo.bcn_cd = tenantVo.bcn_cd;
		vo.tnt_seq = tenantVo.tnt_seq;
		vo.cate_seq = this.cate_seq;
		vo.reg_usr = tenantVo.reg_usr;
		vo.mod_usr = tenantVo.mod_usr;

		return vo;
	}
	
}
