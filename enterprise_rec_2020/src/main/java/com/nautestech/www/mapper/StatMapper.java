package com.nautestech.www.mapper;

import java.util.HashMap;
import java.util.List;

import com.nautestech.www.model.Stat;

public interface StatMapper {
	public List<Stat> getView(HashMap<String, Object>param);
	public List<Stat>getListStateCount(HashMap<String, Object>param);
}
