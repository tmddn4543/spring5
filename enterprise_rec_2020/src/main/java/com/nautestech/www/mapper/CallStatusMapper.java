package com.nautestech.www.mapper;

import java.util.HashMap;
import java.util.List;

import com.nautestech.www.model.CallStatus;

public interface CallStatusMapper {
	public List<CallStatus> setSelect(HashMap<String, Object>param);
	public void setDelete(HashMap<String, Object>param);
	public void setDelete2(HashMap<String, Object>param);
	public void setUpdate (HashMap<String, Object>param);
}
