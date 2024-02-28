
package com.example.EventManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventManagementApplication.class, args);
	}

	/*
	 * @Bean CommandLineRunner run(RoleRepository roleRepository, UserRepository
	 * userRepository, PasswordEncoder passwordEncode) {
	 * 
	 * return args -> { if (roleRepository.findByAuthority("ADMIN").isPresent())
	 * return; Role adminRole = roleRepository.save(new Role("ADMIN"));
	 * roleRepository.save(new Role("USER"));
	 * 
	 * Set<Role> roles = new HashSet<>(); roles.add(adminRole);
	 * 
	 * User admin = new User(1, "admin", passwordEncode.encode("password"),
	 * "Trainer", roles); userRepository.save(admin);
	 * 
	 * }; }
	 */

}
