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

import com.example.EventManagement.Models.Event;
import com.example.EventManagement.Services.IEventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

	private IEventService eventService;

	public EventController(IEventService eventService) {
		this.eventService = eventService;
	}

	@PostMapping
	public ResponseEntity<?> saveEvent(@Valid @RequestBody Event event, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {

				errors.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

		}

		return new ResponseEntity<>(this.eventService.saveEvent(event), HttpStatus.CREATED);

	}

	@GetMapping
	public ResponseEntity<?> getAllEvent() {
		return new ResponseEntity<>(this.eventService.getAllEvents(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getEventById(@PathVariable Integer id)

	{
		Event result = this.eventService.findEventById(id);
		if (result == null) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEventById(@PathVariable Integer id) {

		boolean result = this.eventService.deleteEventById(id);

		if (result) {

			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateEventById(@PathVariable Integer id, @RequestBody Event event) {

		boolean result = this.eventService.updateEventById(id, event);

		if (result) {
			return new ResponseEntity<>(event, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

}
