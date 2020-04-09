package com.nautestech.www.mapper;

import java.util.HashMap;
import java.util.List;

import com.nautestech.www.model.Callhistory_log;

public interface LogMapper {
	public List<Callhistory_log> getView(HashMap<String, Object>param);
	public int getListCount(HashMap<String, Object>param);
}
