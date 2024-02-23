package com.example.EventManagement.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EventManagement.Dto.LoginResponseDTO;
import com.example.EventManagement.Dto.RegistrationDTO;
import com.example.EventManagement.Models.User;
import com.example.EventManagement.Services.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/register")
	public User registerUser(@RequestBody RegistrationDTO body) {
		return authenticationService.registerUser(body.getUsername(), body.getPassword());

	}

	@PostMapping("/login")
	public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body) {
		return authenticationService.loginUser(body.getUsername(), body.getPassword());
	}

}
