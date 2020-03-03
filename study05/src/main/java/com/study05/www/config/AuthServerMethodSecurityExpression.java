package com.study05.www.config;

import org.springframework.security.core.Authentication;

public class AuthServerMethodSecurityExpression {

	private Authentication auth; 
	public AuthServerMethodSecurityExpression(Authentication auth) {
		this.auth = auth;
	}
	
	public boolean isMyWritten(String written) {
		if(written.equals(auth.getName())) {
			return true;
		}else {
			return false;
		}
	}

}
