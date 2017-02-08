package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.List;

/**
 * SEVT004(stamp_event) List VO 클래스
 *
 * @author 
 * @version	1.0,
 * @see
 * @date 2016.11.21
 */

public class SEVT004ListResult<T> {
	
	public String code;
	public String message;
	public String giftCount; // STAMP 교환 건수
	public String exchange; // 교환여부
	public List<T> list = new ArrayList<>();
	
	@Override
	public String toString() {
		return "SEVT004ListResult [ CODE : " + code
							   + ", MESSAGE : " + message
							   + ", giftCount : " + giftCount
							   + ", LIST " + list + " ]";
	}
	
}
