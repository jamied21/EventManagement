package com.example.EventManagement.Services;

import java.util.List;

import com.example.EventManagement.Models.Participant;

public interface IParticipantService {

	Participant saveParticipant(Participant participant);

	List<Participant> getAllParticipants();

	Participant findParticipantById(Integer id);

	boolean updateParticipantById(Integer id, Participant participant);

	boolean deleteParticipantById(Integer id);
}
