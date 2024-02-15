package com.example.EventManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EventManagement.Models.Register;

public interface RegisterRepository extends JpaRepository<Register, Integer> {

	// List<Register> findByUsers_ID(Integer userId);
	List<Register> findByUsersId(Integer userId);
}
