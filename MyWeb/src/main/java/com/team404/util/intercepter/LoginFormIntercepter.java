package com.team404.util.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginFormIntercepter extends HandlerInterceptorAdapter{



	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//로그인 성공시 생성되는 세션 userId
		String userId = (String)request.getSession().getAttribute("userId");
		String uri = (String)request.getSession().getAttribute("uri");
		
		if(uri != null && userId != null) {//기존에 접근하는 URI가 있는 경우
			response.sendRedirect(uri);
		}else if(userId != null) {//로그인 성공인 경우
			response.sendRedirect(request.getContextPath()+"/home");
		}
		
	}

	
	
	
	
}
