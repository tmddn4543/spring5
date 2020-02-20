package com.study03.www.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.study03.www.model.Member;

@Mapper
public interface MemberMapper {
	public Member getView(HashMap<String, Object> param);
}
