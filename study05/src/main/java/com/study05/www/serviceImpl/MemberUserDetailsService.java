package com.study05.www.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.study05.www.mapper.MemberMapper;
import com.study05.www.model.Constant;
import com.study05.www.model.Member;
import com.study05.www.model.Session;



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
		Session session = new Session();
		session.setUsername(username);
		session.setName(member.getName());
		session.setLevel(member.getLevel());
		session.setPassword(member.getPwd());
		session.setAccountNonExpired(true);
		session.setAccountNonLocked(true);
		session.setCredentialsNonExpired(true);
		session.setEnabled(true);
		List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
		if(member.getLevel()==0) {
        	grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_ADMIN.toString()));
        } else if(member.getLevel()==1) {
        	grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_USER.toString()));
        }
		session.setAuthorities(grantedAuthorityList);
		return session;
	}

}
