package com.example.EventManagement.Services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.EventManagement.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	private final String ADMIN_USERNAME = "ADMIN24";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		com.example.EventManagement.Models.User user = userRepository.findUserByUsername(username);
		List<GrantedAuthority> authorities;
		if (user.getUsername().equals(ADMIN_USERNAME)) {
			authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			// Convert each Role to a SimpleGrantedAuthority
			authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
		}

		return new CustomerUserDetails(user, authorities);
	}

	private static class CustomerUserDetails extends User {
		private Integer id;

		public CustomerUserDetails(com.example.EventManagement.Models.User user,
				Collection<? extends GrantedAuthority> authorities) {
			super(user.getUsername(), user.getPassword(), authorities);
			this.id = user.getId();
		}

		public Integer getId() {
			return this.id;
		}
	}

}
