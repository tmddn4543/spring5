package com.team404.command;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnsBoardDTO {
	private int bno;
	private String writer;	//글쓴이
	private String uploadPath;	//fileloca를 포함한업로드경로
	private String fileLoca;	//날짜폴더경로
	private String fileName;	//변경해서저장할이름
	private String fileRealName;//원본이름
	private String content;		//내용
	private Timestamp regdate;	//등록일
}
