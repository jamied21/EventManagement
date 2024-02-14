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

import com.example.EventManagement.Models.Feedback;
import com.example.EventManagement.Services.IFeedbackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/feedback")
@CrossOrigin(origins = "http://localhost:3000")
public class FeedbackController {

	private IFeedbackService feedbackService;

	public FeedbackController(IFeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

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

	@GetMapping
	public ResponseEntity<?> getAllFeedback() {
		return new ResponseEntity<>(this.feedbackService.getAllFeedbacks(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getFeedbackById(@PathVariable Integer id)

	{
		Feedback result = this.feedbackService.findFeedbackById(id);
		if (result == null) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFeedbackById(@PathVariable Integer id) {

		boolean result = this.feedbackService.deleteFeedbackById(id);

		if (result) {

			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateFeedbackById(@PathVariable Integer id, @RequestBody Feedback feedback) {

		boolean result = this.feedbackService.updateFeedbackById(id, feedback);

		if (result) {
			return new ResponseEntity<>(feedback, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

}
