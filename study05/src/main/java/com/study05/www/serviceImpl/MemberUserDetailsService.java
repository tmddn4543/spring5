package com.study05.www.serviceImpl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.study05.www.mapper.MemberMapper;
import com.study05.www.model.Member;



@Service
public class MemberUserDetailsService implements UserDetailsService{
	
	@Autowired
	MemberMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		HashMap<String, Object> param = new HashMap<>();
		param.put("id", username);
		Member member = mapper.getView(param);
		if (null == member) {
        	throw new UsernameNotFoundException("USER NOT FOUND OR NOT MATCH PASSWORD");
        }
		return member;
	}

}
