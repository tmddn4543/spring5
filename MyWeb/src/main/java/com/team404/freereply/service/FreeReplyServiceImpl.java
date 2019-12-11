package com.team404.freereply.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team404.command.ReplyDTO;
import com.team404.freereply.mapper.FreeReplyMapper;
import com.team404.util.Criteria;
import com.team404.util.ReplyPageDTO;

@Service("freeReplyService")
public class FreeReplyServiceImpl implements FreeReplyService{

	
	@Autowired
	private FreeReplyMapper frm;
	
	@Override
	public int regist(ReplyDTO dto) {
		return frm.regist(dto);
	}


	@Override
	public int delete(ReplyDTO dto) {
		return frm.delete(dto);
	}

	@Override
	public int pwCheck(ReplyDTO dto) {
		return frm.pwCheck(dto);
	}

	@Override
	public int update(ReplyDTO dto) {
		return frm.update(dto);
	}

	@Override
	public ArrayList<ReplyDTO> getList(int bno) {
		
		return frm.getList(bno);
	}
}
