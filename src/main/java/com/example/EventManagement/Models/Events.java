package com.example.EventManagement.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Events")
public class Events {

	@Id
	@SequenceGenerator(name = "EVENT_ID_GEN", sequenceName = "event_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENT_ID_GEN")
	private Integer id;

	@NotBlank(message = "Please add an Event name")
	private String name;

	@NotBlank(message = "Please add an Owner name")
	private String owner;

	@NotBlank(message = "Please add a Event Time")
	private LocalDateTime eventDate;

	@NotBlank(message = "Please add a Location")
	private String location;

	// private String[] comments;

	public Events(String name, String owner, LocalDateTime eventDate, String location) {
		super();

		this.name = name;
		this.owner = owner;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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
