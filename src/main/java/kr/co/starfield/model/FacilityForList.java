package kr.co.starfield.model;

import kr.co.starfield.common.CommonCode.Facility.FacilityType;
import kr.co.starfield.model.vo.BaseVo;


/**
 *  Facility model
 *
 * @author yhkim
 * @version	1.0
 * @see
 * @date 2016.10.04
 */

public class FacilityForList extends BaseModel {
	
	public int no;
	public String conv_faci_seq;
	public FacilityType faci_type;
	public String conv_faci_nm_ko;
	public String img_seq_icon_uri;
	public int conv_faci_cnt;
	public int sort_ord;
	public int sts;
	
	@Override
	public BaseVo toVo() {
		throw new UnsupportedOperationException();
	}

}
