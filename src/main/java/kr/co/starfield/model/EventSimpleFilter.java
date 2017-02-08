package kr.co.starfield.model;

/**
 *  EventSimpleFilter model
 *
 * @author yhkim
 * @version	1.0
 * @see
 * @date 2016.09.10
 */

public class EventSimpleFilter implements BaseFilter {
	public String evt_seq;
	public String bcnCd; // 지점코드
	public int offset;
	public int limit;
	
	public String order_key;
	public String order_type;
	
	public int mbr_sts;
}