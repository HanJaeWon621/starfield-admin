package kr.co.starfield.model;

import java.util.Date;

/**
 *  Event model
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.07.15
 */

public class EventFilter implements BaseFilter{
	public String evt_seq;
	public String tnt_seq;
	public String mbr_seq;
	public String bcn_cd;
	public String evt_type;
	public String evt_dvi;
	public Date evt_dt;
	
	public Date evt_strt_dt;
	public Date evt_end_dt;
	
	public String evt_titl;
	
	public int sts;
	public int offset;
	public int limit;
	public boolean search_option_yn;
	public String search_option_selector;
	
	public String order_key;
	public String order_type;
	
	@Override
	public String toString() {
		return "EventFilter [evt_seq=" + evt_seq + ", tnt_seq=" + tnt_seq + ", mbr_seq=" + mbr_seq + ", bcn_cd="
				+ bcn_cd + ", evt_type=" + evt_type + ", evt_dt=" + evt_dt + ", evt_strt_dt=" + evt_strt_dt
				+ ", evt_end_dt=" + evt_end_dt + ", sts=" + sts + ", offset=" + offset + ", limit=" + limit
				+ ", search_option_yn=" + search_option_yn + ", order_key=" + order_key + ", order_type=" + order_type
				+ "]";
	}
}