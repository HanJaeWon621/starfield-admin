package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.BannerGroup;
import kr.co.starfield.model.QNA;

/**
 *  SCMS010Vo(Banner Group) 테이블과 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.09.07
 */

public class SCMS010Vo extends BaseVo {
	public String bn_group_seq; 
	public String bcn_cd;
	public int bn_div;
	public String bn_group_titl;
	public Date bn_post_strt;
	public Date bn_post_end;

	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public BannerGroup toModel() {
	
		return null;
	}

}
