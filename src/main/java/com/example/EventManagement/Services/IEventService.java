package com.example.EventManagement.Services;

import java.util.List;

import com.example.EventManagement.Models.Event;

public interface IEventService {

	Event saveEvent(Event event);

	List<Event> getAllEvents();

	Event findEventById(Integer id);

	boolean updateEventById(Integer id, Event event);

	boolean deleteEventById(Integer id);
}
