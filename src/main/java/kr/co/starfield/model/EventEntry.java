package kr.co.starfield.model;

import java.util.Date;

import kr.co.starfield.model.vo.SEVT003Vo;


/**
 *  EventEntry model
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.07.22
 */

public class EventEntry extends BaseModel {
	public String evt_seq;
	public String mbr_seq;
	public Date aply_dttm;
	public String won_yn;
	public int sts;
	
	@Override
	public SEVT003Vo toVo() {
		SEVT003Vo vo = new SEVT003Vo();
		
		vo.evt_seq = this.evt_seq;
		vo.mbr_seq = this.mbr_seq;
		vo.aply_dttm = this.aply_dttm;
		vo.won_yn = this .won_yn;
		vo.sts = this.sts;
		
		return vo;
	}
	
}
