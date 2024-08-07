package za.co.we.serve.users.api.exception;

import java.time.LocalDateTime;

/**
 * A Class containing error or exception details
 * @author Chris Sibusiso Majuba
 */
public class ErrorDetails {
	
	private LocalDateTime dateTime;
	private String errorMessage;
	private String exceptionDetails;

	public ErrorDetails() {

	}

	public ErrorDetails(LocalDateTime dateTime, String errorMessage, String exceptionDetails) {
		super();
		this.dateTime = dateTime;
		this.errorMessage = errorMessage;
		this.exceptionDetails = exceptionDetails;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getExceptionDetails() {
		return exceptionDetails;
	}

	public void setExceptionDetails(String exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}

	@Override
	public String toString() {
		return "ErrorDetails [dateTime=" + dateTime + ", errorMessage=" + errorMessage + ", exceptionDetails="
				+ exceptionDetails + "]";
	}

	
	
	

}
