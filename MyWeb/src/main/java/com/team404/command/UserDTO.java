package com.team404.command;

import java.sql.Timestamp;
import java.util.ArrayList;

import lombok.Data;

@Data
public class UserDTO {
	private String userId;
	private String userPw;
	private String userName;
	private String userPhone1;
	private String userPhone2;
	private String userEmail1;
	private String userEmail2;
	private String addrZipNum;
	private String addrBasic;
	private String addrDetail;
	private Timestamp regdate;
	
	//마이페이지에 조인을 통해 한번에 게시글에 대한정보를 가져나가기 위한선언
	private ArrayList<FreeBoardDTO> userBoardList;
}
