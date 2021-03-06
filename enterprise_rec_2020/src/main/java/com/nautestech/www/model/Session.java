package com.nautestech.www.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data 
public class Session implements UserDetails {
	private String username;
    private String password;
    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private Collection<? extends GrantedAuthority> authorities;
    
    private String emp_id;
    private String emp_nm;
    private String auth_cd;
    private String tel_no;
    private String down_type;
    private String branch_cd;
    
    private int s_id;
    private String s_totalhdd;
    private String s_usage;
}
