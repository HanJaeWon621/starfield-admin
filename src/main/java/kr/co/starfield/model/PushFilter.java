package kr.co.starfield.model;

/**
 *  Push Filter model
 *
 * @author yhkim
 * @version	1.0
 * @see
 * @date 2016.10.10
 */

public class PushFilter implements BaseFilter{
	public String bcn_cd;
	
	public String push_mng_seq;
	
	public String push_strt_dt;
	public String push_end_dt;
	
	public String send_type;
	public String send_yn;
	
	public String push_msg;

	public int offset;
	public int limit;
	public boolean search_option_yn;
	public String search_option_selector;
	
	public String order_key;
	public String order_type;
}