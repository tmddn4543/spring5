package com.study05.www.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.study05.www.controller.MemberController;
import com.study05.www.mapper.NoticeMapper;
import com.study05.www.model.Notice;
import com.study05.www.model.Session;
import com.study05.www.service.IDelete;
import com.study05.www.service.IInsert;
import com.study05.www.service.IList;
import com.study05.www.service.IUpdate;
import com.study05.www.service.IView;

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

	@Override
	public String isMyWritten(String seq) {
		// TODO Auto-generated method stub
		return null;
	}

}
