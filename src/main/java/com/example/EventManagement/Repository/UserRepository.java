package com.example.EventManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EventManagement.Models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
