package kr.co.starfield.model;

/**
 *  LocInfoManage model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author eezy
 * @version	1.0
 * @see
 * @date 2016.10.10
 */

public class LocInfoManageExcel {
	public String bcn_cd;
	public String name;
	public String phone_num;
	public String req_del_dttm;
	public String act_dttm;
	public String act_usr;
	public String act_yn;
	public int sts; //0:접수 1:확인중 8:삭제불가 9:삭제완료
	public String status;
}
