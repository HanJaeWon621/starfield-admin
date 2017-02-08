package kr.co.starfield.model;

/**
 *  Event model
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.11.21
 */

public class SEVT004Vo {
	
	// Param
	public String bcn_cd;  // 지점코드
	public String evt_seq; // 이벤트 순번
	public String uuid;    // 디바이스 ID
	public String cust_id; // 고객 ID
	public String mbr_seq; // 회원 순번
	
	
	@Override
	public String toString() {
		return "SEVT004Vo [ bcn_cd : " + bcn_cd
					   + ", evt_seq : " + evt_seq
					   + ", uuid : " + uuid
					   + ", cust_id : " + cust_id
					   + ", mbr_seq : " + mbr_seq + " ]";
	}

}