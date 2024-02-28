package com.example.EventManagement.Services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.example.EventManagement.Models.User;
import com.example.EventManagement.Repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

	@InjectMocks
	private UserServiceImp userServiceImp;

	@Mock
	private UserRepository mockOwnerRepository;

	@Mock
	private User user;

	@BeforeEach
	void setUp() throws Exception {
		user = new User("Jamie", "Password", "Trainee");
		user.setId(1);

	}

	@Test
	@DisplayName("Save Owner")
	void arrangeOwnerObject_actOwner_assertCheckOwnerSaveInDB() {
		
		//arrange
		when(mockOwnerRepository.save(user)).thenReturn(user);
		//act
		User result = userServiceImp.saveOwner(user);
		
		//assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(user.getId());
		assertThat(result.getUsername()).isEqualTo(user.getUsername());
		verify(mockOwnerRepository,times(1)).save(user);

	}

	@Test
	@DisplayName("Find Owner By ID")
	void arrangeOwnerObject_actfindOwner_assertCheckOwnerIsCorrect() {
		Optional<User> optionalOwner = Optional.of(user);
		Integer id = 1;
		// arrange
		when(mockOwnerRepository.findById(id)).thenReturn(optionalOwner);
		// act
		User result = userServiceImp.findOwnerById(id);

		// assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(user.getId());
		assertThat(result.getUsername()).isEqualTo(user.getUsername());
		verify(mockOwnerRepository, times(1)).findById(id);

	}

	@Test
	@DisplayName("Delete Owner By ID True")
	void arrangeOwnerObject_actDeleteOwner_assertCheckOwnerIsDeletedReturnTrue() {

		Integer id = 1;

		// arrange
		when(mockOwnerRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = userServiceImp.deleteOwnerById(id);

		// assert
		assertThat(result).isEqualTo(true);
		verify(mockOwnerRepository, times(1)).deleteById(id);

	}

	@Test
	@DisplayName("Delete Owner By ID False")
	void arrangeOwnerObject_actDeleteOwner_assertCheckReturnFalse() {

		Integer id = 20;

		// arrange
		when(mockOwnerRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = userServiceImp.deleteOwnerById(id);

		// assert
		assertThat(result).isEqualTo(false);
		verify(mockOwnerRepository, times(0)).deleteById(id);

	}

	@Test
	@DisplayName("Get all Owners")
	void arrangeOwnerList_actFindOwner_assertGetOwnerList() {

		List<User> users = new ArrayList<>();
		users.add(user);
		// arrange
		when(mockOwnerRepository.findAll()).thenReturn(users);

		// act

		List<User> result = userServiceImp.getAllOwners();
		// assert
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getUsername()).isEqualTo(user.getUsername());
		verify(mockOwnerRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Update Owner By ID True")
	void arrangeOwnerObject_actUpdateOwner_assertCheckUpdateOccurs() {

		Integer id = 1;

		// arrange
		when(mockOwnerRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = userServiceImp.updateOwnerById(id, user);

		// assert
		assertThat(result).isEqualTo(true);
		verify(mockOwnerRepository, times(1)).save(user);

	}

	@Test
	@DisplayName("Update Owner By ID False")
	void arrangeOwnerObject_actUpdateOwner_assertCheckUpdateDoesNotOccur() {

		Integer id = 1;

		// arrange
		when(mockOwnerRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = userServiceImp.updateOwnerById(id, user);

		// assert
		assertThat(result).isEqualTo(false);
		verify(mockOwnerRepository, times(0)).save(user);

	}

	@AfterEach
	void tearDown() throws Exception {

		user = null;

	}

}
