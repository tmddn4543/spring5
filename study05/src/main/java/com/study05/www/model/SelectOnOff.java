package com.study05.www.model;

import java.util.ArrayList;
import java.util.List;

public class SelectOnOff {
	List<LevelObject> list = new ArrayList<LevelObject>();
	public List<LevelObject> getList(){
		list.add(new LevelObject("Y", "ON"));
		list.add(new LevelObject("N", "OFF"));
		return list;
	}
}
