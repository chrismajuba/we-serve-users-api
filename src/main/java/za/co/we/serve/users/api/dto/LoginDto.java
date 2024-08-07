package za.co.we.serve.users.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDto {

	@NotBlank
	@Email(message = "Please enter a valid Email")
	private String email;
	@NotBlank
	private String password;
	
	public LoginDto() {
	}

	public LoginDto(@NotBlank String email, @NotBlank String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginDto [email=" + email + ", password=" + password + "]";
	}
	
	
}
