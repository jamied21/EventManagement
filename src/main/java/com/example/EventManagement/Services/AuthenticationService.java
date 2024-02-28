package com.example.EventManagement.Services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.EventManagement.Dto.LoginResponseDTO;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	public User registerUser(String username, String password) {

		String encodePassword = passwordEncoder.encode(password);

		Role userRole = roleRepository.findByAuthority("USER").get();

		Set<Role> authorities = new HashSet<>();
		authorities.add(userRole);

		return userRepository.save(new User(0, username, encodePassword, "", authorities));
	}

	public LoginResponseDTO loginUser(String username, String password) {
		// Find user, checks deatils are correct and then provides a token
		try {
			Authentication auth = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			String token = tokenService.generateJwt(auth);

			return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);
		}

		catch (AuthenticationException e) {
			return new LoginResponseDTO(null, "");
		}

	}

}
