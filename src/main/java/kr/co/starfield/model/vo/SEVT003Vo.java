package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.Blog;
import kr.co.starfield.model.Event;
import kr.co.starfield.model.EventEntry;

/**
 *  AEVT003(응모) 테이블과 맵핑되는 클래스
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.07.22
 */

public class SEVT003Vo extends BaseVo {
	public String evt_seq;
	public String mbr_seq;
	public Date aply_dttm;
	public String won_yn;
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public EventEntry toModel() {
		EventEntry model = new EventEntry();
		model.evt_seq = this.evt_seq;
		model.mbr_seq = this.mbr_seq;
		model.aply_dttm = this.aply_dttm;
		model.won_yn = this.won_yn;
		model.sts = this.sts;
		
		return model;
	}

}
