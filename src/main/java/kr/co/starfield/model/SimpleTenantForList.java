package kr.co.starfield.model;

import kr.co.starfield.common.CommonCode.Tenant.OperationStatus;
import kr.co.starfield.common.CommonCode.Tenant.TenantType;
import kr.co.starfield.model.vo.BaseVo;

public class SimpleTenantForList extends BaseModel {
	
	public int no;
	public String tnt_seq;
	public String cate_path;
	public String img_logo_uri;
	public String tnt_nm_ko;
	public TenantType tnt_type;
	public String tnt_tag;
	public String room_num;
	public int evt_cnt;
	public int cp_cnt;
	public OperationStatus opr_sts;
	public String open_yn;
	
	@Override
	public BaseVo toVo() {
		throw new UnsupportedOperationException();
	}
	
}
