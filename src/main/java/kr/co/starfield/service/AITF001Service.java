package kr.co.starfield.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.mapper.AITF001Mapper;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.MegaBoxP200;
import kr.co.starfield.model.MegaBoxP201;
import kr.co.starfield.model.MegaBoxP202;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.vo.AITF001Vo;
import kr.co.starfield.model.vo.AITF002Vo;
import kr.co.starfield.model.vo.AITF003Vo;


/**
 * AITF001(megaBox-interface) 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author hhLee
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

@Service
public class AITF001Service {

	@Autowired
	private AITF001Mapper aitf001Mapper;
	
	private static final Logger ll = LoggerFactory.getLogger(AITF001Service.class);
	
	/**
	 * P200 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<MegaBoxP200> getP200List(AITF001Vo vo) throws BaseException {
		
		ListResult<MegaBoxP200> result = new ListResult<MegaBoxP200>();

		List<AITF001Vo> P200List = aitf001Mapper.getP200List(vo);
		
		for(AITF001Vo P200 : P200List){
			result.list.add(P200.toModel());
		}
		
		if(vo.limit != -1) {
			
			int tot_cnt = P200List.size() > 0 ? P200List.get(0).tot_cnt : 0;  
			
			Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
				
			result.paging = paging;
		
		}
	
		return result;
	}

	/**
	 * P201 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<MegaBoxP201> getP201List(AITF002Vo vo) throws BaseException {
		
		ListResult<MegaBoxP201> result = new ListResult<MegaBoxP201>();
			
		List<AITF002Vo> P201List = aitf001Mapper.getP201List(vo);
			
		for(AITF002Vo P201 : P201List){
			result.list.add(P201.toModel());
		}
		
		if(vo.limit != -1) {
			
			int tot_cnt = P201List.size() > 0 ? P201List.get(0).tot_cnt : 0;
			
			Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
				
			result.paging = paging;
		}
		
		return result;
	}
	
	/**
	 * P202 목록 조회
	 * @param vo, q
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<MegaBoxP202> getP202List(AITF003Vo vo) throws BaseException {
		
		ListResult<MegaBoxP202> result = new ListResult<MegaBoxP202>();
			
		List<AITF003Vo> P202List = aitf001Mapper.getP202List(vo);
			
		for(AITF003Vo P202 : P202List){
			result.list.add(P202.toModel());
		}
			
		if(vo.limit != -1) {
		
			int tot_cnt = P202List.size() > 0 ? P202List.get(0).tot_cnt : 0;
			
			Paging paging = new Paging(vo.offset, vo.limit, tot_cnt);
				
			result.paging = paging;
		}
	
		return result;
	}
}
