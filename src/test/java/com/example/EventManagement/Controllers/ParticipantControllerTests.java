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

import com.example.EventManagement.Models.Participant;
import com.example.EventManagement.Services.ParticipantServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ParticipantController.class)
class ParticipantControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper; // needed to convert to JSON object

	private Participant participant;

	@MockBean
	private ParticipantServiceImp mockParticipantService;

	@BeforeEach
	void setUp() throws Exception {
		participant = new Participant("JD2024");
		participant.setId(1);
	}

	@Test
	@DisplayName("FindAllParticipants")
	public void givenNothing_whenFindAllParticipant_thenReturnAllSavedParticipant() throws Exception {
		List<Participant> participants = List.of(participant);
		when(mockParticipantService.getAllParticipants()).thenReturn(participants);

		mockMvc.perform(get("/api/v1/participant")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].userName", is(participant.getUserName())));

		verify(mockParticipantService, times(1)).getAllParticipants();

	}

	@Test
	@DisplayName("Delete participant - positive")
	public void givenParticipantId_whenDeleteParticipantById_thenReturnGone() throws Exception {
		// given
		Integer id = 1;
		given(mockParticipantService.deleteParticipantById(id)).willReturn(true);

		// when-then
		mockMvc.perform(delete("/api/v1/participant/1")).andDo(print()).andExpect(status().isOk());

		verify(mockParticipantService, times(1)).deleteParticipantById(id);
	}

	@Test
	@DisplayName("Delete participant - negative")
	public void givenNonExistentParticipantId_whenDeleteParticipantById_thenReturnNotFound() throws Exception {
		// given
		Integer id = 2;
		given(mockParticipantService.deleteParticipantById(id)).willReturn(false);

		// when-then
		mockMvc.perform(delete("/api/v1/participant/2")).andDo(print()).andExpect(status().isNotFound());

		verify(mockParticipantService, times(1)).deleteParticipantById(id);

	}

	@Test
	@DisplayName("findParticipantByID--positive")
	void givenParticipantId_whenFindParticipantById_thenReturnParticipantObjectFromDB() throws Exception {
		// arrange
		given(mockParticipantService.findParticipantById(1)).willReturn(participant);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/participant/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.userName", is(participant.getUserName())));

		// @Formatter: on

		verify(mockParticipantService, times(1)).findParticipantById(1);

	}

	@Test
	@DisplayName("findParticipantByID--negative")
	void givenParticipantId_whenFindParticipantById_thenReturnError() throws Exception {
		// arrange
		given(mockParticipantService.findParticipantById(2)).willReturn(null);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/participant/2")).andDo(print()).andExpect(status().isNotFound());

		// @Formatter: on

		verify(mockParticipantService, times(1)).findParticipantById(2);

	}

	@Test
	public void testUpdateParticipantById() throws Exception {
	    when(mockParticipantService.updateParticipantById(eq(1), any(Participant.class))).thenReturn(true);

	    mockMvc.perform(put("/api/v1/participant/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(participant)))
	            .andExpect(status().isOk());
	}

	@Test
	public void testUpdateParticipantByIdNotFound() throws Exception {
	    when(mockParticipantService.updateParticipantById(1, participant)).thenReturn(false);

	    mockMvc.perform(put("/api/v1/participant/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(participant)))
	            .andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("saveParticipant-Positive")
	public void givenParticipantObject_whenSaveParticipant_thenReturnSavedParticipant() throws Exception {
		// given
		given(mockParticipantService.saveParticipant(ArgumentMatchers.any(Participant.class))).willReturn(participant);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/participant")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(participant)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.userName", is(participant.getUserName())));
		
		// @formatter:on

		verify(mockParticipantService, times(1)).saveParticipant(ArgumentMatchers.any(Participant.class));
	}

	@Test
	@DisplayName("saveParticipant-Negative")
	public void givenParticipantObject_whenSaveParticipant_thenReturnError() throws Exception {
		// given
		participant.setUserName(null);
		given(mockParticipantService.saveParticipant(ArgumentMatchers.any(Participant.class))).willReturn(participant);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/participant")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(participant)))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
		// @formatter:on

		verify(mockParticipantService, times(0)).saveParticipant(ArgumentMatchers.any(Participant.class));
	}

	@AfterEach
	void tearDown() throws Exception {
		participant = null;
	}

}
