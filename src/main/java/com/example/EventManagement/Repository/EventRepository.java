package com.example.EventManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EventManagement.Models.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
