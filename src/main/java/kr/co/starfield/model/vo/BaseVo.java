/*
 * BaseVo.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model.vo;

import kr.co.starfield.model.BaseFilter;

/**
 *  테이블에서 공통으로 맵핑되는 클래스
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0,
 * @see
 * @date 2016.04.11
 */

public class BaseVo implements BaseFilter {
	public int offset;
	public int limit;
	public int tot_cnt;
	public int all_tot_cnt;
	public String lang;
}
