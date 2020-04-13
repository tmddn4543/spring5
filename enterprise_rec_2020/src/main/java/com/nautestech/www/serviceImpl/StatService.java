package com.nautestech.www.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nautestech.www.mapper.StatMapper;
import com.nautestech.www.model.Stat;
import com.nautestech.www.service.ISelect;

@Service
public class StatService implements ISelect<Stat>{
	
	@Autowired
	StatMapper mapper;

	@Override
	public List<Stat> getView(HashMap<String, Object> param) {
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
	public List<Stat> getListStateCount(HashMap<String, Object> param) {
		return mapper.getListStateCount(param);
	}

	@Override
	public int getListCountYYYYMM(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		return 0;
	}

}
