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

import com.example.EventManagement.Models.User;
import com.example.EventManagement.Services.UserServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper; // needed to convert to JSON object

	private User user;

	@MockBean
	private UserServiceImp mockOwnerService;

	@BeforeEach
	void setUp() throws Exception {
		user = new User("Jamie", "Trainee");
		user.setUsername("Bob");
		user.setId(1);
	}

	@Test
	@DisplayName("FindAllOwners")
	public void givenNothing_whenFindAllOwner_thenReturnAllSavedOwner() throws Exception {
		List<User> users = List.of(user);
		when(mockOwnerService.getAllOwners()).thenReturn(users);

		mockMvc.perform(get("/api/v1/user")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].username", is(user.getUsername())));

		verify(mockOwnerService, times(1)).getAllOwners();

	}

	@Test
	@DisplayName("Delete owner - positive")
	public void givenOwnerId_whenDeleteOwnerById_thenReturnGone() throws Exception {
		// given
		Integer id = 1;
		given(mockOwnerService.deleteOwnerById(id)).willReturn(true);

		// when-then
		mockMvc.perform(delete("/api/v1/user/1")).andDo(print()).andExpect(status().isOk());

		verify(mockOwnerService, times(1)).deleteOwnerById(id);
	}

	@Test
	@DisplayName("Delete owner - negative")
	public void givenNonExistentOwnerId_whenDeleteOwnerById_thenReturnNotFound() throws Exception {
		// given
		Integer id = 2;
		given(mockOwnerService.deleteOwnerById(id)).willReturn(false);

		// when-then
		mockMvc.perform(delete("/api/v1/user/2")).andDo(print()).andExpect(status().isNotFound());

		verify(mockOwnerService, times(1)).deleteOwnerById(id);

	}

	@Test
	@DisplayName("findOwnerByID--positive")
	void givenOwnerId_whenFindOwnerById_thenReturnOwnerObjectFromDB() throws Exception {
		// arrange
		given(mockOwnerService.findOwnerById(1)).willReturn(user);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/user/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.username", is(user.getUsername())));

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
		mockMvc.perform(get("/api/v1/user/2")).andDo(print()).andExpect(status().isNotFound());

		// @Formatter: on

		verify(mockOwnerService, times(1)).findOwnerById(2);

	}

	@Test
	public void testUpdateOwnerById() throws Exception {
	    when(mockOwnerService.updateOwnerById(eq(1), any(User.class))).thenReturn(true);

	    mockMvc.perform(put("/api/v1/user/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(user)))
	            .andExpect(status().isOk());
	}

	@Test
	public void testUpdateOwnerByIdNotFound() throws Exception {
	    when(mockOwnerService.updateOwnerById(1, user)).thenReturn(false);

	    mockMvc.perform(put("/api/v1/user/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(user)))
	            .andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("saveOwner-Positive")
	public void givenOwnerObject_whenSaveOwner_thenReturnSavedOwner() throws Exception {
		// given
		given(mockOwnerService.saveOwner(ArgumentMatchers.any(User.class))).willReturn(user);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.username", is(user.getUsername())));
		
		// @formatter:on

		verify(mockOwnerService, times(1)).saveOwner(ArgumentMatchers.any(User.class));
	}

	@Test
	@DisplayName("saveOwner-Negative")
	public void givenOwnerObject_whenSaveOwner_thenReturnError() throws Exception {
		// given
		user.setUsername(null);
		given(mockOwnerService.saveOwner(ArgumentMatchers.any(User.class))).willReturn(user);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
		// @formatter:on

		verify(mockOwnerService, times(0)).saveOwner(ArgumentMatchers.any(User.class));
	}

	@AfterEach
	void tearDown() throws Exception {
		user = null;
	}

}
