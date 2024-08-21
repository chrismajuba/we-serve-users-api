package za.co.we.serve.users.api.service.impl;

import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import za.co.we.serve.users.api.constant.WeServeConstants;
import za.co.we.serve.users.api.dto.JWTAuthResponseDto;
import za.co.we.serve.users.api.dto.LoginDto;
import za.co.we.serve.users.api.dto.RegistrationDto;
import za.co.we.serve.users.api.entity.Role;
import za.co.we.serve.users.api.entity.UserAccount;
import za.co.we.serve.users.api.exception.WeServeAPIException;
import za.co.we.serve.users.api.repository.RoleRepository;
import za.co.we.serve.users.api.repository.UserAccountRepository;
import za.co.we.serve.users.api.service.AuthService;
import za.co.we.serve.users.api.service.JWTtokenProvider;

@Service
public class AuthServiceImpl implements AuthService {

	/*
	 * Attributes
	 */
	private AuthenticationManager authenticationManager;
	private UserAccountRepository userRepository;
	private PasswordEncoder encoder;
	private RoleRepository roleRepository;
	private JWTtokenProvider jwtProvider;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserAccountRepository userRepository,
			PasswordEncoder encoder, RoleRepository roleRepository, JWTtokenProvider jwtProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.roleRepository = roleRepository;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public JWTAuthResponseDto login(LoginDto loginDto) {

		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authenticate);

		String jwTtoken = jwtProvider.createJWTtoken(authenticate);
		
		return new JWTAuthResponseDto(WeServeConstants.JWTtoken, jwTtoken);
	}

	@Override
	public String registration(RegistrationDto registrationDto) {

		if (userRepository.existsByEmail(registrationDto.getEmail()))
			throw new WeServeAPIException("Email [" + registrationDto.getEmail() + "] already exist!");

		String encodedPassword = encoder.encode(registrationDto.getPassword());

		registrationDto.setPassword(encodedPassword);

		UserAccount userAccount = new UserAccount();
		userAccount.setEmail(registrationDto.getEmail());
		userAccount.setPassword(registrationDto.getPassword());
		userAccount.setName(registrationDto.getName());
		userAccount.setSurname(registrationDto.getSurname());

		Role role = roleRepository.findById(1l).get();
		role.setUsers(Set.of(userAccount));

		userAccount.setRole(role);

		UserAccount saveUserAccount = userRepository.save(userAccount);

		return "Successfully Registered User with id [" + saveUserAccount.getId() + "]";
	}

	@Override
	public boolean validateToken(String token) {
		return jwtProvider.validateJWTtoken(token);
	}

}
