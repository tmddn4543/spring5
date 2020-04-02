package com.nautestech.www.model;

import lombok.Data;

@Data
public class Call {
	public int c_id;
	public String btime;
	public String etime;
	public String caller;
	public String called;
	public String callid;
	public String dirname;
	public String filesize;
	public String fname;
	public String emp_id;
	public String branch_cd;
	public String emp_nm;
	public String system_id;
	public String is_statistics;
	public String auth_cd;
	public String rbtime;
	public String retime;
	public String duple_flag;
	public String rec_type;
	public String group_id;
	
	public int num;
	public String call_date;
	public String call_hour;
	public String call_time;
}
