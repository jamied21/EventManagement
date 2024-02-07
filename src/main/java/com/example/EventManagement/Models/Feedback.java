package com.example.EventManagement.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Feedback")
public class Feedback {

	@Id
	@SequenceGenerator(name = "Feedback_ID_GEN", sequenceName = "Feedback_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Feedback_ID_GEN")
	private Integer id;

	@NotBlank(message = "Please provide comment")
	private String comments;
	@NotBlank(message = "Please provide a rating")
	private Integer rating;

	public Feedback(String comments, Integer rating) {
		super();
		this.comments = comments;
		this.rating = rating;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

}
