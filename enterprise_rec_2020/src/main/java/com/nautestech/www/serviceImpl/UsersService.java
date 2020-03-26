package com.nautestech.www.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nautestech.www.mapper.UsersMapper;
import com.nautestech.www.model.Users;
import com.nautestech.www.service.IInsert;
import com.nautestech.www.service.ISelect;

@Service
public class UsersService implements IInsert,ISelect<Users>{

	
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
}
