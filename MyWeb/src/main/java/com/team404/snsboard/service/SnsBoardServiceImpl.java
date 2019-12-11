package com.team404.snsboard.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team404.command.SnsBoardDTO;
import com.team404.snsboard.mapper.SnsBoardMapper;


@Service("snsBoardService")
public class SnsBoardServiceImpl implements SnsBoardService{

	@Autowired
	private SnsBoardMapper sbm;
	
	@Override
	public boolean insert(SnsBoardDTO dto) {
		return sbm.insert(dto);
	}

	@Override
	public ArrayList<SnsBoardDTO> getList() {
		return sbm.getList();
	}

	@Override
	public SnsBoardDTO snsDetail(int bno) {
		return sbm.snsDetail(bno);
	}


}
