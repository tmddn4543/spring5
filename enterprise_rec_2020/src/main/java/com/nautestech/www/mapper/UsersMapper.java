package com.nautestech.www.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nautestech.www.model.Batch;
import com.nautestech.www.model.Users;

@Mapper
public interface UsersMapper {
	public List<Users> getView(HashMap<String, Object> param);
	public void setInsert(HashMap<String, Object> param);
	public int getListCount(HashMap<String, Object>param);
	public List<Batch> getListBranch(HashMap<String, Object>param);
	public void setInsertBranch(HashMap<String, Object>param);
	public void setBranchDelete(HashMap<String, Object>param);
	public void setDelete(HashMap<String, Object>param);
	public void setUpdate(HashMap<String, Object>param);
	public void setInsertListen_log(HashMap<String, Object>param);
}
