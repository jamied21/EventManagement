package com.example.EventManagement.Exceptions;

public class RegisterNotFoundException extends RuntimeException {
	public RegisterNotFoundException(String message) {
		super(message);
	}
}
