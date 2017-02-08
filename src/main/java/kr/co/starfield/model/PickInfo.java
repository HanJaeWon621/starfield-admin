package kr.co.starfield.model;

import java.util.List;
import java.util.Map;

import kr.co.starfield.model.vo.BaseVo;

public class PickInfo extends BaseModel {
	public String evt_seq;
	public String pick_div;
	public int pick_div_cnt;
	public int pick_count;
	public List<Map<String,String>> mult_pick_info;
	
	@Override
	public String toString() {
		return "PickInfo [pick_div=" + pick_div + ", pick_div_cnt=" + pick_div_cnt + ", pick_count=" + pick_count
				+ ", mult_pick_info=" + mult_pick_info + "]";
	}

	@Override
	public BaseVo toVo() {
		throw new UnsupportedOperationException();
	}
}
