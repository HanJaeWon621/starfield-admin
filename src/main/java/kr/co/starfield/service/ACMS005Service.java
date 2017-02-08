package kr.co.starfield.service;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.mapper.ACMS005Mapper;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Notice;
import kr.co.starfield.model.NoticeFilter;
import kr.co.starfield.model.NoticeWeb;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;


/**
 * Notice 서비스 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.10.05
 */

@Service
public class ACMS005Service {

	@Autowired
	private ACMS005Mapper amcs005Mapper;

	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	private static final Logger ll = LoggerFactory.getLogger(ACMS005Service.class);
	
	/**
	 * 공지사항 등록
	 * @param vo
	 * @return 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regNotice(Notice notice) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		amcs005Mapper.regNotice(notice);
		
		result.extra = notice.noti_seq;
		
		syncNotice(notice.bcn_cd);
		
		return result;
	}
	
	/**
	 * 공지사항 목록 조회
	 * @param filter
	 * @return 
	 * @throws BaseException 
	 */
	public ListResult<Notice> getNoticeList(NoticeFilter filter) throws BaseException {
		
		ListResult<Notice> result = new ListResult<Notice>();
			
		result.list.addAll(amcs005Mapper.getNoticeList(filter));
			
		if(filter.limit > 0){
			int tot_cnt = amcs005Mapper.getTotalCount(filter);
	
			Paging paging = new Paging(filter.offset, filter.limit, tot_cnt);
				
			result.paging = paging;
		}		
		return result;
	}
	
	/**
	 * 공지사항 상세
	 * @param filter
	 * @return
	 * @throws BaseException 
	 */
	public Notice getNotice(NoticeFilter filter) throws BaseException {
		
		Notice notice = amcs005Mapper.getNotice(filter);
		
		if(notice == null){
			BaseException be = new BaseException(ErrorCode.Board.NOTICE_NOT_FOUND_DATA);
			throw be;
		} 
		
		return notice;
	}
	
	/**
	 * 공지사항 수정
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyNotice(Notice notice) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		amcs005Mapper.modifyNotice(notice);
		
		syncNotice(notice.bcn_cd);
		
		return result;
	}
	
	/**
	 * 공지사항 삭제 
	 * @param vo
	 * @return
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteNotice(CommonDeleteModel commonDeleteModel) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		
		amcs005Mapper.deleteNotice(commonDeleteModel);
		
		syncNotice(commonDeleteModel.bcn_cd);
		
		return result;
	}
	
	/**
	 * NoticeWeb
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional
	public List<NoticeWeb> getNoticeWeb(String bcn_cd) throws BaseException {
		
		NoticeFilter filter = new NoticeFilter();
		
		filter.bcn_cd = bcn_cd;
		
		List<NoticeWeb> noticeList = amcs005Mapper.getNoticeWebList(filter);
		
		return noticeList;
	}
	
	/**
	 * Notice redis sync
	 * @param
	 * @return 
	 * @throws BaseException 
	 */
	@Transactional
	public SimpleResult syncNotice(String bcn_cd) throws BaseException {

		SimpleResult result = new SimpleResult();
		
		ZSetOperations<String, String> zOps = stringRedisTemplate.opsForZSet();
		ValueOperations<String, String> vOps = stringRedisTemplate.opsForValue();
		
		List<NoticeWeb> noticeList = getNoticeWeb(bcn_cd);
		
		try {
			
			stringRedisTemplate.delete(zOps.range("keys:notice", 0, -1));
			stringRedisTemplate.delete("keys:notice");
			
			int count = 0;
			
			for(NoticeWeb notice : noticeList){
				vOps.set("notice:" + notice.noti_seq, new ObjectMapper().writeValueAsString(notice));
				zOps.add("keys:notice", "notice:" + notice.noti_seq, count);
				
				count++;
			}
		} catch (Exception e) {
			BaseException be = new BaseException(ErrorCode.Board.NOTICE_SYNC_FAIL_REDIS);
			throw be;
		}
		
		return result;
	}
}
