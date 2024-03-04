package com.example.EventManagement.Controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.example.EventManagement.Models.User;
import com.example.EventManagement.Services.IRegisterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/register")
@CrossOrigin(origins = "http://localhost:3000")
public class RegisterController {

	private IRegisterService registerService;

	public RegisterController(IRegisterService registerService) {
		this.registerService = registerService;
	}

	@Operation(summary = "Creates a new Register")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Register resource successfully created.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "400", description = "Register resource has invalid field(s).") })

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

	@Operation(summary = "Retrieves an Register resource(s) from the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Register resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })

	@GetMapping
	public ResponseEntity<?> getAllRegister() {
		return new ResponseEntity<>(this.registerService.getAllRegisters(), HttpStatus.OK);
	}

	@Operation(summary = "Retrieves an Register resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Register resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Register found for that id.") })

	@GetMapping("/{id}")
	public ResponseEntity<?> getRegisterById(@PathVariable Integer id)

	{
		Register result = this.registerService.findRegisterById(id);
		if (result == null) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@PostMapping("/event/{eventId}")
	public ResponseEntity<?> registerUsersForEvent(@PathVariable Integer eventId, @RequestBody Integer[] userIds) {
		List<Register> registers = this.registerService.registerUsersToEvent(eventId,
				Arrays.stream(userIds).collect(Collectors.toSet()));
		return new ResponseEntity<>(registers, HttpStatus.OK);
	}

	@Operation(summary = "Deletes an User resource(s) from an specific event based on the user id and event id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User resource(s) successfully deleted from event.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No User found for that id.") })

	@DeleteMapping("/event/{eventId}/user/{userId}")
	public ResponseEntity<?> unregisterUsersForEvent(@PathVariable Integer eventId, @PathVariable Integer userId) {
		return null;
	}

	@Operation(summary = "Deletes an Register resource from the database based on the register id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Event resource successfully deleted.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Expense found for that id.") })

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRegisterById(@PathVariable Integer id) {
		boolean result = this.registerService.deleteRegisterById(id);
		if (result) {

			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@Operation(summary = "Updates new Register")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Register resource successfully updated and returned.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Register found for that id.") })

	@PutMapping("/{id}")
	public ResponseEntity<?> updateRegisterById(@PathVariable Integer id, @RequestBody Register register) {

		boolean result = this.registerService.updateRegisterById(id, register);

		if (result) {
			return new ResponseEntity<>(register, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@Operation(summary = "Retrieves an Register resource from the database based on the user id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Register resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Register found for that id.") })

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Register>> getRegistrationsByUserId(@PathVariable Integer userId) {
		List<Register> registrations = registerService.findRegistrationsByUserId(userId);
		return new ResponseEntity<>(registrations, HttpStatus.OK);
	}

	@Operation(summary = "Retrieves an Register resource of users who have attended an event. Based on the event id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Register resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Register found for that id.") })

	@GetMapping("/attended-users/{eventId}")
	public List<User> getAttendedUsersByEvent(@PathVariable Integer eventId) {
		return registerService.getAttendingUsersForEvent(eventId);
	}

}
