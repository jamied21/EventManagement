package com.example.EventManagement.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EventManagement.Models.Participant;
import com.example.EventManagement.Repository.ParticipantRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class ParticipantServiceImp implements IParticipantService {
	@Autowired
	private ParticipantRepository participantRepository;

	public ParticipantServiceImp(ParticipantRepository participantRepository) {

		this.participantRepository = participantRepository;
	}

	public Participant saveParticipant(Participant participant) {
		return this.participantRepository.save(participant);

	}

	public List<Participant> getAllParticipants() {
		return this.participantRepository.findAll();
	}

	public Participant findParticipantById(Integer id) {
		return this.participantRepository.findById(id).orElse(null);
	}

	public boolean updateParticipantById(Integer id, Participant participant) {
		boolean result = this.participantRepository.existsById(id);

		if (result)

		{
			this.participantRepository.save(participant);
			return true;
		}

		return false;

	}

	public boolean deleteParticipantById(Integer id) {

		boolean result = this.participantRepository.existsById(id);

		if (result)

		{
			this.participantRepository.deleteById(id);
			return true;
		}

		return false;

	}
}
