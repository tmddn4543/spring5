package com.study04.www.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study04.www.mapper.MemberMapper;
import com.study04.www.model.Member;
import com.study04.www.service.IAllDelete;
import com.study04.www.service.IDelete;
import com.study04.www.service.IInsert;
import com.study04.www.service.IList;
import com.study04.www.service.ILogin;
import com.study04.www.service.IUpdate;
import com.study04.www.service.IView;

@Service
public class MemberService implements ILogin<Member>, IView<Member>,IInsert, IList<Member>,IDelete,IUpdate , IAllDelete{
	
	@Autowired
	MemberMapper mapper;

	@Override
	public Member getView(HashMap<String, Object> param) {
		return mapper.getView(param);
	}

	@Override
	public void setAllDelete(HashMap<String, Object> param) {
		mapper.setAllDelete(param);
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

	@Override
	public Member getLogin(HashMap<String, Object> param) {
		return mapper.getLogin(param);
	}

}
