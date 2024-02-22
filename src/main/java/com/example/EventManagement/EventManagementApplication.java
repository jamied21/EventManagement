
package com.example.EventManagement;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.EventManagement.Models.Role;
import com.example.EventManagement.Models.User;
import com.example.EventManagement.Repository.RoleRepository;
import com.example.EventManagement.Repository.UserRepository;

@SpringBootApplication
public class EventManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventManagementApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository,
			PasswordEncoder passwordEncode) {

		return args -> {
			if (roleRepository.findByAuthority("ADMIN").isPresent())
				return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User(1, "admin", passwordEncode.encode("password"), "Trainer", roles);
			userRepository.save(admin);

		};
	}

}
