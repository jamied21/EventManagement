package com.example.EventManagement.Services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.EventManagement.Models.Event;
import com.example.EventManagement.Models.Register;
import com.example.EventManagement.Models.User;
import com.example.EventManagement.Repository.RegisterRepository;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTests {

	@InjectMocks
	private RegisterServiceImp registerServiceImp;

	@InjectMocks
	private UserServiceImp userServiceImp;

	@InjectMocks
	private EventServiceImp eventServiceImp;

	@Mock
	private RegisterRepository mockRegisterRepository;

	@Mock
	private Register register;

	@Mock
	private User user1;
	private User user2;

	@Mock
	private Event event;

	@BeforeEach
	void setUp() throws Exception {
		register = new Register(true, LocalDateTime.of(2024, 2, 21, 15, 30), LocalDateTime.of(2025, 2, 21, 15, 30));

		user1 = new User("John01", "Trainee");
		user1.setId(1);

		user2 = new User("Jamie98", "Trainee");
		user2.setId(2);

		event = new Event("TestEvent", LocalDateTime.of(2025, 2, 21, 15, 30), "London");
		event.setId(1);

		register.setUsers(user1);
		register.setEvent(event);

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

	@Test
	@DisplayName("Find Events by User ID that the user has registered for")
	void testFindRegistrationsByUserId() {
		Integer userId = 1;
		List<Register> expectedRegisters = Arrays.asList(register);
		when(mockRegisterRepository.findByUsersId(userId)).thenReturn(expectedRegisters);

		List<Register> result = registerServiceImp.findRegistrationsByUserId(userId);

		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getAttended()).isEqualTo(register.getAttended());
		verify(mockRegisterRepository, times(1)).findByUsersId(userId);
	}

	@Test
	@DisplayName("Find Users who attened one sepcific event by event Id")
	void testGetAttendingUsersForEvent() {
		Integer eventId = 1;
		List<User> expectedUsers = Arrays.asList(user1);
		Mockito.when(mockRegisterRepository.findAttendingUsersByEventId(eventId)).thenReturn(expectedUsers);

		List<User> result = registerServiceImp.getAttendingUsersForEvent(eventId);

		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
		verify(mockRegisterRepository, times(1)).findAttendingUsersByEventId(eventId);
	}

	@Test
	@DisplayName("Find Users who have not attended one sepcific event by event Id")
	void testGetNonAttendingUsersForEvent() {
		Integer eventId = 1;
		List<User> expectedUsers = Arrays.asList();
		Mockito.when(mockRegisterRepository.findNonAttendingUsersByEventId(eventId)).thenReturn(expectedUsers);

		List<User> result = registerServiceImp.getNotAttendingUserForEvent(eventId);

		assertThat(result.size()).isEqualTo(0);
		verify(mockRegisterRepository, times(1)).findNonAttendingUsersByEventId(eventId);
	}
	/*
	 * @Test
	 * 
	 * @DisplayName("Add users by Id to an event id") void
	 * testRegisterUsersToEvent() { Integer eventId = 1; Set<Integer> userIds =
	 * Set.of(1, 2);
	 * 
	 * Event event = new Event(); // Create a sample event for testing
	 * 
	 * Mockito.when(eventServiceImp.findEventById(eventId)).thenReturn(event);
	 * Mockito.when(userServiceImp.findOwnerById(any(Integer.class))).thenReturn(new
	 * User());
	 * 
	 * Register expectedRegister1 = new Register(); Register expectedRegister2 = new
	 * Register();
	 * 
	 * when(mockRegisterRepository.save(any(Register.class))).thenReturn(
	 * expectedRegister1, expectedRegister2);
	 * 
	 * List<Register> result = registerServiceImp.registerUsersToEvent(eventId,
	 * userIds);
	 * 
	 * assertThat(result).isNotNull(); assertThat(result.size()).isEqualTo(2);
	 * assertEquals(expectedRegister1, result.get(0));
	 * assertEquals(expectedRegister2, result.get(1)); //
	 * verify(mockRegisterRepository, times(1)).save(expectedRegister1); //
	 * verify(mockRegisterRepository, times(1)).save(expectedRegister2);
	 * 
	 * // You can add more assertions based on your specific requirements }
	 */

	@AfterEach
	void tearDown() throws Exception {

		register = null;
		user1 = null;
		user2 = null;
		event = null;
	}
}
