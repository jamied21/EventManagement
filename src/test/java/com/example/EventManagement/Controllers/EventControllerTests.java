package com.example.EventManagement.Controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.EventManagement.Models.Event;
import com.example.EventManagement.Services.EventServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EventController.class)
class EventControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper; // needed to convert to JSON object

	private Event event;

	@MockBean
	private EventServiceImp mockEventService;

	@BeforeEach
	void setUp() throws Exception {
		event = new Event("WWE RAW XIV", LocalDateTime.of(2024, 2, 21, 15, 30), "London");
		event.setId(1);
	}

	@Test
	@DisplayName("FindAllEvents")
	public void givenNothing_whenFindAllEvent_thenReturnAllSavedEvent() throws Exception {
		List<Event> events = List.of(event);
		when(mockEventService.getAllEvents()).thenReturn(events);

		mockMvc.perform(get("/api/v1/event")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1))).andExpect(jsonPath("$[0].name", is(event.getName())));

		verify(mockEventService, times(1)).getAllEvents();

	}

	@Test
	@DisplayName("Delete event - positive")
	public void givenEventId_whenDeleteEventById_thenReturnGone() throws Exception {
		// given
		Integer id = 1;
		given(mockEventService.deleteEventById(id)).willReturn(true);

		// when-then
		mockMvc.perform(delete("/api/v1/event/1")).andDo(print()).andExpect(status().isOk());

		verify(mockEventService, times(1)).deleteEventById(id);
	}

	@Test
	@DisplayName("Delete event - negative")
	public void givenNonExistentEventId_whenDeleteEventById_thenReturnNotFound() throws Exception {
		// given
		Integer id = 2;
		given(mockEventService.deleteEventById(id)).willReturn(false);

		// when-then
		mockMvc.perform(delete("/api/v1/event/2")).andDo(print()).andExpect(status().isNotFound());

		verify(mockEventService, times(1)).deleteEventById(id);

	}

	@Test
	@DisplayName("findEventByID--positive")
	void givenEventId_whenFindEventById_thenReturnEventObjectFromDB() throws Exception {
		// arrange
		given(mockEventService.findEventById(1)).willReturn(event);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/event/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(event.getName())));

		// @Formatter: on

		verify(mockEventService, times(1)).findEventById(1);

	}

	@Test
	@DisplayName("findEventByID--negative")
	void givenEventId_whenFindEventById_thenReturnError() throws Exception {
		// arrange
		given(mockEventService.findEventById(2)).willReturn(null);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/event/2")).andDo(print()).andExpect(status().isNotFound());

		// @Formatter: on

		verify(mockEventService, times(1)).findEventById(2);

	}

	@Test
	public void testUpdateEventById() throws Exception {
	    when(mockEventService.updateEventById(eq(1), any(Event.class))).thenReturn(true);

	    mockMvc.perform(put("/api/v1/event/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(event)))
	            .andExpect(status().isOk());
	}

	@Test
	public void testUpdateEventByIdNotFound() throws Exception {
	    when(mockEventService.updateEventById(1, event)).thenReturn(false);

	    mockMvc.perform(put("/api/v1/event/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(event)))
	            .andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("saveEvent-Positive")
	public void givenEventObject_whenSaveEvent_thenReturnSavedEvent() throws Exception {
		// given
		given(mockEventService.saveEvent(ArgumentMatchers.any(Event.class))).willReturn(event);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/event")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(event)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is(event.getName())));
		
		// @formatter:on

		verify(mockEventService, times(1)).saveEvent(ArgumentMatchers.any(Event.class));
	}

	@Test
	@DisplayName("saveEvent-Negative")
	public void givenEventObject_whenSaveEvent_thenReturnError() throws Exception {
		// given
		event.setName(null);
		given(mockEventService.saveEvent(ArgumentMatchers.any(Event.class))).willReturn(event);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/event")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(event)))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
		// @formatter:on

		verify(mockEventService, times(0)).saveEvent(ArgumentMatchers.any(Event.class));
	}

	@AfterEach
	void tearDown() throws Exception {
		event = null;
	}

}
