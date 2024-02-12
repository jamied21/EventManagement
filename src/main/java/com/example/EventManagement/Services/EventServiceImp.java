package com.example.EventManagement.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EventManagement.Models.Event;
import com.example.EventManagement.Repository.EventRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class EventServiceImp implements IEventService {

	@Autowired
	private EventRepository eventRepository;

	public EventServiceImp(EventRepository eventRepository) {

		this.eventRepository = eventRepository;
	}

	public Event saveEvent(Event event) {
		return this.eventRepository.save(event);

	}

	public List<Event> getAllEvents() {
		return this.eventRepository.findAll();
	}

	public Event findEventById(Integer id) {
		return this.eventRepository.findById(id).orElse(null);
	}

	public boolean updateEventById(Integer id, Event event) {
		boolean result = this.eventRepository.existsById(id);

		if (result)

		{
			this.eventRepository.save(event);
			return true;
		}

		return false;

	}

	public boolean deleteEventById(Integer id) {

		boolean result = this.eventRepository.existsById(id);

		if (result)

		{
			this.eventRepository.deleteById(id);
			return true;
		}

		return false;
	}

}
