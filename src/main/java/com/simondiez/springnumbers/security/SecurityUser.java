package com.simondiez.springnumbers.security;

import java.util.Collection;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.simondiez.springnumbers.entity.User;

/**
 * Provides core user information.
 * 
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
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isActive;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isActive;
	}

	@Override
	public boolean isEnabled() {
		return isActive;
	}

	/**
	 * Construct the User with the details required by
	 * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider
	 * DaoAuthenticationProvider}.
	 * 
	 * @param user User retrieved from
	 *             {@link com.simondiez.springnumbers.repository.UserRepository
	 *             UserRepository}
	 * @return UserDetails from user
	 */
	public static UserDetails fromUser(User user) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				user.getStatus().equals(Status.ACTIVE), user.getStatus().equals(Status.ACTIVE),
				user.getStatus().equals(Status.ACTIVE), user.getStatus().equals(Status.ACTIVE),
				user.getRole().getAuthorities());
	}
}
