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

import com.example.EventManagement.Models.Register;
import com.example.EventManagement.Repository.RegisterRepository;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTests {

	@InjectMocks
	private RegisterServiceImp registerServiceImp;

	@Mock
	private RegisterRepository mockRegisterRepository;

	@Mock
	private Register register;

	@BeforeEach
	void setUp() throws Exception {
		register = new Register(true, LocalDateTime.of(2024, 2, 21, 15, 30), LocalDateTime.of(2025, 2, 21, 15, 30));

	}

	@Test
	@DisplayName("Save Register")
	void arrangeRegisterObject_actRegister_assertCheckRegisterSaveInDB() {
		
		//arrange
		when(mockRegisterRepository.save(register)).thenReturn(register);
		//act
		Register result = registerServiceImp.saveRegister(register);
		
		//assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(register.getId());
		assertThat(result.getAttended()).isEqualTo(register.getAttended());
		verify(mockRegisterRepository,times(1)).save(register);

	}

	@Test
	@DisplayName("Find Register By ID")
	void arrangeRegisterObject_actfindRegister_assertCheckRegisterIsCorrect() {
		Optional<Register> optionalRegister = Optional.of(register);
		Integer id = 1;
		// arrange
		when(mockRegisterRepository.findById(id)).thenReturn(optionalRegister);
		// act
		Register result = registerServiceImp.findRegisterById(id);

		// assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(register.getId());
		assertThat(result.getAttended()).isEqualTo(register.getAttended());
		verify(mockRegisterRepository, times(1)).findById(id);

	}

	@Test
	@DisplayName("Delete Register By ID True")
	void arrangeRegisterObject_actDeleteRegister_assertCheckRegisterIsDeletedReturnTrue() {

		Integer id = 1;

		// arrange
		when(mockRegisterRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = registerServiceImp.deleteRegisterById(id);

		// assert
		assertThat(result).isEqualTo(true);
		verify(mockRegisterRepository, times(1)).deleteById(id);

	}

	@Test
	@DisplayName("Delete Register By ID False")
	void arrangeRegisterObject_actDeleteRegister_assertCheckReturnFalse() {

		Integer id = 20;

		// arrange
		when(mockRegisterRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = registerServiceImp.deleteRegisterById(id);

		// assert
		assertThat(result).isEqualTo(false);
		verify(mockRegisterRepository, times(0)).deleteById(id);

	}

	@Test
	@DisplayName("Get all Registers")
	void arrangeRegisterList_actFindRegister_assertGetRegisterList() {

		List<Register> registers = new ArrayList<>();
		registers.add(register);
		// arrange
		when(mockRegisterRepository.findAll()).thenReturn(registers);

		// act

		List<Register> result = registerServiceImp.getAllRegisters();
		// assert
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getAttended()).isEqualTo(register.getAttended());
		verify(mockRegisterRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Update Register By ID True")
	void arrangeRegisterObject_actUpdateRegister_assertCheckUpdateOccurs() {

		Integer id = 1;

		// arrange
		when(mockRegisterRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = registerServiceImp.updateRegisterById(id, register);

		// assert
		assertThat(result).isEqualTo(true);
		verify(mockRegisterRepository, times(1)).save(register);

	}

	@Test
	@DisplayName("Update Register By ID False")
	void arrangeRegisterObject_actUpdateRegister_assertCheckUpdateDoesNotOccur() {

		Integer id = 1;

		// arrange
		when(mockRegisterRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = registerServiceImp.updateRegisterById(id, register);

		// assert
		assertThat(result).isEqualTo(false);
		verify(mockRegisterRepository, times(0)).save(register);

	}

	@AfterEach
	void tearDown() throws Exception {

		register = null;

	}
}
