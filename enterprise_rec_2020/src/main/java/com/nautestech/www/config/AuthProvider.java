package com.nautestech.www.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.nautestech.www.model.Session;
import com.nautestech.www.serviceImpl.UsersDetailsService;

@Component("authProvider")
public class AuthProvider implements AuthenticationProvider {

	@Autowired
	UsersDetailsService uService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String id = authentication.getName();
        String password = authentication.getCredentials().toString();
        Session users = (Session) uService.loadUserByUsername(id);
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), users.getPassword())) {
			throw new UsernameNotFoundException("USER NOT FOUND OR NOT MATCH PASSWORD");
		}
        
        
        
        
        
        
        HashMap<String, Object> param = new HashMap<String, Object>();
        String login_date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA).format(new Date());
        String tel_no = users.getTel_no();
        param.put("tel_no", tel_no);
        param.put("emp_nm", "");
        param.put("login_date", login_date);
        System.out.println(tel_no);
        uService.setUpdate(param);
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), users.getPassword(), users.getAuthorities());
		result.setDetails(users);
		System.out.println(result.getDetails());
		return result;
	}


	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	
}
