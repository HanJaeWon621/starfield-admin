package kr.co.starfield.mapper;

import kr.co.starfield.model.vo.SADM002Vo;
import kr.co.starfield.model.vo.SADM003Vo;

public interface AATH001Mapper {

	SADM003Vo[] getAdminAuthTypes(String adm_seq);

	SADM002Vo[] getAdminRoles(String adm_seq);

}
