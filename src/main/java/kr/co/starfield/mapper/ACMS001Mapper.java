package kr.co.starfield.mapper;

import java.util.ArrayList;

import kr.co.starfield.model.Blog;
import kr.co.starfield.model.BlogExcel;
import kr.co.starfield.model.BlogFilter;
import kr.co.starfield.model.BlogWeb;
import kr.co.starfield.model.CommonDeleteModel;
import kr.co.starfield.model.Theme;
import kr.co.starfield.model.ThemeExcel;
import kr.co.starfield.model.ThemeFilter;
import kr.co.starfield.model.ThemeWeb;

/**
 *  blog, theme
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.06.20
 */

public interface ACMS001Mapper {
	
	// Blog 입력
	public void regBlog(Blog blog);
	
	// Blog 목록 조회
	public ArrayList<Blog> getBlogList(BlogFilter filter);
	
	// Blog count
	public int getTotalCount(BlogFilter filter);
	
	// Blog 상세
	public Blog getBlog(BlogFilter filter);
	
	// Blog 수정
	public void modifyBlog(Blog blog);
	
	// Blog 삭제
	public void deleteBlog(CommonDeleteModel commonDeleteModel);
	
	//excel
	public ArrayList<BlogExcel> getBlogExcelList(BlogFilter vo);
	
	//redis용 블로그
	public ArrayList<BlogWeb> getBlogWebList(BlogFilter vo);
	
	// theme 입력
	public void regTheme(Theme theme);
	
	// theme 목록
	public ArrayList<Theme> getThemeList(ThemeFilter filter);

	// theme count
	public int getThemeTotalCount(ThemeFilter filter);
	
	// theme 상세
	public Theme getTheme(ThemeFilter filter);
	
	// theme 수정
	public void modifyTheme(Theme filter);
	
	// theme 삭제
	public void deleteTheme(CommonDeleteModel commonDeleteModel);

	//theme excel
	public ArrayList<ThemeExcel> getThemeExcelList(ThemeFilter filter);
	
	//redis theme
	public ArrayList<ThemeWeb> getThemeWebList(ThemeFilter filter);
	
}
