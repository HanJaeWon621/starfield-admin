package kr.co.starfield.model.vo;

import java.util.Date;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.CommonCode;
import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.Event;

/**
 *  AEVT003(event 응모 결과) 테이블과 맵핑되는 클래스
 *
 * @author hhlee
 * @version	1.0,
 * @see
 * @date 2016.08.29
 */

public class SEVT003_DVo extends BaseVo {
	public String evt_seq;
	public int won_idx;
	public String mbr_seq;
	public String pick_div;
	public int pick_div_cnt;
	public int pick_count;
	public String win_item;
	public int sts;
	public Date reg_dttm;
	public Date mod_dttm;
	public String reg_usr;
	public String mod_usr;
	
	public void toModel() throws BaseException {

	}

	@Override
	public String toString() {
		return "SEVT003_DVo [evt_seq=" + evt_seq + ", won_idx=" + won_idx + ", mbr_seq=" + mbr_seq + ", pick_div="
				+ pick_div + ", pick_div_cnt=" + pick_div_cnt + ", pick_count=" + pick_count + ", win_item=" + win_item
				+ ", sts=" + sts + ", reg_dttm=" + reg_dttm + ", mod_dttm=" + mod_dttm + ", reg_usr=" + reg_usr
				+ ", mod_usr=" + mod_usr + "]";
	}

}
