package com.study05.www.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study05.www.mapper.MemberMapper;
import com.study05.www.model.Member;
import com.study05.www.service.IDelete;
import com.study05.www.service.IInsert;
import com.study05.www.service.IList;
import com.study05.www.service.IUpdate;
import com.study05.www.service.IView;

@Service
public class MemberService implements IView<Member>,IInsert, IList<Member>,IDelete,IUpdate {
	
	@Autowired
	MemberMapper mapper;

	@Override
	public Member getView(HashMap<String, Object> param) {
		return mapper.getView(param);
	}


	@Override
	public void setUpdate(HashMap<String, Object> param) {
		mapper.setUpdate(param);
	}

	@Override
	public void setDelete(HashMap<String, Object> param) {
		mapper.setDelete(param);
	}

	@Override
	public List<Member> getList(HashMap<String, Object> param) {
		return mapper.getList(param);
	}

	@Override
	public int getListCount(HashMap<String, Object> param) {
		return mapper.getListCount(param);
	}

	@Override
	public void setInsert(HashMap<String, Object> param) {
		mapper.setInsert(param);
	}

	
}
