package kr.co.starfield.model;

import java.util.Arrays;
import java.util.List;

public class TenantCategoryForAdmin {
	
	public String bcn_cd;
	public List<CategoryForList> first_categories;
	public List<CategoryForList> second_categories;
	public String[] del_seq_arr;
	public String user;
	
	@Override
	public String toString() {
		return "TenantCategoryForAdmin [first_categories=" + first_categories + ", second_categories="
				+ second_categories + ", del_seq_arr=" + Arrays.toString(del_seq_arr) + "]";
	}
}
