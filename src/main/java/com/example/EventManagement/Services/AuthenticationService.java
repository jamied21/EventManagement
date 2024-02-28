
package com.example.EventManagement.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.EventManagement.Dto.JwtAuthenticationResponse;
import com.example.EventManagement.Dto.RegisterUserRequest;
import com.example.EventManagement.Dto.SignInUserRequest;
import com.example.EventManagement.Jwt.JwtService;
import com.example.EventManagement.Models.User;
import com.example.EventManagement.Repository.UserRepository;

@Service
public class AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public JwtAuthenticationResponse signup(RegisterUserRequest request) {

		// Check user name already taken
		User userOpt = userRepository.findUserByUsername(request.getUsername());
		if (userOpt == null) {
			System.out.println("User with username {} already exist" + request.getUsername());
			throw new RuntimeException("User with username" + request.getUsername() + " already exist");
		}

		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		userRepository.save(user);

		String jwt = jwtService.generateToken(user);
		return new JwtAuthenticationResponse(jwt);
	}

	public JwtAuthenticationResponse signin(SignInUserRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		User user = userRepository.findUserByUsername(request.getUsername());

		String jwt = jwtService.generateToken(user);
		return new JwtAuthenticationResponse(jwt);
	}

}
