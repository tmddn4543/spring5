package com.study05.www.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.study05.www.serviceImpl.BoardService;

@Configuration
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, 
		  securedEnabled = true, 
		  jsr250Enabled = true)
public class AuthServerMethodSecurityConfig extends GlobalMethodSecurityConfiguration{

	@Autowired
	BoardService bService;
	 
	protected MethodSecurityExpressionHandler createExpressionHandler() { 
    	return new AuthServerMethodSecurityExpressionHandler(bService);
    } 
}
