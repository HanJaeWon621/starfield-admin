package kr.co.starfield.model;

import kr.co.starfield.model.vo.BaseVo;

/**
 *  Event model
 *
 * @author yhkim
 * @version	1.0
 * @see
 * @date 2016.09.26
 */

public class TenantDelete extends BaseModel {
	
	public String bcn_cd;
	public String[] tnt_seq_arr;
	public String mod_usr;

	@Override
	public BaseVo toVo() {
		throw new UnsupportedOperationException();
	}
}
