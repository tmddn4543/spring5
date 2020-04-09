package com.nautestech.www.model;

import lombok.Data;

@Data
public class Stat {
	
	public int num;
	
	public String s_date;
	public int s_caller_cnt; //er발신
	public int s_called_cnt;
	public int s_caller_time;
	public int s_called_time;

	public String emp_id;
	public String emp_nm;
	public String branch_cd;
	public String tel_no;
	
	
	
	public int s_call_cnt_total;
	public int s_call_time_total;
}
