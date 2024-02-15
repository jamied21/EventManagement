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

import com.example.EventManagement.Models.Register;
import com.example.EventManagement.Services.RegisterServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RegisterController.class)
class RegisterControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper; // needed to convert to JSON object

	private Register register;

	@MockBean
	private RegisterServiceImp mockRegisterService;

	@BeforeEach
	void setUp() throws Exception {
		register = new Register(true, LocalDateTime.of(2024, 2, 21, 15, 30), LocalDateTime.of(2025, 2, 21, 15, 30));
		register.setId(1);
	}

	@Test
	@DisplayName("FindAllRegisters")
	public void givenNothing_whenFindAllRegister_thenReturnAllSavedRegister() throws Exception {
		List<Register> registers = List.of(register);
		when(mockRegisterService.getAllRegisters()).thenReturn(registers);

		mockMvc.perform(get("/api/v1/register")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].attended", is(register.getAttended())));

		verify(mockRegisterService, times(1)).getAllRegisters();

	}

	@Test
	@DisplayName("Delete register - positive")
	public void givenRegisterId_whenDeleteRegisterById_thenReturnGone() throws Exception {
		// given
		Integer id = 1;
		given(mockRegisterService.deleteRegisterById(id)).willReturn(true);

		// when-then
		mockMvc.perform(delete("/api/v1/register/1")).andDo(print()).andExpect(status().isOk());

		verify(mockRegisterService, times(1)).deleteRegisterById(id);
	}

	@Test
	@DisplayName("Delete register - negative")
	public void givenNonExistentRegisterId_whenDeleteRegisterById_thenReturnNotFound() throws Exception {
		// given
		Integer id = 2;
		given(mockRegisterService.deleteRegisterById(id)).willReturn(false);

		// when-then
		mockMvc.perform(delete("/api/v1/register/2")).andDo(print()).andExpect(status().isNotFound());

		verify(mockRegisterService, times(1)).deleteRegisterById(id);

	}

	@Test
	@DisplayName("findRegisterByID--positive")
	void givenRegisterId_whenFindRegisterById_thenReturnRegisterObjectFromDB() throws Exception {
		// arrange
		given(mockRegisterService.findRegisterById(1)).willReturn(register);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/register/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.attended", is(register.getAttended())));

		// @Formatter: on

		verify(mockRegisterService, times(1)).findRegisterById(1);

	}

	@Test
	@DisplayName("findRegisterByID--negative")
	void givenRegisterId_whenFindRegisterById_thenReturnError() throws Exception {
		// arrange
		given(mockRegisterService.findRegisterById(2)).willReturn(null);
		// act-assert

		// @Formatter: off
		mockMvc.perform(get("/api/v1/register/2")).andDo(print()).andExpect(status().isNotFound());

		// @Formatter: on

		verify(mockRegisterService, times(1)).findRegisterById(2);

	}

	@Test
	public void testUpdateRegisterById() throws Exception {
	    when(mockRegisterService.updateRegisterById(eq(1), any(Register.class))).thenReturn(true);

	    mockMvc.perform(put("/api/v1/register/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(register)))
	            .andExpect(status().isOk());
	}

	@Test
	public void testUpdateRegisterByIdNotFound() throws Exception {
	    when(mockRegisterService.updateRegisterById(1, register)).thenReturn(false);

	    mockMvc.perform(put("/api/v1/register/{id}", 1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(register)))
	            .andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("saveRegister-Positive")
	public void givenRegisterObject_whenSaveRegister_thenReturnSavedRegister() throws Exception {
		// given
		given(mockRegisterService.saveRegister(ArgumentMatchers.any(Register.class))).willReturn(register);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(register)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.attended", is(register.getAttended())));
		
		// @formatter:on

		verify(mockRegisterService, times(1)).saveRegister(ArgumentMatchers.any(Register.class));
	}

	@Test
	@DisplayName("saveRegister-Negative")
	public void givenRegisterObject_whenSaveRegister_thenReturnError() throws Exception {
		// given
		register.setAttended(null);
		given(mockRegisterService.saveRegister(ArgumentMatchers.any(Register.class))).willReturn(register);

		// when & then

		// @formatter:off

		mockMvc.perform(post("/api/v1/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(register)))
				.andDo(print())
				.andExpect(status().isBadRequest());
		
		// @formatter:on

		verify(mockRegisterService, times(0)).saveRegister(ArgumentMatchers.any(Register.class));
	}

	@AfterEach
	void tearDown() throws Exception {
		register = null;
	}

}
