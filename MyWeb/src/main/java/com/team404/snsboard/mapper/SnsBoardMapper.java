package com.team404.snsboard.mapper;

import java.util.ArrayList;

import com.team404.command.SnsBoardDTO;

public interface SnsBoardMapper {
	public boolean insert(SnsBoardDTO dto);
	public ArrayList<SnsBoardDTO> getList();
	public SnsBoardDTO snsDetail(int bno);
}
