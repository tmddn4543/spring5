package com.study04.www.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study04.www.mapper.NoticeMapper;
import com.study04.www.model.Notice;
import com.study04.www.service.IDelete;
import com.study04.www.service.IInsert;
import com.study04.www.service.IList;
import com.study04.www.service.IUpdate;
import com.study04.www.service.IView;

@Service
public class NoticeService implements IList<Notice>, IInsert, IDelete, IUpdate, IView<Notice> {
	
	@Autowired
	NoticeMapper mapper;

	@Override
	public List<Notice> getList(HashMap<String, Object> param) {
		return mapper.getList(param);
	}

	@Override
	public void setInsert(HashMap<String, Object> param) {
		mapper.setInsert(param);
	}

	@Override
	public int getListCount(HashMap<String, Object> param) {
		return mapper.getListCount(param);
	}

	@Override
	public Notice getView(HashMap<String, Object> param) {
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

}
