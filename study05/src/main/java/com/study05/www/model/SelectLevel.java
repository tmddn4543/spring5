package com.study05.www.model;

import java.util.ArrayList;
import java.util.List;

public class SelectLevel {
	List<LevelObject> list = new ArrayList<LevelObject>();

	public List<LevelObject> getList() {
		list.add(new LevelObject("", "전체"));
		list.add(new LevelObject("0", "관리자"));
		list.add(new LevelObject("1", "사용자"));
		return list;
	}

}