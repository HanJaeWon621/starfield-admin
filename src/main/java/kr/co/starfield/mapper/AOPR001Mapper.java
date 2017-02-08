/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import kr.co.starfield.model.Operation;

/**
 *  AOPR001(Operation)Mapper interface
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author hhLee
 * @version	1.0,
 * @see
 * @date 2016.04.14
 */

public interface AOPR001Mapper {

	// 운영정보 조회
	public Operation getOperation(Operation operation);

	// 운영정보 수정
	public void modifyOperation(Operation operation);
	
}
