package com.example.EventManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EventManagement.Models.Register;

public interface RegisterRepository extends JpaRepository<Register, Integer> {

}
