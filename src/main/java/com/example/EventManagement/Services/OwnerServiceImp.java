package com.example.EventManagement.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EventManagement.Models.Owner;
import com.example.EventManagement.Repository.OwnerRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class OwnerServiceImp implements IOwnerService {

	@Autowired
	private OwnerRepository ownerRepository;

	public OwnerServiceImp(OwnerRepository ownerRepository) {

		this.ownerRepository = ownerRepository;
	}

	public Owner saveOwner(Owner owner) {
		return this.ownerRepository.save(owner);

	}

	public List<Owner> getAllOwners() {
		return this.ownerRepository.findAll();
	}

	public Owner findOwnerById(Integer id) {
		return this.ownerRepository.findById(id).orElse(null);
	}

	public boolean updateOwnerById(Integer id, Owner owner) {
		boolean result = this.ownerRepository.existsById(id);

		if (result)

		{
			this.ownerRepository.save(owner);
			return true;
		}

		return false;

	}

	public boolean deleteOwnerById(Integer id) {

		boolean result = this.ownerRepository.existsById(id);

		if (result)

		{
			this.ownerRepository.deleteById(id);
			return true;
		}

		return false;

	}
}
