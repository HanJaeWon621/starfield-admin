package kr.co.starfield.model;

/**
 *  Facility model
 *
 * @author yhkim
 * @version	1.0
 * @see
 * @date 2016.10.04
 */

public class FacilityFilter implements BaseFilter {
	
	public String conv_faci_seq;
	public String conv_faci_nm_ko;
	public int offset;
	public int limit;
	
	public String bcn_cd;
	
	public String order_key;
	public String order_type;
}
