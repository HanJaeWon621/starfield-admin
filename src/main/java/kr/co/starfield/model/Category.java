package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.List;

public class Category<T> {

	public String cate_seq;
	public String cate_cd;
	public String mama_cate_seq;
	public String cate_nm_ko;
	public String cate_nm_en;
	public String cate_nm_cn;
	public String cate_nm_jp;
	public Integer sort_ord;
	public List<Category<T>> child_cate_list = new ArrayList<>();
	public List<T> data_list = new ArrayList<>();

	@Override
	public String toString() {
		return "Category [cate_seq=" + cate_seq + ", cate_cd=" + cate_cd + ", mama_cate_seq=" + mama_cate_seq
				+ ", cate_nm_ko=" + cate_nm_ko + ", cate_nm_en=" + cate_nm_en + ", cate_nm_cn=" + cate_nm_cn
				+ ", cate_nm_jp=" + cate_nm_jp + ", child_cate_list=" + child_cate_list + "]";
	}
}
