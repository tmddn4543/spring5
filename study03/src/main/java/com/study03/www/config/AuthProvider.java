package com.study03.www.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.study03.www.model.Constant;
import com.study03.www.model.Member;
import com.study03.www.serviceImpl.MemberService;

@Component("authProvider")
public class AuthProvider implements AuthenticationProvider {
	@Autowired
	MemberService mService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String id = authentication.getName();
        String password = authentication.getCredentials().toString();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("param1", id);
        Member user = mService.getView(map);
        
        if (null == user || !user.getPwd().equals(password)) {
        	throw new UsernameNotFoundException("USER NOT FOUND OR NOT MATCH PASSWORD");
        }
        
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        
        // 로그인한 계정에게 권한 부여 등급이 없으니 ADMIN 권한부여
        if(authentication.getPrincipal().equals("admin")) {
        	grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_ADMIN.toString()));
        }else {
        	grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_USER.toString()));
        }
        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), password, grantedAuthorityList);
	}


	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	
}
