package com.team404.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team404.command.UserDTO;
import com.team404.user.mapper.UserMapper;

@Service("userService")
public class UserSerivceImpl implements UserService{
 
	@Autowired
	private UserMapper um;
	
	public int idConfirm(UserDTO dto) {
		return um.idConfirm(dto);
	}

	@Override
	public int join(UserDTO dto) {
		return um.join(dto);
	}

	@Override
	public int login(UserDTO dto) {
		return um.login(dto);
	}

	@Override
	public UserDTO getInfo(String userId) {
		return um.getInfo(userId);
	}

	@Override
	public int updateUser(UserDTO dto) {
		
		return um.updateUser(dto);
	}
}
