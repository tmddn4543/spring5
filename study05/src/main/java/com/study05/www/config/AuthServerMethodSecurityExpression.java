package com.study05.www.config;

import java.util.HashMap;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study05.www.controller.MemberController;
import com.study05.www.model.Board;
import com.study05.www.serviceImpl.BoardService;


public class AuthServerMethodSecurityExpression {

	private Authentication auth;
	BoardService bService;
	
	public AuthServerMethodSecurityExpression(Authentication auth,BoardService bService) {
		this.auth = auth;
		this.bService = bService;
	}
	
	public boolean isMyWritten(String seq) {
		HashMap<String, Object> param = new HashMap<>();
		String name = auth.getName();
		param.put("seq", seq);
		Board board = bService.getView(param);
		String written = board.getWritten();
		if(!name.equals(written)) {
			return false;
		}else {
			return true;
		}
	}
}
