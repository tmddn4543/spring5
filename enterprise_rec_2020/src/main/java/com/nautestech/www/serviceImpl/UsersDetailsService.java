package com.nautestech.www.serviceImpl;

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

import com.nautestech.www.mapper.UsersMapper;
import com.nautestech.www.model.Users;
import com.nautestech.www.model.Constant;
import com.nautestech.www.model.Session;

@Service
public class UsersDetailsService implements UserDetailsService{

	@Autowired
	UsersMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		HashMap<String, Object> param = new HashMap<>();
		param.put("pagesize", 10);
		param.put("pagestart", 0);
		param.put("emp_id", username);
		List<Users> users = mapper.getView(param);
		
		if (users.size()<=0) {
			HashMap<String, Object> param1 = new HashMap<>();
			param1.put("emp_id", username);
			param1.put("result", "fail");
			mapper.setInsertListen_log(param1);
        	throw new UsernameNotFoundException("USER NOT FOUND OR NOT MATCH PASSWORD");
        }
		Session session = new Session();
		session.setUsername(username);
		session.setAuth_cd(users.get(0).getAuth_cd());
		session.setEmp_id(users.get(0).getEmp_id());
		session.setPassword(users.get(0).getPass());
		session.setTel_no(users.get(0).getTel_no());
		session.setDown_type(users.get(0).getDown_type());
		session.setEmp_nm(users.get(0).getEmp_nm());
		session.setBranch_cd(users.get(0).getBranch_cd());
		session.setAccountNonExpired(true);
		session.setAccountNonLocked(true);
		session.setCredentialsNonExpired(true);
		session.setEnabled(true);
		List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
		if(users.get(0).getAuth_cd().equals("00")) {
			grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_ADMIN.toString()));
		}else if(users.get(0).getAuth_cd().equals("11")) {
			grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_OPERATIONADMIN.toString()));
		}else if(users.get(0).getAuth_cd().equals("12")) {
			grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_GROUPADMIN.toString()));
		}else if(users.get(0).getAuth_cd().equals("13")) {
			grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_ENDUSER.toString()));
		}else if(users.get(0).getAuth_cd().equals("14")) {
			grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_LISTENUSER.toString()));
		}else if(users.get(0).getAuth_cd().equals("15")) {
			grantedAuthorityList.add(new SimpleGrantedAuthority(Constant.ROLE_TYPE.ROLE_SMSUSER.toString()));
		}
		
		session.setAuthorities(grantedAuthorityList);
		return session;
	}
	
	public void setUpdate(HashMap<String, Object>param) {
		mapper.setUpdate(param);
	}
	
	public void setInsert(HashMap<String, Object>param) {
		mapper.setInsertListen_log(param);
	}

}
