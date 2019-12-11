package com.team404.freeboard.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team404.command.FreeBoardDTO;
import com.team404.freeboard.mapper.FreeBoardMapper;
import com.team404.util.Criteria;

@Service("freeBoardService")
public class FreeBoardServiceImpl implements FreeBoardService{

	@Autowired
	private FreeBoardMapper fm;
	
	@Override
	public void regist(FreeBoardDTO dto) {
		int res = fm.regist(dto);
		System.out.println(res + "가입여부");
	}

	@Override
	public ArrayList<FreeBoardDTO> list(Criteria cri) {
		ArrayList<FreeBoardDTO> dto = fm.list(cri);
		return dto;
	}

	@Override
	public void delete(int num) {
		int res  = fm.delete(num);
		System.out.println(res + "삭제여부");
	}

	@Override
	public FreeBoardDTO detail(int num) {
		FreeBoardDTO dto = fm.detail(num);
		return dto;
	}

	@Override
	public void modify(FreeBoardDTO dto) {
		int res = fm.modify(dto);
		System.out.println("변경확인" + res);
	}

	@Override
	public int getTotal(Criteria cri) {
		return fm.getTotal(cri);
	}
	
	
}
