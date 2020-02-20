package com.study03.www.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study03.www.mapper.BoardMapper;
import com.study03.www.model.Board;
import com.study03.www.service.IInsert;
import com.study03.www.service.IList;

@Service
public class BoardService implements IList<Board>, IInsert {
	
	@Autowired
	BoardMapper mapper;

	@Override
	public List<Board> getList(HashMap<String, Object> param) {
		return mapper.getList(param);
	}

	@Override
	public void setInsert(HashMap<String, Object> param) {
		mapper.setInsert(param);
	}

}
