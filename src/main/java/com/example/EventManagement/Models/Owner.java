package com.example.EventManagement.Models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Owners")
public class Owner {
	@Id
	@SequenceGenerator(name = "OWNER_ID_GEN", sequenceName = "Owner_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OWNER_ID_GEN")
	private Integer id;

	@NotBlank(message = "Provide an Owner Name")
	private String ownerName;

	@OneToMany(mappedBy = "owner")
	private List<Event> events;

	public Owner(String ownerName) {

		this.ownerName = ownerName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
