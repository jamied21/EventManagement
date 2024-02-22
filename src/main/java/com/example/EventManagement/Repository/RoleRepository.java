package com.example.EventManagement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EventManagement.Models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByAuthority(String authority);
}
