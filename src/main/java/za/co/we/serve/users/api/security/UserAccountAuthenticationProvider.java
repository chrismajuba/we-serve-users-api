package za.co.we.serve.users.api.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import za.co.we.serve.users.api.exception.WeServeAuthenticationException;

@Component
public class UserAccountAuthenticationProvider implements AuthenticationProvider { // Used if we have our own
																					// Authentication logic

	/*
	 * Attributes
	 */
	private UserDetailsService userDetailsService;
	private PasswordEncoder encoder;
	
	
	public UserAccountAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder encoder) {
		this.userDetailsService = userDetailsService;
		this.encoder = encoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());

		if (!encoder.matches(authentication.getCredentials().toString(), userDetails.getPassword()))
			throw new WeServeAuthenticationException("Incorrect Password.");

		return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
