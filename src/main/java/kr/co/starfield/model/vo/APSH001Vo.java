package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.CommonCode.Push.SendType;
import kr.co.starfield.common.CommonCode.Push.SendYn;

public class APSH001Vo extends BaseVo {
	public String bcn_cd;
	public String push_mng_seq;
	public SendType send_type;
	public Date reserve_dttm;
	public String send_dttm;
	public SendYn send_yn;
	public String push_msg;
	public Integer sts;
	
	public String reg_dttm;
	public String reg_usr;
	public String mod_dttm;
	public String mod_usr;
}
