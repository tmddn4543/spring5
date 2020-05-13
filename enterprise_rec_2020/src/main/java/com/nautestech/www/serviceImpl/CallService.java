package com.nautestech.www.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nautestech.www.mapper.CallMapper;
import com.nautestech.www.model.Call;
import com.nautestech.www.model.CallJoin;
import com.nautestech.www.service.ISelect;

@Service
public class CallService implements ISelect<Call>{
	
	@Autowired
	CallMapper mapper;

	@Override
	public List<Call> getView(HashMap<String, Object> param) {
		return mapper.getView(param);
	}

	@Override
	public List<Call> getViewYYYYMM(HashMap<String, Object> param) {
		return mapper.getViewYYYYMM(param);
	}

	@Override
	public void setCreate(HashMap<String, Object> param) {
		mapper.setCreate(param);
	}

	@Override
	public int getListCount(HashMap<String, Object> param) {
		return mapper.getListCount(param);
	}

	@Override
	public List<?> getListBranch(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> getListStateCount(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getListCountYYYYMM(HashMap<String, Object> param) {
		return mapper.getListCountYYYYMM(param);
	}

	@Override
	public List<CallJoin> setSelect(HashMap<String, Object> param) {
		return mapper.setSelect(param);
	}
	
}
