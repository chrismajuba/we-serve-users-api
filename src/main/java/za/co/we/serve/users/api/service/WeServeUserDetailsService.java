package za.co.we.serve.users.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import za.co.we.serve.users.api.entity.UserAccount;
import za.co.we.serve.users.api.exception.WeServeAuthenticationException;
import za.co.we.serve.users.api.repository.UserAccountRepository;

@Component
public class WeServeUserDetailsService implements UserDetailsService{
	

	/*
	 * Attributes
	 */
	private UserAccountRepository repository;

	public WeServeUserDetailsService(UserAccountRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		UserAccount userAccount = repository.findByEmail(username)
				.orElseThrow(() -> new WeServeAuthenticationException("The email [" + username +  "] does not exist."));

		// Container for the roles
		List<GrantedAuthority> auth = new ArrayList<>();

		// Fetch the roles for this user
		String roleFromDb = userAccount.getRole().getRole();

		auth.add(new SimpleGrantedAuthority(roleFromDb));

		return new User(userAccount.getEmail(), userAccount.getPassword(), auth);

	}

}
