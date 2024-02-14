package com.example.EventManagement.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EventManagement.Models.Register;
import com.example.EventManagement.Services.IRegisterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/register")
@CrossOrigin(origins = "http://localhost:3000")
public class RegisterController {

	private IRegisterService registerService;

	public RegisterController(IRegisterService registerService) {
		this.registerService = registerService;
	}

	@PostMapping
	public ResponseEntity<?> saveRegister(@Valid @RequestBody Register register, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {

				errors.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(this.registerService.saveRegister(register), HttpStatus.CREATED);

	}

	@GetMapping
	public ResponseEntity<?> getAllRegister() {
		return new ResponseEntity<>(this.registerService.getAllRegisters(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getRegisterById(@PathVariable Integer id)

	{
		Register result = this.registerService.findRegisterById(id);
		if (result == null) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRegisterById(@PathVariable Integer id) {

		boolean result = this.registerService.deleteRegisterById(id);

		if (result) {

			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateRegisterById(@PathVariable Integer id, @RequestBody Register register) {

		boolean result = this.registerService.updateRegisterById(id, register);

		if (result) {
			return new ResponseEntity<>(register, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

}
