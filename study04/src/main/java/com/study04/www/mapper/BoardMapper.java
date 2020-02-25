package com.study04.www.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study04.www.model.Board;

@Mapper
public interface BoardMapper {
	public List<Board> getList(HashMap<String, Object> param);
	public int getListCount(HashMap<String, Object> param);
	public void setInsert(HashMap<String, Object> param);
	public void setUpdate(HashMap<String, Object> param);
	public void setDelete(HashMap<String, Object> param);
	public Board getView(HashMap<String, Object> param);
}
