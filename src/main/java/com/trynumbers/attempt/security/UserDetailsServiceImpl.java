package com.trynumbers.attempt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trynumbers.attempt.entity.User;
import com.trynumbers.attempt.repository.UserRepository;

/**
 * Loads user-specific data. 
 * @author Serafim Sokhin
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	/**
	 * Locates the user based on the email.
	 * 
	 * @param email the email identifying the user whose data is required.
	 * @return a fully populated user record 
	 * @throws UsernameNotFoundException if the user could not be found or the user has no GrantedAuthority
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
		return SecurityUser.fromUser(user);
	}

}
