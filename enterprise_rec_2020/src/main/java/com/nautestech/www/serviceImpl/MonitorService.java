package com.nautestech.www.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nautestech.www.mapper.MonitorMapper;
import com.nautestech.www.model.Monitor;
import com.nautestech.www.service.ISelect;

@Service
public class MonitorService implements ISelect<Monitor>{
	
	@Autowired
	MonitorMapper mapper;
	

	@Override
	public List<Monitor> getView(HashMap<String, Object> param) {
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
		return 0;
	}

	@Override
	public List<?> getListBranch(HashMap<String, Object> param) {
		return null;
	}

	@Override
	public List<?> setSelect(HashMap<String, Object> param) {
		return null;
	}

	@Override
	public List<?> getListStateCount(HashMap<String, Object> param) {
		return null;
	}

	@Override
	public int getListCountYYYYMM(HashMap<String, Object> param) {
		return 0;
	}

	@Override
	public List<?> getHddInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
