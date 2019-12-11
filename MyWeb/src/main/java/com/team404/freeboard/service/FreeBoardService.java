package com.team404.freeboard.service;

import java.util.ArrayList;

import com.team404.command.FreeBoardDTO;
import com.team404.util.Criteria;

public interface FreeBoardService {
	public void regist(FreeBoardDTO dto);
	public ArrayList<FreeBoardDTO> list(Criteria cri);
	public void delete(int num);
	public FreeBoardDTO detail(int num);
	public void modify(FreeBoardDTO dto);
	public int getTotal(Criteria cri);
}
