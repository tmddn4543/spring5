package com.nautestech.www.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nautestech.www.mapper2.MuMapper;
import com.nautestech.www.model.HddInfo;
import com.nautestech.www.service.IDelete;
import com.nautestech.www.service.IInsert;
import com.nautestech.www.service.ISelect;

@Service
public class MuService implements IInsert, IDelete, ISelect<HddInfo>{

	@Autowired
	MuMapper mapper;

	@Override
	public void setInsert(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInsertBranch(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInsertListen_log(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInsertMrecordUser(HashMap<String, Object> param) {
		mapper.setInsertMrecordUser(param);
	}

	@Override
	public void setDelete(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBranchDelete(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDeleteMrecordUser(HashMap<String, Object> param) {
		mapper.setDeleteMrecordUser(param);
	}

	@Override
	public List<?> getView(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> getViewYYYYMM(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCreate(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getListCount(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<?> getListBranch(HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> setSelect(HashMap<String, Object> param) {
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<HddInfo> getHddInfo() {
		return mapper.getHddInfo();
	}
	

}
