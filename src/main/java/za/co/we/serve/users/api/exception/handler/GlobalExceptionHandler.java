package za.co.we.serve.users.api.exception.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import za.co.we.serve.users.api.exception.ErrorDetails;
import za.co.we.serve.users.api.exception.WeServeAuthenticationException;
import za.co.we.serve.users.api.exception.WeServeJWTException;
import za.co.we.serve.users.api.exception.WeServeAPIException;

/**
 * Exception handler for this API
 * 
 * @author Chris Sibusiso Majuba
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements AuthenticationEntryPoint {

	/**
	 * Exception Handler for weServeAuthenticationException
	 * 
	 * @param weServeAuthenticationException
	 * @param webRequest
	 * @return
	 */

	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(WeServeAuthenticationException.class)
	public ResponseEntity<ErrorDetails> authExceptionHandler(
			WeServeAuthenticationException weServeAuthenticationException, WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), weServeAuthenticationException.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * WeServeAPIException Handler
	 * 
	 * @param authException
	 * @param webRequest
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(WeServeAPIException.class)
	public ResponseEntity<ErrorDetails> authExceptionHandler(WeServeAPIException authException, WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), authException.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handler for Access Denied exceptions
	 * 
	 * @param exception
	 * @param webRequest
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception,
			WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
	}

	/**
	 * Exception Handler for every Exception
	 * 
	 * @param exception
	 * @param webRequest
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleExcetion(Exception exception, WebRequest webRequest) {

		ErrorDetails errorInfo = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handler for WeServeJWTException
	 * @param serveJWTException
	 * @param webRequest
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(WeServeJWTException.class)
	public ResponseEntity<ErrorDetails> handleWeServeJWTException(WeServeJWTException serveJWTException,
			WebRequest webRequest) {

		ErrorDetails errorInfo = new ErrorDetails(LocalDateTime.now(), serveJWTException.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(errorInfo, serveJWTException.getStatus());
	}

	/**
	 * Method for handling exceptions thrown by the @Valid interface
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Number of Error(s)[" + ex.getErrorCount()
				+ "] First Error[" + ex.getFieldError().getDefaultMessage() + "]", request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	/*
	 * Method called for exceptions that occur during Authentication
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), authException.getMessage(),
				HttpStatus.UNAUTHORIZED.getReasonPhrase());
		PrintWriter writer = response.getWriter();
		writer.write("{\r\n" + "  \"timestamp\":\"" + errorDetails.getDateTime() + "\" ,\r\n"
				+ "  \"exceptionDetails\": \"" + errorDetails.getExceptionDetails() + "\" ,\r\n" + "  \"message\": \""
				+ errorDetails.getErrorMessage() + "\" \r\n" + "}");
		writer.flush();

	}
}
