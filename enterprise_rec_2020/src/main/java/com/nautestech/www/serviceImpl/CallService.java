package com.nautestech.www.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nautestech.www.mapper.CallMapper;
import com.nautestech.www.model.Call;
import com.nautestech.www.service.Select;

@Service
public class CallService implements Select<Call>{
	
	@Autowired
	CallMapper mapper;

	@Override
	public List<Call> getView(HashMap<String, Object> param) {
		return mapper.getView(param);
	}
	
}
