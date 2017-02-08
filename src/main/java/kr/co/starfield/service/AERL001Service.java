package kr.co.starfield.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.mapper.AERL001Mapper;
import kr.co.starfield.model.AERL001;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.vo.AERL001VO;

@Service
public class AERL001Service {
	
	private Logger ll = LoggerFactory.getLogger(AERL001Service.class);
	
	@Autowired
	AERL001Mapper aerl001Mapper;
	
	public ListResult<AERL001> getErrorLogList(AERL001VO vo) throws BaseException {
		ListResult<AERL001> result = new ListResult<AERL001>();
		
		List<AERL001VO> errorLogList = aerl001Mapper.getErrorLogList(vo);
		
		for(AERL001VO error : errorLogList){
			result.list.add(error.toModel());
		}
		
		int tot_cnt = errorLogList.size() > 0 ? errorLogList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
			
		result.paging = paging;
		
		return result;
		
	}

}
