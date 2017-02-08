package kr.co.starfield.mapper;

import java.util.List;

import kr.co.starfield.model.APPLOG;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.SAML002;
import kr.co.starfield.model.SAML007;
import kr.co.starfield.model.SMBR001;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.vo.SAML002Vo;
import kr.co.starfield.model.vo.SMBR001Vo;

public interface APLI004Mapper {

	public List<SAML007> getLocationReqViewList(SAML007 saml007);

	public List<SMBR001> getRequester(SMBR001Vo vo);

	public void regLocationReqView(SAML007 saml007);

	public List<SAML002> getLocationReqViewExcel(String mem_seq);

	public void regLocationDnYn(SAML007 saml007);

	public List<APPLOG> getAppLogList(APPLOG applog);

	public List<SAML002> getLocationUseList(SAML002Vo vo);


}
