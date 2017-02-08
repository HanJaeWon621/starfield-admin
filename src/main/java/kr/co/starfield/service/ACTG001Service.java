package kr.co.starfield.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.BaseException;
import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.mapper.ACTG001Mapper;
import kr.co.starfield.model.Category;
import kr.co.starfield.model.CategoryForList;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.TenantCategoryForAdmin;
import kr.co.starfield.model.vo.SCTG001Vo;

@Service
public class ACTG001Service {
	
	private Logger ll = LoggerFactory.getLogger(ACTG001Service.class);
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	ACTG001Mapper actg001Mapper;
	
	@Autowired
	ATNT001Service atnt001Service;

	/**
	 * 카테고리 조회 
	 * @param cateSeq
	 * @return Category
	 * @throws BaseException 
	 */
	public <T> Category<T> getCategory(SCTG001Vo vo) throws BaseException {
		
		SCTG001Vo categoryVo = actg001Mapper.getCategory(vo);
		
		if(categoryVo == null){
			BaseException be = new BaseException(ErrorCode.Common.CATEGORY_NOT_FOUND_DATA);
			throw be;
		} 
		return categoryVo.toModel();
		
	}

	/**
	 * 카테고리 수정
	 * @param vo
	 * @return 
	 */
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyCategory(SCTG001Vo vo, String rootCateCd) throws BaseException {
		SimpleResult result = new SimpleResult();

		actg001Mapper.modifyCategory(vo);
		syncRedisCategories(rootCateCd);
		
		return result;	
	}

	/**
	 * 카테고리 목록 
	 * @param vo
	 * @return 
	 * @throws BaseException 
	 */
	public <T> ListResult<CategoryForList> getCategories(SCTG001Vo vo) throws BaseException {
		
		ListResult<CategoryForList> result = new ListResult<>();
			
		List<SCTG001Vo> categories = actg001Mapper.getCategories(vo);
			
		for(SCTG001Vo category : categories) {
			CategoryForList model = category.toListModel();
			result.list.add(model);
		}
			
		int totCnt = categories.size() > 0 ? categories.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, totCnt);

		result.paging = paging;
	
		return result;
	}
	
	/**
	 * 레디스 업로드
	 * @throws BaseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public void syncRedisCategories(String rootCateCd) throws BaseException{
		ll.info("** syncRedisCategories start");
		SCTG001Vo vo = new SCTG001Vo();
		vo.cate_cd = rootCateCd;
		
		try {

			String categoryJson = new ObjectMapper().writeValueAsString(makeHierachyCategories(vo, rootCateCd));
						
			stringRedisTemplate.opsForValue().set("cate:"+rootCateCd, categoryJson);
		} catch (IOException e) {
			BaseException be = new BaseException(ErrorCode.Common.CATEGORY_TO_JSON_FOR_SET_REDIS_ERROR);
			throw be;
		}
		ll.info("** syncRedisCategories end");
	}

	/**
	 * 하위 카테고리 전체 목록
	 */	
	
	public List<SCTG001Vo> getHierachyCategories(SCTG001Vo vo){
		return actg001Mapper.getHierachyCategories(vo);
	}
	
	/**
	 * 레디스 업로드용 카테고리 계층형 목록
	 */
	public <T> List<Category<T>> makeHierachyCategories(SCTG001Vo vo, String rootCateCd) {

		List<SCTG001Vo> categories = getHierachyCategories(vo);
		
		List<Category<T>> root = new ArrayList<>();
		
		for(SCTG001Vo category : categories) {
			if(category.level == 2){
				
				Category<T> model = category.toModel();
				
				Category<T> resultModel = this.<T>getChildCategories(model, categories, rootCateCd);
				
				root.add(resultModel);
			}
		}
		
		return root;
	}
	
	private <T> Category<T> getChildCategories(Category<T> parents, List<SCTG001Vo> categories, String rootCateCd) {
		
		for(SCTG001Vo category : categories) {
			if(parents.cate_seq.equals(category.mama_cate_seq)){
				
				Category<T> model = category.toModel();
				if(category.isleaf){
					
					//TODO - 일단은 하드코딩
					List<T> dataList;
					if(rootCateCd.equals("TENANT")){
						dataList = (List<T>) actg001Mapper.getTenantByCategorySeq(category);
					} else {
						dataList =  new ArrayList<>();
					}
					
					model.data_list.addAll(dataList);
					parents.child_cate_list.add(model);
				} else {
					parents.child_cate_list.add(getChildCategories(model, categories, rootCateCd));
				}
			}
		}
		
		return parents;
	}

	@Transactional(rollbackFor = Exception.class)
	public SimpleResult saveCategory(TenantCategoryForAdmin category) throws BaseException {
		SimpleResult result = new SimpleResult();
		
		if(category.first_categories != null) {
			for(CategoryForList cate : category.first_categories) {
				cate.bcn_cd = category.bcn_cd;
				cate.user = category.user;
				if(cate.modify_sts == 0) {
					actg001Mapper.modifyCategory(cate.toVo());
				} else if (cate.modify_sts == 1) {
					actg001Mapper.regCategory(cate.toVo());
				}
			}
		}
		
		if(category.second_categories != null) {
			for(CategoryForList cate : category.second_categories) {
				cate.bcn_cd = category.bcn_cd;
				cate.user = category.user;
				if(cate.modify_sts == 0) {
					actg001Mapper.modifyCategory(cate.toVo());
				} else if (cate.modify_sts == 1) {
					actg001Mapper.regCategory(cate.toVo());
				}
			}
		}
		
		if(category.del_seq_arr != null && category.del_seq_arr.length > 0) {
			CommonDeleteModel cateDelete = new CommonDeleteModel();
			cateDelete.bcn_cd = category.bcn_cd;
			cateDelete.seq_arr = category.del_seq_arr;
			cateDelete.mod_usr = category.user;
			actg001Mapper.deleteCategory(cateDelete);
		}
		
		atnt001Service.syncRedisTenantSuite();
		
		return result;
	}
	
//	/**
//	 * 상위 카테고리 전체 목록
//	 */	
//	public List<String> getSimpleHierachyCategories(List<String> cateSeqList) {
//		return actg001Mapper.getSimpleHierachyCategories(cateSeqList);
//	}
}
