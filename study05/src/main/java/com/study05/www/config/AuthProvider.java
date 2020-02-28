package com.study05.www.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.study05.www.model.Member;
import com.study05.www.serviceImpl.MemberUserDetailsService;

@Component("authProvider")
public class AuthProvider implements AuthenticationProvider {
 
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	MemberUserDetailsService m;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Member use = (Member) m.loadUserByUsername(authentication.getName());
		if (!use.getPwd().equals(authentication.getCredentials().toString())) {
			throw new UsernameNotFoundException("USER NOT FOUND OR NOT MATCH PASSWORD");
		}
		use.setPwd(passwordEncoder.encode(use.getPassword()));
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), use.getPassword(), use.getAuthorities());
		result.setDetails(use);
		System.out.println(result.getDetails());
		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
