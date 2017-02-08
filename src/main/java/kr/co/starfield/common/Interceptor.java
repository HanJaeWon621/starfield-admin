/*
 * Interceptor.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.common;

import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.starfield.annotation.AuthRequired;
import kr.co.starfield.model.MapFile;
import kr.co.starfield.model.SADM003;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.service.IndoorMapService;

/**
 *  Interceptor(인증)
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author jg.jo
 * @version	1.0,
 * @see
 * @date 2016.10.24
 */

public class Interceptor extends HandlerInterceptorAdapter   {
	
	private static final Logger ll = LoggerFactory.getLogger(Interceptor.class);
	
	@Value("${server.isLocal}")
	private boolean isLocal;
	@Autowired
	IndoorMapService indoorMapService;
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		HttpSession session = req.getSession();
		String reqUri = req.getRequestURI();
		
		
		// ip 대역 확인
		String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
        	ip = req.getRemoteAddr();
        }
		
        ll.debug("req uri is {}", reqUri);
        
        /**
         * 1. uri중 앞이 다음과 같이 요청이 올때는 무조건 패스 시킨다.
         * 대표적으로  /rest, /sync/redis
         */
        String[] passPrefixUris = {
        	  "/rest"
        	, "/sync/redis"
        };
        
        for(String prefixUri : passPrefixUris) {
        	if(reqUri.indexOf(prefixUri) == 0) {
        		return super.preHandle(req, res, handlerMethod);
        	}
        }
        
        /**
         * 2.ip대역대를 확인하고, 해당 대역대가 아니라면 스타필드 공식 홈페이지로 연결한다.
         */
        boolean isPassIp = this.checkPassIp(ip);
        if(!isPassIp) {
        	
        	if(req.getHeader("AJAX") != null) {
				
				res.setStatus(ErrorCode.Auth.ACCESS_IP_PERMISSION_DENIED.getCode());
				return false;
        		
        	} else {
        	
        		String redirectUrl = "https://www.starfield.co.kr/";
            	res.sendRedirect(redirectUrl);
            	return false;
        	}
        }
        
        	
        /**
         * 3. 관리자 내부에서 session이 없어도 그냥 통과시킬 uri를 체크한다.
         * 대표적으로 login, logout, /access/web/auth/fail등 공통접근이 있다.
         * 해당 pass uri는 정확히 일치해야 한다.
         */
		String[] passUris = {
				  "/check"
				, "/logout"
			    , "/login"
				, "/access/web/auth/fail"
				, "/admin/rest/auth/login"
		};
		
		for(String passUri : passUris) {
			if(reqUri.equals(passUri)) {
				return super.preHandle(req, res, handlerMethod);
			}
		}
		
		
		/**
		 * 4. 나머지 접근은 무조건 session이 존재 해야 한다.
		 */
		
		
		String adm_seq = (String) session.getAttribute("adm_seq");
		Object sts = session.getAttribute("sts");
		String bcn_cd = (String) session.getAttribute("bcn_cd");
		bcn_cd = bcn_cd == null ? "01" : bcn_cd;
		
		if((adm_seq == null || sts == null)) {
			
			if(req.getHeader("AJAX") != null) {
				
				res.setStatus(ErrorCode.Auth.AUTH_FAIL.getCode());
				return false;
				
			} else {
				
				res.sendRedirect("/logout");
				return false;
			}
		
		} else if ((Integer) sts != 101) {
			
			/**
			 * session은 존재하지만, 세션이 없어도 통과해야 하는 uri정보가 있다면,
			 * 패스 해준다. 아니라면, 개인정보 수정 페이지로 이동한다.
			 */
			String[] stsPassUrls = {
					  "login"
					, "self"
			};
			
			boolean isStsPassUri = false;
			for(String passUrl : stsPassUrls) {
				if(reqUri.indexOf(passUrl) != -1) {
					isStsPassUri = true;
					break;
				}
			}
			
			if(isStsPassUri) {
				
				return super.preHandle(req, res, handlerMethod);
				
			} else if(req.getHeader("AJAX") != null) {
				
				res.setStatus(ErrorCode.Auth.AUTH_FAIL.getCode());
				return false;
				
			} else {
				
				String redirectUrl = Paths.get("/", bcn_cd, "/account/self").toString(); 
				
				res.sendRedirect(redirectUrl);
				return false;
			}
		}
		
		
		/**
		 * 5. 현재 호출된 핸들러 메서드에 해당 어노테이션이 존재하는지 체크
		 */
		ll.info("reqUri is {}", reqUri);
		AuthRequired authReq = handlerMethod.getMethodAnnotation(AuthRequired.class);
		
		boolean isAuthPass = false;
		
		if(authReq != null) {
			
			SADM003[] auths = (SADM003[]) session.getAttribute("authTypeArr");
			
			for(String reqAuth : authReq.authArr()) {
				ll.info("---- require auth is {}", reqAuth);
			}
			
			for(SADM003 auth : auths) {
				ll.info("---- my auth is {}", auth.auth_nm);
			}
			
			for(SADM003 auth : auths) {
				
				for(String toAuth : authReq.authArr()) {
					
					if(toAuth.equals(auth.auth_nm)) {
						isAuthPass = true;
						break;
					}
				}
			}
			
		} else {
			isAuthPass = true;
		}
		
		if(isAuthPass) {
			
			return true;
			
		} else if(req.getHeader("AJAX") != null) {
			
			res.setStatus(ErrorCode.Auth.AUTH_FAIL.getCode());
			return false;
			
		} else {
			
			String redirectUrl = "/access/web/auth/fail";
			
			res.sendRedirect(redirectUrl);
			return false;
		}
		
		
	}
	
	/**
	 * 통과할 ip 대역대(classB)까지 체
	 * @param ip
	 * @return
	 */
	private boolean checkPassIp(String ip) {
		
		ll.debug("request ip address is {} ", ip);
		ll.debug("is local access is {}", isLocal);
		
		boolean isPassIp = false;
		if(!isLocal) {
		
			ll.debug("request ip address is {}", ip);
			
			String[] passIpsPetternClassB = {
				    "10.100"
				   ,"10.139"
				   ,"10.140"
				   ,"10.149"
			       ,"10.174"
			       ,"118.143"
			       ,"174.100"
			       ,"174.200"
			       ,"10.162"
			};
		
			// ### ip 대역대 확인 패스 대역대라면 
			String[] classArr = ip.split("\\.");
			ip = classArr[0].concat(".").concat(classArr[1]);
			   
			ll.info("client ip classB is {} ", ip);
			   
			for(String passIp : passIpsPetternClassB) {
				if(passIp.equals(ip)) {
					isPassIp = true;
					continue;
				}
			}
			
		} else {
			isPassIp = true;
		}
		
		return isPassIp;
	}
}
