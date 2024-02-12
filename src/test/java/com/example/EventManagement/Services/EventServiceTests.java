package com.example.EventManagement.Services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.EventManagement.Models.Event;
import com.example.EventManagement.Repository.EventRepository;

@ExtendWith(MockitoExtension.class)
class EventServiceTests {

	@InjectMocks
	private EventServiceImp eventServiceImp;

	@Mock
	private EventRepository mockEventRepository;

	@Mock
	private Event event;

	@BeforeEach
	void setUp() throws Exception {
		event = new Event("WWE RAW XIV", LocalDateTime.of(2024, 2, 21, 15, 30), "London");
		event.setId(1);

	}

	@Test
	@DisplayName("Save Event")
	void arrangeEventObject_actEvent_assertCheckEventSaveInDB() {
		
		//arrange
		when(mockEventRepository.save(event)).thenReturn(event);
		//act
		Event result = eventServiceImp.saveEvent(event);
		
		//assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(event.getId());
		assertThat(result.getEventDate()).isEqualTo(event.getEventDate());
		assertThat(result.getName()).isEqualTo(event.getName());
		verify(mockEventRepository,times(1)).save(event);

	}

	@Test
	@DisplayName("Find Event By ID")
	void arrangeEventObject_actfindEvent_assertCheckEventIsCorrect() {
		Optional<Event> optionalEvent = Optional.of(event);
		Integer id = 1;
		// arrange
		when(mockEventRepository.findById(id)).thenReturn(optionalEvent);
		// act
		Event result = eventServiceImp.findEventById(id);

		// assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(event.getId());
		assertThat(result.getEventDate()).isEqualTo(event.getEventDate());
		assertThat(result.getName()).isEqualTo(event.getName());
		verify(mockEventRepository, times(1)).findById(id);

	}

	@Test
	@DisplayName("Delete Event By ID True")
	void arrangeEventObject_actDeleteEvent_assertCheckEventIsDeletedReturnTrue() {

		Integer id = 1;

		// arrange
		when(mockEventRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = eventServiceImp.deleteEventById(id);

		// assert
		assertThat(result).isEqualTo(true);
		verify(mockEventRepository, times(1)).deleteById(id);

	}

	@Test
	@DisplayName("Delete Event By ID False")
	void arrangeEventObject_actDeleteEvent_assertCheckReturnFalse() {

		Integer id = 20;

		// arrange
		when(mockEventRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = eventServiceImp.deleteEventById(id);

		// assert
		assertThat(result).isEqualTo(false);
		verify(mockEventRepository, times(0)).deleteById(id);

	}

	@Test
	@DisplayName("Get all Events")
	void arrangeEventList_actFindEvent_assertGetEventList() {

		List<Event> events = new ArrayList<>();
		events.add(event);
		// arrange
		when(mockEventRepository.findAll()).thenReturn(events);

		// act

		List<Event> result = eventServiceImp.getAllEvents();
		// assert
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getName()).isEqualTo(event.getName());
		verify(mockEventRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Update Event By ID True")
	void arrangeEventObject_actUpdateEvent_assertCheckUpdateOccurs() {

		Integer id = 1;

		// arrange
		when(mockEventRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = eventServiceImp.updateEventById(id, event);

		// assert
		assertThat(result).isEqualTo(true);
		verify(mockEventRepository, times(1)).save(event);

	}

	@Test
	@DisplayName("Update Event By ID False")
	void arrangeEventObject_actUpdateEvent_assertCheckUpdateDoesNotOccur() {

		Integer id = 1;

		// arrange
		when(mockEventRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = eventServiceImp.updateEventById(id, event);

		// assert
		assertThat(result).isEqualTo(false);
		verify(mockEventRepository, times(0)).save(event);

	}

	@AfterEach
	void tearDown() throws Exception {

		event = null;

	}

}
