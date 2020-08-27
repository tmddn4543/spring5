package com.nautestech.www.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nautestech.www.mapper.CallStatusMapper;
import com.nautestech.www.model.CallStatus;
import com.nautestech.www.service.IDelete;
import com.nautestech.www.service.ISelect;
import com.nautestech.www.service.IUpdate;

@Service
public class CallStatusService implements ISelect<CallStatus>, IDelete,IUpdate{
	
	@Autowired
	CallStatusMapper mapper;

	@Override
	public List<?> getView(HashMap<String, Object> param) {
		return null;
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
	public List<?> getListStateCount(HashMap<String, Object> param) {
		return null;
	}

	@Override
	public int getListCountYYYYMM(HashMap<String, Object> param) {
		return 0;
	}

	@Override
	public List<CallStatus> setSelect(HashMap<String, Object> param) {
		return mapper.setSelect(param);
	}

	@Override
	public List<?> getHddInfo() {
		return null;
	}

	@Override
	public void setDelete(HashMap<String, Object> param) {
		mapper.setDelete(param);
	}
	@Override
	public void setDelete2(HashMap<String, Object> param) {
		mapper.setDelete2(param);
	}

	@Override
	public void setBranchDelete(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDeleteMrecordUser(HashMap<String, Object> param) {
		
	}

	@Override
	public void setUpdate(HashMap<String, Object> param) {
		mapper.setUpdate(param);
	}

	
}
