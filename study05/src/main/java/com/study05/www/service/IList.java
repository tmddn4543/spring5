package com.study05.www.service;

import java.util.HashMap;
import java.util.List;

public interface IList<T> {
	public List<?> getList(HashMap<String, Object> param);
	public int getListCount(HashMap<String, Object> param);
}
