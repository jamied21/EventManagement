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

import com.example.EventManagement.Models.Owner;
import com.example.EventManagement.Services.OwnerServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(OwnerController.class)
class OwnerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper; // needed to convert to JSON object

	private Owner owner;

	@MockBean
	private OwnerServiceImp mockOwnerService;

	@BeforeEach
	void setUp() throws Exception {
		owner = new Owner("Jamie");
		owner.setOwnerName("Bob");
		owner.setId(1);
	}

	@Test
	@DisplayName("FindAllOwners")
	public void givenNothing_whenFindAllOwner_thenReturnAllSavedOwner() throws Exception {
		List<Owner> owners = List.of(owner);
		when(mockOwnerService.getAllOwners()).thenReturn(owners);

		mockMvc.perform(get("/api/v1/owner")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].ownerName", is(owner.getOwnerName())));

		verify(mockOwnerService, times(1)).getAllOwners();

	}

	@Test
	@DisplayName("Delete owner - positive")
	public void givenOwnerId_whenDeleteOwnerById_thenReturnGone() throws Exception {
		// given
		Integer id = 1;
		given(mockOwnerService.deleteOwnerById(id)).willReturn(true);

		// when-then
		mockMvc.perform(delete("/api/v1/owner/1")).andDo(print()).andExpect(status().isOk());

		verify(mockOwnerService, times(1)).deleteOwnerById(id);
	}

	@Test
	@DisplayName("Delete owner - negative")
	public void givenNonExistentOwnerId_whenDeleteOwnerById_thenReturnNotFound() throws Exception {
		// given
		Integer id = 2;
		given(mockOwnerService.deleteOwnerById(id)).willReturn(false);

		// when-then
		mockMvc.perform(delete("/api/v1/owner/2")).andDo(print()).andExpect(status().isNotFound());

		verify(mockOwnerService, times(1)).deleteOwnerById(id);

	}

	@Test
	@DisplayName("findOwnerByID--positive")
	void givenOwnerId_whenFindOwnerById_thenReturnOwnerObjectFromDB() throws Exception {
		// arrange
		given(mockOwnerService.findOwnerById(1)).willReturn(owner);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/owner/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.ownerName", is(owner.getOwnerName())));

		// @Formatter: on

		verify(mockOwnerService, times(1)).findOwnerById(1);

	}

	@Test
	@DisplayName("findOwnerByID--negative")
	void givenOwnerId_whenFindOwnerById_thenReturnError() throws Exception {
		// arrange
		given(mockOwnerService.findOwnerById(2)).willReturn(null);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/owner/2")).andDo(print()).andExpect(status().isNotFound());

		// @Formatter: on

		verify(mockOwnerService, times(1)).findOwnerById(2);

	}

	@Test
	public void testUpdateOwnerById() throws Exception {
	    when(mockOwnerService.updateOwnerById(eq(1), any(Owner.class))).thenReturn(true);

	    mockMvc.perform(put("/api/v1/owner/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(owner)))
	            .andExpect(status().isOk());
	}

	@Test
	public void testUpdateOwnerByIdNotFound() throws Exception {
	    when(mockOwnerService.updateOwnerById(1, owner)).thenReturn(false);

	    mockMvc.perform(put("/api/v1/owner/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(owner)))
	            .andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("saveOwner-Positive")
	public void givenOwnerObject_whenSaveOwner_thenReturnSavedOwner() throws Exception {
		// given
		given(mockOwnerService.saveOwner(ArgumentMatchers.any(Owner.class))).willReturn(owner);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/owner")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(owner)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.ownerName", is(owner.getOwnerName())));
		
		// @formatter:on

		verify(mockOwnerService, times(1)).saveOwner(ArgumentMatchers.any(Owner.class));
	}

	@Test
	@DisplayName("saveOwner-Negative")
	public void givenOwnerObject_whenSaveOwner_thenReturnError() throws Exception {
		// given
		owner.setOwnerName(null);
		given(mockOwnerService.saveOwner(ArgumentMatchers.any(Owner.class))).willReturn(owner);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/owner")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(owner)))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
		// @formatter:on

		verify(mockOwnerService, times(0)).saveOwner(ArgumentMatchers.any(Owner.class));
	}

	@AfterEach
	void tearDown() throws Exception {
		owner = null;
	}

}
