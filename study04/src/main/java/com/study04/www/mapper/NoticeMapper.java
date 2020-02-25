package com.study04.www.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study04.www.model.Notice;

@Mapper
public interface NoticeMapper {
	public List<Notice> getList(HashMap<String, Object> param);
	public int getListCount(HashMap<String, Object> param);
	public Notice getView(HashMap<String, Object> param);
	public void setInsert(HashMap<String, Object> param);
	public void setUpdate(HashMap<String, Object> param);
	public void setDelete(HashMap<String, Object> param);
	public void setAllDelete(HashMap<String, Object> param);
	
}
