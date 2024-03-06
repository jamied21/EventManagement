package com.example.EventManagement.Services;

import java.util.List;
import java.util.Set;

import com.example.EventManagement.Models.Register;
import com.example.EventManagement.Models.User;

public interface IRegisterService {

	Register saveRegister(Register register);

	List<Register> getAllRegisters();

	Register findRegisterById(Integer id);

	boolean updateRegisterById(Integer id, Register register);

	boolean deleteRegisterById(Integer id);

	List<Register> findRegistrationsByUserId(Integer userId);

	List<User> getAttendingUsersForEvent(Integer eventId);

	List<User> getNotAttendingUserForEvent(Integer eventId);

	List<Register> registerUsersToEvent(Integer eventId, Set<Integer> userIds);

}
