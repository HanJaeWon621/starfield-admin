package kr.co.starfield.model;

import kr.co.starfield.model.vo.BaseVo;
import kr.co.starfield.model.vo.SEVT003_DVo;

/**
 *  EventAply model
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.08.30
 */

public class EventApply extends BaseModel{
	public String no;
	public String cust_id;
	public String aply_dttm;
	public String mbr_nm;
	public String mbr_cel_num1;
	public String mbr_cel_num2;
	public String mbr_cel_num3;
	public String mbr_seq;
	public int mbr_sts;
	public String mbr_status;
	public String mbr_mod_dttm;
	
	@Override
	public BaseVo toVo() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString() {
		return "EventApply [no=" + no + ", cust_id=" + cust_id + ", aply_dttm=" + aply_dttm + ", mbr_nm=" + mbr_nm
				+ ", mbr_cel_num1=" + mbr_cel_num1 + ", mbr_cel_num2=" + mbr_cel_num2 + ", mbr_cel_num3=" + mbr_cel_num3
				+ ", mbr_sts=" + mbr_sts + ", mbr_status=" + mbr_status + ", mbr_mod_dttm=" + mbr_mod_dttm + "]";
	}
}
