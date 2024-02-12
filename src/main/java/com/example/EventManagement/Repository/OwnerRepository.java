package com.example.EventManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EventManagement.Models.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {

}
