package kr.co.starfield.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.starfield.mapper.AATH001Mapper;
import kr.co.starfield.model.SADM002;
import kr.co.starfield.model.SADM003;
import kr.co.starfield.model.vo.SADM002Vo;
import kr.co.starfield.model.vo.SADM003Vo;

@Service
public class AATH001Service {

	@Autowired
	private AATH001Mapper aath001Mapper;
	
	public SADM003[] getAdminAuthTypes(String adm_seq) {
		
		SADM003Vo[] authTypeVoArr = null;
		
		authTypeVoArr = aath001Mapper.getAdminAuthTypes(adm_seq);
		
		SADM003[] authTypeArr = new SADM003[authTypeVoArr.length];
		
		for(int i = 0; i < authTypeArr.length; i++) {
			
			authTypeArr[i] = authTypeVoArr[i].toModel();
		}
		
		return authTypeArr;
	}
	
	
	/**
	 * 관리자 role(역할) 리스트 정보를 가져온다.
	 * @return
	 */
	public SADM002[] getAdminRoles(HttpSession session) {
		
		String adm_seq = (String) session.getAttribute("adm_seq");
		
		SADM002Vo[] voRoles = aath001Mapper.getAdminRoles(adm_seq);
		
		SADM002[] roles = new SADM002[voRoles.length];
		
		for(int i = 0; i < voRoles.length; i++) {
			
			roles[i] = voRoles[i].toModel();
		}
		
		return roles;
	}
}
