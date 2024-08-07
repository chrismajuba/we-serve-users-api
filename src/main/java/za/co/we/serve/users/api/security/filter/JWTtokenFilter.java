package za.co.we.serve.users.api.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import za.co.we.serve.users.api.service.JWTtokenProvider;

@Component
public class JWTtokenFilter extends OncePerRequestFilter {

	/*
	 * Attributes
	 */
	private JWTtokenProvider jwTtokenProvider;
	private UserDetailsService userDetailsService;

	public JWTtokenFilter(JWTtokenProvider jwTtokenProvider, UserDetailsService userDetailsService) {
		super();
		this.jwTtokenProvider = jwTtokenProvider;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwTtokenFromRequest = getJWTtokenFromRequest(request);

		if (StringUtils.hasText(jwTtokenFromRequest) && jwTtokenProvider.validateJWTtoken(jwTtokenFromRequest)) {

			String username = jwTtokenProvider.getUsername(jwTtokenFromRequest);

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());

			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

		}

		filterChain.doFilter(request, response);

	}

	/**
	 * Method to extract JWTtoken from HttpServletRequest
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	private String getJWTtokenFromRequest(HttpServletRequest httpServletRequest) {

		String bearToken = httpServletRequest.getHeader("Authorization");

		if (StringUtils.hasText(bearToken) && bearToken.startsWith("Bearer ")) {

			return bearToken.split(" ")[1];

		}

		return null;
	}

}
