package za.co.we.serve.users.api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import za.co.we.serve.users.api.dto.UserAccountDto;
import za.co.we.serve.users.api.entity.UserAccount;
import za.co.we.serve.users.api.exception.WeServeAPIException;
import za.co.we.serve.users.api.repository.UserAccountRepository;
import za.co.we.serve.users.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	/*
	 * Attributes
	 */
	private UserAccountRepository repository;

	public UserServiceImpl(UserAccountRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserAccountDto registerUser(UserAccountDto userAccountDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserAccountDto getUserById(Long id) {

		UserAccount userAccount = repository.findById(id)
				.orElseThrow(() -> new WeServeAPIException("User: [" + id + "] Not found"));

		return convertToDto(userAccount);
	}

	/**
	 * Convert UserAccount to {@link UserAccountDto}
	 * 
	 * @param user
	 * @return
	 */
	private UserAccountDto convertToDto(UserAccount user) {
		UserAccountDto userAccountDto = new UserAccountDto(user.getId(), user.getName(), user.getSurname(),
				user.getEmail(), user.getPassword(), user.getRole());
		return userAccountDto;
	}

	@Override
	public List<UserAccountDto> getUsers() {

		List<UserAccountDto> userAccountDtos = repository.findAll().stream()
				.map((useraccount) -> convertToDto(useraccount)).toList();

		return userAccountDtos;
	}

}
