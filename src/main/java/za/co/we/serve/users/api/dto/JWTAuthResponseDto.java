package za.co.we.serve.users.api.dto;

public class JWTAuthResponseDto {

	/*
	 * Attributes
	 */
	private String tokenType = "";
	private String accessToken = "";
	
	
	public JWTAuthResponseDto(String tokenType, String accessToken) {
		super();
		this.tokenType = tokenType;
		this.accessToken = accessToken;
	}


	public String getToken_type() {
		return tokenType;
	}


	public String getAccess_token() {
		return accessToken;
	}
	
	
}
