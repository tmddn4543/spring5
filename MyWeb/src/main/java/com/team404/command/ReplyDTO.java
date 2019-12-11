package com.team404.command;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReplyDTO {
	private int rno;
	private int bno;
	private String reply;
	private String replyid;
	private String replypw;
	private Timestamp replydate;
	private Timestamp updatedate;
}
