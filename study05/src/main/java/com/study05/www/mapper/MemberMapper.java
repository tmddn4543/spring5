package com.study05.www.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study05.www.model.Member;

@Mapper
public interface MemberMapper {
	public Member getLogin(HashMap<String, Object>param);
	public Member getView(HashMap<String, Object> param);
	public List<Member> getList(HashMap<String, Object> param);
	public int getListCount(HashMap<String, Object> param);
	public void setInsert(HashMap<String, Object> param);
	public void setUpdate(HashMap<String, Object> param);
	public void setDelete(HashMap<String, Object> param);
}
