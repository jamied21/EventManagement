package com.example.EventManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.EventManagement.Models.Register;
import com.example.EventManagement.Models.User;

public interface RegisterRepository extends JpaRepository<Register, Integer> {

	// List<Register> findByUsers_ID(Integer userId);
	List<Register> findByUsersId(Integer userId);

	// Custom query method to find users who attended a specific event
	@Query("SELECT r.users FROM Register r WHERE r.event.id = :eventId AND r.attended = true")
	List<User> findAttendingUsersByEventId(Integer eventId);

}
