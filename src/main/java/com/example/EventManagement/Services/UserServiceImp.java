package com.example.EventManagement.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EventManagement.Models.User;
import com.example.EventManagement.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class UserServiceImp implements IUserService {

	@Autowired
	private UserRepository userRepository;

	public UserServiceImp(UserRepository userRepository) {

		this.userRepository = userRepository;
	}

	public User saveOwner(User user) {
		return this.userRepository.save(user);

	}

	public List<User> getAllOwners() {
		return this.userRepository.findAll();
	}

	public User findOwnerById(Integer id) {
		return this.userRepository.findById(id).orElse(null);
	}

	public boolean updateOwnerById(Integer id, User user) {
		boolean result = this.userRepository.existsById(id);

		if (result)

		{
			this.userRepository.save(user);
			return true;
		}

		return false;

	}

	public boolean deleteOwnerById(Integer id) {

		boolean result = this.userRepository.existsById(id);

		if (result)

		{
			this.userRepository.deleteById(id);
			return true;
		}

		return false;

	}
}
