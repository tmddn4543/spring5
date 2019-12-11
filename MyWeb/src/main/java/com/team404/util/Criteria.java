package com.team404.util;

import lombok.Data;

@Data
public class Criteria {
	private int pageNum; //현재조회하는페이지번호
	private int amount; //한페이지에서 몇개의데이터보여줄거
	
	private String searchType;
	private String searchName;
	
	
	public Criteria() { //첫페이지 생성될클래스
		this(1, 10);
	}
	
	public Criteria(int pageNum, int amount) {// 페이지번호를클릭시실행될생성자
		this.pageNum = pageNum;
		this.amount = amount;
	}
}
