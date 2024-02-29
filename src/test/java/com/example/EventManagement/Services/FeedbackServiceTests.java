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
import com.example.EventManagement.Models.Feedback;
import com.example.EventManagement.Models.Register;
import com.example.EventManagement.Repository.FeedbackRepository;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTests {

	@InjectMocks
	private FeedbackServiceImp feedbackServiceImp;

	@Mock
	private FeedbackRepository mockFeedbackRepository;

	@Mock
	private Feedback feedback;

	@Mock
	private Event event;

	@Mock
	private Register register;

	@BeforeEach
	void setUp() throws Exception {
		feedback = new Feedback("Comment1", 5);

		event = new Event("WWE RAW XIV", LocalDateTime.of(2024, 2, 21, 15, 30), "London");
		event.setId(1);

		register = new Register(true, LocalDateTime.of(2024, 2, 21, 15, 30), LocalDateTime.of(2025, 2, 21, 15, 30));
		register.setId(1);

		register.setEvent(event);
		feedback.setRegister(register);

	}

	@Test
	@DisplayName("Save Feedback")
	void arrangeFeedbackObject_actFeedback_assertCheckFeedbackSaveInDB() {
		
		//arrange
		when(mockFeedbackRepository.save(feedback)).thenReturn(feedback);
		//act
		Feedback result = feedbackServiceImp.saveFeedback(feedback);
		
		//assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(feedback.getId());
		assertThat(result.getComment()).isEqualTo(feedback.getComment());
		assertThat(result.getRating()).isEqualTo(feedback.getRating());
		verify(mockFeedbackRepository,times(1)).save(feedback);

	}

	@Test
	@DisplayName("Find Feedback By ID")
	void arrangeFeedbackObject_actfindFeedback_assertCheckFeedbackIsCorrect() {
		Optional<Feedback> optionalFeedback = Optional.of(feedback);
		Integer id = 1;
		// arrange
		when(mockFeedbackRepository.findById(id)).thenReturn(optionalFeedback);
		// act
		Feedback result = feedbackServiceImp.findFeedbackById(id);

		// assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(feedback.getId());
		assertThat(result.getComment()).isEqualTo(feedback.getComment());
		assertThat(result.getRating()).isEqualTo(feedback.getRating());
		verify(mockFeedbackRepository, times(1)).findById(id);

	}

	@Test
	@DisplayName("Delete Feedback By ID True")
	void arrangeFeedbackObject_actDeleteFeedback_assertCheckFeedbackIsDeletedReturnTrue() {

		Integer id = 1;

		// arrange
		when(mockFeedbackRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = feedbackServiceImp.deleteFeedbackById(id);

		// assert
		assertThat(result).isEqualTo(true);
		verify(mockFeedbackRepository, times(1)).deleteById(id);

	}

	@Test
	@DisplayName("Delete Feedback By ID False")
	void arrangeFeedbackObject_actDeleteFeedback_assertCheckReturnFalse() {

		Integer id = 20;

		// arrange
		when(mockFeedbackRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = feedbackServiceImp.deleteFeedbackById(id);

		// assert
		assertThat(result).isEqualTo(false);
		verify(mockFeedbackRepository, times(0)).deleteById(id);

	}

	@Test
	@DisplayName("Get all Feedbacks")
	void arrangeFeedbackList_actFindFeedback_assertGetFeedbackList() {

		List<Feedback> feedbacks = new ArrayList<>();
		feedbacks.add(feedback);
		// arrange
		when(mockFeedbackRepository.findAll()).thenReturn(feedbacks);

		// act

		List<Feedback> result = feedbackServiceImp.getAllFeedbacks();
		// assert
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getComment()).isEqualTo(feedback.getComment());
		verify(mockFeedbackRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Update Feedback By ID True")
	void arrangeFeedbackObject_actUpdateFeedback_assertCheckUpdateOccurs() {

		Integer id = 1;

		// arrange
		when(mockFeedbackRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = feedbackServiceImp.updateFeedbackById(id, feedback);

		// assert
		assertThat(result).isEqualTo(true);
		verify(mockFeedbackRepository, times(1)).save(feedback);

	}

	@Test
	@DisplayName("Update Feedback By ID False")
	void arrangeFeedbackObject_actUpdateFeedback_assertCheckUpdateDoesNotOccur() {

		Integer id = 1;

		// arrange
		when(mockFeedbackRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = feedbackServiceImp.updateFeedbackById(id, feedback);

		// assert
		assertThat(result).isEqualTo(false);
		verify(mockFeedbackRepository, times(0)).save(feedback);

	}

	@Test
	@DisplayName("Find Register by Feedback Id")
	void testFindRegisterAndEventById_ValidRegisterId_ReturnsRegister() {
		// Arrange
		Integer registerId = 1;
		when(mockFeedbackRepository.findRegisterById(registerId)).thenReturn(register);

		// Act
		Register result = feedbackServiceImp.findRegisterAndEventById(registerId);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(register.getId());
		verify(mockFeedbackRepository, times(1)).findRegisterById(registerId);
	}

	@AfterEach
	void tearDown() throws Exception {

		feedback = null;

	}

}
