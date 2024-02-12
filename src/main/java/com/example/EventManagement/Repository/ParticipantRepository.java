package com.example.EventManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EventManagement.Models.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

}
