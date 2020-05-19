package com.nautestech.www.mapper;

import java.util.HashMap;
import java.util.List;

import com.nautestech.www.model.Monitor;

public interface MonitorMapper {
	public List<Monitor> getView(HashMap<String, Object>param);
}
