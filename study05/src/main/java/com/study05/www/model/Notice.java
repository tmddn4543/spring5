package com.study05.www.model;

import lombok.Data;

@Data
public class Notice {
	public String seq;
	public String title;
	public String contents;
	public String onoff;
	public String regdate;
	public String mod_regdate;
	public String written;
}
