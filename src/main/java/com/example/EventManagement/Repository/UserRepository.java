package com.example.EventManagement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EventManagement.Models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	// User findByUsername(String username);
	Optional<User> findByUsername(String usernmae);
}
