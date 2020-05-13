package com.nautestech.www.mapper;

import java.util.HashMap;
import java.util.List;

import com.nautestech.www.model.Call;
import com.nautestech.www.model.CallJoin;

public interface CallMapper {
	public List<Call> getView(HashMap<String, Object> param);
	public List<Call> getViewYYYYMM(HashMap<String, Object> param);
	public void setCreate(HashMap<String, Object>param);
	public List<CallJoin> setSelect(HashMap<String, Object>param);
	public int getListCount(HashMap<String, Object>param);
	public int getListCountYYYYMM(HashMap<String, Object>param);
}
