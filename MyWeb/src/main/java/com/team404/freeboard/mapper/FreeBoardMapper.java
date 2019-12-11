package com.team404.freeboard.mapper;

import java.util.ArrayList;

import com.team404.command.FreeBoardDTO;
import com.team404.util.Criteria;

public interface FreeBoardMapper {
	public int regist(FreeBoardDTO dto);
	public ArrayList<FreeBoardDTO> list(Criteria cri);
	public int delete(int num);
	public FreeBoardDTO detail(int num);
	public int modify(FreeBoardDTO dto);
	public int getTotal(Criteria cri);
	
}
