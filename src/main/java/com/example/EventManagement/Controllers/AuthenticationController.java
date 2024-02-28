package com.example.EventManagement.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EventManagement.Dto.JwtAuthenticationResponse;
import com.example.EventManagement.Dto.RegisterUserRequest;
import com.example.EventManagement.Dto.SignInUserRequest;
import com.example.EventManagement.Jwt.JwtService;
import com.example.EventManagement.Services.AuthenticationService;
import com.example.EventManagement.Services.IUserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

	private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private IUserService userService;

	@Autowired
	private JwtService jwtService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody SignInUserRequest loginRequest) {

		JwtAuthenticationResponse jwtAuthResp = authenticationService.signin(loginRequest);
		return ResponseEntity.ok(jwtAuthResp);

	}

	@PostMapping("/register")
	public ResponseEntity<JwtAuthenticationResponse> registerUser(@RequestBody RegisterUserRequest signupReq) {

		JwtAuthenticationResponse jwtAuthResp = authenticationService.signup(signupReq);
		return ResponseEntity.ok(jwtAuthResp);

	}

	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationResponse> authenticateUser(HttpServletRequest httpRequest) {

		String jwtToken = jwtService.getJwtTokenFromRequest(httpRequest);
		String userName = jwtService.extractUserName(jwtToken);

		JwtAuthenticationResponse jwtAuthResp = new JwtAuthenticationResponse(
				jwtService.generateToken(userService.findByUsername(userName)));
		return ResponseEntity.ok(jwtAuthResp);

	}
}
