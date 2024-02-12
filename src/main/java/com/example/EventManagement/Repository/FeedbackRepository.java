package com.example.EventManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EventManagement.Models.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

}
