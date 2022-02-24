package com.simondiez.springnumbers.security;

import java.util.Collection;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.simondiez.springnumbers.entity.User;

/**
 * Provides core user information.
 * @author Serafim Sokhin
 */
public class SecurityUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private final String username;
	private final String password;
	private final List<SimpleGrantedAuthority> authorites;
	private final boolean isActive;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorites;
	}

	public SecurityUser(String username, String password, List<SimpleGrantedAuthority> authorites, boolean isActive) {
		super();
		this.username = username;
		this.password = password;
		this.authorites = authorites;
		this.isActive = isActive;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return isActive;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return isActive;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return isActive;
	}

	/**
	 * Construct the User with the details required by {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider DaoAuthenticationProvider}.
	 * @param user User retrieved from {@link com.simondiez.springnumbers.repository.UserRepository UserRepository}
	 * @return UserDetails from user
	 */
	public static UserDetails fromUser(User user) {
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
				user.getStatus().equals(Status.ACTIVE),
				user.getStatus().equals(Status.ACTIVE),
				user.getStatus().equals(Status.ACTIVE),
				user.getStatus().equals(Status.ACTIVE), 
				user.getRole().getAuthorities());
	}
}
