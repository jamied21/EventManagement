package com.example.EventManagement.Models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Participants")
public class Participant {

	@Id
	@SequenceGenerator(name = "PARTICIPANTS_ID_GEN", sequenceName = "Participants_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTICIPANTS_ID_GEN")
	private Integer id;

	@NotBlank(message = "Provide an User Name")
	private String userName;

	@OneToOne(mappedBy = "participant")
	private Feedback feedback;

	@OneToMany(mappedBy = "participants")
	private List<Event> events;

	public Participant() {

	}

	public Participant(String userName) {

		this.userName = userName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
