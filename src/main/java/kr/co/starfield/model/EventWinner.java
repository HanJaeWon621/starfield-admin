package kr.co.starfield.model;

import kr.co.starfield.model.vo.BaseVo;

/**
 *  EventWinner model
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.08.30
 */

public class EventWinner extends BaseModel{
	public String no;
	public String evt_seq;
	public String pick_div;
	public String pick_div_cnt;
	public String pick_count;
	public String win_item;
	public String mbr_nm;
	public String mbr_cel_num1;
	public String mbr_cel_num2;
	public String mbr_cel_num3;
	public String cust_id;
	public String aply_dttm;
	public String mbr_status;
	public String mbr_mod_dttm;
	
	@Override
	public BaseVo toVo() {
		throw new UnsupportedOperationException();
	}
}