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

import com.example.EventManagement.Models.Participant;
import com.example.EventManagement.Services.IParticipantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/participant")
public class ParticipantController {
	private IParticipantService participantService;

	public ParticipantController(IParticipantService participantService) {
		this.participantService = participantService;
	}

	@PostMapping
	public ResponseEntity<?> saveParticipant(@Valid @RequestBody Participant participant, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {

				errors.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(this.participantService.saveParticipant(participant), HttpStatus.CREATED);

	}

	@GetMapping
	public ResponseEntity<?> getAllParticipant() {
		return new ResponseEntity<>(this.participantService.getAllParticipants(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getParticipantById(@PathVariable Integer id)

	{
		Participant result = this.participantService.findParticipantById(id);
		if (result == null) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteParticipantById(@PathVariable Integer id) {

		boolean result = this.participantService.deleteParticipantById(id);

		if (result) {

			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateParticipantById(@PathVariable Integer id, @RequestBody Participant participant) {

		boolean result = this.participantService.updateParticipantById(id, participant);

		if (result) {
			return new ResponseEntity<>(participant, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

}
