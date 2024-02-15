package com.example.EventManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.EventManagement.Models.Feedback;
import com.example.EventManagement.Models.Register;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

	@Query("SELECT r FROM Register r JOIN FETCH r.event WHERE r.id = :registerId")
	Register findRegisterById(@Param("registerId") Integer registerId);

}
