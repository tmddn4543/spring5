package com.nautestech.www.mapper;

import java.util.HashMap;
import java.util.List;

import com.nautestech.www.model.Call;

public interface CallMapper {
	public List<Call> getView(HashMap<String, Object> param);
	public List<Call> getViewYYYYMM(HashMap<String, Object> param);
}
