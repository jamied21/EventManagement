package com.example.EventManagement.Models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Register {

	@Id
	@SequenceGenerator(name = "REGISTER_ID_GEN", sequenceName = "register_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REGISTER_ID_GEN")
	private Integer id;

	// True if attended,false if not
	private Boolean attended;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_PARTICIPANT_ID")
	private Participant particpant;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_EVENT_ID")
	private Event event;

	public Register(Boolean attended) {
		super();
		this.attended = attended;
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

	public Participant getParticpant() {
		return particpant;
	}

	public void setParticpant(Participant particpant) {
		this.particpant = particpant;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}