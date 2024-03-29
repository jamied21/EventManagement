package com.example.EventManagement.Controllers;

import java.util.HashMap;
import java.util.Map;

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

import com.example.EventManagement.Models.Feedback;
import com.example.EventManagement.Models.Register;
import com.example.EventManagement.Services.IFeedbackService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/feedback")
@CrossOrigin(origins = "http://localhost:3000")
public class FeedbackController {

	private IFeedbackService feedbackService;

	public FeedbackController(IFeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	@Operation(summary = "Creates a new Feedback")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Feedback resource successfully created.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "400", description = "Feedback resource has invalid field(s).") })

	@PostMapping
	public ResponseEntity<?> saveFeedback(@Valid @RequestBody Feedback feedback, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {

				errors.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(this.feedbackService.saveFeedback(feedback), HttpStatus.CREATED);

	}

	@Operation(summary = "Retrieves an Feedback resource(s) from the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Feedback resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })

	@GetMapping
	public ResponseEntity<?> getAllFeedback() {
		return new ResponseEntity<>(this.feedbackService.getAllFeedbacks(), HttpStatus.OK);
	}

	@Operation(summary = "Retrieves an Feedback resource from the database with the feedback id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Event Feedback successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Feedback found for that id.") })

	@GetMapping("/{id}")
	public ResponseEntity<?> getFeedbackById(@PathVariable Integer id)

	{
		Feedback result = this.feedbackService.findFeedbackById(id);
		if (result == null) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@Operation(summary = "Deletes an Feedback resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Feedback resource successfully deleted.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Feedback found for that id.") })

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFeedbackById(@PathVariable Integer id) {

		boolean result = this.feedbackService.deleteFeedbackById(id);

		if (result) {

			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@Operation(summary = "Updates new Feedback")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Feedback resource successfully updated and returned.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Feedback found for that id.") })

	@PutMapping("/{id}")
	public ResponseEntity<?> updateFeedbackById(@PathVariable Integer id, @RequestBody Feedback feedback) {

		boolean result = this.feedbackService.updateFeedbackById(id, feedback);

		if (result) {
			return new ResponseEntity<>(feedback, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@Operation(summary = "Retrieves an Register resource(s) from the database based on register id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Register resource(s) successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Register found for that id.") })

	@GetMapping("/register/{registerId}")
	public Register findRegisterAndEventById(@PathVariable Integer registerId) {
		return feedbackService.findRegisterAndEventById(registerId);
	}
}
