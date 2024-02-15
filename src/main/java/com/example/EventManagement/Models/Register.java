package com.example.EventManagement.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;

@Entity
public class Register {

	@Id
	@SequenceGenerator(name = "REGISTER_ID_GEN", sequenceName = "register_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REGISTER_ID_GEN")
	private Integer id;

	// True if attended,false if not
	@NotNull(message = "Please add attendance")
	private Boolean attended;

	@NotNull(message = "Please add a Registration Time")
	private LocalDateTime regsitrationTime;

	// Can be nulllable
	private LocalDateTime checkInTime;

	@ManyToOne
	@JoinColumn(name = "FK_EVENT_ID")
	private Event event;

	@ManyToOne
	@JoinColumn(name = "FK_USER_ID")
	private User users;

	public Register() {

	}

	public Register(Boolean attended, LocalDateTime regsitrationTime, LocalDateTime checkInTime) {

		this.attended = attended;
		this.regsitrationTime = regsitrationTime;
		this.checkInTime = checkInTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getAttended() {
		return attended;
	}

	public void setAttended(Boolean attended) {
		this.attended = attended;
	}

	public LocalDateTime getRegsitrationTime() {
		return regsitrationTime;
	}

	public void setRegsitrationTime(LocalDateTime regsitrationTime) {
		this.regsitrationTime = regsitrationTime;
	}

	public LocalDateTime getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(LocalDateTime checkInTime) {
		this.checkInTime = checkInTime;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
