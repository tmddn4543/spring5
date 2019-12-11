package com.team404.freereply.service;

import java.util.ArrayList;

import com.team404.command.ReplyDTO;
import com.team404.util.Criteria;
import com.team404.util.ReplyPageDTO;

public interface FreeReplyService {
	public int regist(ReplyDTO dto);
	public ArrayList<ReplyDTO> getList(int bno);
	public int pwCheck(ReplyDTO dto);
	public int delete(ReplyDTO dto);
	public int update(ReplyDTO dto);
}
