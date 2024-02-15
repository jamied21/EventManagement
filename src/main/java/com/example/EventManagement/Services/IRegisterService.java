package com.example.EventManagement.Services;

import java.util.List;

import com.example.EventManagement.Models.Register;

public interface IRegisterService {

	Register saveRegister(Register register);

	List<Register> getAllRegisters();

	Register findRegisterById(Integer id);

	boolean updateRegisterById(Integer id, Register register);

	boolean deleteRegisterById(Integer id);

	List<Register> findRegistrationsByUserId(Integer userId);

}
