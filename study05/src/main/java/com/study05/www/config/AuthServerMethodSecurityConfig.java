package com.study05.www.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, 
		  securedEnabled = true, 
		  jsr250Enabled = true)
public class AuthServerMethodSecurityConfig extends GlobalMethodSecurityConfiguration{

	
	protected MethodSecurityExpressionHandler createExpressionHandler() { 
    	return new AuthServerMethodSecurityExpressionHandler();
    }
}
