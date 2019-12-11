package com.team404.command;

import java.sql.Timestamp;

import lombok.Data;


//@AllArgsConstructor 이노베이션은 모든변수초기화
//@NoArgsConstructor 이노베이션 기본생성자
@Data
public class FreeBoardDTO {
	private int num;
	private String writer;
	private String title;
	private String content;
	private Timestamp regdate;
	private Timestamp updatedate;
}
