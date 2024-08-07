package za.co.we.serve.users.api.service;

import java.util.List;

import za.co.we.serve.users.api.dto.UserAccountDto;

public interface UserService {

	public UserAccountDto getUserById(Long id);
	
	public UserAccountDto registerUser(UserAccountDto userAccountDto);

	public List<UserAccountDto> getUsers();
	
}
