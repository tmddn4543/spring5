package com.team404.util.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class FreeBoardIntercepter extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.등록클릭했을때, 로그인인터셉터로연결
		//2.regist화면에서는 작성자를 세션값을처리, 작성자를readonly
		//3.수정,변경,삭제 클릭시 인터셉터를 실행시킴(단, 화면에서는 writer 에대한 정보를반드시넘겨줌)
		
		String writer = request.getParameter("writer");
		String userId = (String)request.getSession().getAttribute("userId");
		
		if(writer != null && writer.equals(userId)) {
			return true;
		}else {
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("<script>");
			response.getWriter().write("alert('권한이없습니다');");
			response.getWriter().write("history.go(-1);");
			response.getWriter().write("</script>");
			return false;
		}
	}
}
