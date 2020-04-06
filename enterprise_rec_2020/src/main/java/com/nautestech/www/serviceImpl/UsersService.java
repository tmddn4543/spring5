package com.nautestech.www.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nautestech.www.mapper.UsersMapper;
import com.nautestech.www.model.Batch;
import com.nautestech.www.model.Call;
import com.nautestech.www.model.Users;
import com.nautestech.www.service.IDelete;
import com.nautestech.www.service.IInsert;
import com.nautestech.www.service.ISelect;

@Service
public class UsersService implements IInsert,ISelect<Users>,IDelete{

	
	@Autowired
	UsersMapper mapper;
	
	@Override
	public void setInsert(HashMap<String, Object> param) {
		mapper.setInsert(param);
	}

	@Override
	public List<Users> getView(HashMap<String, Object> param) {
		return mapper.getView(param);
	}

	@Override
	public List<?> getViewYYYYMM(HashMap<String, Object> param) {
		return null;
	}

	@Override
	public void setCreate(HashMap<String, Object> param) {
	}

	@Override
	public int getListCount(HashMap<String, Object> param) {
		return mapper.getListCount(param);
	}

	@Override
	public List<Batch> getListBranch(HashMap<String, Object> param) {
		return mapper.getListBranch(param);
	}

	@Override
	public void setInsertBranch(HashMap<String, Object> param) {
		mapper.setInsertBranch(param);
	}

	@Override
	public void setDelete(HashMap<String, Object> param) {
		mapper.setDelete(param);
	}

	@Override
	public void setBranchDelete(HashMap<String, Object> param) {
		mapper.setBranchDelete(param);
	}
}
