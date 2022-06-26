package com.security.pki.mapper;

import java.util.ArrayList;
import java.util.List;

import com.security.pki.dto.SignUpUserDTO;
import com.security.pki.model.User;
import com.security.pki.enums.UserType;

import com.security.pki.dto.UserDTO;

public class UserMapper {
	
	public UserMapper() {}
	
	public User UserDtoToUser(UserDTO dto) {
		User user = new User();
		user.setEmail(dto.email);
		user.setPassword(dto.password);
		return user;
		
	}

	public User SignUpUserDtoToUser(SignUpUserDTO dto) {
		User user = new User();
		user.setEmail(dto.email);
		user.setPassword(dto.password);
		return user;
	}

	public UserDTO UserToUserDto(User user) {
		UserDTO dto = new UserDTO();
		dto.id = user.getId();
		dto.userType = user.getUserType().getName();
		dto.email = user.getEmail();
		dto.password = user.getPassword();
		return dto;
	}
}
