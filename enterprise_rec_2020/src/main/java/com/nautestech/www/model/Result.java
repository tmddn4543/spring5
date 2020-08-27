package com.nautestech.www.model;

import lombok.Data;

@Data
public class Result {
	private boolean boolResult;
	private String msg;
	private Object obj;
	private Session session;
}
