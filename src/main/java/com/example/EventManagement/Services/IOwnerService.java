package com.example.EventManagement.Services;

import java.util.List;

import com.example.EventManagement.Models.Owner;

public interface IOwnerService {

	Owner saveOwner(Owner owner);

	List<Owner> getAllOwners();

	Owner findOwnerById(Integer id);

	boolean updateOwnerById(Integer id, Owner owner);

	boolean deleteOwnerById(Integer id);

}
