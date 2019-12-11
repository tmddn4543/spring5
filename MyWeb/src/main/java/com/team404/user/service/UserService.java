package com.team404.user.service;

import com.team404.command.UserDTO;

public interface UserService {
	public int idConfirm(UserDTO dto);
	public int join(UserDTO dto);
	public int login(UserDTO dto);
	public UserDTO getInfo(String userId);
	public int updateUser(UserDTO dto); 
}
