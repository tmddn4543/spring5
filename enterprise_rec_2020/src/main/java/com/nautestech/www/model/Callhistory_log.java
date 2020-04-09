package com.nautestech.www.model;

import lombok.Data;

@Data
public class Callhistory_log {
	public int num;
	public int log_id;
	public String user_id;
	public String filename;
	public String dirname;
	public String logtime;
	public String operation;
}
