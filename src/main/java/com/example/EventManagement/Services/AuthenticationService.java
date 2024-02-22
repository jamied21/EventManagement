package com.example.EventManagement.Services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.EventManagement.Models.Role;
import com.example.EventManagement.Models.User;
import com.example.EventManagement.Repository.RoleRepository;
import com.example.EventManagement.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	private PasswordEncoder passwordEncoder;

	public User registerUser(String username, String password) {

		String encodePassword = passwordEncoder.encode(password);

		Role userRole = roleRepository.findByAuthority("USER").get();

		Set<Role> authorities = new HashSet<>();
		authorities.add(userRole);

		return userRepository.save(new User(0, username, encodePassword, "Trainer", authorities));
	}

}
