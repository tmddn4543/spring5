package com.team404.snsboard.service;

import java.util.ArrayList;

import com.team404.command.SnsBoardDTO;

public interface SnsBoardService {
	public boolean insert(SnsBoardDTO dto);
	public ArrayList<SnsBoardDTO> getList();
	public SnsBoardDTO snsDetail(int bno);
}
