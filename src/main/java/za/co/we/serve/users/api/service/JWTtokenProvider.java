package za.co.we.serve.users.api.service;

import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import za.co.we.serve.users.api.exception.WeServeAuthenticationException;
import za.co.we.serve.users.api.exception.WeServeJWTException;

@ConfigurationProperties(prefix = "source")
@Component
public class JWTtokenProvider {

	private String jwt_secret;
	private Long timeout;

	public JWTtokenProvider() {
	}

	public String getJwt_secret() {
		return jwt_secret;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setJwt_secret(String jwt_secret) {
		this.jwt_secret = jwt_secret;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	@Override
	public String toString() {
		return "JWTProvider [jwt_secret=" + jwt_secret + ", timeout=" + timeout + "]";
	}

	/**
	 * Method to create JWT token
	 * 
	 * @return
	 */
	public String createJWTtoken(Authentication authentication) {

		// Pay-load
		String username = authentication.getName();
		List<? extends GrantedAuthority> roles = authentication.getAuthorities().stream().toList();

		if (!roles.isEmpty()) {

			// Date created
			Date date = new Date();

			// Expiration date
			Date expirationDate = new Date(date.getTime() + timeout);

			String token = Jwts.builder().subject(username).issuedAt(date).claim("role", roles)
					.expiration(expirationDate).signWith(getKey()).compact();

			return token;

		}

		throw new WeServeAuthenticationException(
				username + " is not authorized or does not have the necessary permissions.");
	}

	/**
	 * Return the secret
	 * 
	 * @return
	 */
	private Key getKey() {

		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwt_secret));
	}

	/**
	 * Extract Username from Token
	 * @param token
	 * @return
	 */
	public String getUsername(String token) {

		return Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token).getPayload()
				.getSubject();
	}
	
	/**
	 * Method to validate JWT token
	 * @param token
	 * @return
	 */
	public boolean validateJWTtoken(String token) {
		
		try {
			
			Jwts.parser().verifyWith((SecretKey) getKey()).build().parse(token);
			
			return true;
			
		} catch (MalformedJwtException malformedJwtException) {
			
			throw new WeServeJWTException(HttpStatus.BAD_REQUEST, "[malformed or Invalid JWT-token]");
			
		}catch (ExpiredJwtException expiredJwtException) {
			
			throw new WeServeJWTException(HttpStatus.BAD_REQUEST, "[The JWT-token has expired]");
			
		}catch (UnsupportedJwtException unsupportedJwtException) {
			
			throw new WeServeJWTException(HttpStatus.BAD_REQUEST, "[Unsupported JWT-token]");
			
		}catch (IllegalArgumentException illegalArgumentException) {
			
			throw new WeServeJWTException(HttpStatus.BAD_REQUEST, "[JWT-token claims is empty or null]");
			
		}catch (Exception e) {
			
			throw new WeServeJWTException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
	}

}
