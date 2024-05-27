package com.mvp.todolist.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mvp.todolist.entities.User;

public class DetailUserData implements UserDetails {

	private final Optional<User> user;

	public DetailUserData(Optional<User> user) {

		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return new ArrayList<>();
	}

	@Override
	public String getPassword() {

		return user.orElse(new User()).getPassword();
	}

	@Override
	public String getUsername() {

		return user.orElse(new User()).getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}
}
