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

import com.example.EventManagement.Models.Feedback;
import com.example.EventManagement.Services.FeedbackServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(FeedbackController.class)
class FeedbackControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper; // needed to convert to JSON object

	private Feedback feedback;

	@MockBean
	private FeedbackServiceImp mockFeedbackService;

	@BeforeEach
	void setUp() throws Exception {
		feedback = new Feedback("Comment1", 5);
		feedback.setId(1);
	}

	@Test
	@DisplayName("FindAllFeedbacks")
	public void givenNothing_whenFindAllFeedback_thenReturnAllSavedFeedback() throws Exception {
		List<Feedback> feedbacks = List.of(feedback);
		when(mockFeedbackService.getAllFeedbacks()).thenReturn(feedbacks);

		mockMvc.perform(get("/api/v1/feedback")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].comment", is(feedback.getComment())));

		verify(mockFeedbackService, times(1)).getAllFeedbacks();

	}

	@Test
	@DisplayName("Delete feedback - positive")
	public void givenFeedbackId_whenDeleteFeedbackById_thenReturnGone() throws Exception {
		// given
		Integer id = 1;
		given(mockFeedbackService.deleteFeedbackById(id)).willReturn(true);

		// when-then
		mockMvc.perform(delete("/api/v1/feedback/1")).andDo(print()).andExpect(status().isOk());

		verify(mockFeedbackService, times(1)).deleteFeedbackById(id);
	}

	@Test
	@DisplayName("Delete feedback - negative")
	public void givenNonExistentFeedbackId_whenDeleteFeedbackById_thenReturnNotFound() throws Exception {
		// given
		Integer id = 2;
		given(mockFeedbackService.deleteFeedbackById(id)).willReturn(false);

		// when-then
		mockMvc.perform(delete("/api/v1/feedback/2")).andDo(print()).andExpect(status().isNotFound());

		verify(mockFeedbackService, times(1)).deleteFeedbackById(id);

	}

	@Test
	@DisplayName("findFeedbackByID--positive")
	void givenFeedbackId_whenFindFeedbackById_thenReturnFeedbackObjectFromDB() throws Exception {
		// arrange
		given(mockFeedbackService.findFeedbackById(1)).willReturn(feedback);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/feedback/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.comment", is(feedback.getComment())));

		// @Formatter: on

		verify(mockFeedbackService, times(1)).findFeedbackById(1);

	}

	@Test
	@DisplayName("findFeedbackByID--negative")
	void givenFeedbackId_whenFindFeedbackById_thenReturnError() throws Exception {
		// arrange
		given(mockFeedbackService.findFeedbackById(2)).willReturn(null);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/feedback/2")).andDo(print()).andExpect(status().isNotFound());

		// @Formatter: on

		verify(mockFeedbackService, times(1)).findFeedbackById(2);

	}

	@Test
	public void testUpdateFeedbackById() throws Exception {
	    when(mockFeedbackService.updateFeedbackById(eq(1), any(Feedback.class))).thenReturn(true);

	    mockMvc.perform(put("/api/v1/feedback/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(feedback)))
	            .andExpect(status().isOk());
	}

	@Test
	public void testUpdateFeedbackByIdNotFound() throws Exception {
	    when(mockFeedbackService.updateFeedbackById(1, feedback)).thenReturn(false);

	    mockMvc.perform(put("/api/v1/feedback/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(feedback)))
	            .andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("saveFeedback-Positive")
	public void givenFeedbackObject_whenSaveFeedback_thenReturnSavedFeedback() throws Exception {
		// given
		given(mockFeedbackService.saveFeedback(ArgumentMatchers.any(Feedback.class))).willReturn(feedback);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/feedback")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(feedback)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.comment", is(feedback.getComment())));
		
		// @formatter:on

		verify(mockFeedbackService, times(1)).saveFeedback(ArgumentMatchers.any(Feedback.class));
	}

	@Test
	@DisplayName("saveFeedback-Negative")
	public void givenFeedbackObject_whenSaveFeedback_thenReturnError() throws Exception {
		// given
		feedback.setRating(null);
		given(mockFeedbackService.saveFeedback(ArgumentMatchers.any(Feedback.class))).willReturn(feedback);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/feedback")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(feedback)))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
		// @formatter:on

		verify(mockFeedbackService, times(0)).saveFeedback(ArgumentMatchers.any(Feedback.class));
	}

	@AfterEach
	void tearDown() throws Exception {
		feedback = null;
	}

}
