package com.example.EventManagement.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EventManagement.Models.User;
import com.example.EventManagement.Services.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	private IUserService userService;

	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<?> saveOwner(@Valid @RequestBody User user, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {

				errors.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(this.userService.saveOwner(user), HttpStatus.CREATED);

	}

	@GetMapping
	public ResponseEntity<?> getAllOwner() {
		return new ResponseEntity<>(this.userService.getAllOwners(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOwnerById(@PathVariable Integer id)

	{
		User result = this.userService.findOwnerById(id);
		if (result == null) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOwnerById(@PathVariable Integer id) {

		boolean result = this.userService.deleteOwnerById(id);

		if (result) {

			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateOwnerById(@PathVariable Integer id, @RequestBody User user) {

		boolean result = this.userService.updateOwnerById(id, user);

		if (result) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

}
