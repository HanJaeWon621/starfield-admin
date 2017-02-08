package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.AppVer;
import kr.co.starfield.model.AppVerFilter;
import kr.co.starfield.model.CommonDeleteModel;

public interface AMAV001Mapper {
	// appVer 등록
	public void regAppVer(AppVer appVer);
	
	// appVer 리스트
	public ArrayList<AppVer> getAppVerList(AppVerFilter filter);
	
	// appVer 리스트 카운트
	public int getTotalCount(AppVerFilter filter);
	
	// appVer 수정
	public void modifyAppVer(AppVer appVer);
	
	// appVer 삭제
	public void deleteAppVer(CommonDeleteModel commonDeleteModel);
	
	// appVer iOS
	public String getiOSVer(String bcn_cd);
	
	// appVer android
	public String getAndroidVer(String bcn_cd);
	
}
