/*
 * Emp.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.starfield.common.Utils.Dt;
import kr.co.starfield.model.vo.ACPN001Vo;
import kr.co.starfield.model.vo.SCPN001_DVo;

/**
 *  Emp model
 *
 * Copyright Copyright (c) 2016
 * Company Paprika
 *
 * @author xxx
 * @version	1.0
 * @see
 * @date 2016.04.11
 */

public class TodoMng {
	public String no;//순번
	public int tot_cnt;
	public int offset;
	public int limit;
	public int all_tot_cnt;
	public String lang;
	
	public String to_do_seq;
    public String sys_cd;
    public String work_cls_cd;
    public String work_cd;
    public String work_nm;
    public String work_strt_dt;
    public String work_end_dt;
    public String work_dtl;
    public String work_issue;
    public String priority;
    public String difficulty;
    public String rel_pg_seq;
    public String expl;
	public String sh_work_nm;
	public String sh_to_do_seq;
}
