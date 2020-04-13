package com.nautestech.www.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nautestech.www.mapper.LogMapper;
import com.nautestech.www.model.Callhistory_log;
import com.nautestech.www.service.ISelect;

@Service
public class LogService implements ISelect<Callhistory_log>{
	
	@Autowired
	LogMapper mapper;

	@Override
	public List<Callhistory_log> getView(HashMap<String, Object> param) {
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
	public List<?> getListBranch(HashMap<String, Object> param) {
		return null;
	}

	@Override
	public List<?> getListStateCount(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getListCountYYYYMM(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
