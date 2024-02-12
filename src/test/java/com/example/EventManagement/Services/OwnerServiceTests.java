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

import com.example.EventManagement.Models.Owner;
import com.example.EventManagement.Repository.OwnerRepository;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTests {

	@InjectMocks
	private OwnerServiceImp ownerServiceImp;

	@Mock
	private OwnerRepository mockOwnerRepository;

	@Mock
	private Owner owner;

	@BeforeEach
	void setUp() throws Exception {
		owner = new Owner("Jamie");
		owner.setId(1);

	}

	@Test
	@DisplayName("Save Owner")
	void arrangeOwnerObject_actOwner_assertCheckOwnerSaveInDB() {
		
		//arrange
		when(mockOwnerRepository.save(owner)).thenReturn(owner);
		//act
		Owner result = ownerServiceImp.saveOwner(owner);
		
		//assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(owner.getId());
		assertThat(result.getOwnerName()).isEqualTo(owner.getOwnerName());
		verify(mockOwnerRepository,times(1)).save(owner);

	}

	@Test
	@DisplayName("Find Owner By ID")
	void arrangeOwnerObject_actfindOwner_assertCheckOwnerIsCorrect() {
		Optional<Owner> optionalOwner = Optional.of(owner);
		Integer id = 1;
		// arrange
		when(mockOwnerRepository.findById(id)).thenReturn(optionalOwner);
		// act
		Owner result = ownerServiceImp.findOwnerById(id);

		// assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(owner.getId());
		assertThat(result.getOwnerName()).isEqualTo(owner.getOwnerName());
		verify(mockOwnerRepository, times(1)).findById(id);

	}

	@Test
	@DisplayName("Delete Owner By ID True")
	void arrangeOwnerObject_actDeleteOwner_assertCheckOwnerIsDeletedReturnTrue() {

		Integer id = 1;

		// arrange
		when(mockOwnerRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = ownerServiceImp.deleteOwnerById(id);

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
		boolean result = ownerServiceImp.deleteOwnerById(id);

		// assert
		assertThat(result).isEqualTo(false);
		verify(mockOwnerRepository, times(0)).deleteById(id);

	}

	@Test
	@DisplayName("Get all Owners")
	void arrangeOwnerList_actFindOwner_assertGetOwnerList() {

		List<Owner> owners = new ArrayList<>();
		owners.add(owner);
		// arrange
		when(mockOwnerRepository.findAll()).thenReturn(owners);

		// act

		List<Owner> result = ownerServiceImp.getAllOwners();
		// assert
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getOwnerName()).isEqualTo(owner.getOwnerName());
		verify(mockOwnerRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Update Owner By ID True")
	void arrangeOwnerObject_actUpdateOwner_assertCheckUpdateOccurs() {

		Integer id = 1;

		// arrange
		when(mockOwnerRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = ownerServiceImp.updateOwnerById(id, owner);

		// assert
		assertThat(result).isEqualTo(true);
		verify(mockOwnerRepository, times(1)).save(owner);

	}

	@Test
	@DisplayName("Update Owner By ID False")
	void arrangeOwnerObject_actUpdateOwner_assertCheckUpdateDoesNotOccur() {

		Integer id = 1;

		// arrange
		when(mockOwnerRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = ownerServiceImp.updateOwnerById(id, owner);

		// assert
		assertThat(result).isEqualTo(false);
		verify(mockOwnerRepository, times(0)).save(owner);

	}

	@AfterEach
	void tearDown() throws Exception {

		owner = null;

	}

}
