package com.nautestech.www.service;

import java.util.HashMap;
import java.util.List;


public interface ISelect<T1> {
	public List<?> getView(HashMap<String, Object>param);
	public List<?> getViewYYYYMM(HashMap<String, Object> param);
	public void setCreate(HashMap<String, Object>param);
	public int getListCount(HashMap<String, Object>param);
	public List<?> getListBranch(HashMap<String, Object>param);
	public List<?>getListStateCount(HashMap<String, Object>param);
}
