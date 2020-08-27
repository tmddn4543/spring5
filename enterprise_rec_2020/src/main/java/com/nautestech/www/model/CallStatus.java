package com.nautestech.www.model;

import lombok.Data;

@Data
public class CallStatus {
	public String uid;
	public String caller;
	public String called;
	public String callid;
	public String serialkey;
	public String stime;
	public String sltime;
	public String system_id;
	public int c_flag;
	public int p_flag;
	public String cause;
	public int u_cnt;
	public String u_expire;
	public String delete_date;
}


