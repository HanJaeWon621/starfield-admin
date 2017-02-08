/*
 * AuthenticationRequired.java		1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  특정 컨트롤에서 메소드 및 클래스 단위로 로그인체크 여부를 확인 하기위한 어노테이션
 *
 * Copyright	Copyright (c) 2016
 * Company	Paprika
 *
 * @author		xxx
 * @version	1.0,
 * @see
 * @date		2016.04.11
 */

/*
	로그인이 필요한 페이지와 필요없는 페이지에 대한 기준이 모호하여
	해당 메소드에 어노테이션을 추가하여 로그인을 강제하는 방법이 효율적일것 같음

	@Target
		CONSTRUCTOR : 생성자
		FIELD : enum 상수를 포함한 필드
		LOCAL_VARIABLE : 지역 변수
		METHOD : 메소드
		PACKAGE : 패키지
		PARAMETER : 파라미터
		TYPE : Class, Interface, Enumeration

	@Retention(어노테이션이 얼마나 오랫동안 유지되는거에 대해, JVM이 어떻게 사용자 어노테이션을 다루어야 하는지를 서술)
		SOURCE(어노테이션이 컴파일 타임시 버려진다는 것을 의미, 클래스파일은 어노테이션을 갖지 못함)
		CLASS(어노테이션이 생성된 클래스 파일에서 나타날 것이라는 의미, 런타임시에는 이 어노테이션을 이용하지 못함)
		RUNTIME(소스, 클래스, 실행시에 사용됨)

	@Documented
		어노테이션을 JavaDoc에 포함함
*/

//클래스 및 메서드에 어노테이션 적용
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRequired {

	//어노테이션에 인수를 활용하고 싶을 경우 메서드 정의
	//boolean admin() default false;
	
//	String admin() default "admin";
//	
//	String sub_admin1() default "sub_admin1";
//
//	String sub_admin2() default "sub_admin2";
//	
//	String sub_admin3() default "sub_admin3";
	
	
	String[] roleArr() default "";	// 역할 배열
	String role() default "";		// 필요 시 또 다른 값을 지장하여 사용할 수 있음
	
	String[] authArr() default "";
	String auth() default "";
	
	
}

