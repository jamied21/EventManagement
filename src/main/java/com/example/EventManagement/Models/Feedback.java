package com.example.EventManagement.Models;

import jakarta.persistence.CascadeType;
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
@Table(name = "Feedback")
public class Feedback {

	@Id
	@SequenceGenerator(name = "FEEDBACK_ID_GEN", sequenceName = "Feedback_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FEEDBACK_ID_GEN")
	private Integer id;

	@NotBlank(message = "Please provide comment")
	private String comment;

	@NotNull(message = "Please provide a rating")
	private Integer rating;

	@ManyToOne
	@JoinColumn(name = "FK_EVENT_ID")
	private Event event;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_PARTICIPANT_ID")
	private Participant participant;

	public Feedback(String comment, Integer rating) {

		this.comment = comment;
		this.rating = rating;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

}
