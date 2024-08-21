package za.co.we.serve.users.api.dto;

import jakarta.validation.constraints.NotNull;

public class AccessTokenDto {
	
	@NotNull
	private String token;
	
	public AccessTokenDto() {
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	

}
