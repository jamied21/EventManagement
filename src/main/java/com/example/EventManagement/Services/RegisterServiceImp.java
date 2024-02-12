package com.example.EventManagement.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EventManagement.Models.Register;
import com.example.EventManagement.Repository.RegisterRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class RegisterServiceImp implements IRegisterService {

	@Autowired
	private RegisterRepository registerRepository;

	public RegisterServiceImp(RegisterRepository registerRepository) {

		this.registerRepository = registerRepository;
	}

	public Register saveRegister(Register register) {
		return this.registerRepository.save(register);

	}

	public List<Register> getAllRegisters() {
		return this.registerRepository.findAll();
	}

	public Register findRegisterById(Integer id) {
		return this.registerRepository.findById(id).orElse(null);
	}

	public boolean updateRegisterById(Integer id, Register register) {
		boolean result = this.registerRepository.existsById(id);

		if (result)

		{
			this.registerRepository.save(register);
			return true;
		}

		return false;

	}

	public boolean deleteRegisterById(Integer id) {

		boolean result = this.registerRepository.existsById(id);

		if (result)

		{
			this.registerRepository.deleteById(id);
			return true;
		}

		return false;

	}

}
