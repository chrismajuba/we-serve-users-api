package za.co.we.serve.users.api.service;

import za.co.we.serve.users.api.dto.JWTAuthResponseDto;
import za.co.we.serve.users.api.dto.LoginDto;
import za.co.we.serve.users.api.dto.RegistrationDto;

public interface AuthService {

	public JWTAuthResponseDto login(LoginDto loginDto);

	public String registration(RegistrationDto registrationDto);
	
	public boolean validateToken(String token);
}
