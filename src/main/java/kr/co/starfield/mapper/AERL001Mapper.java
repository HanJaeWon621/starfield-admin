package kr.co.starfield.mapper;

import java.util.List;

import kr.co.starfield.model.vo.AERL001VO;

public interface AERL001Mapper {
	// error log 목록 조회
		public List<AERL001VO> getErrorLogList(AERL001VO vo);
}
