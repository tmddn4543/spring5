package com.nautestech.www.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nautestech.www.mapper.CallMapper;
import com.nautestech.www.model.Call;
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
	
}
