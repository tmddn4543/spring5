package com.study04.www.model;

import lombok.Data;

@Data
public class Board {
	public String seq;
	public String title;
	public String contents;
	public String regdate;
	public String written;
	public String mod_regdate;
}
