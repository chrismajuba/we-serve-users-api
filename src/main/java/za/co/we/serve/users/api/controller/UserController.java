package za.co.we.serve.users.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import za.co.we.serve.users.api.dto.AccessTokenDto;
import za.co.we.serve.users.api.dto.JWTAuthResponseDto;
import za.co.we.serve.users.api.dto.LoginDto;
import za.co.we.serve.users.api.dto.RegistrationDto;
import za.co.we.serve.users.api.dto.UserAccountDto;
import za.co.we.serve.users.api.exception.ErrorDetails;
import za.co.we.serve.users.api.service.AuthService;
import za.co.we.serve.users.api.service.UserService;

@RestController
@RequestMapping("/we-serve/auth/api/v1/users")
public class UserController {

	/*
	 * Attributes
	 */

	private UserService userService;
	private AuthService authService;

	public UserController(UserService userService, AuthService authService) {
		this.userService = userService;
		this.authService = authService;
	}

	@Operation(summary = "Login or Signin",
            description = "This API returns an Access Token.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successful Operation",
                             content = @Content(schema = @Schema(implementation = JWTAuthResponseDto.class))),
                @ApiResponse(responseCode = "400", description = "Bad Request",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "404", description = "Not Found",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<JWTAuthResponseDto> login(@RequestBody @Valid LoginDto loginDto) {

		return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
	}

	@Operation(summary = "Get a list of Users",
            description = "This API returns a list of Users.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successful Operation",
                             content = @Content(schema = @Schema(implementation = UserAccountDto.class))),
                @ApiResponse(responseCode = "400", description = "Bad Request",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "404", description = "Not Found",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<UserAccountDto>> getUsers(){
		
		return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
	}
	
	@Operation(summary = "Get a User",
            description = "This API returns a User.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successful Operation",
                             content = @Content(schema = @Schema(implementation = UserAccountDto.class))),
                @ApiResponse(responseCode = "400", description = "Bad Request",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "404", description = "Not Found",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<UserAccountDto> getUserById(@PathVariable Long id) {

		return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
	}

	@Operation(summary = "Create a User",
            description = "This API creates a User.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successful Operation",
                             content = @Content(schema = @Schema(implementation = UserAccountDto.class))),
                @ApiResponse(responseCode = "400", description = "Bad Request",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "404", description = "Not Found",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody @Valid RegistrationDto registrationDto) {

		return new ResponseEntity<>(authService.registration(registrationDto), HttpStatus.CREATED);
	}
	
	@Operation(summary = "Validate a token",
            description = "This API validates a token.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successful Operation",
                             content = @Content(schema = @Schema(implementation = Boolean.class))),
                @ApiResponse(responseCode = "400", description = "Bad Request",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "401", description = "Unauthorized",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "404", description = "Not Found",content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
	@PostMapping(path = "/validate-token")
	public ResponseEntity<Boolean> validateToken(@RequestBody AccessTokenDto accessTokenDto){

		String token = accessTokenDto.getToken();
		
		if(token.contains("Bearer"))
			token = token.substring(7);
		return new ResponseEntity<>(authService.validateToken(token),HttpStatus.OK);
	}
}
