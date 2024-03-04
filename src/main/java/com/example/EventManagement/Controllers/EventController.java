package com.example.EventManagement.Controllers;

import java.util.HashMap;
import java.util.List;
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

import com.example.EventManagement.Models.Event;
import com.example.EventManagement.Services.IEventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/event")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

	private IEventService eventService;

	public EventController(IEventService eventService) {
		this.eventService = eventService;
	}

	@Operation(summary = "Creates a new Event")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Event resource successfully created.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "400", description = "Event resource has invalid field(s).") })

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

	@Operation(summary = "Retrieves an Event resource(s) from the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Event resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })

	@GetMapping
	public ResponseEntity<?> getAllEvent() {
		return new ResponseEntity<>(this.eventService.getAllEvents(), HttpStatus.OK);
	}

	@Operation(summary = "Retrieves an Event resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Event resource successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Event found for that id.") })

	@GetMapping("/{id}")
	public ResponseEntity<?> getEventById(@PathVariable Integer id)

	{
		Event result = this.eventService.findEventById(id);
		if (result == null) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@Operation(summary = "Deletes an Event resource from the database with the id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Event resource successfully deleted.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Expense found for that id.") })

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEventById(@PathVariable Integer id) {

		boolean result = this.eventService.deleteEventById(id);

		if (result) {

			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@Operation(summary = "Updates new Event")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Event resource successfully updated and returned.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Event found for that id.") })

	@PutMapping("/{id}")
	public ResponseEntity<?> updateEventById(@PathVariable Integer id, @RequestBody Event event) {

		boolean result = this.eventService.updateEventById(id, event);

		if (result) {
			return new ResponseEntity<>(event, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@Operation(summary = "Retrieves an Event resource(s) from the database based on organiser id that is given.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Event resource(s) successfully retrieved.", headers = {
					@Header(name = "location", description = "URI to access the created resource") }, content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "No Event found for that id.") })

	@GetMapping("/organiser/{organiserId}")
	public List<Event> getEventsByOrganiserId(@PathVariable Integer organiserId) {
		return eventService.findEventsByOrganiserId(organiserId);
	}

}
