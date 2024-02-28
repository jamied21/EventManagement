package com.example.EventManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EventManagement.Models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findUserByUsernameAndPassword(String username, String password);

	public User findUserByUsername(String username);
}
