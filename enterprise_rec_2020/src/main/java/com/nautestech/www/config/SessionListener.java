package com.nautestech.www.config;

import java.util.HashMap;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.nautestech.www.controller.IndexController;
import com.nautestech.www.serviceImpl.UsersService;

public class SessionListener implements HttpSessionListener {

	@Autowired
	UsersService uService;
	
	@Autowired
	IndexController indexController;
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("emp_id", se.getSession().getAttribute("session_id"));
		param.put("result", "logout");
		System.out.println("logout");
		uService.setInsertListen_log(param);
//		indexController.test();
	}
}
