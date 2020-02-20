package com.study03.www.serviceImpl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study03.www.mapper.MemberMapper;
import com.study03.www.model.Member;
import com.study03.www.service.IView;

@Service
public class MemberService implements IView<Member> {
	
	@Autowired
	MemberMapper mapper;

	@Override
	public Member getView(HashMap<String, Object> param) {
		return mapper.getView(param);
	}

}
