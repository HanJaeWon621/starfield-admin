package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  AppVerListResult model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0,
 * @see
 * @date 2016.10.12
 */

public class AppVerListResult<T> {
	public String iOS_ver;
	public String Android_ver;
	public Paging paging;
	public List<T> list = new ArrayList<>();
}
