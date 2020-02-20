package com.study03.www.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study03.www.model.Notice;

@Mapper
public interface NoticeMapper {
	public List<Notice> getList(HashMap<String, Object> param);
	public void setInsert(HashMap<String, Object> param);
}
