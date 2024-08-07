package za.co.we.serve.users.api.exception;

import org.springframework.security.core.AuthenticationException;

public class WeServeAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2729643031532155137L;

	public WeServeAuthenticationException(String msg) {
		super(msg);
	}

}
