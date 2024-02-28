package com.example.EventManagement.Services;

import java.util.List;

import com.example.EventManagement.Models.User;

public interface IUserService {

	User saveOwner(User user);

	List<User> getAllOwners();

	User findOwnerById(Integer id);

	boolean updateOwnerById(Integer id, User user);

	boolean deleteOwnerById(Integer id);

	User findByUsername(String username);

}
