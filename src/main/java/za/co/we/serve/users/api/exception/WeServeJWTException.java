package za.co.we.serve.users.api.exception;

import org.springframework.http.HttpStatus;

public class WeServeJWTException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1507959800365299730L;
	private HttpStatus status;

	public WeServeJWTException(HttpStatus status, String message) {

		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
