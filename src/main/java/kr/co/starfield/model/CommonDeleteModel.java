package kr.co.starfield.model;

import kr.co.starfield.model.vo.BaseVo;

/**
 *  Event model
 *
 * @author yhkim
 * @version	1.0
 * @see
 * @date 2016.08.08
 */

public class CommonDeleteModel extends BaseModel {
	
	public String bcn_cd;
	public String[] seq_arr;
	public String mod_usr;

	@Override
	public BaseVo toVo() {
		throw new UnsupportedOperationException();
	}
}
