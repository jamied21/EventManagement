package com.example.EventManagement.Models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Particpants")
public class Participant {

	@Id
	@SequenceGenerator(name = "USER_ID_GEN", sequenceName = "users_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_GEN")
	private Integer id;

	private String userName;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_FEEDBACK_ID")
	private Feedback feedback;

	@OneToMany(mappedBy = "participants")
	private List<Event> events;

	public Participant(String userName) {
		super();

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

}
