package com.study05.www.config;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;

public class AuthServerSecurityExpressionRoot extends SecurityExpressionRoot{

	public AuthServerSecurityExpressionRoot(Authentication authentication) {
		super(authentication);
	}
}
