package com.agent.application.mapper;

import com.agent.application.dto.SignUpUserDTO;
import com.agent.application.dto.UserDTO;
import com.agent.application.model.User;

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
