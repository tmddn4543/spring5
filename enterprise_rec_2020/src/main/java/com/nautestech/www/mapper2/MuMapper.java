package com.nautestech.www.mapper2;

import java.util.HashMap;
import java.util.List;


import com.nautestech.www.model.HddInfo;

public interface MuMapper {
	public void setInsertMrecordUser(HashMap<String, Object>param);
	public void setDeleteMrecordUser(HashMap<String, Object>param);
	public List<HddInfo> getHddInfo();
}
