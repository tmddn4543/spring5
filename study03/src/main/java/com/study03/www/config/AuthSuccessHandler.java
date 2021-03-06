package com.study03.www.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study03.www.model.ResultDto;

@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException {

    ObjectMapper om = new ObjectMapper();
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().print(om.writeValueAsString(ResultDto.success()));
    response.getWriter().flush();
  }

}
