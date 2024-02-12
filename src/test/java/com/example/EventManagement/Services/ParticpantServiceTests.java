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

import com.example.EventManagement.Models.Participant;
import com.example.EventManagement.Repository.ParticipantRepository;

@ExtendWith(MockitoExtension.class)
class ParticpantServiceTests {

	@InjectMocks
	private ParticipantServiceImp participantServiceImp;

	@Mock
	private ParticipantRepository mockParticipantRepository;

	@Mock
	private Participant participant;

	@BeforeEach
	void setUp() throws Exception {
		participant = new Participant("JD2024");

	}

	@Test
	@DisplayName("Save Participant")
	void arrangeParticipantObject_actParticipant_assertCheckParticipantSaveInDB() {
		
		//arrange
		when(mockParticipantRepository.save(participant)).thenReturn(participant);
		//act
		Participant result = participantServiceImp.saveParticipant(participant);
		
		//assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(participant.getId());
		assertThat(result.getUserName()).isEqualTo(participant.getUserName());
		verify(mockParticipantRepository,times(1)).save(participant);

	}

	@Test
	@DisplayName("Find Participant By ID")
	void arrangeParticipantObject_actfindParticipant_assertCheckParticipantIsCorrect() {
		Optional<Participant> optionalParticipant = Optional.of(participant);
		Integer id = 1;
		// arrange
		when(mockParticipantRepository.findById(id)).thenReturn(optionalParticipant);
		// act
		Participant result = participantServiceImp.findParticipantById(id);

		// assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(participant.getId());
		assertThat(result.getUserName()).isEqualTo(participant.getUserName());
		verify(mockParticipantRepository, times(1)).findById(id);

	}

	@Test
	@DisplayName("Delete Participant By ID True")
	void arrangeParticipantObject_actDeleteParticipant_assertCheckParticipantIsDeletedReturnTrue() {

		Integer id = 1;

		// arrange
		when(mockParticipantRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = participantServiceImp.deleteParticipantById(id);

		// assert
		assertThat(result).isEqualTo(true);
		verify(mockParticipantRepository, times(1)).deleteById(id);

	}

	@Test
	@DisplayName("Delete Participant By ID False")
	void arrangeParticipantObject_actDeleteParticipant_assertCheckReturnFalse() {

		Integer id = 20;

		// arrange
		when(mockParticipantRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = participantServiceImp.deleteParticipantById(id);

		// assert
		assertThat(result).isEqualTo(false);
		verify(mockParticipantRepository, times(0)).deleteById(id);

	}

	@Test
	@DisplayName("Get all Participants")
	void arrangeParticipantList_actFindParticipant_assertGetParticipantList() {

		List<Participant> participants = new ArrayList<>();
		participants.add(participant);
		// arrange
		when(mockParticipantRepository.findAll()).thenReturn(participants);

		// act

		List<Participant> result = participantServiceImp.getAllParticipants();
		// assert
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getUserName()).isEqualTo(participant.getUserName());
		verify(mockParticipantRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Update Participant By ID True")
	void arrangeParticipantObject_actUpdateParticipant_assertCheckUpdateOccurs() {

		Integer id = 1;

		// arrange
		when(mockParticipantRepository.existsById(id)).thenReturn(true);
		// act
		boolean result = participantServiceImp.updateParticipantById(id, participant);

		// assert
		assertThat(result).isEqualTo(true);
		verify(mockParticipantRepository, times(1)).save(participant);

	}

	@Test
	@DisplayName("Update Participant By ID False")
	void arrangeParticipantObject_actUpdateParticipant_assertCheckUpdateDoesNotOccur() {

		Integer id = 1;

		// arrange
		when(mockParticipantRepository.existsById(id)).thenReturn(false);
		// act
		boolean result = participantServiceImp.updateParticipantById(id, participant);

		// assert
		assertThat(result).isEqualTo(false);
		verify(mockParticipantRepository, times(0)).save(participant);

	}

	@AfterEach
	void tearDown() throws Exception {

		participant = null;

	}

}
