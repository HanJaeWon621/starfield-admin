package kr.co.starfield.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import kr.co.starfield.common.Utils;
import kr.co.starfield.common.CommonCode.Push.SendType;
import kr.co.starfield.common.CommonCode.Push.SendYn;
import kr.co.starfield.model.vo.APSH001Vo;
import kr.co.starfield.model.vo.BaseVo;

public class Push extends BaseModel{

	public int no;
	public String bcn_cd;
	public String push_mng_seq;
	public SendType send_type;
	public String send_dttm;
	public SendYn send_yn;
	public String push_msg;
	public String reg_dttm;
	public String reserve_dt;
	public String reserve_hour;
	public String reserve_minute;
	public Integer sts;

	public APSH001Vo toPushVo() throws ParseException {
		APSH001Vo vo = new APSH001Vo();
		vo.push_mng_seq = this.push_mng_seq;
		vo.bcn_cd = this.bcn_cd;
		vo.send_type = this.send_type;
		vo.send_dttm = this.send_dttm;
		vo.reserve_dttm = !Utils.Str.isEmpty(this.reserve_dt) ? new SimpleDateFormat("yyyy.MM.dd HH:mm").parse(this.reserve_dt +" "+ this.reserve_hour + ":" + this.reserve_minute) : null;
		vo.send_yn = this.send_yn;
		vo.push_msg = this.push_msg;
		vo.sts = this.sts;
		vo.reg_usr = this.user;
		vo.mod_usr = this.user;
		
		return vo;
	}

	@Override
	public BaseVo toVo() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "Push [no=" + no + ", bcn_cd=" + bcn_cd + ", push_mng_seq=" + push_mng_seq + ", send_type=" + send_type
				+ ", send_dttm=" + send_dttm + ", send_yn=" + send_yn + ", push_msg=" + push_msg + ", reg_dttm="
				+ reg_dttm + ", reserve_dt=" + reserve_dt + ", reserve_hour=" + reserve_hour + ", reserve_minute="
				+ reserve_minute + ", sts=" + sts + "]";
	}

}
