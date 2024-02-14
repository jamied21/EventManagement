package com.example.EventManagement.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Events")
public class Event {

	@Id
	@SequenceGenerator(name = "EVENT_ID_GEN", sequenceName = "event_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENT_ID_GEN")
	private Integer id;

	@NotBlank(message = "Please add an Event name")
	private String name;

	@NotNull(message = "Please add a Event Time")
	private LocalDateTime eventDate;

	@NotBlank(message = "Please add a Location")
	private String location;

	@OneToOne
	@JoinColumn(name = "FK_ORGANISER_ID")
	private User organiser;

	@ManyToOne
	@JoinColumn(name = "FK_PARTICIPANT_ID")
	private User participants;

	public Event() {

	}

	public Event(String name, LocalDateTime eventDate, String location) {

		this.name = name;
		this.eventDate = eventDate;
		this.location = location;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDateTime eventDate) {
		this.eventDate = eventDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
